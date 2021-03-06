package com.juanmacuevas.shoottheflakup;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;

class BulletsControl {
    private final DisplayMetrics metrics;
    private final Resources res;
    private ArrayList<FunctionalBullet> bullets;

    public BulletsControl(Resources res, DisplayMetrics metrics) {
        this.res = res;
        bullets = new ArrayList<>();
        this.metrics=metrics;

    }

    public void draw(Canvas canvas) {
        for(Iterator it = bullets.iterator(); it.hasNext();){
            FunctionalBullet b = (FunctionalBullet) it.next();
            if (b!=null)
                b.draw(canvas);
        }

    }

    public void update(long timer) {
        FunctionalBullet b;
        int i = 0;
        while (!bullets.isEmpty() && bullets.size() > i) {
            b = bullets.get(i);
            if (b.isOver())
                bullets.remove(i);
            else {
                b.update(timer);
                i++;
            }
        }
    }

    public void addBullet(int power, float angle, Pair<Integer, Integer> bulletOrigin) {
        bullets.add(new FunctionalBullet(res,power, angle,bulletOrigin,metrics));

    }

    public Iterable<FunctionalBullet> iterable() {
        return bullets;
    }
}
