package com.juanmacuevas.shoottheflakup;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import androidx.core.util.Pair;


public class FunctionalBullet extends GraphicComponent{

	private static final int STATUS_FLYING = 0;

	private static final int STATUS_BOOM = 1;

	private static final int STATUS_OVER = 2;

	private static final int BULLET_RADIUS  = 4;


	private static final long TIME_EXPLODING  = 100;

	private long time;

	private int status;
	private float posX0;
	private float posY0;
	private float posX;
	private float posY;
	private float iniSpeedX;
	private float iniSpeedY;
	private final float tankBottom;
	private Paint paint;

	private long explodingTimer;


	public FunctionalBullet(Resources res, int power, float angle, Pair<Integer, Integer> bulletOrigin, DisplayMetrics metrics){
		super(res,metrics);
		time=0;
		paint = new Paint();
		posX0=bulletOrigin.first;
		posY0 = bulletOrigin.second;
		iniSpeedY=(float) (Math.sin(angle)*power*1.5) ;
		iniSpeedX=(float) (Math.cos(angle)*power*1.5);

		this.tankBottom = metrics.heightPixels - (FuncionalTank.TANK_BOTTOM_MARGIN * Utils.scale(metrics));

	}

	public void draw(Canvas c) {
		if (status == STATUS_FLYING)
			paint.setColor(Color.BLACK);
		else paint.setColor(Color.RED);
		c.drawCircle(posX, posY, BULLET_RADIUS * scale, paint);

	}

	public void update(long elapsedTime) {
		switch (status){		
		case STATUS_FLYING:
			time+=elapsedTime;
			double t2=(double)(time)/100;
			posX=(float) (posX0 + iniSpeedX*t2);
			posY=(float) (posY0 - (iniSpeedY*t2 - (9.8 *Math.pow(t2, 2)/2)));

			if (posY>tankBottom) {

				status=STATUS_BOOM;
				posY = tankBottom;
				explodingTimer = 0;

			}
			break;

		case STATUS_BOOM:
			explodingTimer+=elapsedTime;
			if (explodingTimer>TIME_EXPLODING)
				status=STATUS_OVER;
			break;
			default:
		}
	}

	public boolean isExploding() {
		return (status == STATUS_BOOM);
	}

	public boolean isOver() {
		return (status == STATUS_OVER);
	}

	public void setImpact() {

		status = STATUS_BOOM;
		explodingTimer = 0;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}


	public boolean isFlying() {
		return (status == STATUS_FLYING);
	}

}
