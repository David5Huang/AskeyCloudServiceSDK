package tw.com.askey.webservice.sdk.api.request;

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
        "provider",
        "token"
})
public class GetUserRequest {

    @JsonProperty("provider")
    private String provider;
    @JsonProperty("token")
    private Token token;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The provider
     */
    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    /**
     *
     * @param provider
     * The provider
     */
    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     *
     * @return
     * The token
     */
    @JsonProperty("token")
    public Token getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    @JsonProperty("token")
    public void setToken(Token token) {
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



    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({

    })
    public class Token {

        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}