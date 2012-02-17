package com.example;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MoveRepeater implements OnTouchListener {

	private GameView game;
	private int maxWidth;
	private int inc;
	private boolean moveThreadRunning = false;
	private boolean cancelMoveThread = false;
	private Handler handler = new Handler();

	private int moveRightInc;
	private int moveLeftInc;

	public MoveRepeater(GameView game, int width, int inc) {
		this.game = game;
		this.maxWidth = width;
		this.inc = inc;
	}

	public boolean onTouch(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			Log.i("Repeat", "ACTION_DOWN");
			if (motionEvent.getX() > maxWidth / 2) {
				moveRightInc = inc;
				moveLeftInc = 0;
			} else {
				moveRightInc = 0;
				moveLeftInc = inc;
			}
			handleMoveDown();
			return true;
		} else if (action == MotionEvent.ACTION_UP) {
			Log.i("Repeat", "ACTION_UP");
			handleMoveUp();
			return true;
		}
		return false;
	}

	private synchronized void handleMoveDown() {
		if (!moveThreadRunning) {
			startRepeatThread();
		}
	}

	private synchronized void handleMoveUp() {
		cancelMoveThread = true;
	}

	private synchronized void startRepeatThread() {

		Thread r = new Thread() {

			@Override
			public void run() {
				try {

					moveThreadRunning = true;
					while (!cancelMoveThread) {

						handler.post(new Runnable() {
							public void run() {
								move();
							}
						});

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							throw new RuntimeException(
									"Could not wait between moves.", e);
						}
					}
				} finally {
					moveThreadRunning = false;
					cancelMoveThread = false;
				}
			}
		};

		// start the move thread
		r.start();
	}

	private void move() {
		if (moveLeftInc > 0)
			game.bg.moveLeft(moveLeftInc);
		if (moveRightInc > 0)
			game.bg.moveRight(moveRightInc);
	}
}