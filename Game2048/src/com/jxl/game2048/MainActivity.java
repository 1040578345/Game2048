package com.jxl.game2048;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvScore;
	private int score = 0;
	
	public MainActivity(){
		mainActivity = this;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
    }
    
    public void clearScore(){
    	score = 0;
    	showScore();
    }
    
    public void showScore(){
    	tvScore.setText(score+"");
    }
    
    public void addScore(int s){
    	score+=s;
    	showScore();
    }
    
    private static MainActivity mainActivity = null;
    
    public static MainActivity getMainActivity(){
		return mainActivity;
    	
    }
    
}
