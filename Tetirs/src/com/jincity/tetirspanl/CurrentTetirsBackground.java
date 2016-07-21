package com.jincity.tetirspanl;

import java.util.Random;

import android.util.Log;

public class CurrentTetirsBackground {
	private final String TAG = "CurrentTetirsBackground";

	// 俄罗斯方块后台
	public final int TETRIS_WIDTH = 10;// 游戏面板格子宽度
	public final int TETRIS_HEIGHT = 23;// 游戏面板格子高度个数(4个隐藏)

	public final int T = 2;// 表示此格子有图案且图案已经固定
	public final int F = 0;// 表示此格子无图案
	public final int Y = 1;// 表示此格子正在降落图片

	private int[][] gamePicture = {
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 } };// 七种基本图形

	// 清空后台数据
	public void clearBackgroundDate(TetirsGirds aTetirsGirds) {
		for (int i = 0; i < TETRIS_WIDTH * TETRIS_HEIGHT; i++) {
			aTetirsGirds.setGirds(i, F);
		}
	}

	// 清空后台提示图片数据
	public void clearBackgroundTipDate(TetirsTips aTetirsTips) {
		for (int i = 0; i < aTetirsTips.WIDTH * aTetirsTips.HEIGHT; i++) {
			aTetirsTips.setGirds(i, F);
		}
	}

	// 游戏结束检查
	// 游戏结束返回true
	// 游戏未结束返回false
	public boolean CkeckGameEnd(TetirsGirds aTetirsGirds) {
		boolean tag = false;
		for (int i = 0; i < TETRIS_WIDTH; i++) {
			if (aTetirsGirds.getGirds(4, i) == T) {
				tag = true;
			}
		}
		return tag;
	}

	// 固定当前运动的图案
	public void setThePicture(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		for (int i = 0; i < place.length; i++) {
			aTetirsGirds.setGirds(Integer.parseInt(place[i]), T);
		}
		// aCurrentPicture.setCurrentPicture(0, 0, 0, 0);// 重置当前图案类
	}

	// 检测图形是否可以下移
	public boolean CheckDateDown(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		boolean tag = true;
		// 获得当前图案的位置
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		// 检测是否已经到了底部
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] + TETRIS_WIDTH >= TETRIS_WIDTH * TETRIS_HEIGHT) {
				return false;// 图案以到达底部
			}
		}
		// 检测图案下方是否有格子存在图案
		for (int i = 0; i < placeInt.length; i++) {
			if (aTetirsGirds.getGirds(placeInt[i] + TETRIS_WIDTH) == T) {
				tag = false;// 存在则图形无法下移,返回false
				break;
			}
		}

		return tag;
	}

	// 游戏的图形下移对应的数据下移
	public void dateDown(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// 获得当前图案的位置
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		// 各数据下移一位
		for (int i = placeInt.length - 1; i >= 0; i--) {
			aTetirsGirds.setGirds(placeInt[i], F);// 将当前位置的图案取消
			placeInt[i] += TETRIS_WIDTH;// 图案对应的格子下移
			aTetirsGirds.setGirds(placeInt[i], Y);// 设置新的格子处有图案
		}
		aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
				placeInt[2], placeInt[3]);// 修正当前图案位置
	}

	// 消除满行的图案
	public int clearCurrentLinePicture(TetirsGirds aTetirsGirds) {
		int line = 0;
		int linePlus = 0;
		for (int i = 0; i < TETRIS_HEIGHT; i++) {
			linePlus = 0;
			for (int j = 0; j < TETRIS_WIDTH; j++) {
				if (aTetirsGirds.getGirds(i, j) == T) {
					linePlus++;
				}
			}
			if (linePlus == TETRIS_WIDTH) {
				line++;
				letFocusLineDown(aTetirsGirds, i);
			}
		}
		return line;
	}

	// 消除满行图案辅助函数(将指定行上所有的行内数据往下平移一行)
	private void letFocusLineDown(TetirsGirds aTetirsGirds, int line) {
		for (int i = line; i > 0; i--) {
			for (int j = 0; j < TETRIS_WIDTH; j++) {
				aTetirsGirds.setGirds(i, j, aTetirsGirds.getGirds(i - 1, j));// 将当前行的数据改为上一行的数据
			}
		}
	}

	// 旋转当前图案
	public void ChangeCurrentPicture(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// 获得当前图案的位置
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		switch (aCurrentPicture.getType()) {// 判定其类型
		case 0:
			// 第0个图形的旋转
			switch (aCurrentPicture.getDirection()) {// 判定其方向
			case 0:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// 该图案右边存在列
					if (aTetirsGirds.getGirds(placeInt[0] - 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[2] + 1) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] - 1;
								newPlaceInt[1] = placeInt[1] - TETRIS_WIDTH + 1;
								newPlaceInt[2] = placeInt[2];
								newPlaceInt[3] = placeInt[3] - TETRIS_WIDTH + 2;
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 1);
							}
						}
					}
				}
				break;
			case 1:
				if (placeInt[2] / TETRIS_WIDTH + 1 < TETRIS_HEIGHT - 1) {// 该图案下边存在行
					if (aTetirsGirds.getGirds(placeInt[2] - 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[2] - 1
								+ TETRIS_WIDTH) == F) {
							int newPlaceInt[] = new int[4];
							newPlaceInt[0] = placeInt[0] + 1;
							newPlaceInt[1] = placeInt[1] + TETRIS_WIDTH - 1;
							newPlaceInt[2] = placeInt[2];
							newPlaceInt[3] = placeInt[3] + TETRIS_WIDTH - 2;
							changePicture(placeInt, aCurrentPicture,
									aTetirsGirds, newPlaceInt, 0);
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		case 1:
			// 第1种图案的旋转
			switch (aCurrentPicture.getDirection()) {// 存在两种方向
			case 0:
				if (placeInt[0] % TETRIS_WIDTH > 0) {// 确定左边存在列
					if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 2
								* TETRIS_WIDTH) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH - 1) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] + TETRIS_WIDTH;
								newPlaceInt[1] = placeInt[1] + 1;
								newPlaceInt[2] = placeInt[2] + TETRIS_WIDTH - 2;
								newPlaceInt[3] = placeInt[3] - 1;
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 1);
							}
						}
					}
				}
				break;
			case 1:
				if (aTetirsGirds.getGirds(placeInt[0] - TETRIS_WIDTH) == F) {
					if (aTetirsGirds.getGirds(placeInt[0] - TETRIS_WIDTH + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH
								+ 1) == F) {
							int newPlaceInt[] = new int[4];
							newPlaceInt[0] = placeInt[0] - TETRIS_WIDTH;
							newPlaceInt[1] = placeInt[1] - 1;
							newPlaceInt[2] = placeInt[2] - TETRIS_WIDTH + 2;
							newPlaceInt[3] = placeInt[3] + 1;
							changePicture(placeInt, aCurrentPicture,
									aTetirsGirds, newPlaceInt, 0);
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		case 2:
			// 第2种图案旋转
			switch (aCurrentPicture.getDirection()) {// 该种图案存在4个方向
			case 0:
				if (placeInt[0] / TETRIS_WIDTH + 2 <= TETRIS_HEIGHT - 1) {// 检测是否存在下一行
					if (aTetirsGirds.getGirds(placeInt[0] - 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH + 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0] + 2
										* TETRIS_WIDTH) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0];
									newPlaceInt[1] = placeInt[1] + 1;
									newPlaceInt[2] = placeInt[2] + 1;
									newPlaceInt[3] = placeInt[3] + TETRIS_WIDTH
											- 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 1);
								}
							}
						}
					}
				}
				break;
			case 1:
				if (placeInt[0] % TETRIS_WIDTH > 0) {// 检测左边存在列
					if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 2
								* TETRIS_WIDTH + 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH - 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH - 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + TETRIS_WIDTH
											- 1;
									newPlaceInt[1] = placeInt[1];
									newPlaceInt[2] = placeInt[2];
									newPlaceInt[3] = placeInt[3];
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 2);
								}
							}
						}
					}
				}
				break;
			case 2:
				if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH + 2) == F) {
					if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] - TETRIS_WIDTH) == F) {
							if (aTetirsGirds.getGirds(placeInt[0]
									- TETRIS_WIDTH + 1) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] - TETRIS_WIDTH + 1;
								newPlaceInt[1] = placeInt[1] - 1;
								newPlaceInt[2] = placeInt[2] - 1;
								newPlaceInt[3] = placeInt[3];
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 3);
							}
						}
					}
				}
				break;
			case 3:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// 判断其右边是否存在列
					if (aTetirsGirds.getGirds(placeInt[0] + 2 * TETRIS_WIDTH
							- 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] - 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH + 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0];
									newPlaceInt[1] = placeInt[1];
									newPlaceInt[2] = placeInt[2];
									newPlaceInt[3] = placeInt[3] - TETRIS_WIDTH
											+ 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 0);
								}
							}
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		case 3:
			// 第3种图案旋转
			switch (aCurrentPicture.getDirection()) {// 该种图案存在4个方向
			case 0:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// 判断右边存在列
					if (aTetirsGirds.getGirds(placeInt[0] + 2) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH
								+ 2) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + 2;
									newPlaceInt[1] = placeInt[1] + TETRIS_WIDTH
											- 1;
									newPlaceInt[2] = placeInt[2];
									newPlaceInt[3] = placeInt[3] - TETRIS_WIDTH
											+ 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 1);
								}
							}
						}
					}
				}
				break;
			case 1:
				if (placeInt[0] / TETRIS_WIDTH + 2 < TETRIS_HEIGHT) {// 判断是否存在下一行
					if (aTetirsGirds.getGirds(placeInt[0] + 2 * TETRIS_WIDTH) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 2
								* TETRIS_WIDTH - 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] - 2) == F) {
								if (aTetirsGirds.getGirds(placeInt[0] - 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] - 1;
									newPlaceInt[1] = placeInt[1] + 1;
									newPlaceInt[2] = placeInt[2] + TETRIS_WIDTH;
									newPlaceInt[3] = placeInt[3] + TETRIS_WIDTH;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 2);
								}
							}
						}
					}
				}
				break;
			case 2:
				if (placeInt[0] % TETRIS_WIDTH - 1 >= 0) {// 判断左边存在一行
					if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH
								+ 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH - 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH - 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + TETRIS_WIDTH
											- 1;
									newPlaceInt[1] = placeInt[1];
									newPlaceInt[2] = placeInt[2] - TETRIS_WIDTH
											+ 1;
									newPlaceInt[3] = placeInt[3] - 2;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 3);
								}
							}
						}
					}
				}
				break;
			case 3:
				if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH + 2) == F) {
					if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] - TETRIS_WIDTH) == F) {
							if (aTetirsGirds.getGirds(placeInt[0]
									- TETRIS_WIDTH + 1) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] - TETRIS_WIDTH;
								newPlaceInt[1] = placeInt[1] - TETRIS_WIDTH;
								newPlaceInt[2] = placeInt[2] - 1;
								newPlaceInt[3] = placeInt[3] + 1;
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 0);
							}
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		case 4:
			// 第4种图案旋转
			switch (aCurrentPicture.getDirection()) {// 该种图案存在4个方向
			case 0:
				if (placeInt[0] % TETRIS_WIDTH > 0) {// 检测左边存在一列
					if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 2
								* TETRIS_WIDTH + 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH - 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH - 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + TETRIS_WIDTH
											- 1;
									newPlaceInt[1] = placeInt[1] + TETRIS_WIDTH
											- 1;
									newPlaceInt[2] = placeInt[2] + 1;
									newPlaceInt[3] = placeInt[3] + 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 1);
								}
							}
						}
					}
				}
				break;
			case 1:
				if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH + 1) == F) {
					if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] - TETRIS_WIDTH) == F) {
							if (aTetirsGirds.getGirds(placeInt[0]
									- TETRIS_WIDTH + 1) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] - TETRIS_WIDTH + 1;
								newPlaceInt[1] = placeInt[1];
								newPlaceInt[2] = placeInt[2] + TETRIS_WIDTH - 2;
								newPlaceInt[3] = placeInt[3] - 1;
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 2);
							}
						}
					}
				}
				break;
			case 2:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// 判断右边存在列
					if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH - 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] - 1) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0]
										+ TETRIS_WIDTH + 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] - 1;
									newPlaceInt[1] = placeInt[1] - 1;
									newPlaceInt[2] = placeInt[2] - TETRIS_WIDTH
											+ 1;
									newPlaceInt[3] = placeInt[3] - TETRIS_WIDTH
											+ 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 3);
								}
							}
						}
					}
				}
				break;
			case 3:
				if (placeInt[0] / TETRIS_WIDTH < TETRIS_HEIGHT - 1) {// 判断是否存在下一行
					if (aTetirsGirds.getGirds(placeInt[0] + 1) == F) {
						if (aTetirsGirds.getGirds(placeInt[0] + 2) == F) {
							if (aTetirsGirds.getGirds(placeInt[0] + 2
									* TETRIS_WIDTH + 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0] + 2
										* TETRIS_WIDTH + 2) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + 1;
									newPlaceInt[1] = placeInt[1] - TETRIS_WIDTH
											+ 2;
									newPlaceInt[2] = placeInt[2];
									newPlaceInt[3] = placeInt[3] + TETRIS_WIDTH
											- 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 0);
								}
							}
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		case 5:
			// 该图形不能旋转
			break;
		case 6:
			// 第6种图案旋转
			switch (aCurrentPicture.getDirection()) {// 该种图案存在2个方向
			case 0:
				if (placeInt[0] % TETRIS_WIDTH - 2 >= 0) {// 判断左边存在2列
					if (placeInt[0] % TETRIS_WIDTH + 1 <= TETRIS_WIDTH - 1) {// 判断右边存在1列
						int k = 0;
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 2; j++) {
								if (aTetirsGirds.getGirds((placeInt[0] - 2) + i
										* TETRIS_WIDTH + j) == F) {
									k++;
								}
							}
						}
						if (k == 6) {
							if (aTetirsGirds.getGirds(placeInt[0] + 3
									* TETRIS_WIDTH + 1) == F) {
								if (aTetirsGirds.getGirds(placeInt[0] + 2
										* TETRIS_WIDTH + 1) == F) {
									int newPlaceInt[] = new int[4];
									newPlaceInt[0] = placeInt[0] + 2
											* TETRIS_WIDTH - 2;
									newPlaceInt[1] = placeInt[1] + TETRIS_WIDTH
											- 1;
									newPlaceInt[2] = placeInt[2];
									newPlaceInt[3] = placeInt[3] - TETRIS_WIDTH
											+ 1;
									changePicture(placeInt, aCurrentPicture,
											aTetirsGirds, newPlaceInt, 1);
								}
							}
						}
					} else if (placeInt[0] % TETRIS_WIDTH - 3 >= 0) {// 判断左边存在3列
						int k = 0;
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 3; j++) {
								if (aTetirsGirds.getGirds((placeInt[0] - 3) + i
										* TETRIS_WIDTH + j) == F) {
									k++;
								}
							}
						}
						if (k == 12) {
							int newPlaceInt[] = new int[4];
							newPlaceInt[0] = placeInt[0] + 3 * TETRIS_WIDTH - 3;
							newPlaceInt[1] = placeInt[1] + 2 * TETRIS_WIDTH - 2;
							newPlaceInt[2] = placeInt[2] + TETRIS_WIDTH - 1;
							newPlaceInt[3] = placeInt[3];
							changePicture(placeInt, aCurrentPicture,
									aTetirsGirds, newPlaceInt, 1);
						}
					}
				} else {
					if (placeInt[0] % TETRIS_WIDTH + 3 < TETRIS_WIDTH) {// 判断右边存在3列
						int k = 0;
						for (int i = 0; i < 4; i++) {
							for (int j = 1; j < 4; j++) {
								if (aTetirsGirds.getGirds((placeInt[0] + 1) + i
										* TETRIS_WIDTH + j) == F) {
									k++;
								}
							}
						}
						if (k == 12) {
							int newPlaceInt[] = new int[4];
							newPlaceInt[0] = placeInt[0] + 3 * TETRIS_WIDTH;
							newPlaceInt[1] = placeInt[1] + 2 * TETRIS_WIDTH + 1;
							newPlaceInt[2] = placeInt[2] + TETRIS_WIDTH + 2;
							newPlaceInt[3] = placeInt[3] + 3;
							changePicture(placeInt, aCurrentPicture,
									aTetirsGirds, newPlaceInt, 1);
						}
					} else if (placeInt[0] % TETRIS_WIDTH + 2 < TETRIS_WIDTH) {// 判断右边存在2列
						if (placeInt[0] % TETRIS_WIDTH > 0) {// 判断左边存在1列
							int k = 0;
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 2; j++) {
									if (aTetirsGirds.getGirds((placeInt[0] + 1)
											+ i * TETRIS_WIDTH + j) == F) {
										k++;
									}
								}
							}
							if (k == 6) {
								if (aTetirsGirds.getGirds(placeInt[0] + 3
										* TETRIS_WIDTH - 1) == F) {
									if (aTetirsGirds.getGirds(placeInt[0] + 2
											* TETRIS_WIDTH - 1) == F) {
										int newPlaceInt[] = new int[4];
										newPlaceInt[0] = placeInt[0] + 2
												* TETRIS_WIDTH - 1;
										newPlaceInt[1] = placeInt[1]
												+ TETRIS_WIDTH;
										newPlaceInt[2] = placeInt[2] + 1;
										newPlaceInt[3] = placeInt[3]
												- TETRIS_WIDTH + 2;
										changePicture(placeInt,
												aCurrentPicture, aTetirsGirds,
												newPlaceInt, 1);
									}
								}
							}
						}

					}
				}
				break;
			case 1:
				if (placeInt[0] / TETRIS_WIDTH < TETRIS_HEIGHT - 1) {// 判断是否存在下一行
					int k = 0;
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 3; j++) {
							if (aTetirsGirds
									.getGirds((placeInt[0] - 2 * TETRIS_WIDTH)
											+ i * TETRIS_WIDTH + j) == F) {
								k++;
							}
						}
					}
					if (k == 6) {
						if (aTetirsGirds.getGirds(placeInt[0] + TETRIS_WIDTH
								+ 2) == F) {
							if (aTetirsGirds.getGirds(placeInt[0]
									+ TETRIS_WIDTH + 3) == F) {
								int newPlaceInt[] = new int[4];
								newPlaceInt[0] = placeInt[0] - 2 * TETRIS_WIDTH
										+ 2;
								newPlaceInt[1] = placeInt[1] - TETRIS_WIDTH + 1;
								newPlaceInt[2] = placeInt[2];
								newPlaceInt[3] = placeInt[3] + TETRIS_WIDTH - 1;
								changePicture(placeInt, aCurrentPicture,
										aTetirsGirds, newPlaceInt, 0);
							}
						}
					}
				}
				break;
			default:
				Log.e(TAG, "picture " + aCurrentPicture.getType()
						+ " don't have such direction");
				break;
			}
			break;
		default:
			Log.e(TAG, "No such type (" + aCurrentPicture.getType()
					+ ") picture");
			break;
		}
	}

	// 实现图案旋转(辅助函数)
	private void changePicture(int placeInt[], CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds, int newPlaceInt[], int direction) {
		// 消除原来的图案
		for (int i = 0; i < placeInt.length; i++) {
			aTetirsGirds.setGirds(placeInt[i], F);
		}
		// 设置新图案的坐标点
		placeInt[0] = newPlaceInt[0];
		placeInt[1] = newPlaceInt[1];
		placeInt[2] = newPlaceInt[2];
		placeInt[3] = newPlaceInt[3];
		// 修正新图案的坐标点和方向
		aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
				placeInt[2], placeInt[3]);
		aCurrentPicture.setDirection(direction);
		// 生成新的图案
		for (int i = 0; i < placeInt.length; i++) {
			aTetirsGirds.setGirds(placeInt[i], Y);
		}
	}

	// 生成新的提示图案
	public void CreateNewPicture(TetirsTips aTetirsTips) {
		Random aRandom = new Random();
		int type = aRandom.nextInt(100);
		type = type % 10;
		if(type < 0 || type >= 7){
			type = 2;
		}
		int direction = 0;
		aTetirsTips.setType(type);
		aTetirsTips.setDirection(direction);
		for (int i = 0; i < aTetirsTips.WIDTH * aTetirsTips.HEIGHT; i++) {
			aTetirsTips.setGirds(i, gamePicture[type][i]);
		}

	}

	// 当前图案左移
	public void moveLeft(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// 获得当前图案的位置
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}

		boolean tag = true;// 用以判断左边是否存在(即是否到达最左)
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] % TETRIS_WIDTH == 0) {
				tag = false;// 以达到最左,停止左移
				break;
			}
		}

		if (tag) {
			boolean flag = false;
			for (int i = 0; i < placeInt.length; i++) {
				// 判断左边是否存在图案
				if (aTetirsGirds.getGirds(placeInt[i] - 1) == T) {
					flag = true;// 左边存在图案,停止左移
					break;
				}
			}
			if (!flag) {
				// 左移
				for (int i = 0; i < placeInt.length; i++) {
					aTetirsGirds.setGirds(placeInt[i], F);
					placeInt[i] = placeInt[i] - 1;
					aTetirsGirds.setGirds(placeInt[i], Y);
				}
				aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
						placeInt[2], placeInt[3]);
			}
		}

	}

	// 当前图案右移
	public void moveRight(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// 获得当前图案的位置
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}

		boolean tag = true;// 用以判断右边是否存在(即是否到达最右)
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] % TETRIS_WIDTH == TETRIS_WIDTH - 1) {
				tag = false;// 以达到最右,停止右移
				break;
			}
		}

		if (tag) {
			boolean flag = false;
			for (int i = 0; i < placeInt.length; i++) {
				// 判断右边是否存在图案
				if (aTetirsGirds.getGirds(placeInt[i] + 1) == T) {
					flag = true;// 右边存在图案,停止右移
					break;
				}
			}
			if (!flag) {
				// 右移
				for (int i = placeInt.length - 1; i >= 0; i--) {
					aTetirsGirds.setGirds(placeInt[i], F);
					placeInt[i] = placeInt[i] + 1;
					aTetirsGirds.setGirds(placeInt[i], Y);
				}
				aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
						placeInt[2], placeInt[3]);
			}
		}
	}

	// 图案开始降落
	public void setFirstPicture(TetirsTips aTetirsTips,
			CurrentPicture aCurrentPicture, TetirsGirds aTetirsGirds) {
		// 将提示面板中的图片添加到游戏面板中
		int l = 0;
		int temp[] = new int[4];
		for (int i = 0; i < aTetirsTips.HEIGHT; i++) {
			for (int j = 0, x = 4; j < aTetirsTips.WIDTH && x <= 7; j++, x++) {

				// 将提示面板中16个格子中的信息复制到游戏面板格子1至4行的正中间
				aTetirsGirds.setGirds(i, x, aTetirsTips.getGirds(i, j));
				if (aTetirsTips.getGirds(i, j) == Y) {
					temp[l] = i * TETRIS_WIDTH + x;
					l++;
				}
			}
		}
		aCurrentPicture.setCurrentPicture(temp[0], temp[1], temp[2], temp[3]);
		aCurrentPicture.setType(aTetirsTips.getType());
		aCurrentPicture.setDirection(aTetirsTips.getDirection());

	}

	// 加分操作
	public void plusScore(int score, GameScore aGameScore) {
		aGameScore.setTheGameScore(score);
	}

	// 重置分数
	public void resetScore(GameScore aGameScore) {
		aGameScore.setBasicScore();
	}

}
