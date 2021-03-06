package com.zhongzilu.bit100.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.AppManager;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.RequestUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;
import com.zhongzilu.bit100.application.util.StatusBarUtils;
import com.zhongzilu.bit100.model.bean.FileBean;
import com.zhongzilu.bit100.model.response.LoginResponse;
import com.zhongzilu.bit100.presenter.FolderManagerPresenter;
import com.zhongzilu.bit100.presenter.IFolderManagerView;
import com.zhongzilu.bit100.view.adapter.Bit100PagerAdapter;
import com.zhongzilu.bit100.widget.PagerSlidingTabStrip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100MainActivity extends AppCompatActivity
        implements View.OnClickListener, IFolderManagerView {

    private static final String TAG = "Bit100MainActivity==>";

    // UI
    private ImageView mHeaderBgImage, mUserHeadImg;
    private TextView mUserName;

    //选择更换图片时的请求码
    private static final int REQUEST_IMAGE_CODE = 111;
    //选择裁剪封面的请求码
    private static final int REQUEST_CROP_CODE = 113;
    private Uri mCropImageUri;
    //获取图片标示
    private static final String IMAGE_TYPE = "image/*";

    // Extra Tag
    public static final String EXTRA_LOGIN_BEAN = "loginBean";

    // Value
    private LoginResponse mLoginInfo;
    private FolderManagerPresenter mPresenter;
    private List<FileBean> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();

        mHeaderBgImage = (ImageView) findViewById(R.id.img_header_change_bg_image);
        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab_new_publish);
        mFab.setOnClickListener(this);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_organization_info_main);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager_organization_info_main);
        mUserHeadImg = (ImageView) findViewById(R.id.img_user_header_image);
//        mUserName = (TextView) findViewById(R.id.tv_user_nick_name);

        Bit100PagerAdapter mAdapter = new Bit100PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);
        // 选中的文字颜色
        mTabs.setSelectedTextColor(R.color.colorPrimary);
        mTabs.setViewPager(mViewPager);

        if (getIntent().hasExtra(EXTRA_LOGIN_BEAN)){
            mLoginInfo = getIntent().getParcelableExtra(EXTRA_LOGIN_BEAN);
        }

        initValue();

        requestPermission();

        //初始化Presenter
        mPresenter = new FolderManagerPresenter(files);
        mPresenter.attachView(this);
        mPresenter.initRoot(this);
    }


    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 必须在初始化阶段调用,例如onCreate()方法中
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (!granted) { // 在android 6.0之前会默认返回true
                            // 未获取权限
                            new AlertDialog.Builder(this)
                                    .setMessage(R.string.dialog_storage_permission_fail_text)
                                    .setPositiveButton(R.string.dialog_confirm_button_text, null)
                                    .create()
                                    .show();
                        }
                    });
        }
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        StatusBarUtils.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //白底黑字状态栏
                .setLightStatusBar(false)
                //设置toolbar,actionbar等view
                .setActionbarView(toolbar)
                .process();
    }

    private void initValue(){
//        if (mLoginInfo != null){
//
//            Glide.with(this)
//                    .load(mLoginInfo.avatar)
//                    .into(mUserHeadImg);
//            mUserName.setText(mLoginInfo.displayName);
//            new Thread(() -> {
//                final String path = SharePreferenceUtil.getImagePath();
//                if (!TextUtils.isEmpty(path)) {
//                    runOnUiThread(() -> mHeaderBgImage.setImageURI(Uri.fromFile(new File(path))));
//
//                }
//            }).start();
//
//        } else {
            new Thread(() -> {
                final String path = SharePreferenceUtil.getImagePath();
//                    final String avatar = CacheHelper.getUserAvatar();
//                    final String username = CacheHelper.getDisplayName();
                if (path != null) {
                    runOnUiThread(() -> mHeaderBgImage.setImageURI(Uri.fromFile(new File(path))));

                }

//                    if (!TextUtils.isEmpty(avatar)) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Glide.with(Bit100MainActivity.this)
//                                        .load(avatar)
//                                        .into(mUserHeadImg);
//                            }
//                        });
//                    }

//                    if (!TextUtils.isEmpty(username)){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mUserName.setText(username);
//                            }
//                        });
//                    }
            }).start();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mUserHeadImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_header_image:
                startActivity(new Intent(this, Bit100LoginActivity.class));
                break;
            case R.id.fab_new_publish:
                goEditNewArticle();
                break;
            default:break;
        }
    }

    private void goEditNewArticle() {
        Intent intent = new Intent(this, EditorActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        //设置数据URI与数据类型匹配
        intent.setDataAndType(Uri.fromFile(new File(mPresenter.currentPath())), "article");
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //选择图片之后的处理
            case REQUEST_IMAGE_CODE:

                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    cropImage(uri, 720, 500, REQUEST_CROP_CODE);
                }
                break;

            //裁剪图片之后的处理
            case REQUEST_CROP_CODE:

                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        //更换封面
                        mHeaderBgImage.setImageURI(uri);
                        SharePreferenceUtil.saveImagePath(uri.getPath());
                    } else {
                        mHeaderBgImage.setImageURI(mCropImageUri);
                        SharePreferenceUtil.saveImagePath(mCropImageUri.getPath());
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择本地文件
     *
     * @param code intent的请求码。取值必须为本类中的REQUEST_VIDEO_CODE、REQUEST_IMAGE_CODE
     *             以及REQUEST_INSTRUMENT_CODE中的一个
     * @param type 选择过滤文件的类型。参照本类中的常量VIDEO_TYPE和IMAGE_TYPE
     *             以及FILE_TYPE
     */
    private void chooseFile(int code, String type) {
        LogUtil.d(TAG, "choose file: ");
        Intent target = new Intent(Intent.ACTION_GET_CONTENT);
        target.setType(type);
        target.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent = Intent.createChooser(target, getString(R.string.title_chooser));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, code);
        } else {
            Toast.makeText(this, R.string.toast_can_not_deal, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对选择的图片封面进行裁剪
     *
     * @param uri         图片uri
     * @param outputX     输出图片宽度
     * @param outputY     输出图片高度
     * @param requestCode 请求码
     */
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra("return-path-if-too-large", true);
        intent.putExtra("scale", true);
        File file = new File(getApplicationContext().getExternalCacheDir(), "bg_headImg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //输入文件格式
        startActivityForResult(intent, requestCode);

        this.mCropImageUri = Uri.fromFile(file);
    }


    public void changeSkin(){
        chooseFile(REQUEST_IMAGE_CODE, IMAGE_TYPE);
    }

    private boolean isExit = false;
    @Override
    public void onBackPressed() {
        if (!isExit){
            isExit = true;
            Toast.makeText(this, R.string.toast_exit_confirm_text, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> isExit = false, 1000);
            return;
        }

        AppManager.getAppManager().AppExit(this);
    }


    @Override
    public void onDestroy() {
        RequestUtil.cancelAllRequest();
        mPresenter.detachView();//VP分离
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void getFileListSuccess(List<FileBean> files) {

    }

    @Override
    public void addTab(String title) {

    }

    @Override
    public void updatePosition(int position, FileBean bean) {

    }

    @Override
    public void addFilePosition(int position, FileBean bean) {

    }

    @Override
    public void otherSuccess(int flag) {

    }

    @Override
    public void onFailure(int errorCode, String message, int flag) {

    }

    @Override
    public void showWait(String message, boolean canBack, int flag) {

    }

    @Override
    public void hideWait(int flag) {

    }
}
