package com.wzystal.weibokong;

import java.net.URLEncoder;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.http.StatusManager;
import com.wzystal.weibokong.model.Status;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StatusWriteActivity extends Activity implements OnClickListener, TextWatcher, Listener<Status>, ErrorListener{
	private static final int STATUS_MAX_LENGTH = 140;
	private TextView tvLimit;
	private EditText etContent;
	private boolean outOfLimit = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_write);
		initView();
	}

	private void initView(){
		tvLimit = (TextView) findViewById(R.id.tv_text_limit);
		tvLimit.setText(""+STATUS_MAX_LENGTH);
		etContent = (EditText) findViewById(R.id.et_status_content);
		etContent.addTextChangedListener(this);
		etContent.setOnClickListener(this);
		ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnBack.setOnClickListener(this);
		TextView btnSend = (TextView) findViewById(R.id.btn_status_send);
		btnSend.setOnClickListener(this);
	}
	
	private void sendStatus(){
		String content = URLEncoder.encode(etContent.getText().toString());
		StatusManager.getInstance().updateStatus(this, this, content);
	}
	
	@Override
	public void onResponse(Status status) {
		if(null != status){
			Toast.makeText(this, "发送成功！", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = etContent.getText().toString();
        int len = content.length();
        if (len <= STATUS_MAX_LENGTH) {
        	len = STATUS_MAX_LENGTH-len;
        	tvLimit.setText("剩余" + len + "字");
        	outOfLimit = false;
        } else {
            len = len - STATUS_MAX_LENGTH;
            tvLimit.setTextColor(Color.RED);
            tvLimit.setText("超过"+ len + "字");
            outOfLimit = true;
        }
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_back:
			this.finish();
			break;
		case R.id.et_status_content:			
			break;
		case R.id.btn_status_send:
			if(!outOfLimit) sendStatus();
			break;
		default:
			break;
		}
	}
}
