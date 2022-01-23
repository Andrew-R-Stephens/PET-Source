package com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.entities;

// http://stackoverflow.com/questions/14607640/rotating-a-vector-in-3d-space

import android.opengl.Matrix;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.services.SceneLoader;

/*
 Class Name:

 CCamera.

 Created by:

 Allen Sherrod (Programming Ace of www.UltimateGameProgramming.com).

 Description:

 This class represents a camera in a 3D scene.
 */

public class Camera {

	public float xPos, yPos, zPos; // Camera position.
	public float oxPos, oyPos, ozPos; // original Camera position

	public float xView, yView, zView; // Look at position.
	public float oxView, oyView, ozView; // original Look at position.

	public float xUp, yUp, zUp; // Up direction.
	public float oxUp, oyUp, ozUp; // original Up direction.

	private SceneLoader scene;
	private final BoundingBox boundingBox = new BoundingBox("scene",-20,20,-20,20,-20,20);

	float[] matrix = new float[16];
	float[] buffer = new float[12 + 12 + 16 + 16];
	private long animationCounter;
	private Object[] lastAction;
	private boolean changed = false;

	public Camera() {
		// Initialize variables...
		//this(0, 0, 6, 0, 0, -1, 0, 1, 0);
		this(0, 0, 6, 0, 0, -1, 0, 1, 0);
	}

	public Camera(float xPos, float yPos, float zPos, float xView, float yView, float zView, float xUp, float yUp,
				  float zUp) {
		// Here we set the camera to the values sent in to us. This is mostly
		// used to set up a
		// default position.
		this.oxPos = this.xPos = xPos;
		this.oyPos = this.yPos = yPos;
		this.ozPos = this.zPos = zPos;
		this.oxView = this.xView = xView;
		this.oyView = this.yView = yView;
		this.ozView = this.zView = zView;
		this.oxUp = this.xUp = xUp;
		this.oyUp = this.yUp = yUp;
		this.ozUp = this.zUp = zUp;
	}

	public void setScene(SceneLoader scene) {
		this.scene = scene;
	}

	public synchronized void animate(){
		if (lastAction == null || animationCounter == 0){
			lastAction = null;
			animationCounter = 100;
			return;
		}
		String method = (String) lastAction[0];
		if (method.equals("translate")){
			float dX = (Float) lastAction[1];
			float dY = (Float) lastAction[2];
			translateCameraImpl(dX*animationCounter/100, dY*animationCounter/100);
		} else if (method.equals("rotate")){
			float rotZ = (Float)lastAction[1];
			RotateImpl(rotZ/100*animationCounter);
		}
		animationCounter--;
	}

	public synchronized void MoveCameraZ(float direction){
		if (direction == 0) return;
		MoveCameraZImpl(direction);
		lastAction = new Object[]{"zoom",direction};
	}
	public void MoveCameraZImpl(float direction) {
		// Moving the camera requires a little more then adding 1 to the z or
		// subracting 1.
		// First we need to get the direction at which we are looking.
		float xLookDirection = 0, yLookDirection = 0, zLookDirection = 0;

		// The look direction is the view minus the position (where we are).
		xLookDirection = xView - xPos;
		yLookDirection = yView - yPos;
		zLookDirection = zView - zPos;

		// Normalize the direction.
		float dp = Matrix.length(xLookDirection, yLookDirection, zLookDirection);
		xLookDirection /= dp;
		yLookDirection /= dp;
		zLookDirection /= dp;

		direction *= .1;

		// Call UpdateCamera to move our camera in the direction we want.
		UpdateCamera(xLookDirection, yLookDirection, zLookDirection, direction);
	}

	public synchronized void PanCamera(float dx, float dy) {
		if (dx == 0 && dy == 0) return;

		xPos -= dx;
		zPos -= dy;
		xView -= dx;
		zView -= dy;

		Log.d("RotPan", "\n");

		setChanged(true);

		lastAction = new Object[]{"pan", dx + " " + dy};

	}

	void UpdateCamera(float xDir, float yDir, float zDir, float dir) {

		Matrix.setIdentityM(matrix, 0);
		Matrix.translateM(matrix, 0, xDir * dir, yDir * dir, zDir * dir);

		Matrix.multiplyMV(buffer, 0, matrix, 0, getLocationVector(), 0);
		Matrix.multiplyMV(buffer, 4, matrix, 0, getLocationViewVector(), 0);
		Matrix.multiplyMV(buffer, 8, matrix, 0, getLocationUpVector(), 0);

		if (isOutOfBounds(buffer)) return;

		xPos = buffer[0] / buffer[3];
		yPos = buffer[1] / buffer[3];
		zPos = buffer[2] / buffer[3];
		xView = buffer[4] / buffer[7];
		yView = buffer[5] / buffer[7];
		zView = buffer[6] / buffer[7];
		xUp = buffer[8] / buffer[11];
		yUp = buffer[9] / buffer[11];
		zUp = buffer[10] / buffer[11];

		//pointViewToOrigin();

		setChanged(true);
	}

	private boolean isOutOfBounds(float[] buffer) {
		if (boundingBox.outOfBound(buffer[0] / buffer[3],buffer[1] / buffer[3],buffer[2] / buffer[3])){
			Log.i("Camera", "Out of scene bounds");
			return true;
		}
		return false;
	}

	/**
	 * Translation is the movement that makes the Earth around the Sun.
	 * So in this context, translating the camera means moving the camera around the Zero (0,0,0)
	 *
	 * This implementation makes uses of 3D Vectors Algebra.
	 *
	 * The idea behind this implementation is to translate the 2D user vectors (the line in the
	 * screen) with the 3D equivalents.
	 *
	 * In order to to that, we need to calculate the Right and Arriba vectors so we have a match
	 * for user 2D vector.
	 *
	 * @param dX the X component of the user 2D vector, that is, a value between [-1,1]
	 * @param dY the Y component of the user 2D vector, that is, a value between [-1,1]
	 */
	public synchronized void translateCamera(float dX, float dY) {
		Log.d("Camera","translate:"+dX+","+dY);
		if (dX == 0 && dY == 0) return;

		translateCameraImpl(dX, dY);

		lastAction = new Object[]{"translate",dX, dY};
	}

	public void translateCameraImpl(float dX, float dY) {
		float vlen;

		// Translating the camera requires a directional vector to rotate
		// First we need to get the direction at which we are looking.
		// The look direction is the view minus the position (where we are).
		// Get the Direction of the view.
		float xLook = 0, yLook = 0, zLook = 0;
		xLook = oxView - oxPos;
		yLook = oyView - oyPos;
		zLook = ozView - ozPos;
		vlen = Matrix.length(xLook, yLook, zLook);
		xLook /= vlen;
		yLook /= vlen;
		zLook /= vlen;

		// Arriba is the 3D vector that is **almost** equivalent to the 2D user Y vector
		// Get the direction of the up vector
		float xUp = 0, yUp = 0, zUp = 0;
		xUp = this.oxUp - oxPos;
		yUp = this.oyUp - oyPos;
		zUp = this.ozUp - ozPos;
		// Normalize the Right.
		vlen = Matrix.length(xUp, yUp, zUp);
		xUp /= vlen;
		yUp /= vlen;
		zUp /= vlen;

		// Right is the 3D vector that is equivalent to the 2D user X vector
		// In order to calculate the Right vector, we have to calculate the cross product of the
		// previously calculated vectors...

		// The cross product is defined like:
		// A x B = (a1, a2, a3) x (b1, b2, b3) = (a2 * b3 - b2 * a3 , - a1 * b3 + b1 * a3 , a1 * b2 - b1 * a2)
		float xRight = 0, yRight = 0, zRight = 0;
		xRight = (yLook * zUp) - (zLook * yUp);
		yRight = (zLook * xUp) - (xLook * zUp);
		zRight = (xLook * yUp) - (yLook * xUp);
		// Normalize the Right.
		vlen = Matrix.length(xRight, yRight, zRight);
		xRight /= vlen;
		yRight /= vlen;
		zRight /= vlen;

		// Once we have the Look & Right vector, we can recalculate where is the final Arriba vector,
		// so its equivalent to the user 2D Y vector.
		xUp = (yRight * zLook) - (zRight * yLook);
		yUp = (zRight * xLook) - (xRight * zLook);
		zUp = (xRight * yLook) - (yRight * xLook);
		// Normalize the Right.
		vlen = Matrix.length(xUp, yUp, zUp);
		xUp /= vlen;
		yUp /= vlen;
		zUp /= vlen;

		float[] coordinates = new float[] {
				xPos, yPos, zPos, 1,
				xView, yView, zView, 1,
				this.xUp, this.yUp, this.zUp, 1 };

		if (dX != 0 && dY != 0) {

			// in this case the user is drawing a diagonal line:    \v     ^\    v/     /^
			// so, we have to calculate the perpendicular vector of that diagonal

			// The perpendicular vector is calculated by inverting the X/Y values
			// We multiply the initial Right and Arriba vectors by the User's 2D vector
			xRight *= dY;
			yRight *= dY;
			zRight *= dY;
			xUp *= dX;
			yUp *= dX;
			zUp *= dX;

			// Then we add the 2 affected vectors to the the final rotation vector
			float rotX, rotY, rotZ;
			rotX = xRight + xUp;
			rotY = yRight + yUp;
			rotZ = zRight + zUp;
			vlen = Matrix.length(rotX, rotY, rotZ);
			rotX /= vlen;
			rotY /= vlen;
			rotZ /= vlen;

			// in this case we use the vlen angle because the diagonal is not perpendicular
			// to the initial Right and Arriba vectors
			createRotationMatrixAroundVector(buffer, 24, vlen,
					rotX,
					rotY,
					rotZ);
		}
		else if (dX != 0){
			// in this case the user is drawing an horizontal line: <-- ó -->
			createRotationMatrixAroundVector(buffer, 24, dX,
					xUp,
					yUp,
					zUp);
		}
		else{
			// in this case the user is drawing a vertical line: |^  v|
			createRotationMatrixAroundVector(buffer, 24, dY,
					xRight,
					yRight,
					zRight);
		}
		multiplyMMV(buffer, 0, buffer, 24, coordinates, 0);

		if (isOutOfBounds(buffer)) return;

		xPos = buffer[0] / buffer[3];
		yPos = buffer[1] / buffer[3];
		zPos = buffer[2] / buffer[3];
		xView = buffer[4 + 0] / buffer[4 + 3];
		yView = buffer[4 + 1] / buffer[4 + 3];
		zView = buffer[4 + 2] / buffer[4 + 3];
		this.xUp = buffer[8 + 0] / buffer[8 + 3];
		this.yUp = buffer[8 + 1] / buffer[8 + 3];
		this.zUp = buffer[8 + 2] / buffer[8 + 3];

		setChanged(true);

	}

	public static void createRotationMatrixAroundVector(float[] matrix, int offset, float angle, float x, float y,
			float z) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		float cos_1 = 1 - cos;

		// @formatter:off
		matrix[offset+0 ]=cos_1*x*x + cos     ;    	matrix[offset+1 ]=cos_1*x*y - z*sin   ;   matrix[offset+2 ]=cos_1*z*x + y*sin   ;   matrix[offset+3]=0   ;
		matrix[offset+4 ]=cos_1*x*y + z*sin   ;  	matrix[offset+5 ]=cos_1*y*y + cos     ;   matrix[offset+6 ]=cos_1*y*z - x*sin   ;   matrix[offset+7]=0   ;
		matrix[offset+8 ]=cos_1*z*x - y*sin   ;  	matrix[offset+9 ]=cos_1*y*z + x*sin   ;   matrix[offset+10]=cos_1*z*z + cos    ;   matrix[offset+11]=0  ;
		matrix[offset+12]=0           		 ;      matrix[offset+13]=0          		  ;   matrix[offset+14]=0          		  ;   matrix[offset+15]=1  ;
		
		// @formatter:on
	}

	public static void multiplyMMV(float[] result, int retOffset, float[] matrix, int matOffet, float[] vector4Matrix,
			int vecOffset) {
		for (int i = 0; i < vector4Matrix.length / 4; i++) {
			Matrix.multiplyMV(result, retOffset + (i * 4), matrix, matOffet, vector4Matrix, vecOffset + (i * 4));
		}
	}

	public float[] getLocationVector() {
		return new float[] { xPos, yPos, zPos, 1f };
	}

	public float[] getLocationViewVector() {
		return new float[] { xView, yView, zView, 1f };
	}

	public float[] getLocationUpVector() {
		return new float[] { xUp, yUp, zUp, 1f };
	}

	public boolean hasChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
	public String toString() {
		return "Camera [xPos=" + xPos + ", yPos=" + yPos + ", zPos=" + zPos + ", xView=" + xView + ", yView=" + yView
				+ ", zView=" + zView + ", xUp=" + xUp + ", yUp=" + yUp + ", zUp=" + zUp + "]";
	}

	public synchronized void Rotate(float rotViewerZ) {
		if (rotViewerZ == 0) return;
		RotateImpl(rotViewerZ);
		lastAction = new Object[]{"rotate",rotViewerZ};
	}

	public void RotateImpl(float rotViewerZ) {
		if (Float.isNaN(rotViewerZ)) {
			Log.w("Rot", "NaN");
			return;
		}
		float xLook = xView - xPos;
		float yLook = yView - yPos;
		float zLook = zView - zPos;
		float vlen = Matrix.length(xLook, yLook, zLook);
		xLook /= vlen;
		yLook /= vlen;
		zLook /= vlen;

		createRotationMatrixAroundVector(buffer, 24, rotViewerZ, xLook, yLook, zLook);
		float[] coordinates = new float[] { xPos, yPos, zPos, 1, xView, yView, zView, 1, xUp, yUp, zUp, 1 };
		multiplyMMV(buffer, 0, buffer, 24, coordinates, 0);

		xPos = buffer[0];
		yPos = buffer[1];
		zPos = buffer[2];
		xView = buffer[4 + 0];
		yView = buffer[4 + 1];
		zView = buffer[4 + 2];
		xUp = buffer[8 + 0];
		yUp = buffer[8 + 1];
		zUp = buffer[8 + 2];

		setChanged(true);
	}

}
