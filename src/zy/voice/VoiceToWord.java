package zy.voice;

import zy.voice.MyRecognizerDialogLister.MyCallInterface;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.voiceserial.MainActivity;
import com.example.voiceserial.R;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

public class VoiceToWord extends Activity  implements MyCallInterface {
    
    public MyCallInterface mc;
    private final Context context;
    private final Toast mToast;
    private int ID;
    // 识别窗口
    private RecognizerDialog iatDialog;
    // 识别对象

    final Intent intent = getIntent();  
    private SpeechRecognizer iatRecognizer;
    // 缓存，保存当前的引擎参数到下一次启动应用程序使用.
    private final SharedPreferences mSharedPreferences;

    private RecognizerDialogListener recognizerDialogListener = null;

    /**
     * 用户登录回调监听器.
     */
    private final SpeechListener listener = new SpeechListener() {

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {
                System.out.println("user login success");
            }
        }

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){

        VoiceToWord voice = new VoiceToWord(context, "54ae8c54");// 你申请的appid
    }
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

    public void CallbackID(int IDx) {
        // TODO Auto-generated method stub
        int debug ; 
        ID = IDx;
        String res = ID+"";  
        intent.putExtra("result", res);// 把数据塞入intent里面  
    }
//    
//   public void renewProgress(int ID)
//   {
//       int debug2 = ID;
//       sb = (SeekBar) findViewById(R.id.seekBar01);
//       sb.setProgress(ID);
//    int debug3=sb.getProgress();
//   }
    private void getRecognizerDialogListener() {
        /**
         * 识别回调监听器
         */
        recognizerDialogListener = new MyRecognizerDialogLister(context);
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
                // iatRecognizer.setParameter(SpeechConstant.RESULT_TYPE,
                // "json");
                //
                // String lag = mSharedPreferences.getString(
                // "iat_language_preference", "mandarin");
                // if (lag.equals("en_us")) {
                // // 设置语言
                // iatRecognizer
                // .setParameter(SpeechConstant.LANGUAGE, "en_us");
                // } else {
                // // 设置语言
                // iatRecognizer
                // .setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                // // 设置语言区域
                // iatRecognizer.setParameter(SpeechConstant.ACCENT, lag);
                // }
                // // 设置语音前端点
                // iatRecognizer.setParameter(SpeechConstant.VAD_BOS,
                // mSharedPreferences.getString("iat_vadbos_preference",
                // "4000"));
                // // 设置语音后端点
                // iatRecognizer.setParameter(SpeechConstant.VAD_EOS,
                // mSharedPreferences.getString("iat_vadeos_preference",
                // "1000"));
                // // 设置标点符号
                // iatRecognizer.setParameter(SpeechConstant.ASR_PTT,
                // mSharedPreferences
                // .getString("iat_punc_preference", "1"));
                // // 设置音频保存路径
                // iatRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
                // Environment.getExternalStorageDirectory()
                // + "/iflytek/wavaudio.pcm");//需在清单文件里添加sd卡的权限
            }
            if (iatRecognizer.isListening()) {
                iatRecognizer.stopListening();
                // ((Button)
                // findViewById(android.R.id.button1)).setEnabled(false);
            } else {
            }
        }

//         return ID;
    }

    
    public void onDestroy() {
        // 退出时释放连接
        iatRecognizer.cancel();
        iatRecognizer.destroy();
    };

    @Override
    protected void onPause() {
        // 移动数据统计分析
        FlowerCollector.onPageEnd("VoiceToWord");
        FlowerCollector.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        // 移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart("VoiceToWord");
        super.onResume();
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

    @Override
    public void setCallfuc(MyCallInterface mc) {
        // TODO Auto-generated method stub
        this.mc=mc;
    }
}
