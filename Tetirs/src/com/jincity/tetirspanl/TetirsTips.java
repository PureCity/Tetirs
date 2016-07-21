package com.jincity.tetirspanl;

import android.util.Log;

public class TetirsTips {
	CurrentTetirsBackground aCurrentTetirsBackground = new CurrentTetirsBackground();
	public final int WIDTH = 4;
	public final int HEIGHT = 4;
	private int tetirsGirds[] = new int[WIDTH * HEIGHT];// 后台数据
	private int type = 0;
	private int direction = 0;
	
	
	//获得图案类型
	public int getType() {
		return type;
	}
	//设置图案类型
	public void setType(int type) {
		this.type = type;
	}
	//获得图案方向
	public int getDirection() {
		return direction;
	}
	//设置图案方向
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public TetirsTips(){
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			tetirsGirds[i] = aCurrentTetirsBackground.F;//所有数据默认值 F
		}
	}
	
	//设置指定格子的值
	public void setGirds(int i, int j,int l){
		if (l == aCurrentTetirsBackground.T) {
			this.tetirsGirds[i * WIDTH + j] = aCurrentTetirsBackground.T;
		}else if(l == aCurrentTetirsBackground.F){
			this.tetirsGirds[i * WIDTH + j] = aCurrentTetirsBackground.F;
		}else if (l == aCurrentTetirsBackground.Y) {
			this.tetirsGirds[i * WIDTH + j] = aCurrentTetirsBackground.Y;
		}else{
			Log.e("NoIdError", "TetrisTips.setGirds(int i,int j,int l)");
		}
		
	}
	public void setGirds(int x,int l){
		if (l == aCurrentTetirsBackground.T) {
			this.tetirsGirds[x] = aCurrentTetirsBackground.T;
		}else if(l == aCurrentTetirsBackground.F){
			this.tetirsGirds[x] = aCurrentTetirsBackground.F;
		}else if (l == aCurrentTetirsBackground.Y) {
			this.tetirsGirds[x] = aCurrentTetirsBackground.Y;
		}else{
			Log.e("NoIdError", "TetrisTips.setGirds(int x,int l)");
		}
	}
	
	//获取指定格子的值
	public int getGirds(int i , int j){
		return this.tetirsGirds[i * WIDTH + j];
	}
	
	public int getGirds(int x){
		return this.tetirsGirds[x];
	}
}
