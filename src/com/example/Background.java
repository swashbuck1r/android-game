package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class Background extends View {
	private static final int offscreenBufferWidth = 96;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	BitmapDrawable drawable;
	Bitmap b;
	int xOffset = 0;
	int leftBorder;
	int rightBorder;
	
	public Background(Context context, int width, int height) {
		super(context);
		mPaint.setColor(0xFFFF0000);
		drawable = (BitmapDrawable)getResources().getDrawable(R.drawable.bgsheet);
		b = drawable.getBitmap();
		
		//the actual draw borders are wide enough to let the entire image draw offscreen when
		//offset is zero.  This provides the appearance of the mountains scrolling on/off the screen.
		leftBorder = 0 - offscreenBufferWidth;
		rightBorder = width + offscreenBufferWidth + 100;
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		
		drawMountain(c, 60, 300);
		drawMountain(c, 90, 200);
		drawMountain(c, 200, 400);
		drawMountain(c, 500, 320);
		drawMountain(c, 600, 200);
		drawMountain(c, 650, 300);
	}
	
	private int getMountainOffset(int loc) {
		int mod = (xOffset + loc)%rightBorder;
		int returnVal = leftBorder + mod;
		if(returnVal < leftBorder){
			returnVal = rightBorder + mod;
		}
		return returnVal;
	}
	
	public void setOffset(int offset) {
		this.xOffset = offset;
	}
	
	public void moveRight(int offset) {
		this.xOffset = xOffset - offset;
	}
	
	public void moveLeft(int offset) {
		this.xOffset = xOffset + offset;
	}
	
	private void drawMountain(Canvas c, int x, int height) {
		int offsetLoc = getMountainOffset(x);
		drawMountainBase(c, offsetLoc, height);
		drawMountainPeak(c, offsetLoc, height);
	}
	
	private void drawMountainBase(Canvas c, int x, int height) {
		int bottom = c.getHeight();
		int bHeight = 32;
		int top = bottom - height + 32;
		
		for(int i=top; i<bottom; i+=bHeight) {
			Rect r = new Rect(0, 32, 64, 32+bHeight);
			Rect d = new Rect(x, i, x+64, i+bHeight);
			c.drawBitmap(b, r, d, null);
		}
	}
	
    private void drawMountainPeak(Canvas c, int x, int height) {
    	int bottom = c.getHeight();
    	int top = bottom-height;
    	Rect r = new Rect(0, 0, 64, 32);
		Rect d = new Rect(x, top, x+64, top+32);
		c.drawBitmap(b, r, d, null);
	}
}
