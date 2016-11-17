package tw.com.askey.webservice.sdk.api.response.auth;

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
        "userid",
        "certificatePem",
        "privateKey"
})
public class AWSIoTCertResponse {

    @JsonProperty("userid")
    private String userid;
    @JsonProperty("certificatePem")
    private String certificatePem;
    @JsonProperty("privateKey")
    private String privateKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The userid
     */
    @JsonProperty("userid")
    public String getUserid() {
        return userid;
    }

    /**
     *
     * @param userid
     * The userid
     */
    @JsonProperty("userid")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     *
     * @return
     * The certificatePem
     */
    @JsonProperty("certificatePem")
    public String getCertificatePem() {
        return certificatePem;
    }

    /**
     *
     * @param certificatePem
     * The certificatePem
     */
    @JsonProperty("certificatePem")
    public void setCertificatePem(String certificatePem) {
        this.certificatePem = certificatePem;
    }

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}