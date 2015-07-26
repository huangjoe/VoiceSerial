package zy.voice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;


public class VoiceToWord extends Activity {
	private Context context;
	private Toast mToast;
	private boolean ID;
	// 识别窗口
	private RecognizerDialog iatDialog;
	// 识别对象
	private SpeechRecognizer iatRecognizer;
	// 缓存，保存当前的引擎参数到下一次启动应用程序使用.
	private SharedPreferences mSharedPreferences;
	private RecognizerDialogListener recognizerDialogListener = null;

	public VoiceToWord(Context context, String APP_ID) {
		// TODO Auto-generated constructor stub
		// 用户登录
		this.context = context;
		// 初始化缓存对象.
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), MODE_PRIVATE);
		SpeechUser.getUser().login(context, null, null, "appid=" + APP_ID,
				listener);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		iatDialog.setCanceledOnTouchOutside(false);

	}

	public VoiceToWord(Context context, String APP_ID,
			RecognizerDialogListener recognizerDialogListener) {
		this.context = context;
		SpeechUser.getUser().login(context, null, null, "appid=" + APP_ID,
				listener);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		// 在dialog外面不能取消
		iatDialog.setCanceledOnTouchOutside(false);
		// 初始化缓存对象.
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), MODE_PRIVATE);
		this.recognizerDialogListener = recognizerDialogListener;
	}

	public void GetWordFromVoice() {
		boolean isShowDialog = mSharedPreferences.getBoolean("iat_show", true);
		if (isShowDialog) {
			// 显示语音听写Dialog.
			showIatDialog();
		} else {
			if (null == iatRecognizer) {
				iatRecognizer = SpeechRecognizer.createRecognizer(this);
           			// 设置返回结果格式
//				iatRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
//
//				String lag = mSharedPreferences.getString(
//						"iat_language_preference", "mandarin");
//				if (lag.equals("en_us")) {
//					// 设置语言
//					iatRecognizer
//							.setParameter(SpeechConstant.LANGUAGE, "en_us");
//				} else {
//					// 设置语言
//					iatRecognizer
//							.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//					// 设置语言区域
//					iatRecognizer.setParameter(SpeechConstant.ACCENT, lag);
//				}
//				// 设置语音前端点
//				iatRecognizer.setParameter(SpeechConstant.VAD_BOS,
//						mSharedPreferences.getString("iat_vadbos_preference",
//								"4000"));
//				// 设置语音后端点
//				iatRecognizer.setParameter(SpeechConstant.VAD_EOS,
//						mSharedPreferences.getString("iat_vadeos_preference",
//								"1000"));
//				// 设置标点符号
//				iatRecognizer.setParameter(SpeechConstant.ASR_PTT,
//						mSharedPreferences
//								.getString("iat_punc_preference", "1"));
//				// 设置音频保存路径
//				iatRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//						Environment.getExternalStorageDirectory()
//								+ "/iflytek/wavaudio.pcm");//需在清单文件里添加sd卡的权限
			}
			if (iatRecognizer.isListening()) {
				iatRecognizer.stopListening();
				// ((Button)
				// findViewById(android.R.id.button1)).setEnabled(false);
			} else {
			}
		}
		
//		return ID;
	}


	/**
	 * 显示听写对话框.
	 * 
	 * @param
	 */
	
	
	public void showIatDialog() {
		if (null == iatDialog) {
			// 初始化听写Dialog
			iatDialog = new RecognizerDialog(this);
		}

		// 获取引擎参数
		String engine = mSharedPreferences.getString("iat_engine", "iat");

		// 清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		// 设置听写Dialog的引擎
		iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
		// 设置采样率参数，支持8K和16K
		String rate = mSharedPreferences.getString("sf", "sf");
		if (rate.equals("rate8k")) {
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
		} else {
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		}

		if (recognizerDialogListener == null) {
			getRecognizerDialogListener();
		}

		// 显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		 
	}

	private void getRecognizerDialogListener() {
		/**
		 * 识别回调监听器
		 */
		recognizerDialogListener = new MyRecognizerDialogLister(context);
	}

	/**
	 * 用户登录回调监听器.
	 */
	private SpeechListener listener = new SpeechListener() {

		@Override
		public void onData(byte[] arg0) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error != null) {
				System.out.println("user login success");
			}
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}
	};

	protected void onDestroy() {
		// 退出时释放连接
		iatRecognizer.cancel();
		iatRecognizer.destroy();
	};

	@Override
	protected void onResume() {
		// 移动数据统计分析
		FlowerCollector.onResume(this);
		FlowerCollector.onPageStart("VoiceToWord");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// 移动数据统计分析
		FlowerCollector.onPageEnd("VoiceToWord");
		FlowerCollector.onPause(this);
		super.onPause();
	}
}
