package com.virtuel.test;

import com.virtuel.Engine;
import com.virtuel.Window;

import org.lwjgl.LWJGLException;

public class Main {

	public static int WIDTH = 960, HEIGHT = 640;
	
	public static void main(String[] args) {
		try {
			Window window = new Window(WIDTH, HEIGHT).create();
			Engine warEngine = new WarEngine(window).sync(120);
			warEngine.init();
			warEngine.start();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
}