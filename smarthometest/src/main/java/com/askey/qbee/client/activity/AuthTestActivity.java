package com.askey.qbee.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.HashMap;

import askey.com.tw.authapi.AuthHelper;
import askey.com.tw.authapi.Script;
import tw.com.askey.ia4test.BasicActivity;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/24.
 */
public class AuthTestActivity extends BasicActivity implements
        Animation.AnimationListener,
        View.OnClickListener,
        FacebookCallback<LoginResult>,
        ServiceCallback,
        GraphRequest.GraphJSONObjectCallback {

    View startTxt;

    View block01;
    View block02;

    HashMap<Animation, Script> scripts;

    AuthHelper authHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        authHelper = new AuthHelper(this, this);
        scripts = new HashMap<>();

        this.setContentView(askey.com.tw.authapi.R.layout.auth_main);

        startTxt = findViewById(askey.com.tw.authapi.R.id.auth_test_start_txt);
        block01 = findViewById(askey.com.tw.authapi.R.id.auth_test_step01_block);
        block02 = findViewById(askey.com.tw.authapi.R.id.auth_test_step02_block);

        findViewById(askey.com.tw.authapi.R.id.auth_test_step01_btn).setOnClickListener(this);
        findViewById(askey.com.tw.authapi.R.id.auth_test_step02_btn).setOnClickListener(this);

        Animation startAnim = getTransAlphaAnim(1000);
        startAnim.setAnimationListener(this);
        scripts.put(startAnim, new Script(block01, getTransAlphaAnim(800)));
        startTxt.startAnimation(startAnim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authHelper.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Script script = scripts.get(animation);
        script.getActView().setVisibility(View.VISIBLE);
        script.getActView().startAnimation(script.getActAnim());
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View view) {
        Log.e("ia4test", "auth onclick");
        if(view.getId() == askey.com.tw.authapi.R.id.auth_test_step01_btn){
            authHelper.facebookLogin();
        }
        else if(view.getId() == askey.com.tw.authapi.R.id.auth_test_step02_btn){
            Intent it = new Intent(this, CognitoMqttTestActivity.class);
            startActivity(it);
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.e("ia4test", "login success");
        authHelper.getFacebookUserData(loginResult, this);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    public void success(String tag, Object result) {
        Log.e("ia4test", "auth success");
        if(tag.equals("ia4register")){
            block02.setVisibility(View.VISIBLE);
            Animation step02Anim = getTransAlphaAnim(800);
            block02.startAnimation(step02Anim);
        }
    }

    @Override
    public void error(String tag, Object result) {

    }

    @Override
    public void problem(String tag, Object result) {

    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {

        Log.e("ia4test", "on complete");

        Log.e("ia4test", "FB name: "+object.optString("name"));
        Log.e("ia4test", "FB id: "+object.optString("id"));
        Log.e("ia4test", "FB mail: "+object.optString("email"));
        Log.e("ia4test", object.toString());

        String name = object.optString("name");
        String id = object.optString("id");
        String mail = object.optString("email");

        authHelper.activeAskeyUser(name, id, mail, this);
    }
}
