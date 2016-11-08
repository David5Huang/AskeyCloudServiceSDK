package tw.com.askey.webservice.sdk.api.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "privateKey",
        "publicKey"
})
public class GetKeypairResponse {

    @JsonProperty("privateKey")
    private String privateKey;
    @JsonProperty("publicKey")
    private String publicKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The privateKey
     */
    @JsonProperty("privateKey")
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     *
     * @param privateKey
     * The privateKey
     */
    @JsonProperty("privateKey")
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     *
     * @return
     * The publicKey
     */
    @JsonProperty("publicKey")
    public String getPublicKey() {
        return publicKey;
    }

    /**
     *
     * @param publicKey
     * The publicKey
     */
    @JsonProperty("publicKey")
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
