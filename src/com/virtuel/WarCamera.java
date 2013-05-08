package com.virtuel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

import com.virtuel.math.vec.Vec3;

public class WarCamera extends Camera {	
	
	/** The speed of the mouse. */
	protected int MouseSpeed = 2;
	/** The max rotation value to look up. */
	protected double MaxLookUp = 90;
	/** The max rotation value to look down. */
	protected double MaxLookDown = -90;
	/** The Field of view. */
	protected float Fov = 70;
	/** The aspectRatio - Width/Height.*/
	protected float AspectRatio = 1;
	/** The near value. */
	protected float Near = 0.1f;
	/** the far value*/
	protected float Far = 10f;
	
	protected Vec3.d Velocity;
	
	protected boolean flying = true;
	
	public WarCamera() {
		Velocity = new Vec3.d();
	}
	
	public void reset() {
		Position = new Vec3.d();
		Rotation = new Vec3.d();
	}
	
	public WarCamera set(float fov, float aspectRatio, float near, float far) {
		Fov = fov;
		AspectRatio = aspectRatio;
		Near = near;
		Far = far;
		return this;
	}
	
	public WarCamera setFov(float fov) {
		Fov = fov;
		return this;
	}
	
	public WarCamera setAspectRatio(float aspectRatio) {
		AspectRatio = aspectRatio;
		return this;
	}
	
	public WarCamera setAspectRatio(float width, float height) {
		AspectRatio = width/height;
		return this;
	}
	
	public WarCamera setNearFar(float near, float far) {
		Near = near;
		Far = far;
		return this;
	}
	
	public WarCamera setMouseSpeed(int mouseSpeed) {
		return this;
	}
	
	@Override
	public void create() {
		GLU.gluPerspective(Fov, AspectRatio, Near, Far);
	}

	@Override
	public void update(double delta) {
		updateInput(delta);
	}
	
	@Override
	protected void updateInput(double delta) {
		if (Mouse.isGrabbed()) {
			float mouseDX = Mouse.getDX() * MouseSpeed * .16f;
			float mouseDY = Mouse.getDY() * MouseSpeed * .16f;
        	Rotation.Y += mouseDX;
        	Rotation.Y %= 360;
        	if (Rotation.Y < 0) Rotation.Y += 360;
        		
        	Rotation.X += -mouseDY;
        	if (Rotation.X > MaxLookUp) Rotation.X = MaxLookUp;
        	else if (Rotation.X < MaxLookDown) Rotation.X = MaxLookDown;
        }
		
		Position.X += Velocity.X;
		Position.Y += Velocity.Y;
		Position.Z += Velocity.Z;
	}
	
	public float getFov() {
		return Fov;
	}
	
	public float getAspectRatio() {
		return AspectRatio;
	}
	
	public float getNear() {
		return Near;
	}
	
	public float getFar() {
		return Far;
	}

}
