package com.jincity.tetirspanl;

import java.util.Random;

import android.util.Log;

public class CurrentTetirsBackground {
	private final String TAG = "CurrentTetirsBackground";

	// ����˹�����̨
	public final int TETRIS_WIDTH = 10;// ��Ϸ�����ӿ��
	public final int TETRIS_HEIGHT = 23;// ��Ϸ�����Ӹ߶ȸ���(4������)

	public final int T = 2;// ��ʾ�˸�����ͼ����ͼ���Ѿ��̶�
	public final int F = 0;// ��ʾ�˸�����ͼ��
	public final int Y = 1;// ��ʾ�˸������ڽ���ͼƬ

	private int[][] gamePicture = {
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 } };// ���ֻ���ͼ��

	// ��պ�̨����
	public void clearBackgroundDate(TetirsGirds aTetirsGirds) {
		for (int i = 0; i < TETRIS_WIDTH * TETRIS_HEIGHT; i++) {
			aTetirsGirds.setGirds(i, F);
		}
	}

	// ��պ�̨��ʾͼƬ����
	public void clearBackgroundTipDate(TetirsTips aTetirsTips) {
		for (int i = 0; i < aTetirsTips.WIDTH * aTetirsTips.HEIGHT; i++) {
			aTetirsTips.setGirds(i, F);
		}
	}

	// ��Ϸ�������
	// ��Ϸ��������true
	// ��Ϸδ��������false
	public boolean CkeckGameEnd(TetirsGirds aTetirsGirds) {
		boolean tag = false;
		for (int i = 0; i < TETRIS_WIDTH; i++) {
			if (aTetirsGirds.getGirds(4, i) == T) {
				tag = true;
			}
		}
		return tag;
	}

	// �̶���ǰ�˶���ͼ��
	public void setThePicture(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		for (int i = 0; i < place.length; i++) {
			aTetirsGirds.setGirds(Integer.parseInt(place[i]), T);
		}
		// aCurrentPicture.setCurrentPicture(0, 0, 0, 0);// ���õ�ǰͼ����
	}

	// ���ͼ���Ƿ��������
	public boolean CheckDateDown(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		boolean tag = true;
		// ��õ�ǰͼ����λ��
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		// ����Ƿ��Ѿ����˵ײ�
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] + TETRIS_WIDTH >= TETRIS_WIDTH * TETRIS_HEIGHT) {
				return false;// ͼ���Ե���ײ�
			}
		}
		// ���ͼ���·��Ƿ��и��Ӵ���ͼ��
		for (int i = 0; i < placeInt.length; i++) {
			if (aTetirsGirds.getGirds(placeInt[i] + TETRIS_WIDTH) == T) {
				tag = false;// ������ͼ���޷�����,����false
				break;
			}
		}

		return tag;
	}

	// ��Ϸ��ͼ�����ƶ�Ӧ����������
	public void dateDown(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// ��õ�ǰͼ����λ��
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		// ����������һλ
		for (int i = placeInt.length - 1; i >= 0; i--) {
			aTetirsGirds.setGirds(placeInt[i], F);// ����ǰλ�õ�ͼ��ȡ��
			placeInt[i] += TETRIS_WIDTH;// ͼ����Ӧ�ĸ�������
			aTetirsGirds.setGirds(placeInt[i], Y);// �����µĸ��Ӵ���ͼ��
		}
		aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
				placeInt[2], placeInt[3]);// ������ǰͼ��λ��
	}

	// �������е�ͼ��
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

	// ��������ͼ����������(��ָ���������е�������������ƽ��һ��)
	private void letFocusLineDown(TetirsGirds aTetirsGirds, int line) {
		for (int i = line; i > 0; i--) {
			for (int j = 0; j < TETRIS_WIDTH; j++) {
				aTetirsGirds.setGirds(i, j, aTetirsGirds.getGirds(i - 1, j));// ����ǰ�е����ݸ�Ϊ��һ�е�����
			}
		}
	}

	// ��ת��ǰͼ��
	public void ChangeCurrentPicture(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// ��õ�ǰͼ����λ��
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}
		switch (aCurrentPicture.getType()) {// �ж�������
		case 0:
			// ��0��ͼ�ε���ת
			switch (aCurrentPicture.getDirection()) {// �ж��䷽��
			case 0:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// ��ͼ���ұߴ�����
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
				if (placeInt[2] / TETRIS_WIDTH + 1 < TETRIS_HEIGHT - 1) {// ��ͼ���±ߴ�����
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
			// ��1��ͼ������ת
			switch (aCurrentPicture.getDirection()) {// �������ַ���
			case 0:
				if (placeInt[0] % TETRIS_WIDTH > 0) {// ȷ����ߴ�����
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
			// ��2��ͼ����ת
			switch (aCurrentPicture.getDirection()) {// ����ͼ������4������
			case 0:
				if (placeInt[0] / TETRIS_WIDTH + 2 <= TETRIS_HEIGHT - 1) {// ����Ƿ������һ��
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
				if (placeInt[0] % TETRIS_WIDTH > 0) {// �����ߴ�����
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
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// �ж����ұ��Ƿ������
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
			// ��3��ͼ����ת
			switch (aCurrentPicture.getDirection()) {// ����ͼ������4������
			case 0:
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// �ж��ұߴ�����
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
				if (placeInt[0] / TETRIS_WIDTH + 2 < TETRIS_HEIGHT) {// �ж��Ƿ������һ��
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
				if (placeInt[0] % TETRIS_WIDTH - 1 >= 0) {// �ж���ߴ���һ��
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
			// ��4��ͼ����ת
			switch (aCurrentPicture.getDirection()) {// ����ͼ������4������
			case 0:
				if (placeInt[0] % TETRIS_WIDTH > 0) {// �����ߴ���һ��
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
				if (placeInt[0] % TETRIS_WIDTH < TETRIS_WIDTH - 1) {// �ж��ұߴ�����
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
				if (placeInt[0] / TETRIS_WIDTH < TETRIS_HEIGHT - 1) {// �ж��Ƿ������һ��
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
			// ��ͼ�β�����ת
			break;
		case 6:
			// ��6��ͼ����ת
			switch (aCurrentPicture.getDirection()) {// ����ͼ������2������
			case 0:
				if (placeInt[0] % TETRIS_WIDTH - 2 >= 0) {// �ж���ߴ���2��
					if (placeInt[0] % TETRIS_WIDTH + 1 <= TETRIS_WIDTH - 1) {// �ж��ұߴ���1��
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
					} else if (placeInt[0] % TETRIS_WIDTH - 3 >= 0) {// �ж���ߴ���3��
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
					if (placeInt[0] % TETRIS_WIDTH + 3 < TETRIS_WIDTH) {// �ж��ұߴ���3��
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
					} else if (placeInt[0] % TETRIS_WIDTH + 2 < TETRIS_WIDTH) {// �ж��ұߴ���2��
						if (placeInt[0] % TETRIS_WIDTH > 0) {// �ж���ߴ���1��
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
				if (placeInt[0] / TETRIS_WIDTH < TETRIS_HEIGHT - 1) {// �ж��Ƿ������һ��
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

	// ʵ��ͼ����ת(��������)
	private void changePicture(int placeInt[], CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds, int newPlaceInt[], int direction) {
		// ����ԭ����ͼ��
		for (int i = 0; i < placeInt.length; i++) {
			aTetirsGirds.setGirds(placeInt[i], F);
		}
		// ������ͼ���������
		placeInt[0] = newPlaceInt[0];
		placeInt[1] = newPlaceInt[1];
		placeInt[2] = newPlaceInt[2];
		placeInt[3] = newPlaceInt[3];
		// ������ͼ���������ͷ���
		aCurrentPicture.setCurrentPicture(placeInt[0], placeInt[1],
				placeInt[2], placeInt[3]);
		aCurrentPicture.setDirection(direction);
		// �����µ�ͼ��
		for (int i = 0; i < placeInt.length; i++) {
			aTetirsGirds.setGirds(placeInt[i], Y);
		}
	}

	// �����µ���ʾͼ��
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

	// ��ǰͼ������
	public void moveLeft(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// ��õ�ǰͼ����λ��
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}

		boolean tag = true;// �����ж�����Ƿ����(���Ƿ񵽴�����)
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] % TETRIS_WIDTH == 0) {
				tag = false;// �Դﵽ����,ֹͣ����
				break;
			}
		}

		if (tag) {
			boolean flag = false;
			for (int i = 0; i < placeInt.length; i++) {
				// �ж�����Ƿ����ͼ��
				if (aTetirsGirds.getGirds(placeInt[i] - 1) == T) {
					flag = true;// ��ߴ���ͼ��,ֹͣ����
					break;
				}
			}
			if (!flag) {
				// ����
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

	// ��ǰͼ������
	public void moveRight(CurrentPicture aCurrentPicture,
			TetirsGirds aTetirsGirds) {
		// ��õ�ǰͼ����λ��
		String place[] = new String[4];
		String getString = aCurrentPicture.getCurrentPicture();
		place = getString.split(":");
		int placeInt[] = new int[4];
		for (int i = 0; i < placeInt.length; i++) {
			placeInt[i] = Integer.parseInt(place[i]);
		}

		boolean tag = true;// �����ж��ұ��Ƿ����(���Ƿ񵽴�����)
		for (int i = 0; i < placeInt.length; i++) {
			if (placeInt[i] % TETRIS_WIDTH == TETRIS_WIDTH - 1) {
				tag = false;// �Դﵽ����,ֹͣ����
				break;
			}
		}

		if (tag) {
			boolean flag = false;
			for (int i = 0; i < placeInt.length; i++) {
				// �ж��ұ��Ƿ����ͼ��
				if (aTetirsGirds.getGirds(placeInt[i] + 1) == T) {
					flag = true;// �ұߴ���ͼ��,ֹͣ����
					break;
				}
			}
			if (!flag) {
				// ����
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

	// ͼ����ʼ����
	public void setFirstPicture(TetirsTips aTetirsTips,
			CurrentPicture aCurrentPicture, TetirsGirds aTetirsGirds) {
		// ����ʾ����е�ͼƬ��ӵ���Ϸ�����
		int l = 0;
		int temp[] = new int[4];
		for (int i = 0; i < aTetirsTips.HEIGHT; i++) {
			for (int j = 0, x = 4; j < aTetirsTips.WIDTH && x <= 7; j++, x++) {

				// ����ʾ�����16�������е���Ϣ���Ƶ���Ϸ������1��4�е����м�
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

	// �ӷֲ���
	public void plusScore(int score, GameScore aGameScore) {
		aGameScore.setTheGameScore(score);
	}

	// ���÷���
	public void resetScore(GameScore aGameScore) {
		aGameScore.setBasicScore();
	}

}
