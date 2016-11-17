package tw.com.askey.webservice.sdk.api.response.device;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import tw.com.askey.webservice.sdk.api.response.BasicResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "deviceid",
        "gri",
        "iot-thingname",
        "rest-endpoint",
        "shadow-topic",
        "report-topic"
})
public class IoTDeviceInfoResponse extends BasicResponse {

    @JsonProperty("deviceid")
    private String deviceid;
    @JsonProperty("gri")
    private String gri;
    @JsonProperty("iot-thingname")
    private String iotThingname;
    @JsonProperty("rest-endpoint")
    private String restEndpoint;
    @JsonProperty("shadow-topic")
    private String shadowTopic;
    @JsonProperty("report-topic")
    private String reportTopic;
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
     * The shadowTopic
     */
    @JsonProperty("shadow-topic")
    public String getShadowTopic() {
        return shadowTopic;
    }

    /**
     *
     * @param shadowTopic
     * The shadow-topic
     */
    @JsonProperty("shadow-topic")
    public void setShadowTopic(String shadowTopic) {
        this.shadowTopic = shadowTopic;
    }

    /**
     *
     * @return
     * The reportTopic
     */
    @JsonProperty("report-topic")
    public String getReportTopic() {
        return reportTopic;
    }

    /**
     *
     * @param reportTopic
     * The report-topic
     */
    @JsonProperty("report-topic")
    public void setReportTopic(String reportTopic) {
        this.reportTopic = reportTopic;
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