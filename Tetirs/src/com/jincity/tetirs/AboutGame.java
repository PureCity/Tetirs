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
				.fromHtml("������һ������˹������Ϸ�ĳ����汾,�������̽��𽥼�ǿ������"));
		aboutChangeTextView.setText(Html.fromHtml("2014.2.10���1.0�汾:<br>ʵ����Ϸ��������<br>"
				+ "2014.2.11��� 1.7�汾:<br>����������ʾ����,<br>" + "�������ƶ���ÿ��һ����һ���Ϊ�����ƶ�,<br>"
				+ "�޸Ĳ������ִ���,<br>" + "�޸�����ͣ���ܲ�����ǰͼ������<br>" + "�޸��˰����������ֻ���ҳ��Ϸ�������Ĵ���<br>"
				+ "�޸������绰ʱ��Ϸ���ڼ������д���<br>"
				+ "������ʾ,ʹ�ô󲿷��ֻ��ֱ��ʶ���֧��"));

	}

	protected OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();

		}
	};
}
