package com.wandou.voicetoword;

import java.util.List;

import android.R.string;
import android.os.Bundle;

import com.wandou.voicetoword.serial;

public class senddata {

	private serial com3 = new serial();
//	private int IDx[] = {0};
	//private string IDs;
	private int i=0;
	public void sent(int ID) {
		
		/*
		Integer.toString(i)
		(CharSequence)String
		*/
		
		String s ;//= "Hello World!";
		s= String.valueOf(ID);	
		// String => CharSequence conversion:

		CharSequence tx = s;  // String is already a CharSequence
		
		com3.Open(3, 115200);
			
			int[] text = new int[tx.length()];
			
        for (i=0; i<tx.length(); i++) 
        {
                text[i] = tx.charAt(i);
        }
//        	IDx[1]=160;
        	//	com3.Write(IDx, IDx.length);
			com3.Write(text, tx.length());
		/*
        
        IDx[0]=ID;
        com2.Write(IDx, 4);
        */
        
    }
	static{
        System.loadLibrary("serialtest");
}
}
