import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.util.AccumulatorV2;

import java.io.Serializable;

public class CustomAccumulator {

    public static void main(String args[]) {


        SparkSession spark = SparkSession
                                        .builder()
                                        .appName("CustomPartitionerJOB")
                                        .master("local[*]")
                                        .getOrCreate();



        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        DigiCoinAccumulator dAccumulator = new DigiCoinAccumulator(new DigiCoin());
        javaSparkContext.sc().register(dAccumulator, "DAccumulator");

        JavaRDD<String> textFileRDD = javaSparkContext.textFile("/Users/dharshekthvel/ac/code/scalatrainingintellij/data/auth.csv");

        DigiCoin digiCoin = new DigiCoin();

        textFileRDD.map(each -> {

            String data = each.split(",")[2];

            return data;

        }).foreach(each -> {

            System.out.println(each);
            digiCoin.setSatoshiData(each);
            dAccumulator.add(digiCoin);
        });


        System.out.println("Output - " + dAccumulator.value().getSatoshiData());
    }
}


class DigiCoinAccumulator extends AccumulatorV2<DigiCoin, DigiCoin> {

    private DigiCoin digiCoin = new DigiCoin();

    public DigiCoinAccumulator(DigiCoin _digiCoin) {
        this.digiCoin = _digiCoin;
    }

    @Override
    public boolean isZero() {
        if (digiCoin.getSatoshiData().equals(""))
            return true;
        else
            return false;
    }

    @Override
    public AccumulatorV2<DigiCoin, DigiCoin> copy() {
        return new DigiCoinAccumulator(value());
    }

    @Override
    public void reset() {
        digiCoin.reset();
    }

    @Override
    public void add(DigiCoin v) {
        digiCoin.add(v);
    }

    @Override
    public void merge(AccumulatorV2<DigiCoin, DigiCoin> other) {
        digiCoin.add(other.value());
    }

    @Override
    public DigiCoin value() {
        return digiCoin;
    }
}

class DigiCoin implements Serializable {

    private String satoshiData = "";

    public DigiCoin() {
        satoshiData = "D";
    }

    public void reset() {
        satoshiData = "";
    }

    public DigiCoin add(DigiCoin dc) {
        satoshiData.concat(dc.getSatoshiData());
        return this;
    }

    public String getSatoshiData() {
        return satoshiData;
    }

    public void setSatoshiData(String satoshiData) {
        this.satoshiData = satoshiData;
    }
}