package com.wandou.voicetoword;

 
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.wandou.voicetoword.InstructionsAnalyze;
import com.wandou.voicetoword.senddata;
import com.wandou.voicetoword.*;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends  Activity implements OnClickListener, OnSeekBarChangeListener {

	protected static final String TAG = "IatDemo";
	public Button mRecognize;
	public EditText mResultText;
	private SeekBar mSeekBar;
	private int seekBarProgress;
	private Toast mToast;
	public int times;
	private int ID;
	public senddata data;
	private int progressnow;
	
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	private SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initLayout();

        data.sent(999);
		SpeechUtility.createUtility(this, SpeechConstant.APPID +"=55b71019");   
		
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		
		mIat = SpeechRecognizer.createRecognizer(MainActivity.this,
				mInitListener);

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(MainActivity.this, mInitListener);

		mSharedPreferences = getSharedPreferences(this.getPackageName(),MODE_PRIVATE);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
	 
	}

	private void initLayout() {
		// TODO Auto-generated method stub
		mRecognize= (Button) findViewById(R.id.button);
		mResultText = (EditText) findViewById(R.id.editText);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mRecognize.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
	}
	
	int ret = 0; // 函数调用返回值
	// 开始听写
	// 如何判断一次听写结束：OnResult isLast=true 或者 onError
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mResultText.setText(null);// 清空显示内容
		mIatResults.clear();
		// 设置参数
	    setParam();
	    times=0;
	    boolean isShowDialog = mSharedPreferences.getBoolean(
				getString(R.string.pref_key_iat_show), true);
		if (isShowDialog) {
			// 显示听写对话框
			mIatDialog.setListener(mRecognizerDialogListener);
			mIatDialog.show();
			showTip(getString(R.string.text_begin));
		} else {
			// 不显示听写对话框
			ret = mIat.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				showTip("听写失败,错误码：" + ret);
			} else {
				showTip(getString(R.string.text_begin));
			}
		}
		
	}
 
	
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			printResult(results);
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};
	
	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}
	
	
	
	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code);
			}
		}
	};
	
	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			// 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.e(TAG, results.getResultString());
			printResult(results);
			if (isLast) {
				// TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("当前正在说话，音量大小：" + volume);
			Log.d(TAG, "返回音频数据："+data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};
	
	private void printResult(RecognizerResult results) {
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}
		String resultText = resultBuffer.toString();
		mResultText.setText(resultText);
		mResultText.setSelection(mResultText.length());
		
		 text=resultText;
		 if (times==0)
	      {
		     ID = InstructionsAnalyze.InstructionsAnalyze(text);
		     Toast.makeText(MainActivity.this, String.format("ID = %d", ID),
	                  Toast.LENGTH_SHORT).show();
	          Log.e("Reutrn ID", String.valueOf(ID));
		     if (ID!=0)
		     {
	      // ID=Msb.getProgress();
	      // sb.setProgress(0);
	      // SBEditor.putInt("ID", ID);
	      //new senddata().sent(ID);
	      progressnow=mSeekBar.getProgress();
	      switch (ID)
	      {
	      case 3://increase
	          if (progressnow==5)
	              mSeekBar.setProgress(5);
	          else
	          {
	              mSeekBar.setProgress(progressnow+1);
	          }
	          break;
	      case 4://decrease
	          if (progressnow==0)
	              mSeekBar.setProgress(0);
	          else
	          {
	              mSeekBar.setProgress(progressnow-1);
	          }
	          break;
	       default:
	           mSeekBar.setProgress(ID);
	      }
		     }
		     else
		     {
		         // flytek AI resoponse here
		         Toast.makeText(MainActivity.this, text,Toast.LENGTH_SHORT).show();
		     }
	      times++;
	      }
	}
		
		
//		if ((resultText.trim()).equals("增大")) {
//			changeSeekBarLevel();
//		}
//		if ((resultText.trim()).equals("关闭") || (resultText.trim()).equals("关灯")) {
//			closeLight();
//		}

//	}
	
	private void closeLight() {
		// TODO Auto-generated method stub
		
	}

	private void changeSeekBarLevel() {
		// TODO Auto-generated method stub
		if (seekBarProgress >=7) {
			seekBarProgress = 0;
		}
		mSeekBar.setProgress(seekBarProgress+1);
	}

	/**
	 * 参数设置
	 * 
	 * @param param
	 * @return
	 */
	public void setParam() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// 设置听写引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		String lag = mSharedPreferences.getString("iat_language_preference",
				"mandarin");
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		
		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		
		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
		
		// 设置听写结果是否结果动态修正，为“1”则在听写过程中动态递增地返回结果，否则只在听写结束之后返回最终结果
		// 注：该参数暂时只对在线听写有效
		mIat.setParameter(SpeechConstant.ASR_DWA, mSharedPreferences.getString("iat_dwa_preference", "0"));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		seekBarProgress = progress;
        new senddata().sent(progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
