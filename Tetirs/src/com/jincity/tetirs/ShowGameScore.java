package com.jincity.tetirs;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowGameScore extends Activity {
	
	private TextView scoreTextView = null;
	private Button scoreButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamescore);
		
		String getScoreString = getIntent().getExtras().getString("score");
		int score = Integer.parseInt(getScoreString);
		scoreTextView = (TextView)findViewById(R.id.score_title);
		if (score >= 10000) {
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_super_high)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 2000){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_very_high)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 1000){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_middle_high)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 800){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_high)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 500){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_middle)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 200){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_just_so_so)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 100){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_general)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 50){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_low)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else if(score >= 10){
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_very_low)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}else{
			scoreTextView.setText( Html.fromHtml(getString(R.string.score_super_low)
					+"<br><center><h1>您获得了<font color=\"red\"><strong>" + score +"</strong></font>分<h1></center>"));
		}
		
		scoreButton = (Button)findViewById(R.id.score_back);
		scoreButton.setOnClickListener(back);
		
	}
	
	private OnClickListener back = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	
}
