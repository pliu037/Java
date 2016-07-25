package week5;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import scala.Tuple2;

public final class KMeansMP {
    private static final String SEPARATOR = ",";

    // TODO
    private static class parseLine implements Function<String, String> {
        public String call(String line) {
            String[] tokens = line.split(SEPARATOR);
            return tokens[0];
        }
    }

    private static class parsePoint implements Function<String, Vector> {
        public Vector call(String line) {
            String[] tokens = line.split(SEPARATOR);
            double[] values = new double[tokens.length - 1];
            for (int i = 1; i < tokens.length; i ++) {
                values[i - 1] = Double.parseDouble(tokens[i]);
            }
            return Vectors.dense(values);
        }
    }

    private static class ClusterCars implements PairFunction<Tuple2<String, Vector>, Integer, String> {
        private KMeansModel model;
        public ClusterCars(KMeansModel model) {
            this.model = model;
        }
        public Tuple2<Integer, String> call(Tuple2<String, Vector> args) {
            String title = args._1();
            Vector point = args._2();
            int cluster = model.predict(point);
            return new Tuple2<Integer, String>(cluster, title);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println(
                    "Usage: KMeansMP <input_file> <results>");
            System.exit(1);
        }
        String inputFile = args[0];
        String results_path = args[1];
        int k = 4;
        int iterations = 100;
        int runs = 1;
        final KMeansModel model;

        SparkConf sparkConf = new SparkConf().setAppName("KMeans MP");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        //TODO
        JavaRDD<String> data = sc.textFile(inputFile);
        JavaRDD<Vector> points = data.map(new parsePoint());
        JavaRDD<String> titles = data.map(new parseLine());

        model = KMeans.train(points.rdd(), k, iterations, runs, KMeans.RANDOM(), 0);
        JavaPairRDD<Integer, Iterable<String>> clusters = titles.zip(points).mapToPair(new ClusterCars(model)).groupByKey();

        clusters.saveAsTextFile(results_path);
        sc.stop();
    }
}