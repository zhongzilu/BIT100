package com.zhongzilu.bit100.view.activity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.helper.CacheHelper;
import com.zhongzilu.bit100.application.particlesys.ParticleSystemRenderer;
import com.zhongzilu.bit100.application.util.RequestUtil;
import com.zhongzilu.bit100.model.response.LoginResponse;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * 登录界面
 * Created by zhongzilu on 2016-10-13.
 */
public class Bit100LoginActivity extends BaseToolbarActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,RequestUtil.RequestCallback{

    // UI
    @Bind(R.id.gl_surface_view)
    protected GLSurfaceView mGlSurfaceView;
    @Bind(R.id.et_login_account)
    protected EditText mUsername;
    @Bind(R.id.et_login_password)
    protected EditText mPassword;
    @Bind(R.id.tv_forgot_password)
    protected View mForgetPass;
    @Bind(R.id.btn_login)
    protected Button mLoginBtn;
    @Bind(R.id.cb_remember_pwd)
    protected CheckBox mRememberPwd;

    //other
    private String name;
    private String pwd;
    private ProgressDialog mWaitingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initStatusBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar.setTitle("");
        initToolbar(toolbar);
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {

        mForgetPass.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mRememberPwd.setOnCheckedChangeListener(this);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible getAppContext.
            mGlSurfaceView.setEGLContextClientVersion(2);

            // Set the renderer to our demo renderer, defined below.
            ParticleSystemRenderer mRenderer = new ParticleSystemRenderer(mGlSurfaceView);
            mGlSurfaceView.setRenderer(mRenderer);
            mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void initData() {
        getLoginInfoCache();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGlSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlSurfaceView.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forgot_password:
//                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.btn_login:
                startLogin();
                break;
        }
    }

    private boolean validate() {
        name = mUsername.getText().toString();
        pwd = mPassword.getText().toString();

        if (TextUtils.isEmpty(name)){
            mUsername.requestFocus();
            mUsername.setError(getString(R.string.error_account_null));
            return false;
        }

        if (TextUtils.isEmpty(pwd)){
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.error_password_null));
            return false;
        }

        saveLoginInfo();

        return true;
    }

    private void startLogin(){
        if (validate()){
            RequestUtil.postLogin(name, pwd, this);
        }
    }

    private void saveLoginInfo(){
        CacheHelper.saveLoginName(name);
        if (mRememberPwd.isChecked()){
            CacheHelper.saveLoginPwd(pwd);
        }
    }

    private void getLoginInfoCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String name = CacheHelper.getLoginName();
                final String pwd = CacheHelper.getLoginPwd();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUsername.setText(name);
                        mPassword.setText(pwd);
                        if (!TextUtils.isEmpty(pwd)){
                            mRememberPwd.setChecked(true);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            if (!TextUtils.isEmpty(pwd))
                CacheHelper.saveLoginPwd(pwd);
        } else {
            CacheHelper.saveLoginPwd("");
        }
    }

    @Override
    public OnResponseListener callback() {
        return new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {
                mWaitingDialog = ProgressDialog.show(Bit100LoginActivity.this, null, getString(R.string.dialog_landing_text) );
            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {

                handleLoginResult(response.get());
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {

            }

            @Override
            public void onFinish(int what) {
                mWaitingDialog.dismiss();
            }
        };
    }

    private void handleLoginResult(JSONObject obj){
        if (obj.has("errors")){
            try {
                JSONObject errors = obj.getJSONObject("errors");
                if (errors.has("invalid_username")){
                    mUsername.setError(getString(R.string.error_account_error));
                    mUsername.requestFocus();
                } else {
                    mPassword.setError(getString(R.string.error_password_error));
                    mPassword.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            LoginResponse response = new Gson().fromJson(obj.toString(),
                    new TypeToken<LoginResponse>(){}.getType());

            CacheHelper.saveLoginInfo(response);

            Intent intent = new Intent(Bit100LoginActivity.this, Bit100MainActivity.class);
            intent.putExtra(Bit100MainActivity.EXTRA_LOGIN_BEAN, response);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
