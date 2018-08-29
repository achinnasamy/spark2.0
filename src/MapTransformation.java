import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.SparkSession;

public class MapTransformation {


    public static void main(String args[]) {

        SparkSession spark = SparkSession
                .builder()
                .appName("CustomPartitionerJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        //JavaRDD<String> textFileRDD = javaSparkContext.textFile("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");
        //javaSparkContext.hadoopConfiguration().setLong("dfs.blocksize",2);

        JavaRDD<String> textFileRDD = javaSparkContext.textFile("hdfs://localhost:9000/ac/auth.csv");

        //textFileRDD.map(each -> each.split(",")[4]).foreach(each -> System.out.println(each));

        JavaRDD<String> mappedRDD = textFileRDD.map(new Function<String, String>() {
            public String call(String s) {
                return s.split(",")[4];
            }
        });

        JavaRDD<String> mappedRDDAliter = textFileRDD.map(ComputeClojure.compute());

        mappedRDD.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                //System.out.println(s);
            }
        });

        mappedRDD.foreach(System.out::println);


    }

}


class ComputeClojure {

    public static Function<String, String> compute() {

        Function<String, String> function =  each -> {
            return each.split(",")[4];
        };

        return function;
    }
}