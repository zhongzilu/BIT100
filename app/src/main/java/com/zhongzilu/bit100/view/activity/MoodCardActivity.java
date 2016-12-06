package com.zhongzilu.bit100.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.BitmapUtil;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.StatusBarUtils;
import com.zhongzilu.bit100.model.bean.CardMoodModel;

import java.io.File;

/**
 * 心情签名页面
 * Created by zhongzilu on 2016-11-07.
 */
public class MoodCardActivity extends BaseActivity
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_card_layout);
        setToolbar();

        mMoodContent = (TextView) findViewById(R.id.tv_mood_content);
        Typeface face = Typeface.createFromAsset(getAssets(), "font/FZYTK.ttf");
        mMoodContent.setTypeface(face);
        mMoodImage = (ImageView) findViewById(R.id.img_mood_image);
        mMoodImage.setOnClickListener(this);
        mMoodContent.setOnClickListener(this);
        registerForContextMenu(mMoodContent);
        getIntentData();
        initData();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        setupActionBar();
    }

    private void initData() {
        if (mMoodObject != null){
            mMoodContent.setText(mMoodObject.mood_text);
            if (mImageBitmap != null){
                mMoodImage.setImageBitmap(mImageBitmap);
            } else {
                Glide.with(this)
                        .load(mMoodObject.mood_img)
                        .into(mMoodImage);
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
                shareAction("来自BIT100的分享", saveCaptureAndReturnPath());
                break;
            case R.id.action_capture:
                Toast.makeText(this, "截图保存在" + saveCaptureAndReturnPath(), Toast.LENGTH_LONG).show();
                break;
            case R.id.action_copy:
                copyToClipboard(mMoodContent.getText().toString());
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 将文字复制到剪切板
     * @param text 复制的文字
     */
    public void copyToClipboard(String text){
        try {
            ((ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE))
                    .setPrimaryClip(ClipData.newPlainText(null, text.trim()));
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "复制失败", Toast.LENGTH_SHORT).show();
        }
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
