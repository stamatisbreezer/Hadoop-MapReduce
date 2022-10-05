import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.HadoopIllegalArgumentException;
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

public class DNA {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {

            String line=value.toString();
            for(int i=0;i<line.length()-1;i++) {
                try {
                    word.set(line.substring(i,i+2));
                    context.write(word, one);
                    word.set(line.substring(i,i+3));
                    context.write(word, one);
                    word.set(line.substring(i,i+4));
                    context.write(word, one);
                }
             catch (Exception e) {
                //do nothing
             }
            }

/*
            String[] DNA2=value.toString().split("(?<=\\G.{2})");
            String[] DNA3=value.toString().split("(?<=\\G.{3})");
            String[] DNA4=value.toString().split("(?<=\\G.{4})");
            HashMap<String,Integer> Dna = new HashMap();

            for(int i=0; i<DNA2.length;i++){
                if (DNA2[i].length()==2) word.set(DNA2[i]);
                context.write(word, one);
            }
            for(int i=0; i<DNA3.length;i++){
                if (DNA3[i].length()==3) word.set(DNA3[i]);
                context.write(word, one);
            }
            for(int i=0; i<DNA4.length;i++){
                if (DNA4[i].length()==4) word.set(DNA4[i]);
                context.write(word, one);
            }

 */
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
        Job job = Job.getInstance(conf, "Dna");
        job.setJarByClass(DNA.class);
        job.setMapperClass(DNA.TokenizerMapper.class);
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
