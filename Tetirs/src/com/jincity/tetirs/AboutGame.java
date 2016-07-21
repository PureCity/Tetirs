package com.jincity.tetirs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutGame extends Activity {

	private TextView aboutDescrbleTextView = null;
	private TextView aboutChangeTextView = null;
	private Button backButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_game);

		backButton = (Button) findViewById(R.id.about_back);
		backButton.setOnClickListener(listener);

		aboutDescrbleTextView = (TextView) findViewById(R.id.a_About_Text_Describle);
		aboutChangeTextView = (TextView) findViewById(R.id.a_About_Text_Change);

		aboutDescrbleTextView.setText(Html
				.fromHtml("描述：一个俄罗斯方块游戏的初级版本,后续过程将逐渐加强娱乐性"));
		aboutChangeTextView.setText(Html.fromHtml("2014.2.10完成1.0版本:<br>实现游戏基本功能<br>"
				+ "2014.2.11完成 1.7版本:<br>修正分数显示错误,<br>" + "将左右移动从每按一下移一格改为持续移动,<br>"
				+ "修改部分文字错误,<br>" + "修改了暂停后还能操作当前图案错误<br>" + "修改了按主键返回手机主页游戏还继续的错误<br>"
				+ "修改了来电话时游戏还在继续进行错误<br>"
				+ "修正显示,使得大部分手机分辨率都能支持"));

	}

	protected OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();

		}
	};
}
