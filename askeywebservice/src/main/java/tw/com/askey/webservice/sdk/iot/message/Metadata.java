package tw.com.askey.webservice.sdk.iot.message;

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
        "desired",
        "reported"
})
public class Metadata {

    @JsonProperty("desired")
    private Desired desired;
    @JsonProperty("reported")
    private Reported reported;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The desired
     */
    @JsonProperty("desired")
    public Desired getDesired() {
        return desired;
    }

    /**
     *
     * @param desired
     * The desired
     */
    @JsonProperty("desired")
    public void setDesired(Desired desired) {
        this.desired = desired;
    }

    /**
     *
     * @return
     * The reported
     */
    @JsonProperty("reported")
    public Reported getReported() {
        return reported;
    }

    /**
     *
     * @param reported
     * The reported
     */
    @JsonProperty("reported")
    public void setReported(Reported reported) {
        this.reported = reported;
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
