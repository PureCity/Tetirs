package com.jincity.tetirs;

import com.jincity.exit.ExitTheApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ExitTheApp aExitTheApp = null;
	
	
	private Button exitGameButton = null;
	private Button aboutGameButton = null;
	private Button gameHelpButton = null;
	private Button startGameButton = null;
	
	//终止程序的判断条件
	@Override
	protected void onStart() {	
		super.onStart();
		aExitTheApp = (ExitTheApp) getApplication();
		if (aExitTheApp.getExit()) {
			finish();
		}
	}
	
	
	//程序界面显示的开始
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		A_Begin_OnClickListener aBegin_OnClickListener = new A_Begin_OnClickListener();
		
		
		exitGameButton = (Button)findViewById(R.id.A_Begin_Button_ExitGame);
		aboutGameButton = (Button)findViewById(R.id.A_Begin_Button_AboutGame);
		gameHelpButton = (Button)findViewById(R.id.A_Begin_Button_GameHelp);
		startGameButton = (Button)findViewById(R.id.A_Begin_Button_StartGame);
		
		exitGameButton.setOnClickListener(aBegin_OnClickListener);
		aboutGameButton.setOnClickListener(aBegin_OnClickListener);
		gameHelpButton.setOnClickListener(aBegin_OnClickListener);
		startGameButton.setOnClickListener(aBegin_OnClickListener);
		
		exitGameButton.getBackground().setAlpha(100);
		aboutGameButton.getBackground().setAlpha(100);
		gameHelpButton.getBackground().setAlpha(100);
		startGameButton.getBackground().setAlpha(100);
		
	}
	
	public class A_Begin_OnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			int theId = v.getId();
			switch (theId) {
			case R.id.A_Begin_Button_StartGame:
				//开始游戏
				Intent gameIntent = new Intent(MainActivity.this,TetirsGame.class);
				startActivity(gameIntent);
				break;
			case R.id.A_Begin_Button_GameHelp:
				//显示帮助信息
				Intent helpIntent = new Intent(MainActivity.this,GameHelp.class);
				startActivity(helpIntent);
				break;
			case R.id.A_Begin_Button_AboutGame:
				//显示软件相关信息
				Intent goToAboutIntent = new Intent(MainActivity.this, AboutGame.class);
				startActivity(goToAboutIntent);
				break;
			case R.id.A_Begin_Button_ExitGame:
				//彻底退出本软件
				aExitTheApp = (ExitTheApp) getApplication();
				aExitTheApp.setExit(true);
				finish();
				System.exit(0);
				break;
			default:
				break;
			}
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			ExitTheApp aExitTheApp = (ExitTheApp) getApplication();
			aExitTheApp.setExit(true);
			finish();
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
