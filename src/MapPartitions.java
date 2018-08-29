import org.apache.log4j.ConsoleAppender;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapPartitions {

    public static void main(String args[]) {

        SparkSession spark = SparkSession
                .builder()
                .appName("CustomPartitionerJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<Integer> dataRDD = javaSparkContext.parallelize(Arrays.asList(1,9200,3,4,5), 1);



        //dataRDD.map(each -> each + 1).foreach(each -> System.out.println(each));

        dataRDD.mapPartitions(iterator -> {

            List<Integer> intList = new ArrayList<>();
            while (iterator.hasNext()) {
                intList.add(iterator.next()+1);
            }
            return intList.iterator();
        }).foreach(each -> System.out.println(each));

    }

}
