package com.example.Implementation;

import com.example.Infrastructure.ApplyEffect;
import com.example.probaslikidaliraboti.MainActivity;

public abstract class BasicApplyEffect {

	private MainActivity mainActivity;

	public BasicApplyEffect(MainActivity mainActivity) {
		super();
		this.mainActivity = mainActivity;
	}
	
	public void addToList(ApplyEffect ae){
		mainActivity.addEffect(ae);
	
	}
	
	

}
