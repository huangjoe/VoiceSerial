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
	// ʶ�𴰿�
	private RecognizerDialog iatDialog;
	// ʶ�����
	private SpeechRecognizer iatRecognizer;
	// ���棬���浱ǰ�������������һ������Ӧ�ó���ʹ��.
	private SharedPreferences mSharedPreferences;
	private RecognizerDialogListener recognizerDialogListener = null;

	public VoiceToWord(Context context, String APP_ID) {
		// TODO Auto-generated constructor stub
		// �û���¼
		this.context = context;
		// ��ʼ���������.
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), MODE_PRIVATE);
		SpeechUser.getUser().login(context, null, null, "appid=" + APP_ID,
				listener);
		// ��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		// ��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		iatDialog.setCanceledOnTouchOutside(false);

	}

	public VoiceToWord(Context context, String APP_ID,
			RecognizerDialogListener recognizerDialogListener) {
		this.context = context;
		SpeechUser.getUser().login(context, null, null, "appid=" + APP_ID,
				listener);
		// ��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
		// ��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
		iatDialog = new RecognizerDialog(context);
		// ��dialog���治��ȡ��
		iatDialog.setCanceledOnTouchOutside(false);
		// ��ʼ���������.
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), MODE_PRIVATE);
		this.recognizerDialogListener = recognizerDialogListener;
	}

	public void GetWordFromVoice() {
		boolean isShowDialog = mSharedPreferences.getBoolean("iat_show", true);
		if (isShowDialog) {
			// ��ʾ������дDialog.
			showIatDialog();
		} else {
			if (null == iatRecognizer) {
				iatRecognizer = SpeechRecognizer.createRecognizer(this);
           			// ���÷��ؽ����ʽ
//				iatRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
//
//				String lag = mSharedPreferences.getString(
//						"iat_language_preference", "mandarin");
//				if (lag.equals("en_us")) {
//					// ��������
//					iatRecognizer
//							.setParameter(SpeechConstant.LANGUAGE, "en_us");
//				} else {
//					// ��������
//					iatRecognizer
//							.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//					// ������������
//					iatRecognizer.setParameter(SpeechConstant.ACCENT, lag);
//				}
//				// ��������ǰ�˵�
//				iatRecognizer.setParameter(SpeechConstant.VAD_BOS,
//						mSharedPreferences.getString("iat_vadbos_preference",
//								"4000"));
//				// ����������˵�
//				iatRecognizer.setParameter(SpeechConstant.VAD_EOS,
//						mSharedPreferences.getString("iat_vadeos_preference",
//								"1000"));
//				// ���ñ�����
//				iatRecognizer.setParameter(SpeechConstant.ASR_PTT,
//						mSharedPreferences
//								.getString("iat_punc_preference", "1"));
//				// ������Ƶ����·��
//				iatRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//						Environment.getExternalStorageDirectory()
//								+ "/iflytek/wavaudio.pcm");//�����嵥�ļ������sd����Ȩ��
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
	 * ��ʾ��д�Ի���.
	 * 
	 * @param
	 */
	
	
	public void showIatDialog() {
		if (null == iatDialog) {
			// ��ʼ����дDialog
			iatDialog = new RecognizerDialog(this);
		}

		// ��ȡ�������
		String engine = mSharedPreferences.getString("iat_engine", "iat");

		// ���Grammar_ID����ֹʶ��������дʱGrammar_ID�ĸ���
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		// ������дDialog������
		iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
		// ���ò����ʲ�����֧��8K��16K
		String rate = mSharedPreferences.getString("sf", "sf");
		if (rate.equals("rate8k")) {
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
		} else {
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		}

		if (recognizerDialogListener == null) {
			getRecognizerDialogListener();
		}

		// ��ʾ��д�Ի���
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		 
	}

	private void getRecognizerDialogListener() {
		/**
		 * ʶ��ص�������
		 */
		recognizerDialogListener = new MyRecognizerDialogLister(context);
	}

	/**
	 * �û���¼�ص�������.
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
		// �˳�ʱ�ͷ�����
		iatRecognizer.cancel();
		iatRecognizer.destroy();
	};

	@Override
	protected void onResume() {
		// �ƶ�����ͳ�Ʒ���
		FlowerCollector.onResume(this);
		FlowerCollector.onPageStart("VoiceToWord");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// �ƶ�����ͳ�Ʒ���
		FlowerCollector.onPageEnd("VoiceToWord");
		FlowerCollector.onPause(this);
		super.onPause();
	}
}
