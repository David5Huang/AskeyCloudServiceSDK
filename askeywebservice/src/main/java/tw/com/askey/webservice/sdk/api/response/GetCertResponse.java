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
        "certificatePem"
})
public class GetCertResponse {

    @JsonProperty("certificatePem")
    private String certificatePem;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}