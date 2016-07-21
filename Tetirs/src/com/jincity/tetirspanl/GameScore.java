package com.jincity.tetirspanl;

public class GameScore {
	private int theGameScore = 0;
	
	public GameScore(){
		this.theGameScore = 0;
	}
	
	//«Â¡„
	public void setBasicScore(){
		this.theGameScore = 0;
	}
	
	public int getTheGameScore() {
		return theGameScore;
	}

	public void setTheGameScore(int theGameScore) {
		this.theGameScore += theGameScore;
	}
	
	
	
}
