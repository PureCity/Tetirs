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
	
	//��ֹ������ж�����
	@Override
	protected void onStart() {	
		super.onStart();
		aExitTheApp = (ExitTheApp) getApplication();
		if (aExitTheApp.getExit()) {
			finish();
		}
	}
	
	
	//���������ʾ�Ŀ�ʼ
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
				//��ʼ��Ϸ
				Intent gameIntent = new Intent(MainActivity.this,TetirsGame.class);
				startActivity(gameIntent);
				break;
			case R.id.A_Begin_Button_GameHelp:
				//��ʾ������Ϣ
				Intent helpIntent = new Intent(MainActivity.this,GameHelp.class);
				startActivity(helpIntent);
				break;
			case R.id.A_Begin_Button_AboutGame:
				//��ʾ��������Ϣ
				Intent goToAboutIntent = new Intent(MainActivity.this, AboutGame.class);
				startActivity(goToAboutIntent);
				break;
			case R.id.A_Begin_Button_ExitGame:
				//�����˳������
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
