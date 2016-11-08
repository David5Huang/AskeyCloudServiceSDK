package tw.com.askey.webservice.sdk.api.builder;

import java.util.ArrayList;
import java.util.HashMap;

import tw.com.askey.webservice.sdk.api.request.ActiveDeviceRequest;

/**
 * Created by david5_huang on 2016/8/2.
 */
public class ActiveDeviceRequestBuilder  {

    protected ActiveDeviceRequest activeDeviceRequest;

    /**
     * @param userid 註冊過後取得的user id
     * @param model IoT裝置的model name, 用來辨認是何種裝置
     * @param uniqueid 代表IoT裝置獨一無二的值
     * @param displayName IoT裝置的名字(隨意取, 通常是裝置持有人設定)
     */
    public ActiveDeviceRequestBuilder(String userid,
                                      String model,
                                      String uniqueid,
                                      String displayName){
        activeDeviceRequest = new ActiveDeviceRequest();
        activeDeviceRequest.setUserid(userid);
        ActiveDeviceRequest.Device device = genDevice(model, uniqueid, displayName);
        ActiveDeviceRequest.Detail detail = new ActiveDeviceRequest().new Detail();
        device.setDetail(detail);
        activeDeviceRequest.setDevice(device);
    }

    private ActiveDeviceRequest.Device genDevice(String model,
                                                 String uniqueid,
                                                 String displayName){
        ActiveDeviceRequest.Device device = new ActiveDeviceRequest().new Device();
        device.setModel(model);
        device.setUniqueid(uniqueid);
        device.setDisplayname(displayName);
        return device;
    }

//    private ActiveDeviceRequest.Detail genDetail(String sampleKey,
//                                                 String macAddress,
//                                                 String firmware,
//                                                 String version,
//                                                 String moreDetailKey){
//        ActiveDeviceRequest.Detail detail = new ActiveDeviceRequest().new Detail();
//        detail.setSamplekey(sampleKey);
//        detail.setMacaddress(macAddress);
//        detail.setFirmware(firmware);
//        detail.setVersion(version);
//        detail.setMoredetailkey(moreDetailKey);
//        return detail;
//    }

    public void setDeviceDetail(HashMap<String, String> details){
//        ActiveDeviceRequest.Detail detail = new ActiveDeviceRequest().new Detail();
        ArrayList<String> keys = new ArrayList<>(details.keySet());
        for(int i=0;i<keys.size();i++){
            activeDeviceRequest.getDevice().getDetail().setAdditionalProperty(keys.get(i), details.get(keys.get(i)));
        }
//        activeDeviceRequest.getDevice().setDetail(detail);
    }

    public ActiveDeviceRequest getActiveDeviceRequest() {
        return activeDeviceRequest;
    }

}
