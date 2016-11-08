package tw.com.askey.webservice.sdk.api.builder;

import tw.com.askey.webservice.sdk.api.request.UpdateDeviceInfoRequest;

/**
 * Created by david5_huang on 2016/8/3.
 */
public class UpdateDeviceInfoRequestBuilder {
    private UpdateDeviceInfoRequest updateDeviceInfoRequest;

    public UpdateDeviceInfoRequestBuilder(String displayName){
        updateDeviceInfoRequest = new UpdateDeviceInfoRequest();

        UpdateDeviceInfoRequest.Device device = new UpdateDeviceInfoRequest().new Device();
        device.setDisplayname(displayName);

        updateDeviceInfoRequest.setDevice(device);
    }

    public UpdateDeviceInfoRequest getUpdateDeviceInfoRequest() {
        return updateDeviceInfoRequest;
    }
}
