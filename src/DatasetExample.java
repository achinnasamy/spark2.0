import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;



public class DatasetExample {


    public static void main(String args[]) {


        SparkSession sparkSession = SparkSession
                .builder()
                .appName("DataSetJOB")
                .master("local[*]")
                .getOrCreate();

        LiteCoinDataHash lch = new LiteCoinDataHash();
        lch.setHash("HASHES__");
        lch.setNonce("123");

        Encoder<LiteCoinDataHash> liteCoinDataHashEncoder = Encoders.bean(LiteCoinDataHash.class);

        Dataset dataSetLiteCoin =
                sparkSession.createDataset(Collections.singletonList(lch),
                        liteCoinDataHashEncoder);


        dataSetLiteCoin.toDF();
        dataSetLiteCoin.toJavaRDD();

        dataSetLiteCoin.show();


    }
}
