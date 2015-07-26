package zy.voice;

import android.text.TextUtils;
import java.util.*;

/**
 * Created by young on 2015/7/17.
 */
public class InstructionsAnalyze {
    public static int  InstructionsAnalyze (String listened) {
        StringBuffer ret = new StringBuffer();
        int small=0;
        int notzero=0;
        int i=0;
        ArrayList<Integer> Indexs=new ArrayList<Integer>();
        ArrayList<Integer> sorts=new ArrayList<Integer>();
        try {
            String[]  Str={"我不说话","开灯","关灯","音量加","音量减"};
            for(i=0;i<5;i++)
            {
               if (listened.indexOf(Str[i])!=-1)
                Indexs.add(listened.indexOf(Str[i]));
               else Indexs.add(0);
            }
            sorts=(ArrayList)Indexs.clone();
            if(!Indexs.isEmpty())
            {
                Collections.sort(sorts);

        		for(i=0;i<=(sorts.size());i++)
        		{
        			if (sorts.get(i)!=0)
        			{notzero=i;break;}
        			else notzero=0;
        		}
                small=Indexs.indexOf(sorts.get(notzero));
                small=Indexs.indexOf(sorts.get(notzero));
            }

        }catch(Exception e){
        }

        return small;
    }
   
}
