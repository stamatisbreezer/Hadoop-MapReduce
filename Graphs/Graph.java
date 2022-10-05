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
        private static DoubleWritable possibility = new DoubleWritable();
        private Text word = new Text();
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().split("\\s+");  //απο την γραμμογραφηση φορτωνω το αρχειο με διαχωριστικο το κενο

                try {
                    possibility.set(Double.valueOf(tokens[2])); //φορτωνω την πιθανοτητα των ακμων
                    word.set(tokens[0]);
                    context.write(word, possibility);           //καταγραφω την πιθανοτητα της α κορυφης
                    word.set(tokens[1]);
                    context.write(word, possibility);           //καταγραφω την πιθανοτητα της β κορυφης
                } catch (Exception ex) {
                }
        }
    }

    public static class IntSumReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
        private DoubleWritable result = new DoubleWritable();
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
            result.set(sum);
            if (sum>=T) context.write(key, result); //Κοψε οσα ειναι κατω απο το οριο Τ
            plithos++;
            synolo+=sum;  //μετραω για τον μεσο ορο
            mesos=synolo/plithos;
            //System.out.println("Reduce: "+plithos+"-Αθροισμα: "+synolo+" - Μεσος ορος: "+mesos);
        }
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
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);  //για να μπορεσουμε να στειλουμε την δεκαδικη τιμη στον reducer
//        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path("input"));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(TMP_PATH));
//        FileOutputFormat.setOutputPath(job, new Path("output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
