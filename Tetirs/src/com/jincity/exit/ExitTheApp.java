package com.jincity.exit;

import android.app.Application;


//该类专门用来处理本andoird程序的指定activity是否应该结束的标识
public class ExitTheApp extends Application {
	
	private boolean isAppExit = false;
	
	public void setExit(boolean exit){
		this.isAppExit = exit;
	}
	
	public boolean getExit(){
		return this.isAppExit;
	}
	
}
