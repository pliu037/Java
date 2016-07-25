package week5;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.tree.RandomForest;

import java.util.HashMap;

public final class RandomForestMP {
    private static final String SEPARATOR = ",";

    private static class parseLine implements Function<String, LabeledPoint> {
        public LabeledPoint call(String line) {
            String[] tokens = line.split(SEPARATOR);
            double label = Double.parseDouble(tokens[tokens.length - 1]);
            double[] values = new double[tokens.length - 1];
            for (int i = 0; i < tokens.length - 1; i ++) {
                values[i] = Double.parseDouble(tokens[i]);
            }
            return new LabeledPoint(label, Vectors.dense(values));
        }
    }

    private static class parseLine2 implements Function<String, Vector> {
        public Vector call(String line) {
            String[] tokens = line.split(SEPARATOR);
            double[] values = new double[tokens.length];
            for (int i = 0; i < tokens.length; i ++) {
                values[i] = Double.parseDouble(tokens[i]);
            }
            return Vectors.dense(values);
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println(
                    "Usage: RandomForestMP <training_data> <test_data> <results>");
            System.exit(1);
        }
        String training_data_path = args[0];
        String test_data_path = args[1];
        String results_path = args[2];

        SparkConf sparkConf = new SparkConf().setAppName("RandomForestMP");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        final RandomForestModel model;

        Integer numClasses = 2;
        HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
        Integer numTrees = 3;
        String featureSubsetStrategy = "auto";
        String impurity = "gini";
        Integer maxDepth = 5;
        Integer maxBins = 32;
        Integer seed = 12345;

        // TODO
        JavaRDD<String> data = sc.textFile(training_data_path);
        JavaRDD<LabeledPoint> labeledPoints = data.map(new parseLine());

        model = RandomForest.trainClassifier(labeledPoints, numClasses, categoricalFeaturesInfo, numTrees, featureSubsetStrategy,
                impurity, maxDepth, maxBins, seed);

        JavaRDD<String> testData = sc.textFile(test_data_path);
        JavaRDD<Vector> test = testData.map(new parseLine2());

        JavaRDD<LabeledPoint> results = test.map(new Function<Vector, LabeledPoint>() {
            public LabeledPoint call(Vector points) {
                return new LabeledPoint(model.predict(points), points);
            }
        });

        results.saveAsTextFile(results_path);
        sc.stop();
    }

}
