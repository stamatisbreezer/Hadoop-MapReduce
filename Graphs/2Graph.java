import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Graph {
    private final static String TMP_PATH = "tmp-out";
    private final static String MEAN = "mean";
    private static double mesos=0;


    public static class TokenizerMapper extends Mapper<Object, Text, Text, DoubleWritable> {
        private static DoubleWritable akmi = new DoubleWritable();
        private Text word = new Text();
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().split(" ");  //απο την γραμμογραφηση φορτωνω το αρχειο με διαχωριστικο το κενο

            try {
                akmi.set(Double.parseDouble(tokens[2])); //φορτωνω την πιθανοτητα των ακμων
                word.set(tokens[0]);
                context.write(word, akmi);           //καταγραφω την πιθανοτητα της α κορυφης
                word.set(tokens[1]);
                context.write(word, akmi);           //καταγραφω την πιθανοτητα της β κορυφης
                //System.out.println("Mapper1: "+tokens[0]+" "+akmi);
            } catch (Exception ex) {
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
        private DoubleWritable possibility = new DoubleWritable();
        private int plithos=0;
        private double synolo=0;

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

            Iterator<DoubleWritable> iter = values.iterator();
            Configuration conf = context.getConfiguration();

            int T = Integer.parseInt(conf.get("T")); // φορτωση της παραμετρου ελαχιστης τιμης - κατωφλι
            double sum=0;
            while (iter.hasNext()) {
                double d = iter.next().get();
                sum+=d;
            }
            possibility.set(sum);
            if (sum>=T) context.write(key, possibility); //Κοψε οσα ειναι κατω απο το οριο Τ
            plithos++;
            synolo+=sum;  //μετραω για τον μεσο ορο
            mesos=synolo/plithos;
            //System.out.println("Reduce1: "+key+"-Αθροισμα: "+possibility+" - Μεσος ορος: "+mesos);
        }
    }


    public static class AboveMeanMapper extends Mapper<Object, Text, Text, DoubleWritable> {
        private static DoubleWritable timi = new DoubleWritable();
        private Text word = new Text();
        private double possibility=0;
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            //Configuration conf = context.getConfiguration();
            String[] tokens = value.toString().split(" ");  //απο την γραμμογραφηση φορτωνω το αρχειο με διαχωριστικο το κενο

            try {
                possibility=Double.valueOf(tokens[1]);
                if (possibility>mesos) {
                    word.set(tokens[0]);
                    timi.set(possibility); //φορτωνω την πιθανοτητα των ακμων
                    context.write(word, timi);           //καταγραφω την πιθανοτητα της α κορυφης\
                    //System.out.println("AboveMeanMapper: "+tokens[0]+"-Τιμη: "+tokens[1]);
                }

            } catch (Exception ex) {
            }
        }
    }


    public static class AboveMeanReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
        private DoubleWritable possibility = new DoubleWritable();
        private int plithos=0;
        private double synolo=0;


        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

            Iterator<DoubleWritable> iter = values.iterator();
            Configuration conf = context.getConfiguration();
            int T = Integer.parseInt(conf.get("T")); // φορτωση της παραμετρου ελαχιστης τιμης - κατωφλι
            double sum=0;
            while (iter.hasNext()) {
                double d = iter.next().get();
                sum+=d;
            }
            possibility.set(sum);

            if (sum>=mesos) context.write(key, possibility); //Μονο αυτα πανω απο το Μ.Ο.
            //System.out.println("Reduce2: "+key+"-Αθροισμα: "+possibility+" - Μεσος ορος: "+mesos);
        }
    }





    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }




    public static void main(String[] args) throws Exception {
        int T=0;   //εδω οριζεται το κατωφλι Τ η το περναμε ως παραμετρο
        try { T = Integer.parseInt(args[0]); }
        catch (Exception e) {}

        Configuration conf = new Configuration();
        conf.setInt("T", T);

        Job job = Job.getInstance(conf, "Graph");
        job.setJarByClass(Graph.class);
        job.setMapperClass(TokenizerMapper.class);
        //job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);  //για να μπορεσουμε να στειλουμε την δεκαδικη τιμη στον reducer
//        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path("input"));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(TMP_PATH));
//        FileOutputFormat.setOutputPath(job, new Path("output"));
        job.waitForCompletion(true) ;


        System.out.println("----ΑΚΜΕΣ ΑΝΩ ΤΟΥ---: "+mesos);



        //double mean = readAndCalcMeanLocal();
        File tmp = new File(TMP_PATH);
        deleteDirectory(tmp);

        //conf.setDouble(MEAN, mean);
        Job job1 = Job.getInstance(conf, "Graph_AboveMean");
        job1.setJarByClass(Graph.class);
        job1.setMapperClass(Graph.TokenizerMapper.class);
        job1.setReducerClass(AboveMeanReducer.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job1, new Path("input"));
        FileOutputFormat.setOutputPath(job1, new Path("output-final"));
        job1.waitForCompletion(true);
    }
}
