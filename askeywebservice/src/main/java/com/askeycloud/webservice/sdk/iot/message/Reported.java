package com.askeycloud.webservice.sdk.iot.message;

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
        "tstamp",
        "data"
})
public class Reported {

    @JsonProperty("deviceid")
    private String deviceid;
    @JsonProperty("tstamp")
    private String tstamp;
    @JsonProperty("data")
    private Data data;
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
     * The tstamp
     */
    @JsonProperty("tstamp")
    public String getTstamp() {
        return tstamp;
    }

    /**
     *
     * @param tstamp
     * The tstamp
     */
    @JsonProperty("tstamp")
    public void setTstamp(String tstamp) {
        this.tstamp = tstamp;
    }

    /**
     *
     * @return
     * The data
     */
    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
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