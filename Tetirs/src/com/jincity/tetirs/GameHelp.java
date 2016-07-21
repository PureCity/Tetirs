package com.jincity.tetirs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameHelp extends Activity {
	
	private TextView helpTextView  = null;
	private Button helpClose = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamehelp);
		
		helpTextView = (TextView)findViewById(R.id.help_text);
		helpClose = (Button)findViewById(R.id.help_close);
		
		helpTextView.setText(Html.fromHtml("<h1>帮助文档</h1>" +
				"<font color=\"red\">1.</font>游戏无分数上限,最低分为0<br>" +
				"<font color=\"red\">2.</font>图案满足一行格子时将消除并加1分<br>" +
				"<font color=\"red\">3.</font>当图案高度达到格子顶端时游戏结束<br>" +
				"<font color=\"red\">4.</font>游戏中将会降落7种不同的图案<br>" +
				"<font color=\"red\">5.</font>通过左,右键移动当前移动图案<br>" +
				"<font color=\"red\">6.</font>通过上键旋转图案<br>" +
				"<font color=\"red\">7.</font>通过下键加速图案下降速度<br>" +
				"<font color=\"red\">8.</font>通过暂停键暂时停止游戏<br>" +
				"<font color=\"blue\">祝您游戏愉快</font><br>" +
				"<font color=\"yellow\">游戏BUG请反馈至</font><br>" +
				"<font color=\"red\">jincitykasto@vip.qq.com</font>"));
		helpClose.setOnClickListener(listener);
		
		
	}
	
	protected OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();	
		}
	};
	
}
