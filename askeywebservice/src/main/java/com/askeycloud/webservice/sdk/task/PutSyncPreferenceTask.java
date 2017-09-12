package com.askeycloud.webservice.sdk.task;

import android.content.Context;

/**
 * Created by david5_huang on 2016/8/16.
 */
public class PutSyncPreferenceTask extends SyncPreferenceTask {

    protected String key;
    protected Object value;

    public PutSyncPreferenceTask(Context context, String key, Object value) {
        super(context);
        this.key = key;
        this.value = value;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(key != null && value != null && syncClient != null){
//            UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest()
//                    .withDatasetName("test")
//                    .withIdentityId(identityId)
//                    .withIdentityPoolId(IDENTITY_POOL_ID)
//                    .withSyncSessionToken(token)
//                    .withRecordPatches(
//                            new RecordPatch()
//                                    .withKey("test")
//                                    .withValue("value")
//                                    .withOp(Operation.Replace)
//                                    .withSyncCount(listRecordsResult.getDatasetSyncCount()));
//            syncClient.updateRecords(updateRecordsRequest);
        }
        return null;
    }
}
