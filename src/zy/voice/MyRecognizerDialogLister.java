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
 * ʶ��ص�������
 */
public class MyRecognizerDialogLister implements RecognizerDialogListener,SynthesizerListener{
	private Context context;
	public senddata data;
	private int ID=0;
	//���غϳɶ���
	private SpeechSynthesizer speechSynthesizer;
//	SharedPreferences SeekBarProgess;
//	Editor SBEditor;
	public MyRecognizerDialogLister(Context context) {
		this.context = context;
		 /*setParam();*/
	}

	// �Զ���Ľ���ص��������ɹ�ִ�е�һ��������ʧ��ִ�еڶ�������
	@Override
	public void onResult(RecognizerResult results, boolean isLast) {
		String text = JsonParser.parseIatResult(results.getResultString());
		//Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		//app.setText(text);
//		LayoutInflater inflater = LayoutInflater.from(context);
//	    View rootView = inflater.inflate(R.layout.seekbar,null);
	   //sb = (MySeekBar)rootView.findViewById(R.id.seekBar01);
		

			if(!TextUtils.isEmpty(text)){//������ص����ݲ�Ϊ��/**/
	    	//��ʼ���ı���Ϣ�ϳ�����
	    	//speechSynthesizer.startSpeaking(text, this);
			//��ʼ���������ڴ��ڵ�����
	    	//text="�ص�";
	    	text="��"+text;
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
	 * ʶ��ص�����.
	 */

	 @ Override
	
	public void onError(SpeechError error) {//�����������http://open.voicecloud.cn/index.php/default/doccenter/doccenterInner?itemTitle=ZmFx&anchor=Y29udGl0bGU2Ng==
		// TODO Auto-generated method stub
		//String text = "���������ص�";
		int errorCoder = error.getErrorCode();
		int errorID=0;
		switch (errorCoder) {
		case 10118://�û�û��˵����û������
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
		case 10200://����һ�����
			System.out.println("can't connect to internet");
			new senddata().sent(errorID);
			new senddata().sent(errorID);
			break;
		case 10114://�������ӷ����쳣
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
		//����SpeechSynthesizer ����
		speechSynthesizer = SpeechSynthesizer.createSynthesizer(context);
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//Ĭ�ϵģ�������ΪС�ࣨ����Ů����,С÷Ϊ�����������Ч���õ�
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//����
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");//��������Χ��0---100
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
	}
     /**
      *  percent  ������� 0-100
      *  beginPos ������Ƶ�����еĿ�ʼλ��
      *  endPos ������Ƶ�����е�ĩλ��
      *  info   ������Ϣ
      * */

	@Override
	public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
		
	}
     //�Ự�����ص��ӿ�,�޴���ʱerrΪnull
	@Override
	public void onCompleted(SpeechError err) {
		
		
	}
    //��ʼ����
	@Override
	public void onSpeakBegin() {
		
		
	}
    //��ͣ����
	@Override
	public void onSpeakPaused() {
	
		
	}
	  /**
     *  percent  ���Ž��� 0-100
     *  beginPos  ������Ƶ�����еĿ�ʼλ��
     *  endPos  ������Ƶ�����е�ĩλ��
     * */
	@Override
	public void onSpeakProgress(int percent, int beginPos, int endPos) {
		
		
	}
    //�ָ� ����
	@Override
	public void onSpeakResumed() {
		
		
	}
	

}
