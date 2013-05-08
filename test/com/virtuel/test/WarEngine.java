package com.virtuel.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.virtuel.Engine;
import com.virtuel.Game;
import com.virtuel.InputHandler;
import com.virtuel.Time;
import com.virtuel.Window;
import com.virtuel.gui.Button;
import com.virtuel.gui.Menu;
import com.virtuel.gui.Theme;
import com.virtuel.interfaces.IEngine;
import com.virtuel.math.vec.Vec2;
import com.virtuel.test.menu.MainMenu;
import com.virtuel.test.menu.OptionsMenu;
import com.virtuel.util.Color;
import com.virtuel.util.Font;
import com.virtuel.util.Keys;
import com.virtuel.util.ScreenShotHandler;
import com.virtuel.util.Terminal;

public class WarEngine extends Engine implements IEngine {
	
	private Game warGame;
	
	private Terminal terminal;
	private Menu mainMenu, optionsMenu, pauseMenu;
	
	public WarEngine(Window window) {
		super(window);
		warGame = new Warriors(this);

		terminal = new Terminal(new Vec2.i(8, window.getHeight()-8), new Vec2.i(512, 256), "Main");
		
		mainMenu = new MainMenu(this, "MainMenu");
		optionsMenu = new OptionsMenu(this, "OptionsMenu");
		pauseMenu  = new PauseMenu(this, "PauseMenu");
		
		Instance = this;
	}
	
	@Override
	public void init() throws LWJGLException {
		super.init();

		mainMenu.init();
		optionsMenu.init();
		pauseMenu.init();
		
		menuHandler.addMenu(mainMenu);
		menuHandler.addMenu(optionsMenu);
		menuHandler.addMenu(pauseMenu);
		menuHandler.setCurrentMenu(mainMenu.getName());
		
		terminal.init();
		warGame.init();


		Theme warTheme = new Theme("res/WarriorsTheme/") {

			@Override
			public void destory() {
				
			}

			@Override
			public void drawButton(Button button) {
				
			}
			
		};
		
		if (warTheme.init() == false) {
			Running = false;
		}
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void update() {
		terminal.update();
		
		if (InputHandler.isKeyPressed(Keys.KEY_F2)) {
			long time = Time.getTime();
			terminal.process("Taking screenshot... screenshots/ " + time + ".png");
			ScreenShotHandler.take("screenshots/" + time);
		} if (InputHandler.isKeyPressed(Keys.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
		} if (InputHandler.isKeyPressed(Keys.KEY_B)) {
			menuHandler.lastMenu();
		}
		
		if ((InputHandler.isMouseButtonPressed(0) || terminal.isTyping()) && currentGame != null) {
			Paused = false;
			Mouse.setGrabbed(true);
		}
	}

	@Override
	public void draw() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
			fontRender.drawText(0, 0, String.format("FPS: %d", Time.getFps()));
			fontRender.drawText(8, 0, String.format("Delta: %.1f", Time.getDelta()));
			if (Paused) {
				fontRender.drawText(8, 1, "Paused", new Font(20, 20, Color.Red));
			}
			fontRender.drawText(0, 2, menuHandler.getCurrentMenuName());
			
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		terminal.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
	}

	
	public void startGame() {
		warGame.start();
		currentGame = warGame;
		menuHandler.setCurrentMenu(null);
	}
	
}
