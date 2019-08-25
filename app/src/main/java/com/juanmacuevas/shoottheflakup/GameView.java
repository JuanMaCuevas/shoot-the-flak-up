package com.juanmacuevas.shoottheflakup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private GameThread thread;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// register our interest in hearing about changes to our surface
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		setFocusable(true); // make sure we get key events
		setFocusableInTouchMode(true); // make sure we get touch events

	}


	public void setThread(GameThread thread) {
		this.thread = thread;
	}


//	/**
//	 * Standard window-focus override. Notice focus lost so we can pause on
//	 * focus lost. e.g. user switches to take a call.
//	 */
//	@Override
//	public void onWindowFocusChanged(boolean hasWindowFocus) {
//		if (!hasWindowFocus) thread.pause();
//	}
//

	/*
	 * Callback invoked when the Surface has been created and is ready to be
	 * used.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/*
	 * Callback invoked when the Surface has been destroyed and must no longer
	 * be touched. WARNING: after this method returns, the Surface/Canvas must
	 * never be touched again!
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode

		thread.setRunning(false);
	}



}



