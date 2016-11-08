package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.CardMoodModel;

/**
 * 心情签名页面
 * Created by zhongzilu on 2016-11-07.
 */
public class MoodCardActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MoodCardActivity==>";

    //UI
    private ImageView mMoodImage;
    private TextView mMoodContent;

    //Extra Tag
    public static final String EXTRA_MOOD_OBJECT = "mood_object";

    //Extra Value
    private CardMoodModel mMoodObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_card_layout);

        mMoodContent = (TextView) findViewById(R.id.tv_mood_content);
        mMoodImage = (ImageView) findViewById(R.id.img_mood_image);
        mMoodImage.setOnClickListener(this);

        getIntentData();
        initData();
    }

    private void initData() {
        if (mMoodObject != null){
            mMoodContent.setText(mMoodObject.mood_text);
            Glide.with(this)
                    .load(mMoodObject.mood_img)
                    .into(mMoodImage);
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null){
            Object obj = intent.getParcelableExtra(EXTRA_MOOD_OBJECT);
            if (obj instanceof CardMoodModel){
                mMoodObject = (CardMoodModel) obj;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_mood_image:
                goImage();
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
}
