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
        "device"
})
public class UpdateDeviceInfoRequest {

    @JsonProperty("device")
    private Device device;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The device
     */
    @JsonProperty("device")
    public Device getDevice() {
        return device;
    }

    /**
     *
     * @param device
     * The device
     */
    @JsonProperty("device")
    public void setDevice(Device device) {
        this.device = device;
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
            "displayname"
    })
    public class Device {

        @JsonProperty("displayname")
        private String displayname;
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