package tw.com.askey.webservice.sdk.api.response.device;

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
        "deviceid",
        "displayname",
        "devicemodel",
        "uniqueid"
})
public class UserIoTDevice {

    @JsonProperty("deviceid")
    private String deviceid;
    @JsonProperty("displayname")
    private String displayname;
    @JsonProperty("devicemodel")
    private String devicemodel;
    @JsonProperty("uniqueid")
    private String uniqueid;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserIoTDevice() {
    }

    /**
     *
     * @param devicemodel
     * @param uniqueid
     * @param displayname
     * @param deviceid
     */
    public UserIoTDevice(String deviceid, String displayname, String devicemodel, String uniqueid) {
        this.deviceid = deviceid;
        this.displayname = displayname;
        this.devicemodel = devicemodel;
        this.uniqueid = uniqueid;
    }

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
     * The devicemodel
     */
    @JsonProperty("devicemodel")
    public String getDevicemodel() {
        return devicemodel;
    }

    /**
     *
     * @param devicemodel
     * The devicemodel
     */
    @JsonProperty("devicemodel")
    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}