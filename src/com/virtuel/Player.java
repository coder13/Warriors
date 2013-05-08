package com.virtuel;

import org.lwjgl.input.Keyboard;

import com.virtuel.math.vec.Vec3;
import com.virtuel.net.Client;

import static com.virtuel.math.VMath.*;

public class Player extends Client {
	
	private WarCamera cam;
	
	private boolean Flying;
	private double Speed;
	
	public Player() {
		cam = new WarCamera();
		Flying = true;
		Speed = 1;
	}
	
	public void reset() {
		Flying = true;
		Speed = 1;
		cam.reset();
	}
	
	public Player setSpeed(double speed) {
		Speed = speed;
		return this;
	}
	
	public WarCamera createCam() {
		return cam;
	}
	
	
	/** Updates the camera's position. */
	public void update(double delta) {
		boolean keyUp      = InputHandler.isKeyDown(Keyboard.KEY_W),
       			keyDown    = InputHandler.isKeyDown(Keyboard.KEY_S),
       			keyLeft    = InputHandler.isKeyDown(Keyboard.KEY_A),
       			keyRight   = InputHandler.isKeyDown(Keyboard.KEY_D),
       			flyUp      = InputHandler.isKeyDown(Keyboard.KEY_Q),
       			flyDown    = InputHandler.isKeyDown(Keyboard.KEY_Z),
       			moveFaster = InputHandler.isKeyDown(Keyboard.KEY_TAB),
       			moveSlower = InputHandler.isKeyDown(Keyboard.KEY_LSHIFT),
//				jumping	   = InputHandler.isKeyPressed(Keyboard.KEY_SPACE),
				toggleFly  = InputHandler.isKeyPressed(Keyboard.KEY_F);
		
		double speed = Speed / (delta/32);
		
		cam.Velocity = new Vec3.d();
		cam.Velocity = move(cam.Velocity, cam.getRotation(), 
					keyRight ? 1 : keyLeft ? -1 : 0, keyUp ? -1 : keyDown ? 1 : 0);
		if (moveFaster) {
			speed *= 4;
		} if (moveSlower) {
			speed /= 4;
		}
		
		if (toggleFly) {
			Flying = !Flying;
		}
		
		if (Flying) {
			cam.Velocity.Y += (flyUp ? 1 : flyDown ? -1 : 0);
		}
		
		cam.Velocity = cam.Velocity.scale(speed);
		cam.update(delta);
	}
	
	/** Applies the camera's ModelViewMatrix. */
	public void draw() {
		cam.applyModelViewMatrix();
	}

	
	public boolean isFlying() {
		return Flying;
	}
	
	
	public Vec3.i getBlockPosition(){
		return new Vec3.i((int)Math.floor(cam.getX()), (int)Math.floor(cam.getY()), (int)Math.floor(cam.getZ()));
	}
	
	public Vec3.d getPosition(){
		return cam.getPosition();
	}
	
	public double getX() {
		return cam.getX();
	}
	
	public double getY() {
		return cam.getY();
	}
	
	public double getZ() {
		return cam.getZ();
	}
	
	public Vec3.d getRotation() {
		return cam.getRotation();
	} 
	
	public Camera getCam() {
		return cam;
	}
	
	private static Vec3.d move(Vec3.d Position, Vec3.d Rotation, double dx, double dz) {
		Position.Z += dx * cos(Rotation.Y - 90) + dz * cos(Rotation.Y);
		Position.X -= dx * sin(Rotation.Y - 90) + dz * sin(Rotation.Y);
		return Position;
	}
	
	
}