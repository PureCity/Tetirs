package com.jincity.tetirs;

import java.net.ContentHandler;

import javax.security.auth.PrivateCredentialPermission;

import com.jincity.exit.ExitTheApp;
import com.jincity.tetirspanl.CurrentPicture;
import com.jincity.tetirspanl.CurrentTetirsBackground;
import com.jincity.tetirspanl.GameScore;
import com.jincity.tetirspanl.TetirsGirds;
import com.jincity.tetirspanl.TetirsTips;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TetirsGame extends Activity {

	private final String TAG = "TetirsGame";

	// ��ȡ��̨��
	CurrentTetirsBackground aCurrentTetirsBackground = new CurrentTetirsBackground();
	CurrentPicture aCurrentPicture = new CurrentPicture();
	TetirsGirds aTetirsGirds = new TetirsGirds();
	TetirsTips aTetirsTips = new TetirsTips();
	GameScore aGameScore = new GameScore();
	private Handler theGameHandler = null;

	// ��ȡ�����������õĿؼ�
	// private TextView tetrisBoradcastTextView = null;
	private TextView scoreTextView = null;
	private Button pauseButton = null;
	private ImageButton upButton = null;
	private ImageButton leftButton = null;
	private ImageButton downButton = null;
	private ImageButton rightButton = null;

	// ��ȡ�������ӿؼ�
	private TableLayout tetirsTableLayout = null;
	private int allTextViewForGame = aCurrentTetirsBackground.TETRIS_HEIGHT
			* aCurrentTetirsBackground.TETRIS_WIDTH;
	private TextView gameTextView[] = new TextView[allTextViewForGame];

	// ��ȡ��ʾ�������ӿؼ�
	private final int TipNeedGrid = 16;// Ĭ����ʾ����Ҫ16������
	private TableLayout nextTableLayout = null;
	private TextView theTipTextView[] = new TextView[TipNeedGrid];

	// ��Ϸ��ͣ������
	private boolean GameLord = true;// ��Ϸ�Ƿ����
	private boolean GameEnd = false;// ��Ϸ�Ƿ����
	private boolean MoveFlag = true;// ��ǰͼ���Ƿ������,���ƶ�

	// ��Ϸ�ٶ�
	private int BasicSpeed = 500;// Ĭ���ٶ�Ϊ500ms
	private int GoDownSpeed = BasicSpeed;// Ĭ���½��ٶ�ΪĬ���ٶ�

	// �˵�����
	protected static final int MENU_HELP = Menu.FIRST;
	protected static final int MENU_BACK = Menu.FIRST + 1;

	ExitTheApp aExitTheApp = null;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		aExitTheApp = (ExitTheApp) getApplication();
		if (aExitTheApp.getExit()) {
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tetris_game);

		// ʵ������Ϸ������
		// ʵ��������gameTextView
		LinearLayout gameLinearLayout[] = new LinearLayout[allTextViewForGame];// Ϊÿһ����������һ��
																				// LinearLayout����

		// ��ȡ�ֻ���Ϣ,ʹ�����ʵ�ǰ�ֻ���Ļ
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		int PHONE_WIDTH = mDisplayMetrics.widthPixels;
		int PHONE_HEIGHT = mDisplayMetrics.heightPixels;
		Log.i(TAG, "The Phone Width is " + PHONE_WIDTH);
		Log.i(TAG, "The Phone Height is " + PHONE_HEIGHT);
		int Grids_WIDTH = PHONE_WIDTH * 3 / 5
				/ aCurrentTetirsBackground.TETRIS_WIDTH;
		// PHONE_HEIGHT - 120�Ǽ�ȥ�ֻ�֪ͨ���Լ�����������Ƶĸ߶�
		int Grids_HEIGHT = (PHONE_HEIGHT - 120) * 9 / 10
				/ (aCurrentTetirsBackground.TETRIS_HEIGHT - 4 + 1);
		for (int i = 0; i < allTextViewForGame; i++) {
			// ʵ����ÿ������
			gameTextView[i] = new TextView(TetirsGame.this);
			gameTextView[i].setMinHeight(Grids_HEIGHT);
			gameTextView[i].setMinWidth(Grids_WIDTH);
			gameTextView[i].setMaxHeight(Grids_HEIGHT);
			gameTextView[i].setMaxWidth(Grids_WIDTH);
			gameTextView[i]
					.setBackgroundResource(R.color.basic_game_grid_background_color);

			// ʵ����ÿ�����Ӻ�Ĳ������
			gameLinearLayout[i] = new LinearLayout(this);
			gameLinearLayout[i].setPadding(1, 1, 1, 1);
			gameLinearLayout[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			gameLinearLayout[i].addView(gameTextView[i]);

		}

		// �����Ϸ������
		tetirsTableLayout = (TableLayout) findViewById(R.id.Tetris_Game_Panel);
		// tetirsTableLayout.removeAllViews();
		TableRow tetirsTableRow[] = new TableRow[aCurrentTetirsBackground.TETRIS_HEIGHT - 4];// 4�и��ӱ�����
		for (int i = 0; i < aCurrentTetirsBackground.TETRIS_HEIGHT - 4; i++) {// 4�и��ӱ�����
			tetirsTableRow[i] = new TableRow(TetirsGame.this);
			tetirsTableRow[i].setPadding(0, 1, 0, 1);
			tetirsTableRow[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			for (int j = 0; j < aCurrentTetirsBackground.TETRIS_WIDTH; j++) {
				tetirsTableRow[i].addView(gameLinearLayout[(i + 4)
						* aCurrentTetirsBackground.TETRIS_WIDTH + j]);// i+4��ǰ��4�и��ӱ�����
			}
			tetirsTableLayout.addView(tetirsTableRow[i]);
		}

		// ʵ������ʾ������

		LinearLayout TipBackground[] = new LinearLayout[TipNeedGrid];
		for (int i = 0; i < TipNeedGrid; i++) {
			// ʵ����ÿ������
			theTipTextView[i] = new TextView(this);
			theTipTextView[i].setMinHeight(Grids_HEIGHT);
			theTipTextView[i].setMinWidth(Grids_WIDTH);
			theTipTextView[i].setMaxHeight(Grids_HEIGHT);
			theTipTextView[i].setMaxWidth(Grids_WIDTH);
			theTipTextView[i]
					.setBackgroundResource(R.color.basic_next_picture_color);

			// ʵ����ÿ�����Ӻ�Ĳ������
			TipBackground[i] = new LinearLayout(this);
			TipBackground[i].setPadding(1, 1, 1, 1);
			TipBackground[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			TipBackground[i].addView(theTipTextView[i]);
		}

		// �����ʾ������
		nextTableLayout = (TableLayout) findViewById(R.id.tetris_next);
		TableRow tipTableRow[] = new TableRow[4];// 4��
		for (int i = 0; i < tipTableRow.length; i++) {
			tipTableRow[i] = new TableRow(this);
			tipTableRow[i].setPadding(0, 1, 0, 1);
			tipTableRow[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			for (int j = 0; j < tipTableRow.length; j++) {// 4��
				tipTableRow[i]
						.addView(TipBackground[i * tipTableRow.length + j]);
			}
			nextTableLayout.addView(tipTableRow[i]);
		}

		// ��ȡ���ID
		scoreTextView = (TextView) findViewById(R.id.tetris_score);
		pauseButton = (Button) findViewById(R.id.tetris_pause);
		upButton = (ImageButton) findViewById(R.id.tetris_button_up);
		leftButton = (ImageButton) findViewById(R.id.tetris_button_left);
		downButton = (ImageButton) findViewById(R.id.tetris_button_dwon);
		rightButton = (ImageButton) findViewById(R.id.tetris_button_right);

		// ����¼�������
		pauseButton.setOnClickListener(listener);
		upButton.setOnClickListener(listener);
		leftButton.setOnTouchListener(moveContralListener);
		downButton.setOnTouchListener(speedContralListener);// ��Ӵ������Ƽ���
		rightButton.setOnTouchListener(moveContralListener);
		//���ð�ť����͸��
		pauseButton.getBackground().setAlpha(150);
		upButton.getBackground().setAlpha(50);
		leftButton.getBackground().setAlpha(50);
		rightButton.getBackground().setAlpha(50);
		downButton.getBackground().setAlpha(50);

		// ������Ϸ���޸�
		theGameHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					// �޸ĵ�ǰͼ����ʾ���
					for (int i = 0; i < aTetirsTips.WIDTH * aTetirsTips.HEIGHT; i++) {
						if (aTetirsTips.getGirds(i) == aCurrentTetirsBackground.Y) {
							theTipTextView[i]
									.setBackgroundResource(R.color.game_picture_down_background_color);
						} else {
							theTipTextView[i]
									.setBackgroundResource(R.color.basic_next_picture_color);
						}
					}
					break;
				case 1:
					// �޸ĵ�ǰ�ƶ���ͼ��

				case 2:
					// �޸�������Ϸ�������
					for (int i = 0; i < aCurrentTetirsBackground.TETRIS_WIDTH
							* aCurrentTetirsBackground.TETRIS_HEIGHT; i++) {
						if (aTetirsGirds.getGirds(i) == aCurrentTetirsBackground.Y) {
							gameTextView[i]
									.setBackgroundResource(R.color.game_picture_down_background_color);
						} else if (aTetirsGirds.getGirds(i) == aCurrentTetirsBackground.T) {
							gameTextView[i]
									.setBackgroundResource(R.color.game_grid_fixed);
						} else if (aTetirsGirds.getGirds(i) == aCurrentTetirsBackground.F) {
							gameTextView[i]
									.setBackgroundResource(R.color.basic_game_grid_background_color);
						}
					}
					break;
				case 3:
					// �޸ĵ÷����
					int score = aGameScore.getTheGameScore();
					scoreTextView.setText(score + "");
					break;
				case 4:
					// �޸Ĺ�������Ϣ

					break;
				case 6:
					// �ж���Ϸ������Ϣ
					if (GameEnd) {
						Log.i(TAG, "The game is end");
						Intent gameScoreIntent = new Intent(TetirsGame.this,
								ShowGameScore.class);
						gameScoreIntent.putExtra("score",
								aGameScore.getTheGameScore() + "");
						startActivity(gameScoreIntent);
						pauseButton.setText("��ʼ��Ϸ");
						GameLord = false;// ��Ϸ����ͣ
					}
					break;
				default:
					break;
				}
			}

		};
		// ��Ϸ����
		GameGoDown aGameGoDown = new GameGoDown();
		Thread mainGameThread = new Thread(aGameGoDown);
		mainGameThread.start();
	}

	// ��Ϸ��ʼ���е����߳�
	protected class GameGoDown implements Runnable {

		@Override
		public void run() {
			// ���ú�̨����
			aCurrentTetirsBackground.clearBackgroundTipDate(aTetirsTips);// �����ʾ�������
			Message message1 = new Message();
			message1.what = 0;
			theGameHandler.sendMessage(message1);
			aCurrentTetirsBackground.clearBackgroundDate(aTetirsGirds);// �����Ϸ���
			Message message2 = new Message();
			message2.what = 2;
			theGameHandler.sendMessage(message2);
			aCurrentTetirsBackground.resetScore(aGameScore);// ����������
			Message message3 = new Message();
			message3.what = 3;
			theGameHandler.sendMessage(message3);
			Log.i(TAG, "Background Date Is Reset");
			// ������һ��ͼ��
			aCurrentTetirsBackground.CreateNewPicture(aTetirsTips);
			Message message4 = new Message();
			message4.what = 0;
			theGameHandler.sendMessage(message4);
			Log.i(TAG, "New Picture is Create");
			// ����һ��ͼ�����õ���Ϸ���
			aCurrentTetirsBackground.setFirstPicture(aTetirsTips,
					aCurrentPicture, aTetirsGirds);
			Message message5 = new Message();
			message5.what = 2;
			theGameHandler.sendMessage(message5);
			Log.i(TAG, "The picture is set in game girds");
			while (!GameEnd) {
				if (GameLord) {
					// �������еĸ���
					int score = aCurrentTetirsBackground
							.clearCurrentLinePicture(aTetirsGirds);
					Message message6 = new Message();
					message6.what = 2;
					theGameHandler.sendMessage(message6);
					// �޸ķ���
					aCurrentTetirsBackground.plusScore(score, aGameScore);
					Message message7 = new Message();
					message7.what = 3;
					theGameHandler.sendMessage(message7);
					if (score > 0) {
						Log.i(TAG, "Score is plused " + score);
					}
					if (GameLord) {
						// �ж���Ϸ�Ƿ����
						GameEnd = aCurrentTetirsBackground
								.CkeckGameEnd(aTetirsGirds);
						Message message8 = new Message();
						message8.what = 6;
						theGameHandler.sendMessage(message8);
					}
					// �����µ�ͼƬ
					aCurrentTetirsBackground.CreateNewPicture(aTetirsTips);
					Message message9 = new Message();
					message9.what = 0;
					theGameHandler.sendMessage(message9);
					// ��⵱ǰͼ���Ƿ�����½�
					boolean candatedown = aCurrentTetirsBackground
							.CheckDateDown(aCurrentPicture, aTetirsGirds);
					while (candatedown) {
						if (GameLord) {
							aCurrentTetirsBackground.dateDown(aCurrentPicture,
									aTetirsGirds);
							Message message10 = new Message();
							message10.what = 1;
							theGameHandler.sendMessage(message10);
							try {
								Thread.currentThread().sleep(GoDownSpeed);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							candatedown = aCurrentTetirsBackground
									.CheckDateDown(aCurrentPicture,
											aTetirsGirds);
						}
					}
					// �̶���ǰͼƬ
					aCurrentTetirsBackground.setThePicture(aCurrentPicture,
							aTetirsGirds);
					Message message11 = new Message();
					message11.what = 1;
					theGameHandler.sendMessage(message11);
					// ��ȡͼƬ
					aCurrentTetirsBackground.setFirstPicture(aTetirsTips,
							aCurrentPicture, aTetirsGirds);
					Message message12 = new Message();
					message12.what = 2;
					theGameHandler.sendMessage(message12);
				}
			}
		}

	}

	// �����ƶ��Լ���ͣ�¼�����
	protected OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tetris_pause:
				// ��ͣ�¼�
				if (GameLord) {
					GameLord = false;// ��Ϸ��ͣ
					pauseButton.setText("����");
				} else {
					GameLord = true;
					pauseButton.setText("��ͣ");
					if (GameEnd) {// �����ʱ��Ϸ�Ѿ����������¿�ʼ
						GameGoDown aGameGoDown = new GameGoDown();
						Thread new_gameThread = new Thread(aGameGoDown);
						new_gameThread.start();
						GameEnd = false;// ����Ϸδֹͣ
					}
				}
				break;
			// case R.id.tetris_button_left:
			// //����¼�
			// aCurrentTetirsBackground.moveLeft(aCurrentPicture, aTetirsGirds);
			// Message aMessage = new Message();
			// aMessage.what = 1;
			// theGameHandler.sendMessage(aMessage);
			// break;
			// case R.id.tetris_button_right:
			// //�Ҽ��¼�
			// aCurrentTetirsBackground.moveRight(aCurrentPicture,
			// aTetirsGirds);
			// Message bMessage = new Message();
			// bMessage.what = 1;
			// theGameHandler.sendMessage(bMessage);
			// break;
			case R.id.tetris_button_up:
				// ͼ�α���
				if (GameLord) {
					aCurrentTetirsBackground.ChangeCurrentPicture(
							aCurrentPicture, aTetirsGirds);
					Message cMessage = new Message();
					cMessage.what = 1;
					theGameHandler.sendMessage(cMessage);
				}
				break;
			default:
				Log.e(TAG, "Can not find such button id");
				break;
			}
		}
	};

	// �½��ٶȵ���
	protected OnTouchListener speedContralListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.getId() == R.id.tetris_button_dwon) {// ȷ�����µ��� �����½�����
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// ���¼���
					GoDownSpeed = 100;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// ̧����ظ�ԭ�����ٶ�
					GoDownSpeed = BasicSpeed;
				}
			}
			return false;
		}
	};

	// ���Ҽ�����
	protected OnTouchListener moveContralListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (GameLord) {
				if (v.getId() == R.id.tetris_button_left) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// ����¼�
						MoveFlag = true;
						MoveLeftThread aLeftThread = new MoveLeftThread();
						Thread aThread = new Thread(aLeftThread);
						aThread.start();
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						MoveFlag = false;
					}
				} else if (v.getId() == R.id.tetris_button_right) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// �Ҽ��¼�
						MoveFlag = true;
						MoveRightThread aLeftThread = new MoveRightThread();
						Thread aThread = new Thread(aLeftThread);
						aThread.start();
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						MoveFlag = false;
					}
				}
			}
			return false;
		}
	};

	// ��ǰͼ�����Ƶ��߳�
	private class MoveLeftThread implements Runnable {

		@Override
		public void run() {
			while (MoveFlag) {
				aCurrentTetirsBackground
						.moveLeft(aCurrentPicture, aTetirsGirds);
				Message aMessage = new Message();
				aMessage.what = 1;
				theGameHandler.sendMessage(aMessage);
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// ��ǰͼ�����Ƶ��߳�
	private class MoveRightThread implements Runnable {

		@Override
		public void run() {
			while (MoveFlag) {
				aCurrentTetirsBackground.moveRight(aCurrentPicture,
						aTetirsGirds);
				Message aMessage = new Message();
				aMessage.what = 1;
				theGameHandler.sendMessage(aMessage);
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	// �û����·��ؼ��������¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// ���·��ؼ��¼�
			if (GameLord && !GameEnd) {
				GameLord = false;// �����Ϸ���ڼ�������Ϸ��ͣ
				pauseButton.setText("����");
			}
			// �����˳��Ի���
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// ���öԻ������
			isExit.setTitle("ϵͳ��ʾ");
			// ���öԻ�����Ϣ
			isExit.setMessage("ȷ��Ҫ������Ϸ��������?");
			// ���ѡ��ť��ע�����
			isExit.setButton(AlertDialog.BUTTON_POSITIVE, "ȷ��", BackListener);
			isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "ȡ��", BackListener);
			// ��ʾ�Ի���
			isExit.show();
		} else if (keyCode == KeyEvent.KEYCODE_HOME
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// ��������
			if (GameLord && !GameEnd) {
				// �����Ϸ���ڼ�������ͣ
				GameLord = false;
				pauseButton.setText("����");
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/** �����Ի��������button����¼� */
	DialogInterface.OnClickListener BackListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_HELP, 0, "����");
		menu.add(0, MENU_BACK, 0, "����");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_HELP:
			if (GameLord && !GameEnd) {
				GameLord = false;// ��Ϸ��ͣ
				pauseButton.setText("����");
			}
			Intent helpIntent = new Intent(TetirsGame.this, GameHelp.class);
			startActivity(helpIntent);
			break;
		case MENU_BACK:
			if (GameLord && !GameEnd) {
				GameLord = false;// ��Ϸ��ͣ
				pauseButton.setText("����");
			}
			// �����˳��Ի���
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// ���öԻ������
			isExit.setTitle("ϵͳ��ʾ");
			// ���öԻ�����Ϣ
			isExit.setMessage("ȷ��Ҫ������Ϸ��������?");
			// ���ѡ��ť��ע�����
			isExit.setButton(AlertDialog.BUTTON_POSITIVE, "ȷ��", BackListener);
			isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "ȡ��", BackListener);
			// ��ʾ�Ի���
			isExit.show();		
			break;
		default:
			Log.e(TAG, "No such select");
			break;
		}
		return true;
	}

	// �������������ʱ��ͣ����
	@Override
	protected void onPause() {
		super.onPause();
		if (GameLord && !GameEnd) {
			GameLord = false;// ��Ϸ��ͣ
			pauseButton.setText("����");
		}
	}

	// �ָ�����
	@Override
	protected void onResume() {
		super.onResume();
	}

}
