package com.jincity.tetirspanl;

public class CurrentPicture {

	private int NUM = 4;// ÿһ��ͼ������4������
	private int currentPicture[] = new int[NUM];
	private int type = 0;
	private int direction = 0;

	public CurrentPicture() {
		for (int i = 0; i < NUM; i++) {
			currentPicture[i] = 0;
		}
		type = 0;
	}
	
	//���õ�ǰͼ������
	public void setType(int type){
		this.type = type;
	}
	
	//��ȡ��ǰͼ������
	public int getType(){
		return this.type;
	}
	
	//���õ�ǰͼ������
	public void setDirection(int d){
		this.direction = d;
	}
	
	//��õ�ǰͼ������
	public int getDirection(){
		return this.direction;
	}

	// ���õ�ǰͼ����λ��
	public void setCurrentPicture(int x, int y, int l, int m) {
		currentPicture[0] = x;
		currentPicture[1] = y;
		currentPicture[2] = l;
		currentPicture[3] = m;
	}

	// ��õ�ǰͼ��λ��,�ã��ָ�4������
	public String getCurrentPicture() {
		String localString = "";
		localString = currentPicture[0] + ":" + currentPicture[1] + ":"
				+ currentPicture[2] + ":" + currentPicture[3];
		return localString;
	}

}
