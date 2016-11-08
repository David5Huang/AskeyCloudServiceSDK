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
        "displayname",
        "email",
        "oauth"
})
public class AddUserRequest {

    @JsonProperty("displayname")
    private String displayname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("oauth")
    private Oauth oauth;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The displayname
     */
    @JsonProperty("displayname")
    public String getDisplayname() {
        return displayname;
    }

    /**
     *
     * @param displayname
     * The displayname
     */
    @JsonProperty("displayname")
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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
            "provider-uid",
            "provider-email",
            "token"
    })
    public class Oauth {

        @JsonProperty("provider")
        private String provider;
        @JsonProperty("provider-uid")
        private String providerUid;
        @JsonProperty("provider-email")
        private String providerEmail;
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
         * The providerUid
         */
        @JsonProperty("provider-uid")
        public String getProviderUid() {
            return providerUid;
        }

        /**
         *
         * @param providerUid
         * The provider-uid
         */
        @JsonProperty("provider-uid")
        public void setProviderUid(String providerUid) {
            this.providerUid = providerUid;
        }

        /**
         *
         * @return
         * The providerEmail
         */
        @JsonProperty("provider-email")
        public String getProviderEmail() {
            return providerEmail;
        }

        /**
         *
         * @param providerEmail
         * The provider-email
         */
        @JsonProperty("provider-email")
        public void setProviderEmail(String providerEmail) {
            this.providerEmail = providerEmail;
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
