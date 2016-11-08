package tw.com.askey.webservice.sdk.api.request.auth;

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
        "email",
        "passcode",
        "OTP"
})
public class LoginUserRequest {

    @JsonProperty("email")
    private String email;
    @JsonProperty("passcode")
    private String passcode;
    @JsonProperty("OTP")
    private String oTP;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The passcode
     */
    @JsonProperty("passcode")
    public String getPasscode() {
        return passcode;
    }

    /**
     *
     * @param passcode
     * The passcode
     */
    @JsonProperty("passcode")
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    /**
     *
     * @return
     * The oTP
     */
    @JsonProperty("OTP")
    public String getOTP() {
        return oTP;
    }

    /**
     *
     * @param oTP
     * The OTP
     */
    @JsonProperty("OTP")
    public void setOTP(String oTP) {
        this.oTP = oTP;
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