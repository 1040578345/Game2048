package com.jxl.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout{
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initGameView();
	}

    private void initGameView(){
    	
    	setColumnCount(4);
    	setBackgroundColor(0xffbbada0);
    	
    	setOnTouchListener(new OnTouchListener() {
			
    		private float startX,startY,offsetX,offsetY;
    		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
			    switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX()-startX;
					offsetY = event.getY()-startY;
					
					//在水平方向上移动
					if(Math.abs(offsetX)>Math.abs(offsetY)){
						//向左滑动
						if(offsetX<-5){
							swipeLeft();
						//向右滑动
						}else if(offsetX>5){
							swipeRight();
						}
					}else{
						//向上滑动
						if(offsetY<-5){
							swipeUp();
						//向下滑动
						}else if(offsetY>5){
							swipeDown();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
    }
    
    @Override
    //只在屏幕在创建的时候被执行
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	// TODO Auto-generated method stub
    	super.onSizeChanged(w, h, oldw, oldh);
    	
    	//对宽、高求最小值，然后求出每一张卡片的宽度
    	int cardWidth = (Math.min(w,h)-10)/4;
    	addCards(cardWidth, cardWidth);
    	startGame();
    }
    
    private void addCards(int cardWidth,int cardHeight){
    	Card c;
    	
    	for(int y=0;y<4;y++){
    		for(int x=0;x<4;x++){
    			c = new Card(getContext());
    			c.setNum(0);
    			c.setBGColor(c.getNum());
    			addView(c, cardWidth, cardHeight);
    			
    			cardsMap[x][y] = c;
    		}
    		
    	}
    }
    
    private void startGame(){
    	
    	MainActivity.getMainActivity().clearScore();
    	
    	for (int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++){
				cardsMap[x][y].setNum(0);
			}			
		}
    	
    	addRandomNum();
    	addRandomNum();
    }    
    
    private void addRandomNum(){
    	
    	emptyPoints.clear();
    	
    	for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum()<=0){
					emptyPoints.add(new Point(x,y));
				}
			}
			
		}
    	
    	Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
    	cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    	cardsMap[p.x][p.y].setBGColor(cardsMap[p.x][p.y].getNum());
    }
    
    private void swipeLeft(){
    	
    	boolean merge = false;
    	
    	for (int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++){
				
				for(int x1 = x+1; x1 < 4; x1++){
					//当前位置上的值不为0
					if(cardsMap[x1][y].getNum()>0){
						//当前卡片左边的卡片为空，则将当前卡片的值传到左边
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setBGColor(cardsMap[x1][y].getNum());
							x--;
							merge = true;
						//左边卡片的值不为空且与当前值当等
						}else if(cardsMap[x][y].equals(cardsMap[x1][y])){
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setBGColor(cardsMap[x1][y].getNum());
							
					        MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
					        merge = true;
						}
						break;
					}
				}
			}			
		}
    	
    	if(merge){
    		addRandomNum();
    		checkComplete();
    	}
    }
    
    private void swipeRight(){
    	
    	boolean merge = false;
    	
    	for (int y = 0; y < 4; y++) {
			for(int x = 3; x >= 0; x--){
				
				for(int x1 = x-1; x1 >= 0; x1--){
					if(cardsMap[x1][y].getNum()>0){
						//左边的卡片为空，则将当前卡片的值传到左边
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setBGColor(cardsMap[x1][y].getNum());
							x++;
							merge = true;
						//左边卡片的值不为空且与当前值当等
						}else if(cardsMap[x][y].equals(cardsMap[x1][y])){
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x1][y].setNum(0);
							cardsMap[x1][y].setBGColor(cardsMap[x1][y].getNum());
							
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}			
		}
    	
    	if(merge){
    		addRandomNum();
    		checkComplete();
    	}
    }

    private void swipeUp(){
    	
    	boolean merge = false;
	
    	for (int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++){
				
				for(int y1 = y+1; y1 < 4; y1++){
					if(cardsMap[x][y1].getNum()>0){
						//左边的卡片为空，则将当前卡片的值传到左边
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x][y1].setNum(0);
							cardsMap[x][y1].setBGColor(cardsMap[x][y1].getNum());
							y--;
							merge = true;
						//左边卡片的值不为空且与当前值当等
						}else if(cardsMap[x][y].equals(cardsMap[x][y1])){
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x][y1].setNum(0);
							cardsMap[x][y1].setBGColor(cardsMap[x][y1].getNum());
							
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}			
		}
    	
    	if(merge){
    		addRandomNum();
    		checkComplete();
    	}
    }

    private void swipeDown(){
	
    	boolean merge = false;
    	
    	for (int x = 0; x < 4; x++) {
			for(int y = 3; y >= 0; y--){
				
				for(int y1 = y-1; y1 >= 0; y1--){
					if(cardsMap[x][y1].getNum()>0){
						//左边的卡片为空，则将当前卡片的值传到左边
						if(cardsMap[x][y].getNum()<=0){
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x][y1].setNum(0);
							cardsMap[x][y1].setBGColor(cardsMap[x][y1].getNum());
							y++;
							merge = true;
						//左边卡片的值不为空且与当前值当等
						}else if(cardsMap[x][y].equals(cardsMap[x][y1])){
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y].setBGColor(cardsMap[x][y].getNum());
							cardsMap[x][y1].setNum(0);
							cardsMap[x][y1].setBGColor(cardsMap[x][y1].getNum());
							
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}			
		}
    	
    	if(merge){
    		addRandomNum();
    		checkComplete();
    	}
    }
    
    private void checkComplete(){
    	
    	boolean complete = true;
    	
    	ALL:
    	for (int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++){
				if(cardsMap[x][y].getNum()==0||
						(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
						(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
						(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
						(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
					
					complete = false;
					break ALL;
				}
			}
		}
    	
    	if(complete){
    		new AlertDialog.Builder(getContext()).setTitle("2048").
    		setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					startGame();
				}
			}).show();
    	}
    }
    

}
