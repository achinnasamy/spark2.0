import java.io.Serializable;

public class LiteCoinDataHash implements Serializable {

    private String hash = "";

    private String nonce = "";

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}