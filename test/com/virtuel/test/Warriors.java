package com.virtuel.test;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;

import com.virtuel.Engine;
import com.virtuel.Game;
import com.virtuel.InputHandler;
import com.virtuel.Player;
import com.virtuel.Time;
import com.virtuel.World;
import com.virtuel.math.vec.Vec2;
import com.virtuel.rendering.BlockRenderer;
import com.virtuel.rendering.FontRenderer;
import com.virtuel.util.Color;
import com.virtuel.util.Font;
import com.virtuel.util.Keys;

public class Warriors extends Game {
	
	public static boolean DEBUG = true;
	
	private double scale;
	
	private boolean WireFrame = false;
	
	private Player player;
	private World world;
	
	
	public Warriors(Engine parent) {
		super(parent, "WarriorsGame");
		scale = parentEngine.getWindow().getWidth() / 32;
	}
	
	@Override
	public void init() {
		fontRender = new FontRenderer(new Font(20, 20, 16, new Color(192), true));
		fontRender.init();
		
		System.out.println(parentEngine.getWindow().getWidth() + "\t" + parentEngine.getWindow().getHeight());
		
		glEnable(GL_DEPTH_TEST);

		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
	}

	@Override
	public void start() {
		world = new World();
		BlockRenderer.setWorld(world);

		player = new Player();
		player.createCam().setAspectRatio(parentEngine.getWindow().getWidth(), parentEngine.getWindow().getHeight()).setNearFar(0.1f, 512).setup();
		
		
		glClearColor(0f, 0f, .75f, 1f);
		
		this.hasStarted = true;
	}
	
	@Override
	public void stop() {
		
	}

	@Override
	public void update() {
		if (DEBUG && InputHandler.isKeyPressed(Keys.KEY_R)) {
			world.reset();
			player.reset();
		} if (DEBUG && InputHandler.isKeyPressed(Keys.KEY_V)) {
			WireFrame = !WireFrame;
		} if (InputHandler.isKeyPressed(Keys.KEY_F3)) {
			DEBUG = !DEBUG;
		} 
		
		checkForGLErrors();
		
		player.update(Time.getDelta());
		
		world.update(player);
	}

	@Override
	public void draw() {
		player.getCam().use();
		
		player.draw();
		
		if (WireFrame)
			glPolygonMode(GL_FRONT, GL_LINE);
		glEnable(GL_CULL_FACE);
		
		world.renderAll();
		
		if (WireFrame)
			glPolygonMode(GL_FRONT, GL_FILL);
		glDisable(GL_CULL_FACE);
		
		
		parentEngine.getGuiCam().use();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		fontRender.drawText(16, 0, String.format("Chunks: %d / %d", world.getChunkCount(), world.getTotalChunkCount()));
		fontRender.drawText(64, 0, DEBUG ? "DEBUG" : "");
		fontRender.drawText(0, 1, String.format("x:%+5.3f\ny:%+5.3f\nz:%+5.3f", player.getX(), player.getY(), player.getZ()));

		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		
		Vec2.i mid = new Vec2.i(parentEngine.getWindow().getWidth()/2, parentEngine.getWindow().getHeight()/2);
		glPushMatrix();
			glTranslated(mid.X, mid.Y, 0);
			glRotated(player.getRotation().Z, 0,0,1);
			glBegin(GL_LINES);
				glColor3d(1, 1, 1);
				glVertex2d(-scale, 0);
				glVertex2d( scale, 0);
	
				glVertex2d(0, -scale);
				glVertex2d(0,  scale);
			glEnd();
		glPopMatrix();
	}
	
	public void destory(boolean crash){
		if (crash) {
			System.out.println("Crashed!");
		}
		System.exit(crash?1:0);
	}
	
	public boolean checkForGLErrors() {
		int error = glGetError(); 
		if (error != 0)
			System.out.println("openGL Error! id: " + error + "\t" + GLU.gluErrorString(error));
		return error != 0;
	}

	
	
}
