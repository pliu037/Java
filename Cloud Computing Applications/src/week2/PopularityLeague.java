import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PopularityLeague extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new PopularityLeague(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        // TODO
        Configuration conf = this.getConf();

        Job job = Job.getInstance(conf, "Popularity League");
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(LinkCountMap.class);
        job.setReducerClass(LinkCountReduce.class);
        job.setNumReduceTasks(1);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setJarByClass(PopularityLeague.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    // TODO
    public static class LinkCountMap extends Mapper<Object, Text, IntWritable, IntWritable> {
        HashSet<Integer> league = new HashSet<>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            Configuration conf = context.getConfiguration();
            String path = conf.get("league");

            String[] members = readHDFSFile(path, conf).split("\n");
            for (String member : members) {
                league.add(Integer.parseInt(member));
            }
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer st = new StringTokenizer(line, ":");
            Integer src = Integer.parseInt(st.nextToken());
            if (league.contains(src)) {
                context.write(new IntWritable(src), new IntWritable(0));
            }
            line = st.nextToken();
            st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                Integer dest = Integer.parseInt(st.nextToken());
                if (league.contains(dest)) {
                    context.write(new IntWritable(dest), new IntWritable(1));
                }
            }
        }
    }

    public static class LinkCountReduce extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();

        @Override
        public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            list.add(new Pair<>(key.get(), sum));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Pair<Integer, Integer> pair : list) {
                int count = 0;
                for (Pair<Integer, Integer> pair2 : list) {
                    if (pair.compareTo(pair2) > 0) {
                        count++;
                    }
                }
                context.write(new IntWritable(pair.first), new IntWritable(count));
            }
        }
    }

    public static class Pair<A, B extends Comparable<B>> implements Comparable<Pair<A, B>> {

        public final A first;
        public final B second;

        public Pair(A a, B b) {
            first = a;
            second = b;
        }

        public int compareTo(Pair<A, B> other) {
            return (other == null ? 1 : (this.second).compareTo(other.second));
        }
    }

    public static String readHDFSFile(String path, Configuration conf) throws IOException {
        Path pt = new Path(path);
        FileSystem fs = FileSystem.get(pt.toUri(), conf);
        FSDataInputStream file = fs.open(pt);
        BufferedReader buffIn = new BufferedReader(new InputStreamReader(file));

        StringBuilder everything = new StringBuilder();
        String line;
        while ((line = buffIn.readLine()) != null) {
            everything.append(line);
            everything.append("\n");
        }
        return everything.toString();
    }
}

