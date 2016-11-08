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
        "device"
})
public class ActiveDeviceRequest {

    @JsonProperty("userid")
    private String userid;
    @JsonProperty("device")
    private Device device;
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

    })
    public class Detail {

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




    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "model",
            "uniqueid",
            "displayname",
            "detail"
    })
    public class Device {

        @JsonProperty("model")
        private String model;
        @JsonProperty("uniqueid")
        private String uniqueid;
        @JsonProperty("displayname")
        private String displayname;
        @JsonProperty("detail")
        private Detail detail;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

        /**
         *
         * @return
         * The uniqueid
         */
        @JsonProperty("uniqueid")
        public String getUniqueid() {
            return uniqueid;
        }

        /**
         *
         * @param uniqueid
         * The uniqueid
         */
        @JsonProperty("uniqueid")
        public void setUniqueid(String uniqueid) {
            this.uniqueid = uniqueid;
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
         * The detail
         */
        @JsonProperty("detail")
        public Detail getDetail() {
            return detail;
        }

        /**
         *
         * @param detail
         * The detail
         */
        @JsonProperty("detail")
        public void setDetail(Detail detail) {
            this.detail = detail;
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