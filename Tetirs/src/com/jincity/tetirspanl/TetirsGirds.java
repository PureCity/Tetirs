package com.jincity.tetirspanl;

import android.util.Log;


//ʵ����̨��Ϸ������
public class TetirsGirds {

	CurrentTetirsBackground aCurrentTetirsBackground = new CurrentTetirsBackground();
	private int tetirsGirds[] = new int[aCurrentTetirsBackground.TETRIS_HEIGHT
			* aCurrentTetirsBackground.TETRIS_WIDTH];// ��̨����
	
	public TetirsGirds(){
		for (int i = 0; i < aCurrentTetirsBackground.TETRIS_WIDTH * aCurrentTetirsBackground.TETRIS_HEIGHT; i++) {
			tetirsGirds[i] = aCurrentTetirsBackground.F;//��������Ĭ��ֵ F
		}
	}
	
	//����ָ�����ӵ�ֵ
	public void setGirds(int i, int j,int l){
		if (l == aCurrentTetirsBackground.T) {
			this.tetirsGirds[i * aCurrentTetirsBackground.TETRIS_WIDTH + j] = aCurrentTetirsBackground.T;
		}else if(l == aCurrentTetirsBackground.F){
			this.tetirsGirds[i * aCurrentTetirsBackground.TETRIS_WIDTH + j] = aCurrentTetirsBackground.F;
		}else if (l == aCurrentTetirsBackground.Y) {
			this.tetirsGirds[i * aCurrentTetirsBackground.TETRIS_WIDTH + j] = aCurrentTetirsBackground.Y;
		}else{
			Log.e("NoIdError", "TetrisGirds.setGirds(int i,int j,int l)");
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
			Log.e("NoIdError", "TetrisGirds.setGirds(int x,int l)");
		}
	}
	
	//��ȡָ�����ӵ�ֵ
	public int getGirds(int i , int j){
		return this.tetirsGirds[i * aCurrentTetirsBackground.TETRIS_WIDTH + j];
	}
	
	public int getGirds(int x){
		return this.tetirsGirds[x];
	}
	
}
