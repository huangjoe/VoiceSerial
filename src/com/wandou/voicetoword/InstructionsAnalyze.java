package com.wandou.voicetoword;

import android.text.TextUtils;
import java.util.*;

/**
 * Created by young on 2015/7/17.
 */
public class InstructionsAnalyze {
    public static int  InstructionsAnalyze (String listened) {
        listened="烫"+listened;
//        StringBuffer ret = new StringBuffer();
        int small=0;
        int notzero=-1;
        int i=0;
        ArrayList<Integer> Indexs=new ArrayList<Integer>();
        ArrayList<Integer> sorts=new ArrayList<Integer>();
        
            String[]  Str={"后悔","开灯","关灯","增加","减少"};
            for(i=0;i<5;i++)
            {
               if (listened.indexOf(Str[i])!=-1)
               { Indexs.add(listened.indexOf(Str[i]));}
               else Indexs.add(0);
            }
            sorts=(ArrayList)Indexs.clone();
            
                Collections.sort(sorts);

        		for(i=0;i<sorts.size();i++)
        		{
        			if (sorts.get(i)!=0)
        			{notzero=i;
        			small=Indexs.indexOf(sorts.get(notzero));
                    small=Indexs.indexOf(sorts.get(notzero));
        			break;}
        		}
        return small;

    }
}
