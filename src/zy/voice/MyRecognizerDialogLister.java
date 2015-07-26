package zy.voice;

import static zy.voice.InstructionsAnalyze.InstructionsAnalyze;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import android.app.Activity;    
import android.os.Bundle;    
import android.widget.TextView;  

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.voiceserial.R;
import com.example.voiceserial.senddata;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 识别回调监听器
 */
public class MyRecognizerDialogLister implements RecognizerDialogListener,SynthesizerListener{
	private Context context;
	public senddata data;
	private int ID=0;
	//本地合成对象
	private SpeechSynthesizer speechSynthesizer;
//	SharedPreferences SeekBarProgess;
//	Editor SBEditor;
	public MyRecognizerDialogLister(Context context) {
		this.context = context;
		 /*setParam();*/
	}

	// 自定义的结果回调函数，成功执行第一个方法，失败执行第二个方法
	@Override
	public void onResult(RecognizerResult results, boolean isLast) {
		String text = JsonParser.parseIatResult(results.getResultString());
		//Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		//app.setText(text);
//		LayoutInflater inflater = LayoutInflater.from(context);
//	    View rootView = inflater.inflate(R.layout.seekbar,null);
	   //sb = (MySeekBar)rootView.findViewById(R.id.seekBar01);
		

			if(!TextUtils.isEmpty(text)){//如果返回的内容不为空/**/
	    	//开始把文本信息合成语音
	    	//speechSynthesizer.startSpeaking(text, this);
			//开始分析句子内存在的命令
	    	//text="关灯";
	    	text="烫"+text;
			ID= InstructionsAnalyze(text);
			Toast.makeText(context, String.format("ID = %d", ID),Toast.LENGTH_SHORT).show();
			Log.v("Reutrn ID",String.valueOf(ID));
			//ID=Msb.getProgress();
			//sb.setProgress(0);
//			SBEditor.putInt("ID", ID);
			new senddata().sent(ID);	
	    }
	} 
	
	
	
	
	public  int getID() {
		return ID;
	} 
	/**
	 * 识别回调错误.
	 */

	 @ Override
	
	public void onError(SpeechError error) {//错误码请访问http://open.voicecloud.cn/index.php/default/doccenter/doccenterInner?itemTitle=ZmFx&anchor=Y29udGl0bGU2Ng==
		// TODO Auto-generated method stub
		//String text = "烫五你好请关灯";
		int errorCoder = error.getErrorCode();
		int errorID=0;
		switch (errorCoder) {
		case 10118://用户没有说话，没有数据
			Log.v("No Voice","user don't speak anything");
			
		/*	
			//for test
			ID= InstructionsAnalyze(text);
			Toast.makeText(context, String.format("ID = %d", ID),Toast.LENGTH_SHORT).show();
			//data.sent(ID);			
			*/
			new senddata().sent(errorID);
			new senddata().sent(errorID);
			break;
		case 10200://网络一般错误
			System.out.println("can't connect to internet");
			new senddata().sent(errorID);
			new senddata().sent(errorID);
			break;
		case 10114://网络连接发生异常
			System.out.println("can't connect to internet");
			new senddata().sent(errorID);
			new senddata().sent(errorID);
		default:
			System.out.println("Unknown error");
			new senddata().sent(errorID);
			new senddata().sent(errorID);
			break;
		}
	}

	
	/*
	public void setParam()
	{
		//创建SpeechSynthesizer 对象
		speechSynthesizer = SpeechSynthesizer.createSynthesizer(context);
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//默认的，发音人为小燕（青年女声）,小梅为新引擎参数，效果好点
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//语速
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");//音量，范围是0---100
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
	}
     /**
      *  percent  缓冲进度 0-100
      *  beginPos 缓冲音频在文中的开始位置
      *  endPos 缓冲音频在文中的末位置
      *  info   附加信息
      * */

	@Override
	public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
		
	}
     //会话结束回调接口,无错误时err为null
	@Override
	public void onCompleted(SpeechError err) {
		
		
	}
    //开始播放
	@Override
	public void onSpeakBegin() {
		
		
	}
    //暂停播放
	@Override
	public void onSpeakPaused() {
	
		
	}
	  /**
     *  percent  播放进度 0-100
     *  beginPos  播放音频在文中的开始位置
     *  endPos  播放音频在文中的末位置
     * */
	@Override
	public void onSpeakProgress(int percent, int beginPos, int endPos) {
		
		
	}
    //恢复 播放
	@Override
	public void onSpeakResumed() {
		
		
	}
	

}
