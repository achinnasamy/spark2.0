import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;

import java.io.Serializable;


public class SparkJOB {


    public static void main(String args[]) {
        SparkConf sparkConfig = new SparkConf()
                .setAppName("DataCockpitJOB")
                .setMaster("local[*]");


        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConfig);


        SatoshiDTO dto = new SatoshiDTO();
        dto.setData("ETHEREUM");

        Broadcast<SatoshiDTO> broadcastVar = javaSparkContext.broadcast(dto);
        LongAccumulator accum = javaSparkContext.sc().longAccumulator();


        JavaRDD<String> textFileRDD = javaSparkContext.textFile("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");

        textFileRDD.map(each -> each.split(",")[2]).foreach(eachLine -> System.out.println(eachLine));


//        javaSparkContext.wholeTextFiles("/home/dharshekthvel/ac/code/scalatrainingintellij/data")
//                .foreach(eachLine -> System.out.println(eachLine._1));

        javaSparkContext.close();
        javaSparkContext.stop();


    }

}



class SatoshiDTO implements Serializable {

    private String data = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}