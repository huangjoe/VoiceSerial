package zy.voice;

import static zy.voice.InstructionsAnalyze.InstructionsAnalyze;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.voiceserial.MainActivity;
import com.example.voiceserial.senddata;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * ʶ��ص�������
 */
public class MyRecognizerDialogLister implements RecognizerDialogListener,
        SynthesizerListener {

    public interface MyCallInterface {
        public void setCallfuc(MyCallInterface mc);
    }

    private final Context context;
    public senddata data;
    public int ID = 0;
    // ���غϳɶ���
    private SpeechSynthesizer speechSynthesizer;

    // SharedPreferences SeekBarProgess;
    // Editor SBEditor;
    public MyRecognizerDialogLister(Context context) {
        this.context = context;
        /* setParam(); */
    }

    /**
     * ʶ��ص�����.
     */

    @Override
    public void onBufferProgress(int percent, int beginPos, int endPos,
            String info) {

    }

    /*
     * public void setParam() { //����SpeechSynthesizer ���� speechSynthesizer =
     * SpeechSynthesizer.createSynthesizer(context);
     * speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,
     * "xiaoyan");//Ĭ�ϵģ�������ΪС�ࣨ����Ů����,С÷Ϊ�����������Ч���õ�
     * speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//����
     * speechSynthesizer.setParameter(SpeechConstant.VOLUME,
     * "50");//��������Χ��0---100
     * speechSynthesizer.setParameter(SpeechConstant.PITCH, "50"); } /** percent
     * ������� 0-100 beginPos ������Ƶ�����еĿ�ʼλ�� endPos ������Ƶ�����е�ĩλ�� info ������Ϣ
     */

    // �Ự�����ص��ӿ�,�޴���ʱerrΪnull
    @Override
    public void onCompleted(SpeechError err) {

    }

    @Override
    public void onError(SpeechError error) {// �����������http://open.voicecloud.cn/index.php/default/doccenter/doccenterInner?itemTitle=ZmFx&anchor=Y29udGl0bGU2Ng==
        // TODO Auto-generated method stub
        // String text = "���������ص�";

        VoiceToWord zhangsan = new VoiceToWord(context, null);
        zhangsan.setCallfuc(zhangsan);
        int errorCoder = error.getErrorCode();
        int errorID = 0;
        ID=999;
        switch (errorCoder) {
        case 10118:// �û�û��˵����û������
            Log.v("No Voice", "user don't speak anything");
            zhangsan.CallbackID(ID);
            /*
             * //for test ID= InstructionsAnalyze(text); Toast.makeText(context,
             * String.format("ID = %d", ID),Toast.LENGTH_SHORT).show();
             * //data.sent(ID);
             */
            new senddata().sent(errorID);
            new senddata().sent(errorID);
            ID = 2;
            break;
        case 10200:// ����һ�����
            System.out.println("can't connect to internet");
            new senddata().sent(errorID);
            new senddata().sent(errorID);
            zhangsan.CallbackID(ID);
            break;
        case 10114:// �������ӷ����쳣
            System.out.println("can't connect to internet");
            new senddata().sent(errorID);
            new senddata().sent(errorID);
            zhangsan.CallbackID(ID);
        default:
            System.out.println("Unknown error");
            new senddata().sent(errorID);
            new senddata().sent(errorID);
            zhangsan.CallbackID(ID);
            break;
        }
    }

    // �Զ���Ľ���ص��������ɹ�ִ�е�һ��������ʧ��ִ�еڶ�������
    @Override
    public void onResult(RecognizerResult results, boolean isLast) {

        String text = JsonParser.parseIatResult(results.getResultString());
        // Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        // app.setText(text);
        // LayoutInflater inflater = LayoutInflater.from(context);
        // View rootView = inflater.inflate(R.layout.seekbar,null);
        // sb = (MySeekBar)rootView.findViewById(R.id.seekBar01);

        if (!TextUtils.isEmpty(text)) {// ������ص����ݲ�Ϊ��/**/
            // ��ʼ���ı���Ϣ�ϳ�����
            // speechSynthesizer.startSpeaking(text, this);
            // ��ʼ���������ڴ��ڵ�����
            text = "�ص�";
            text = "��" + text;
            ID = InstructionsAnalyze(text);
            Toast.makeText(context, String.format("ID = %d", ID),
                    Toast.LENGTH_SHORT).show();
            Log.v("Reutrn ID", String.valueOf(ID));
            // ID=Msb.getProgress();
            // sb.setProgress(0);
            // SBEditor.putInt("ID", ID);
            new senddata().sent(ID);
        }
    }

    // ��ʼ����
    @Override
    public void onSpeakBegin() {

    }

    // ��ͣ����
    @Override
    public void onSpeakPaused() {

    }

    /**
     * percent ���Ž��� 0-100 beginPos ������Ƶ�����еĿ�ʼλ�� endPos ������Ƶ�����е�ĩλ��
     * */
    @Override
    public void onSpeakProgress(int percent, int beginPos, int endPos) {

    }

    // �ָ� ����
    @Override
    public void onSpeakResumed() {

    }

}
