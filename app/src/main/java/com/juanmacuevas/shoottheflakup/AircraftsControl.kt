package com.juanmacuevas.shoottheflakup

import android.content.res.Resources
import android.graphics.Canvas
import android.util.DisplayMetrics

import java.util.ArrayList

internal class AircraftsControl(
    private val res: Resources,
    private val dm: DisplayMetrics,
    private val gameControl: GameEvents) {

    private var aircrafts= ArrayList<FunctionalAircraft>()
    private var newaircraftimer = 0L

    init {
        FunctionalAircraft(res, dm)
    }

    fun draw(canvas: Canvas) {
        val it = aircrafts!!.iterator()
        while (it.hasNext()) {
            val a = it.next()
            a?.draw(canvas)
        }
    }

    fun update(timer: Long, bullets: Iterable<FunctionalBullet>) {
        val newList = ArrayList<FunctionalAircraft>()
        for (a in aircrafts!!) {
            if (a.isOver) continue
            a.update(timer)

            for (b in bullets) {
                if (a.isFlying && b.isFlying && a.impactDetected(b)) {
                    gameControl.aircraftExploded()
                    a.setImpact()
                    b.setImpact()
                    newList.add(FunctionalAircraft(res, dm))


                }
            }
            newList.add(a)

        }
        aircrafts = newList
        newaircraftimer += timer
        if (newaircraftimer > 3000) {
            aircrafts!!.add(FunctionalAircraft(res, dm))
            newaircraftimer = 0
        }

    }
}
