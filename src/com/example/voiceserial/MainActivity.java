package com.example.voiceserial;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.topeet.serialtest.*;
import android.widget.Button;
import android.widget.EditText;
import zy.voice.*;


public class MainActivity extends Activity implements OnClickListener{
	Button but = null;
	SeekBar sb;
	//MySeekBar Msb;
	private EditText content;
	private int i=0;
	private int ID;
//	private boolean VoiceRecognizeFinishflag;
//	SharedPreferences SeekBarProgess;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		but = (Button) findViewById(R.id.button1);
		but.setOnClickListener(this);
        sb=(SeekBar)findViewById(R.id.seekBar01);
        sb.setOnSeekBarChangeListener(sbLis);
	//	com3.Open(3, 115200);
	}
	
	@Override
	public void onClick(View v) {
//		SeekBarProgess = getSharedPreferences("ss", Context.MODE_PRIVATE);
			//获取ss文件中User对应的数据，注意第二个参数，若此键值对中暂时没有数值，则默认返回第二个参数的值
			//switch (v.getId()) {
			//听写按钮
			//case R.id.button1:
				VoiceToWord voice = new VoiceToWord(MainActivity.this,"54ae8c54");//你申请的appid
				voice.GetWordFromVoice();
//				new senddata().sent(0);	
//				ID = SeekBarProgess.getInt("ID",ID);
//				sb.setProgress(ID);
				//break;
			
			//}
	}
	
	    	    	     
	private OnSeekBarChangeListener sbLis=new OnSeekBarChangeListener(){
		 
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
//			String str;
			
			new senddata().sent(progress);
			//tv.setText(String.valueOf(sb.getProgress()));
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// 开始拖动时触发，与onProgressChanged区别在于onStartTrackingTouch在停止拖动前只触发一次
			//而onProgressChanged只要在拖动，就会重复触发
			//new senddata().sent(0);
			//tv.setText(String.valueOf(sb.getProgress()));
			
		}
		
		  
		
 
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			//结束拖动时触发
			//new senddata().sent(0);
			//tv.setText(String.valueOf(sb.getProgress()));
			
			
		}
    	
    };
	
	@Override
	protected void onResume() {
		super.onResume();
//		String str;
//		int x=0;
//		try {   
//	        //获取logcat日志信息   
//		Process mLogcatProc = null;   
//		BufferedReader reader = null; 
//	    mLogcatProc = Runtime.getRuntime().exec(new String[] { "logcat","Reutrn ID:E *:S" });   
//	    reader = new BufferedReader(new InputStreamReader(mLogcatProc.getInputStream()));   
//	   
//	    String line;   
//	      
//	    while ((line = reader.readLine()) != null) {   
//	          
//	        if (line.length()> 0) {   
//	        	 str=line.toString();
//	        	x = Integer.parseInt(str);
//	        }   
//	    }   
//	   
//	} catch (Exception e) {   
//	   
//	    e.printStackTrace(); 
//	    sb.setProgress(x);
//	} 
		//content.setText(((MyApplicaton)getApplication()).getText());
	}
	static{
        System.loadLibrary("serialtest");
}
}


