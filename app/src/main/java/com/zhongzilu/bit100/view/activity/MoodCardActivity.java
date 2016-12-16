package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.App;
import com.zhongzilu.bit100.application.util.BitmapUtil;
import com.zhongzilu.bit100.application.util.ImageLoadUtil;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.SystemUtils;
import com.zhongzilu.bit100.model.bean.CardMoodModel;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;

import java.io.File;

/**
 * 心情签名页面
 * Created by zhongzilu on 2016-11-07.
 */
public class MoodCardActivity extends BaseToolbarActivity
        implements View.OnClickListener{
    private static final String TAG = "MoodCardActivity==>";

    //UI
    private ImageView mMoodImage;
    private TextView mMoodContent;

    //Extra Tag
    public static final String EXTRA_MOOD_OBJECT = "mood_object";
    public static final String EXTRA_IMAGE_BITMAP = "image_bitmap";

    //Extra Value
    private CardMoodModel mMoodObject;
    private Bitmap mImageBitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mood_card_layout;
    }

    @Override
    protected void initStatusBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mood_card);
        toolbar.setTitle("");
        initToolbar(toolbar);
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {

        mMoodContent = (TextView) findViewById(R.id.tv_mood_content);
        mMoodContent.setTypeface(App.getTypeface());
        mMoodImage = (ImageView) findViewById(R.id.img_mood_image);
        mMoodImage.setOnClickListener(this);
        mMoodContent.setOnClickListener(this);
        registerForContextMenu(mMoodContent);
        getIntentData();
    }

    @Override
    public void initData() {
        if (mMoodObject != null){
            mMoodContent.setText(mMoodObject.mood_text);
            if (mImageBitmap != null){
                mMoodImage.setImageBitmap(mImageBitmap);
            } else {
                ImageLoadUtil.loadImage(mMoodObject.mood_img, mMoodImage);
            }
        }
    }

    private void getIntentData() {
        if (getIntent().hasExtra(EXTRA_MOOD_OBJECT)){
            Object obj = getIntent().getParcelableExtra(EXTRA_MOOD_OBJECT);
            if (obj instanceof CardMoodModel){
                mMoodObject = (CardMoodModel) obj;
            }
        }
        if (getIntent().hasExtra(EXTRA_IMAGE_BITMAP)){
            Object obj = getIntent().getParcelableExtra(EXTRA_IMAGE_BITMAP);
            if (obj instanceof Bitmap){
                mImageBitmap = (Bitmap) obj;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_mood_image:
                goImage();
                break;
            case R.id.tv_mood_content:
                v.showContextMenu();
                break;
        }
    }

    private void goImage(){
        LogUtil.d(TAG, "goImage: imageUrl==>" + mMoodObject.mood_img);
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_IMAGES_LIST, new String[]{mMoodObject.mood_img});
        startActivity(intent);
        overridePendingTransition(R.anim.fade_anim_in, R.anim.fade_anim_out);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_mood_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                shareAction(getString(R.string.share_where_from_text), saveCaptureAndReturnPath());
                break;
            case R.id.action_capture:
                Toast.makeText(this, getString(R.string.toast_image_saved) + saveCaptureAndReturnPath(), Toast.LENGTH_LONG).show();
                break;
            case R.id.action_copy:
                SystemUtils.copyToClipBoard(this, mMoodContent.getText().toString());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private String saveCaptureAndReturnPath(){
        Bitmap bitmap = BitmapUtil.getViewBitmap(findViewById(R.id.layout_mood_parent_wrap));
        String path = BitmapUtil.saveBitmap(bitmap);
        addToGallery(path);
        return path;
    }

    /**
     * 通知图库加入新保存的图片
     * @param path 图片的真实地址
     */
    private void addToGallery(String path){
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        localIntent.setData(Uri.fromFile(new File(path)));
        sendBroadcast(localIntent);
    }

    /**
     * 分享功能
     * @param text 消息内容
     * @param imgPath 图片路径，不分享图片则传null
     */
    public void shareAction(String text, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (TextUtils.isEmpty(imgPath)) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f.exists() && f.isFile()){
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            }
        }
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.title_chooser_share)));

    }
}
