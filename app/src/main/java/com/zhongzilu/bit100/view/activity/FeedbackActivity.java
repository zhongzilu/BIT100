package com.zhongzilu.bit100.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.zhongzilu.bit100.R;

/**
 * Created by zhongzilu on 16-11-29.
 */
public class FeedbackActivity extends BaseActivity {

    private EditText mContact,mContent;
    private String contact, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_layout);
        setupCenterTitleToolbar();
        setupActionBar();

        mContact = (EditText) findViewById(R.id.et_feed_contact);
        mContent = (EditText) findViewById(R.id.et_feed_content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_feedback:
                sendFeedback();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFeedback() {
        if (validate()){

        }
    }

    private boolean validate() {
        contact = mContact.getText().toString().trim();
        content = mContent.getText().toString();

        if (TextUtils.isEmpty(content)){
            mContent.setError(getString(R.string.error_feedback_null));
            mContent.requestFocus();
            return false;
        }

        return true;
    }
}
