package tw.com.askey.webservice.sdk.api.response;

import android.util.Log;

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
public class GetDeviceDetailResponse extends BasicResponse {

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
            "deviceid",
            "gri",
            "model",
    })
    public class Device {

        @JsonProperty("deviceid")
        private String deviceid;
        @JsonProperty("gri")
        private String gri;
        @JsonProperty("model")
        private String model;

        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The deviceid
         */
        @JsonProperty("deviceid")
        public String getDeviceid() {
            return deviceid;
        }

        /**
         *
         * @param deviceid
         * The deviceid
         */
        @JsonProperty("deviceid")
        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
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

        /**
         *
         * @return
         * The model
         */
        @JsonProperty("model")
        public String getModel() {
            return model;
        }

        /**
         *
         * @param model
         * The model
         */
        @JsonProperty("model")
        public void setModel(String model) {
            this.model = model;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public Map getDeviceDetails(){
            return (Map)additionalProperties.get("detail");
        }
    }

}