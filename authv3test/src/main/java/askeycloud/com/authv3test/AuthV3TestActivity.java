package askeycloud.com.authv3test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.askeycloud.sdk.auth.response.OAuthProvider;
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.model.auth.v3.DeviceProvidersQueryOptions;
import com.askeycloud.webservice.sdk.model.auth.v3.OAuthLoginResultModel;
import com.askeycloud.webservice.sdk.model.auth.v3.ProviderQueryOptions;
import com.askeycloud.webservice.sdk.service.web.AskeyWebService;
import com.askeycloud.webservice.sdk.task.ServiceCallback;
import com.askeycloud.webservice.sdk.view.OAuthDeviceConfirmDialogBuilder;

/**
 * Created by david5_huang on 2017/6/20.
 */

public class AuthV3TestActivity extends Activity implements View.OnClickListener, ServiceCallback, DemoConst {

    private final String SERVICE_TAG = "authv3test";
    private final String LOGIN_TAG = "login_tag";
    private OAuthProvider[] providers;

    private final String YOUR_APP_SCHEME = "oauthv3test";
    private final String YOUR_APPLICATION_ID = "askeycloud.testclientid.scheme";
    private final String ACCOUNT_PROVIDER = "askey";

    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.test_authv3_layout);

        findViewById(R.id.v3_test_provider_btn).setOnClickListener(this);
        findViewById(R.id.v3_test_askey_auth_btn).setOnClickListener(this);
        findViewById(R.id.v3_test_fb_auth_btn).setOnClickListener(this);
        findViewById(R.id.v3_test_user_login_btn).setOnClickListener(this);
        findViewById(R.id.v3_test_revoke_btn).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.v3_test_provider_btn){
//            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
//            startActivityForResult(intent, 0);

            DeviceProvidersQueryOptions options = new DeviceProvidersQueryOptions();
            options.setDeviceAuthAppId(YOUR_DEVICE_AUTH_APPID);
            options.setDeviceModel(YOUR_AUTH_DEVICE_MODEL);
            Log.e("testing", "unique id: "+YOUR_AUTH_DEVICE_UNIQUEID);
            options.setDeviceAuthUniqueId(YOUR_AUTH_DEVICE_UNIQUEID);

            AskeyWebService.getInstance(this)
                    .queryDeviceProviders(context, options, SERVICE_TAG, this);

        }
        else if(v.getId() == R.id.v3_test_askey_auth_btn){
            OAuthProvider provider = getAuthUrl(providers, "askey");
//            openDialog(provider);

//            AskeyWebService.getInstance(this).activeV3(queryOption, this, LOGIN_TAG);
            AskeyWebService.getInstance(this)
                    .activeDeviceV3(
                            provider.getAuthorizeUri(),
                            YOUR_DEVICE_AUTH_SCHEME,
                            this,
                            LOGIN_TAG);

        }
        else if(v.getId() == R.id.v3_test_fb_auth_btn){
//            OAuthProvider provider = getAuthUrl(providers, "facebook");
//            openDialog(provider);
        }
        else if(v.getId() == R.id.v3_test_user_login_btn){
//            ProviderQueryOptions queryOption = new ProviderQueryOptions(
//                    YOUR_APP_SCHEME,
//                    YOUR_APPLICATION_ID,
//                    ACCOUNT_PROVIDER
//            );

            ProviderQueryOptions queryOption = new ProviderQueryOptions(
                    YOUR_DEVICE_AUTH_SCHEME,
                    YOUR_DEVICE_AUTH_APPID,
                    ACCOUNT_PROVIDER
            );

            AskeyWebService.getInstance(this).activeV3(queryOption, this, LOGIN_TAG);
        }
        else if(v.getId() == R.id.v3_test_revoke_btn){
            AskeyWebService.getInstance(this).revoke();
            finish();
        }
    }

    @Override
    public void success(String tag, Object result) {
        if(tag.equals(SERVICE_TAG)) {

            Log.e("testing", "service tag");

            if(progressDialog != null){
                progressDialog.dismiss();
            }

            providers = (OAuthProvider[]) result;

            if(providers.length > 0){
                Log.e("testing", "provider code: "+providers[0].getCode());
                findViewById(R.id.v3_test_askey_auth_btn).setVisibility(View.VISIBLE);
//                findViewById(R.id.v3_test_fb_auth_btn).setVisibility(View.VISIBLE);
            }
        }
        else if(tag.equals(LOGIN_TAG)){
            OAuthLoginResultModel resultModel = (OAuthLoginResultModel) result;
            Log.e(DebugConst.DEBUG_TAG, resultModel.getAccessToken());

//            Intent it = new Intent(this, ApiTestActivity.class);
//            startActivity(it);

            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    public void error(String tag, Object result) {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void problem(String tag, Object result) {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private OAuthProvider getAuthUrl(OAuthProvider[] providers, String useProvider){
        for(int i=0;i<providers.length;i++){
            OAuthProvider provider = providers[i];
            if(provider.getProvider().equals(useProvider)){
                return provider;
            }
        }
        return null;
    }

    private void openDialog(OAuthProvider provider){
//        OAuthDeviceConfirmDialogBuilder.build(this).showDialog(provider);
    }
}
