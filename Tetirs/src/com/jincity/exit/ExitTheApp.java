package com.jincity.exit;

import android.app.Application;


//����ר����������andoird�����ָ��activity�Ƿ�Ӧ�ý����ı�ʶ
public class ExitTheApp extends Application {
	
	private boolean isAppExit = false;
	
	public void setExit(boolean exit){
		this.isAppExit = exit;
	}
	
	public boolean getExit(){
		return this.isAppExit;
	}
	
}
