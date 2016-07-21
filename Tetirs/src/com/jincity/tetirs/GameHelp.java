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
		
		helpTextView.setText(Html.fromHtml("<h1>�����ĵ�</h1>" +
				"<font color=\"red\">1.</font>��Ϸ�޷�������,��ͷ�Ϊ0<br>" +
				"<font color=\"red\">2.</font>ͼ������һ�и���ʱ����������1��<br>" +
				"<font color=\"red\">3.</font>��ͼ���߶ȴﵽ���Ӷ���ʱ��Ϸ����<br>" +
				"<font color=\"red\">4.</font>��Ϸ�н��ή��7�ֲ�ͬ��ͼ��<br>" +
				"<font color=\"red\">5.</font>ͨ����,�Ҽ��ƶ���ǰ�ƶ�ͼ��<br>" +
				"<font color=\"red\">6.</font>ͨ���ϼ���תͼ��<br>" +
				"<font color=\"red\">7.</font>ͨ���¼�����ͼ���½��ٶ�<br>" +
				"<font color=\"red\">8.</font>ͨ����ͣ����ʱֹͣ��Ϸ<br>" +
				"<font color=\"blue\">ף����Ϸ���</font><br>" +
				"<font color=\"yellow\">��ϷBUG�뷴����</font><br>" +
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
