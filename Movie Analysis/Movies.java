import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Movies {


    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        private final IntWritable runtimeCount = new IntWritable();
        private final static Text Country = new Text("records");
        private final static Text Year_Cat = new Text("yearcategory");

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String str = value.toString();
            String[] tokens = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);  // Να χωρισουν στο κομμα εκτος και αν ειναι εντος ""

            //διαχωρισμων των χωρων
            tokens[8] = tokens[8].replaceAll("\"", "");
            String[] countries = tokens[8].toUpperCase().split(", ");

            //διαχωρισμων για την κατηγορια των ταινιων
            tokens[4] = tokens[4].replaceAll("\"", "");
            String[] gentres = tokens[4].toUpperCase().split(", ");

            double rating=0;
            int year=0;
            if (tokens.length == 9) { // Η γραμμη εχει το σωστο αριθμο στηλων
                try {
                    year = Integer.parseInt(tokens[2]);
                    rating = Double.parseDouble(tokens[6]);
                    int runtime = Integer.parseInt(tokens[3].replace(" min",""));  //Και η πρωτη γραμμη θα παραλειφθει
                    for (int i=0; i<countries.length; i++){
                        runtimeCount.set(runtime);
                        Country.set(countries[i]);
                        context.write(Country, runtimeCount);
                    }
                } catch (NumberFormatException ex) {
                }

                String yearcateg=new String();
                if (rating >8) {
                    for (int i=0; i<gentres.length; i++) {
                        yearcateg=String.valueOf(year)+"_"+gentres[i];
                        Year_Cat.set(yearcateg);
                        context.write(Year_Cat, one);
                    }
                }
            }
        }
    }

        public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
            }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MoviesIMDB");
        job.setJarByClass(Movies.class);
        job.setMapperClass(Movies.TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path("input"));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path("output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}