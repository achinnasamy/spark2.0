import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

public class AllTransformations {

    public static void main(String args[]) {


        SparkSession spark = SparkSession
                .builder()
                .appName("CustomPartitionerJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> textFileRDD = javaSparkContext.textFile("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");

        JavaPairRDD<String, Integer> pairRDD = textFileRDD.mapToPair(each -> new Tuple2<String,Integer>(each.split(",")[3],1));

        pairRDD.reduceByKey((x,y) -> x+y).foreach(each -> System.out.println(each._1 + " --- " + each._2));


        textFileRDD.union(textFileRDD);
        textFileRDD.intersection(textFileRDD);
        textFileRDD.distinct();
        textFileRDD.pipe("ls -l");


        textFileRDD.checkpoint();
        textFileRDD.persist(StorageLevel.MEMORY_AND_DISK());

    }
}
