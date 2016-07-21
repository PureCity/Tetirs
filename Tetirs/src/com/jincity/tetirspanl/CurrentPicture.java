package com.jincity.tetirspanl;

public class CurrentPicture {

	private int NUM = 4;// 每一个图案都是4个格子
	private int currentPicture[] = new int[NUM];
	private int type = 0;
	private int direction = 0;

	public CurrentPicture() {
		for (int i = 0; i < NUM; i++) {
			currentPicture[i] = 0;
		}
		type = 0;
	}
	
	//设置当前图案类型
	public void setType(int type){
		this.type = type;
	}
	
	//获取当前图案类型
	public int getType(){
		return this.type;
	}
	
	//设置当前图案方向
	public void setDirection(int d){
		this.direction = d;
	}
	
	//获得当前图案方向
	public int getDirection(){
		return this.direction;
	}

	// 设置当前图案的位置
	public void setCurrentPicture(int x, int y, int l, int m) {
		currentPicture[0] = x;
		currentPicture[1] = y;
		currentPicture[2] = l;
		currentPicture[3] = m;
	}

	// 获得当前图案位置,用：分割4个坐标
	public String getCurrentPicture() {
		String localString = "";
		localString = currentPicture[0] + ":" + currentPicture[1] + ":"
				+ currentPicture[2] + ":" + currentPicture[3];
		return localString;
	}

}
