import org.apache.spark.Partitioner;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomPartitioner {

    public static void main(String args[]) {
        SparkSession spark = SparkSession
                .builder()
                .appName("CustomPartitionerJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());

        List<String> data = Arrays.asList("20101111","2099", "2077");

        JavaRDD<String> dataRDD = sc.parallelize(data);

        dataRDD.mapToPair(each -> new Tuple2(each, each))
               .partitionBy(new YearPartitioner())
               .saveAsTextFile("/Users/dharshekthvel/ac/code/outdata3");


        JavaPairRDD<String,String> partitionedRDD = dataRDD.mapToPair(each -> new Tuple2(each, each))
                                    .partitionBy(new YearPartitioner());


        partitionedRDD.mapPartitionsWithIndex((index, iterator) -> {

            if (index == 4) {
                while (iterator.hasNext()) {
                    Tuple2<String, String> next = iterator.next();
                    System.out.println(next._1 + " --- " + next._2);
                }
            }
            return iterator;

        },false).collect();



    }


}


class YearPartitioner extends Partitioner {

    @Override
    public int numPartitions() {
        return 10;
    }

    @Override
    public int getPartition(Object key) {

        String inputKey = (String)key;

        if (inputKey.startsWith("2010"))
            return 1;
        else
            return 4;
    }
}