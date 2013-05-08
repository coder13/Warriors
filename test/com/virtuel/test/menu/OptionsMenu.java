package com.virtuel.test.menu;

import com.virtuel.Engine;
import com.virtuel.gui.Button;
import com.virtuel.gui.Menu;
import com.virtuel.math.vec.Vec2;

public class OptionsMenu extends Menu {

	private Button Back;
	
	public OptionsMenu(Engine parent, String name) {
		super(parent, name);
	}

	@Override
	public void init() {
		int middle = parent.getWindow().getWidth()/2;
		Back = new Button(new Vec2.i(parent.getWindow().getWidth()-144,0), new Vec2.i(144, 89), "Back") {

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
				parent.getMenuHandler().lastMenu();
			}
			
		};
	
		addControl(Back);
	}

	@Override
	public void draw() {
		
	}

}
