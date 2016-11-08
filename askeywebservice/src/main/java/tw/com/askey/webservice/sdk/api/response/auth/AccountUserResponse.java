package tw.com.askey.webservice.sdk.api.response.auth;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import tw.com.askey.webservice.sdk.api.response.BasicResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userid",
        "token"
})
public class AccountUserResponse extends BasicResponse {

    @JsonProperty("userid")
    private String userid;
    @JsonProperty("token")
    private String token;
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
     * The token
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
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