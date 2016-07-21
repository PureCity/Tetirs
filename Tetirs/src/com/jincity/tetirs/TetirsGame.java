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

	// 获取后台类
	CurrentTetirsBackground aCurrentTetirsBackground = new CurrentTetirsBackground();
	CurrentPicture aCurrentPicture = new CurrentPicture();
	TetirsGirds aTetirsGirds = new TetirsGirds();
	TetirsTips aTetirsTips = new TetirsTips();
	GameScore aGameScore = new GameScore();
	private Handler theGameHandler = null;

	// 获取基本的已设置的控件
	// private TextView tetrisBoradcastTextView = null;
	private TextView scoreTextView = null;
	private Button pauseButton = null;
	private ImageButton upButton = null;
	private ImageButton leftButton = null;
	private ImageButton downButton = null;
	private ImageButton rightButton = null;

	// 获取面板以添加控件
	private TableLayout tetirsTableLayout = null;
	private int allTextViewForGame = aCurrentTetirsBackground.TETRIS_HEIGHT
			* aCurrentTetirsBackground.TETRIS_WIDTH;
	private TextView gameTextView[] = new TextView[allTextViewForGame];

	// 获取提示面板以添加控件
	private final int TipNeedGrid = 16;// 默认提示框需要16个格子
	private TableLayout nextTableLayout = null;
	private TextView theTipTextView[] = new TextView[TipNeedGrid];

	// 游戏暂停控制器
	private boolean GameLord = true;// 游戏是否继续
	private boolean GameEnd = false;// 游戏是否结束
	private boolean MoveFlag = true;// 当前图案是否继续左,右移动

	// 游戏速度
	private int BasicSpeed = 500;// 默认速度为500ms
	private int GoDownSpeed = BasicSpeed;// 默认下降速度为默认速度

	// 菜单设置
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

		// 实例化游戏面板格子
		// 实例化所有gameTextView
		LinearLayout gameLinearLayout[] = new LinearLayout[allTextViewForGame];// 为每一个格子设置一个
																				// LinearLayout容器

		// 获取手机信息,使面板合适当前手机屏幕
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		int PHONE_WIDTH = mDisplayMetrics.widthPixels;
		int PHONE_HEIGHT = mDisplayMetrics.heightPixels;
		Log.i(TAG, "The Phone Width is " + PHONE_WIDTH);
		Log.i(TAG, "The Phone Height is " + PHONE_HEIGHT);
		int Grids_WIDTH = PHONE_WIDTH * 3 / 5
				/ aCurrentTetirsBackground.TETRIS_WIDTH;
		// PHONE_HEIGHT - 120是减去手机通知栏以及软件顶部名称的高度
		int Grids_HEIGHT = (PHONE_HEIGHT - 120) * 9 / 10
				/ (aCurrentTetirsBackground.TETRIS_HEIGHT - 4 + 1);
		for (int i = 0; i < allTextViewForGame; i++) {
			// 实例化每个格子
			gameTextView[i] = new TextView(TetirsGame.this);
			gameTextView[i].setMinHeight(Grids_HEIGHT);
			gameTextView[i].setMinWidth(Grids_WIDTH);
			gameTextView[i].setMaxHeight(Grids_HEIGHT);
			gameTextView[i].setMaxWidth(Grids_WIDTH);
			gameTextView[i]
					.setBackgroundResource(R.color.basic_game_grid_background_color);

			// 实例化每个格子后的布局面板
			gameLinearLayout[i] = new LinearLayout(this);
			gameLinearLayout[i].setPadding(1, 1, 1, 1);
			gameLinearLayout[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			gameLinearLayout[i].addView(gameTextView[i]);

		}

		// 添加游戏面板格子
		tetirsTableLayout = (TableLayout) findViewById(R.id.Tetris_Game_Panel);
		// tetirsTableLayout.removeAllViews();
		TableRow tetirsTableRow[] = new TableRow[aCurrentTetirsBackground.TETRIS_HEIGHT - 4];// 4行格子被隐藏
		for (int i = 0; i < aCurrentTetirsBackground.TETRIS_HEIGHT - 4; i++) {// 4行格子被隐藏
			tetirsTableRow[i] = new TableRow(TetirsGame.this);
			tetirsTableRow[i].setPadding(0, 1, 0, 1);
			tetirsTableRow[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			for (int j = 0; j < aCurrentTetirsBackground.TETRIS_WIDTH; j++) {
				tetirsTableRow[i].addView(gameLinearLayout[(i + 4)
						* aCurrentTetirsBackground.TETRIS_WIDTH + j]);// i+4，前面4行格子被隐藏
			}
			tetirsTableLayout.addView(tetirsTableRow[i]);
		}

		// 实例化提示面板格子

		LinearLayout TipBackground[] = new LinearLayout[TipNeedGrid];
		for (int i = 0; i < TipNeedGrid; i++) {
			// 实例化每个格子
			theTipTextView[i] = new TextView(this);
			theTipTextView[i].setMinHeight(Grids_HEIGHT);
			theTipTextView[i].setMinWidth(Grids_WIDTH);
			theTipTextView[i].setMaxHeight(Grids_HEIGHT);
			theTipTextView[i].setMaxWidth(Grids_WIDTH);
			theTipTextView[i]
					.setBackgroundResource(R.color.basic_next_picture_color);

			// 实例化每个格子后的布局面板
			TipBackground[i] = new LinearLayout(this);
			TipBackground[i].setPadding(1, 1, 1, 1);
			TipBackground[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			TipBackground[i].addView(theTipTextView[i]);
		}

		// 添加提示面板格子
		nextTableLayout = (TableLayout) findViewById(R.id.tetris_next);
		TableRow tipTableRow[] = new TableRow[4];// 4行
		for (int i = 0; i < tipTableRow.length; i++) {
			tipTableRow[i] = new TableRow(this);
			tipTableRow[i].setPadding(0, 1, 0, 1);
			tipTableRow[i]
					.setBackgroundResource(R.color.basic_game_grid_padding_color);
			for (int j = 0; j < tipTableRow.length; j++) {// 4列
				tipTableRow[i]
						.addView(TipBackground[i * tipTableRow.length + j]);
			}
			nextTableLayout.addView(tipTableRow[i]);
		}

		// 获取组件ID
		scoreTextView = (TextView) findViewById(R.id.tetris_score);
		pauseButton = (Button) findViewById(R.id.tetris_pause);
		upButton = (ImageButton) findViewById(R.id.tetris_button_up);
		leftButton = (ImageButton) findViewById(R.id.tetris_button_left);
		downButton = (ImageButton) findViewById(R.id.tetris_button_dwon);
		rightButton = (ImageButton) findViewById(R.id.tetris_button_right);

		// 添加事件监听器
		pauseButton.setOnClickListener(listener);
		upButton.setOnClickListener(listener);
		leftButton.setOnTouchListener(moveContralListener);
		downButton.setOnTouchListener(speedContralListener);// 添加触摸控制监听
		rightButton.setOnTouchListener(moveContralListener);
		//设置按钮背景透明
		pauseButton.getBackground().setAlpha(150);
		upButton.getBackground().setAlpha(50);
		leftButton.getBackground().setAlpha(50);
		rightButton.getBackground().setAlpha(50);
		downButton.getBackground().setAlpha(50);

		// 界面游戏性修改
		theGameHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					// 修改当前图案提示面板
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
					// 修改当前移动的图案

				case 2:
					// 修改整个游戏格子面板
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
					// 修改得分面板
					int score = aGameScore.getTheGameScore();
					scoreTextView.setText(score + "");
					break;
				case 4:
					// 修改公告栏信息

					break;
				case 6:
					// 判断游戏结束信息
					if (GameEnd) {
						Log.i(TAG, "The game is end");
						Intent gameScoreIntent = new Intent(TetirsGame.this,
								ShowGameScore.class);
						gameScoreIntent.putExtra("score",
								aGameScore.getTheGameScore() + "");
						startActivity(gameScoreIntent);
						pauseButton.setText("开始游戏");
						GameLord = false;// 游戏已暂停
					}
					break;
				default:
					break;
				}
			}

		};
		// 游戏进程
		GameGoDown aGameGoDown = new GameGoDown();
		Thread mainGameThread = new Thread(aGameGoDown);
		mainGameThread.start();
	}

	// 游戏开始运行的主线程
	protected class GameGoDown implements Runnable {

		@Override
		public void run() {
			// 重置后台数据
			aCurrentTetirsBackground.clearBackgroundTipDate(aTetirsTips);// 清除提示面板数据
			Message message1 = new Message();
			message1.what = 0;
			theGameHandler.sendMessage(message1);
			aCurrentTetirsBackground.clearBackgroundDate(aTetirsGirds);// 清除游戏面板
			Message message2 = new Message();
			message2.what = 2;
			theGameHandler.sendMessage(message2);
			aCurrentTetirsBackground.resetScore(aGameScore);// 清除分数面板
			Message message3 = new Message();
			message3.what = 3;
			theGameHandler.sendMessage(message3);
			Log.i(TAG, "Background Date Is Reset");
			// 产生第一个图案
			aCurrentTetirsBackground.CreateNewPicture(aTetirsTips);
			Message message4 = new Message();
			message4.what = 0;
			theGameHandler.sendMessage(message4);
			Log.i(TAG, "New Picture is Create");
			// 将第一个图案放置到游戏面板
			aCurrentTetirsBackground.setFirstPicture(aTetirsTips,
					aCurrentPicture, aTetirsGirds);
			Message message5 = new Message();
			message5.what = 2;
			theGameHandler.sendMessage(message5);
			Log.i(TAG, "The picture is set in game girds");
			while (!GameEnd) {
				if (GameLord) {
					// 消除满行的格子
					int score = aCurrentTetirsBackground
							.clearCurrentLinePicture(aTetirsGirds);
					Message message6 = new Message();
					message6.what = 2;
					theGameHandler.sendMessage(message6);
					// 修改分数
					aCurrentTetirsBackground.plusScore(score, aGameScore);
					Message message7 = new Message();
					message7.what = 3;
					theGameHandler.sendMessage(message7);
					if (score > 0) {
						Log.i(TAG, "Score is plused " + score);
					}
					if (GameLord) {
						// 判断游戏是否结束
						GameEnd = aCurrentTetirsBackground
								.CkeckGameEnd(aTetirsGirds);
						Message message8 = new Message();
						message8.what = 6;
						theGameHandler.sendMessage(message8);
					}
					// 产生新的图片
					aCurrentTetirsBackground.CreateNewPicture(aTetirsTips);
					Message message9 = new Message();
					message9.what = 0;
					theGameHandler.sendMessage(message9);
					// 检测当前图案是否可以下降
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
					// 固定当前图片
					aCurrentTetirsBackground.setThePicture(aCurrentPicture,
							aTetirsGirds);
					Message message11 = new Message();
					message11.what = 1;
					theGameHandler.sendMessage(message11);
					// 获取图片
					aCurrentTetirsBackground.setFirstPicture(aTetirsTips,
							aCurrentPicture, aTetirsGirds);
					Message message12 = new Message();
					message12.what = 2;
					theGameHandler.sendMessage(message12);
				}
			}
		}

	}

	// 左右移动以及暂停事件监听
	protected OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tetris_pause:
				// 暂停事件
				if (GameLord) {
					GameLord = false;// 游戏暂停
					pauseButton.setText("继续");
				} else {
					GameLord = true;
					pauseButton.setText("暂停");
					if (GameEnd) {// 如果此时游戏已经结束则重新开始
						GameGoDown aGameGoDown = new GameGoDown();
						Thread new_gameThread = new Thread(aGameGoDown);
						new_gameThread.start();
						GameEnd = false;// 新游戏未停止
					}
				}
				break;
			// case R.id.tetris_button_left:
			// //左键事件
			// aCurrentTetirsBackground.moveLeft(aCurrentPicture, aTetirsGirds);
			// Message aMessage = new Message();
			// aMessage.what = 1;
			// theGameHandler.sendMessage(aMessage);
			// break;
			// case R.id.tetris_button_right:
			// //右键事件
			// aCurrentTetirsBackground.moveRight(aCurrentPicture,
			// aTetirsGirds);
			// Message bMessage = new Message();
			// bMessage.what = 1;
			// theGameHandler.sendMessage(bMessage);
			// break;
			case R.id.tetris_button_up:
				// 图形变形
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

	// 下降速度调控
	protected OnTouchListener speedContralListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.getId() == R.id.tetris_button_dwon) {// 确保按下的是 加速下降按键
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 按下加速
					GoDownSpeed = 100;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 抬起则回复原来的速度
					GoDownSpeed = BasicSpeed;
				}
			}
			return false;
		}
	};

	// 左右键触控
	protected OnTouchListener moveContralListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (GameLord) {
				if (v.getId() == R.id.tetris_button_left) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// 左键事件
						MoveFlag = true;
						MoveLeftThread aLeftThread = new MoveLeftThread();
						Thread aThread = new Thread(aLeftThread);
						aThread.start();
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						MoveFlag = false;
					}
				} else if (v.getId() == R.id.tetris_button_right) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// 右键事件
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

	// 当前图案左移的线程
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

	// 当前图案右移的线程
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

	// 用户按下返回键和主键事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 按下返回键事件
			if (GameLord && !GameEnd) {
				GameLord = false;// 如果游戏尚在继续则游戏暂停
				pauseButton.setText("继续");
			}
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要返回游戏主界面吗?");
			// 添加选择按钮并注册监听
			isExit.setButton(AlertDialog.BUTTON_POSITIVE, "确定", BackListener);
			isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", BackListener);
			// 显示对话框
			isExit.show();
		} else if (keyCode == KeyEvent.KEYCODE_HOME
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 按下主键
			if (GameLord && !GameEnd) {
				// 如果游戏还在继续则暂停
				GameLord = false;
				pauseButton.setText("继续");
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener BackListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_HELP, 0, "帮助");
		menu.add(0, MENU_BACK, 0, "返回");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case MENU_HELP:
			if (GameLord && !GameEnd) {
				GameLord = false;// 游戏暂停
				pauseButton.setText("继续");
			}
			Intent helpIntent = new Intent(TetirsGame.this, GameHelp.class);
			startActivity(helpIntent);
			break;
		case MENU_BACK:
			if (GameLord && !GameEnd) {
				GameLord = false;// 游戏暂停
				pauseButton.setText("继续");
			}
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要返回游戏主界面吗?");
			// 添加选择按钮并注册监听
			isExit.setButton(AlertDialog.BUTTON_POSITIVE, "确定", BackListener);
			isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", BackListener);
			// 显示对话框
			isExit.show();		
			break;
		default:
			Log.e(TAG, "No such select");
			break;
		}
		return true;
	}

	// 在其它程序介入时暂停程序
	@Override
	protected void onPause() {
		super.onPause();
		if (GameLord && !GameEnd) {
			GameLord = false;// 游戏暂停
			pauseButton.setText("继续");
		}
	}

	// 恢复程序
	@Override
	protected void onResume() {
		super.onResume();
	}

}
