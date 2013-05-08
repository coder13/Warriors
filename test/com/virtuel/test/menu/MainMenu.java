package com.virtuel.test.menu;

import com.virtuel.Engine;
import com.virtuel.gui.Button;
import com.virtuel.gui.Menu;
import com.virtuel.math.vec.Vec2;
import com.virtuel.test.WarEngine;

public class MainMenu extends Menu {

	private Button Start, Quit, Options;
	
	public MainMenu(Engine parent, String name) {
		super(parent, name);
	}

	@Override
	public void init() {
		int startWidth = 144, startHeight = 89;
		System.out.println(String.format("%d / %d", startWidth, startHeight));
		Start 	= new Button(new Vec2.i(parent.getWindow().getWidth()/2-72, 128), new Vec2.i(startWidth, startHeight), "Start Game") {

			@Override
			public void down() {
				color.set(127, 127, 127);
			}

			@Override
			public void up() {
				color.set(255, 255, 255);
			}

			@Override
			public void clicked() {
				((WarEngine) WarEngine.Instance).startGame();
				
			}
			
		};
		
		Quit 	= new Button(new Vec2.i(parent.getWindow().getWidth()/2-72, parent.getWindow().getHeight()-(int)(startHeight*1.5)), new Vec2.i(startWidth, startHeight), "Quit") {

			@Override
			public void down() {
				color.set(127, 127, 127);
			}

			@Override
			public void up() {
				color.set(255, 255, 255);
			}

			@Override
			public void clicked() {
				parent.stop();
			}
			
		};
		
		Options = new Button(new Vec2.i(parent.getWindow().getWidth()/2-72, (int)(128+startHeight*1.5)), new Vec2.i(startWidth, startHeight), "Options") {
			
			@Override
			public void down() {
				color.set(127, 127, 127);
			}

			@Override
			public void up() {
				color.set(255, 255, 255);
			}

			@Override
			public void clicked() {
				parent.getMenuHandler().setCurrentMenu("OptionsMenu");
			}
			
			
		};
		
		
		addControl(Start);
		addControl(Quit);
		addControl(Options);
	}

	@Override
	public void draw() {
	}

}
