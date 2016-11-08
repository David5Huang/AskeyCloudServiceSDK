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
        "device"
})
public class GetDeviceBasicInfoResponse extends BasicResponse {

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
            "displayname",
            "gri",
            "iot-thingname",
            "rest-endpoint",
            "mqtt-topic"
    })
    public class Device {

        @JsonProperty("deviceid")
        private String deviceid;
        @JsonProperty("displayname")
        private String displayname;
        @JsonProperty("gri")
        private String gri;
        @JsonProperty("iot-thingname")
        private String iotThingname;
        @JsonProperty("rest-endpoint")
        private String restEndpoint;
        @JsonProperty("mqtt-topic")
        private String mqttTopic;
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
         * The iotThingname
         */
        @JsonProperty("iot-thingname")
        public String getIotThingname() {
            return iotThingname;
        }

        /**
         *
         * @param iotThingname
         * The iot-thingname
         */
        @JsonProperty("iot-thingname")
        public void setIotThingname(String iotThingname) {
            this.iotThingname = iotThingname;
        }

        /**
         *
         * @return
         * The restEndpoint
         */
        @JsonProperty("rest-endpoint")
        public String getRestEndpoint() {
            return restEndpoint;
        }

        /**
         *
         * @param restEndpoint
         * The rest-endpoint
         */
        @JsonProperty("rest-endpoint")
        public void setRestEndpoint(String restEndpoint) {
            this.restEndpoint = restEndpoint;
        }

        /**
         *
         * @return
         * The mqttTopic
         */
        @JsonProperty("mqtt-topic")
        public String getMqttTopic() {
            return mqttTopic;
        }

        /**
         *
         * @param mqttTopic
         * The mqtt-topic
         */
        @JsonProperty("mqtt-topic")
        public void setMqttTopic(String mqttTopic) {
            this.mqttTopic = mqttTopic;
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
