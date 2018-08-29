import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Arrays;

public class AllTransformations {

    public static void main(String args[]) {


        SparkSession spark = SparkSession
                .builder()
                .appName("TransformationsJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<String> textFileRDD = javaSparkContext.textFile("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");

        //JavaPairRDD<String, Integer> pairRDD = textFileRDD.mapToPair(each -> new Tuple2<String,Integer>(each.split(",")[3],1));

        //pairRDD.reduceByKey((x,y) -> x+y).foreach(each -> System.out.println(each._1 + " --- " + each._2));


        textFileRDD.union(textFileRDD);
        textFileRDD.intersection(textFileRDD);
        textFileRDD.distinct();
        textFileRDD.pipe("ls -l");


        //textFileRDD.checkpoint();
        //textFileRDD.persist(StorageLevel.MEMORY_AND_DISK());



        /* Cartesian Transformation */
        //JavaRDD<String> rddStrings = javaSparkContext.parallelize(Arrays.asList("A","B","C"));
        //JavaRDD<Integer> rddIntegers = javaSparkContext.parallelize(Arrays.asList(77,88,99));

        //rddStrings.cartesian(rddIntegers).foreach(each -> System.out.println(each._1 + " --- " + each._2));;




        JavaPairRDD<String, Integer> pairRDD =
                    javaSparkContext.parallelizePairs(
                            Arrays.asList(new Tuple2<String, Integer>("B", 2),
                                            new Tuple2<String, Integer>("C", 5),
                                            new Tuple2<String, Integer>("D", 7),
                                            new Tuple2<String, Integer>("A", 8)));

        // Count By Key is an action on PairRDD
        pairRDD.countByKey();





    }
}
