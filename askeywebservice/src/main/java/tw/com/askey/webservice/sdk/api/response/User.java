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
        "userid",
        "cognitoidentityid",
        "gri"
})
public class User {

    @JsonProperty("userid")
    private String userid;
    @JsonProperty("cognitoidentityid")
    private String cognitoidentityid;
    @JsonProperty("gri")
    private String gri;
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
     * The cognitoidentityid
     */
    @JsonProperty("cognitoidentityid")
    public String getCognitoidentityid() {
        return cognitoidentityid;
    }

    /**
     *
     * @param cognitoidentityid
     * The cognitoidentityid
     */
    @JsonProperty("cognitoidentityid")
    public void setCognitoidentityid(String cognitoidentityid) {
        this.cognitoidentityid = cognitoidentityid;
    }

    /**
     *
     * @return
     * The gri
     */
    @JsonProperty("gri")
    public String getGri() {
        return gri;
    }

    /**
     *
     * @param gri
     * The gri
     */
    @JsonProperty("gri")
    public void setGri(String gri) {
        this.gri = gri;
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