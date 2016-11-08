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
        "userid",
        "oauth"
})
public class GetKeypairRequest {

    @JsonProperty("userid")
    private String userid;
    @JsonProperty("oauth")
    private Oauth oauth;
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
     * The oauth
     */
    @JsonProperty("oauth")
    public Oauth getOauth() {
        return oauth;
    }

    /**
     *
     * @param oauth
     * The oauth
     */
    @JsonProperty("oauth")
    public void setOauth(Oauth oauth) {
        this.oauth = oauth;
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
            "provider",
            "token"
    })
    public class Oauth {

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

    }




    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "access"
    })
    public class Token {

        @JsonProperty("access")
        private String access;
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