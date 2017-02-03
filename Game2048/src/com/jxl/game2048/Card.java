package com.jxl.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 将游戏界面视为由16张小卡片组成
 * regard the board as being composed by 16 cards  
 * @author Administrator
 *
 */
public class Card extends FrameLayout{

	private TextView label;
	
	public Card(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		label = new TextView(getContext());
		label.setTextSize(32);
//		label.setBackgroundColor(0x33ffffff);
//		label.setBackgroundColor(0xffFFEC8B);
		label.setGravity(Gravity.CENTER);
//		setBGColor(0);
		
		LayoutParams lp = new LayoutParams(-1,-1);
		//设置卡片之间的间隔
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);
		
		setNum(0);
	}
	
	private int num = 0;
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
		if(num<=0){
			//先转换为字符串格式
			label.setText("");
		}else{
			label.setText(num+"");
		}
	}

	public boolean equals(Card o) {
		// TODO Auto-generated method stub
		return getNum()==o.getNum();
	}
	
	public void setBGColor(int num){
		switch (num) {
		case 2:
			label.setBackgroundColor(0xffFFF68F);
			break;
		case 4:
			label.setBackgroundColor(0xffFFEC8B);
			break;
		case 8:
			label.setBackgroundColor(0xffFFD700);
			break;
		case 16:
			label.setBackgroundColor(0xffFFC125);
			break;
		case 32:
			label.setBackgroundColor(0xffFF890F);
			break;
		case 64:
			label.setBackgroundColor(0xffFFA500);
			break;
		case 128:
			label.setBackgroundColor(0xffFF8C00);
			break;
		case 256:
			label.setBackgroundColor(0xffFF7F24);
			break;
		case 512:
			label.setBackgroundColor(0xffFF4500);
			break;
		case 1024:
			label.setBackgroundColor(0xffFF0000);
			break;
		case 2048:
			label.setBackgroundColor(0xff7FF00);
			break;
		case 4096:
			label.setBackgroundColor(0xff68228B);
			break;
		default:
			label.setBackgroundColor(0x33ffffff);
			break;
		}
	}
	
}
