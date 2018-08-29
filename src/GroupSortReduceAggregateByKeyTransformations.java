import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;

public class GroupSortReduceAggregateByKeyTransformations
{


    public static void main(String args[]) {


        SparkSession spark = SparkSession
                .builder()
                .appName("TransformationsJOB")
                .master("local[*]")
                .getOrCreate();


        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());


        JavaPairRDD<String, Integer> pairRDD =
                javaSparkContext.parallelizePairs(
                        Arrays.asList(new Tuple2<String, Integer>("B", 2),
                                new Tuple2<String, Integer>("C", 5),
                                new Tuple2<String, Integer>("D", 7),
                                new Tuple2<String, Integer>("A", 8)));



        JavaPairRDD<String, Iterable<Integer>> groupByKey = pairRDD.groupByKey();

        // reducebykey
        JavaPairRDD<String, Integer> reduceByKey = pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // sort by key
        JavaPairRDD<String, Integer> sortByKey = pairRDD.sortByKey();



        JavaPairRDD<String, Integer> aggregateByKey = pairRDD.aggregateByKey(0,
                new Function2<Integer, Integer, Integer>() {

                    /**
                     *
                     */
                    private static final long serialVersionUID = -9193256894160862119L;

                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                }, new Function2<Integer, Integer, Integer>() {

                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {

                        return v1 + v2;
                    }
                });

        JavaPairRDD<String, Integer> combineByKey = pairRDD.combineByKey(new Function<Integer, Integer>() {

            /**
             *
             */
            private static final long serialVersionUID = -1965754276530922495L;

            @Override
            public Integer call(Integer v1) throws Exception {

                return v1;
            }
        }, new Function2<Integer, Integer, Integer>() {

            /**
             *
             */
            private static final long serialVersionUID = -9193256894160862119L;

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }, new Function2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {

                return v1 + v2;
            }
        });

        JavaPairRDD<String, Integer> foldByKey = pairRDD.foldByKey(0, new Function2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {

                return v1 + v2;
            }
        });


    }

}
