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
        "token"
})
public class GetLongTokenResponse extends BasicResponse {

    @JsonProperty("token")
    private Token token;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
            "access",
            "expires"
    })
    public class Token {

        @JsonProperty("access")
        private String access;
        @JsonProperty("expires")
        private String expires;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The access
         */
        @JsonProperty("access")
        public String getAccess() {
            return access;
        }

        /**
         *
         * @param access
         * The access
         */
        @JsonProperty("access")
        public void setAccess(String access) {
            this.access = access;
        }

        /**
         *
         * @return
         * The expires
         */
        @JsonProperty("expires")
        public String getExpires() {
            return expires;
        }

        /**
         *
         * @param expires
         * The expires
         */
        @JsonProperty("expires")
        public void setExpires(String expires) {
            this.expires = expires;
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

}