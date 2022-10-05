import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Numeronyms {
    static int posotita;

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());

            while (itr.hasMoreTokens()) {
                String str = itr.nextToken();
                str = str.toLowerCase().trim().replaceAll("[,*-_=?\'#\"():~!.1234567890]", "");  //Αφαιρεση χαρακτηρων και πεζα

                if (str.length() > 2) {   //Μεταβολη μεγεθους string προς καταγραφη
                    str = str.substring(0, 1) + String.valueOf(str.length() - 2) + str.substring(str.length() - 1);  //κατασκευη numeronyms
                    word.set(str);
                    context.write(word, one);
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
            if (sum >= posotita) {
                result.set(sum);
                context.write(key, result);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {  //Παραμετρικα μπορει να δωθει το πληθος εμφανισεων που επιθυμουμε
            posotita=Integer.parseInt(args[0]);
        }
        catch (Exception e) {
            posotita=10;
        }
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Numeronyms");
        job.setJarByClass(Numeronyms.class);
        job.setMapperClass(TokenizerMapper.class);
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