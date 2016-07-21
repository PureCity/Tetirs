package com.jincity.tetirspanl;

import android.util.Log;

public class TetirsTips {
	CurrentTetirsBackground aCurrentTetirsBackground = new CurrentTetirsBackground();
	public final int WIDTH = 4;
	public final int HEIGHT = 4;
	private int tetirsGirds[] = new int[WIDTH * HEIGHT];// ��̨����
	private int type = 0;
	private int direction = 0;
	
	
	//���ͼ������
	public int getType() {
		return type;
	}
	//����ͼ������
	public void setType(int type) {
		this.type = type;
	}
	//���ͼ������
	public int getDirection() {
		return direction;
	}
	//����ͼ������
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public TetirsTips(){
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			tetirsGirds[i] = aCurrentTetirsBackground.F;//��������Ĭ��ֵ F
		}
	}
	
	//����ָ�����ӵ�ֵ
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
	
	//��ȡָ�����ӵ�ֵ
	public int getGirds(int i , int j){
		return this.tetirsGirds[i * WIDTH + j];
	}
	
	public int getGirds(int x){
		return this.tetirsGirds[x];
	}
}
