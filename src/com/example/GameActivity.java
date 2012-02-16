package com.example;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GameActivity extends Activity {

	public int xmax, ymax;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Set FullScreen & portrait
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.game);

		// Calculate Boundry
		Display display = getWindowManager().getDefaultDisplay();
		xmax = display.getWidth() - 50;
		ymax = display.getHeight() - 50;

		final FrameLayout main = (FrameLayout) findViewById(R.id.main_view);
		
		final GameView game = new GameView(this, xmax, ymax);
		main.addView(game);
		
		MoveRepeater moveRepeater = new MoveRepeater(game, xmax, 10);
		main.setOnTouchListener(moveRepeater);
	}
}