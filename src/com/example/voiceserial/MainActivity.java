package com.example.voiceserial;

import zy.voice.MyRecognizerDialogLister.MyCallInterface;
import zy.voice.VoiceToWord;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnClickListener {

   
    Button but = null;
    SeekBar sb;
    // MySeekBar Msb;
    private EditText content;
    private final int i = 0;
    private int mainID;
    // private boolean VoiceRecognizeFinishflag;
    // SharedPreferences SeekBarProgess;

    private final OnSeekBarChangeListener sbLis = new OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            // String str;

            new senddata().sent(progress);
            // tv.setText(String.valueOf(sb.getProgress()));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // ��ʼ�϶�ʱ��������onProgressChanged��������onStartTrackingTouch��ֹͣ�϶�ǰֻ����һ��
            // ��onProgressChangedֻҪ���϶����ͻ��ظ�����
            // new senddata().sent(0);
            // tv.setText(String.valueOf(sb.getProgress()));

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // �����϶�ʱ����
            // new senddata().sent(0);
            // tv.setText(String.valueOf(sb.getProgress()));

        }

    };

    static {
        System.loadLibrary("serialtest");
    }

    @Override
    public void onClick(View v) {
        // SeekBarProgess = getSharedPreferences("ss", Context.MODE_PRIVATE);
        // ��ȡss�ļ���User��Ӧ�����ݣ�ע��ڶ������������˼�ֵ������ʱû����ֵ����Ĭ�Ϸ��صڶ���������ֵ
        // switch (v.getId()) {
        // ��д��ť
        // case R.id.button1:

        Intent intent = new Intent(MainActivity.this,VoiceToWord.class);  

        VoiceToWord voice = new VoiceToWord(MainActivity.this, "54ae8c54");// �������appid
        MainActivity.this.startActivityForResult(intent, 15);  
        
//        int debug=mainID;
        // new senddata().sent(0);
        // ID = SeekBarProgess.getInt("ID",ID);
//         sb.setProgress(mainID);
        // break;

        // }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        // TODO Auto-generated method stub  
        super.onActivityResult(requestCode, resultCode, data);  
        switch (requestCode) {// ͨ��requestCode��������������ĸ�activity  
        case 15:// ȡ������Activity2�����ݣ�����ʾ�ڻ�����  
            String resultstr1 = data.getStringExtra("result");
            String debugstr=resultstr1;
            debugstr=resultstr1;
            break;  
        case 25:  
        default:  
            break;  
        }  
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but = (Button) findViewById(R.id.button1);
        but.setOnClickListener(this);
        sb = (SeekBar) findViewById(R.id.seekBar01);
        sb.setOnSeekBarChangeListener(sbLis);
        // com3.Open(3, 115200);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // String str;
        // int x=0;
        // try {
        // //��ȡlogcat��־��Ϣ
        // Process mLogcatProc = null;
        // BufferedReader reader = null;
        // mLogcatProc = Runtime.getRuntime().exec(new String[] {
        // "logcat","Reutrn ID:E *:S" });
        // reader = new BufferedReader(new
        // InputStreamReader(mLogcatProc.getInputStream()));
        //
        // String line;
        //
        // while ((line = reader.readLine()) != null) {
        //
        // if (line.length()> 0) {
        // str=line.toString();
        // x = Integer.parseInt(str);
        // }
        // }
        //
        // } catch (Exception e) {
        //
        // e.printStackTrace();
        // sb.setProgress(x);
        // }
        // content.setText(((MyApplicaton)getApplication()).getText());
    }

   
   }
