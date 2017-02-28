package com.example.shwetashahane.assignment3;

/**
 * Created by shwetashahane on 2/25/17.
 */

import android.graphics.*;

public class Circle
{
    float startX;
    float startY;
    float radius_circle;
    Paint circle_color;
    public float dx;
    public float dy;
    boolean moveFlag;

    public Circle(float x1, float y1, float radius, Paint p, float xVelocity, float yVelocity)
    {
        startX = x1;
        startY = y1;
        radius_circle = radius;
        circle_color = p;
        dx = xVelocity;
        dy = yVelocity;
        this.moveFlag=false;
    }


    public float getDx()
    {
        return dx;
    }

    public void setDx(float dx)
    {
        this.dx = dx;
    }

    public float getDy()
    {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }
}
