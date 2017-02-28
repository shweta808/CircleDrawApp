package com.example.shwetashahane.assignment3;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.graphics.*;
import android.content.Context;
import java.util.*;
import android.view.View.OnTouchListener;


/**
 * Created by shwetashahane on 2/25/17.
 */

public class DrawCircleView extends View implements OnTouchListener {

    static Paint paint_obj;
    static public int height = 0;
    static public int width = 0;
    private float startX = 0;
    private float startY = 0;
    private float radius = 0;
    private float endX = 0;
    private float endY = 0;
    private static float xVelocity;
    private static float yVelocity;
    float moveX = 0;
    float moveY = 0;
    boolean moveDraw = false;
    private static String action_mode = "draw";
    ArrayList<Circle> circleList = new ArrayList<Circle>();
    ArrayList<Circle> deleteCircle = new ArrayList<Circle>();
    public VelocityTracker velocity;
    float top = 0f;
    float bottom = 0f;
    float right = 0f;
    float left = 0f;

    static
    {
        paint_obj = new Paint();
        paint_obj.setColor(Color.BLACK);
        paint_obj.setStyle(Paint.Style.STROKE);
        paint_obj.setStrokeWidth(15.0f);
    }

    public DrawCircleView(Context context)
    {
        super(context);
        this.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                return computeActionDown(event);
            case MotionEvent.ACTION_UP:
                return computeActionUp(event);
            case MotionEvent.ACTION_MOVE:
                return onComputeMove(event);
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                return false;
        }
        return true;
    }

    private boolean computeActionDown(MotionEvent event)
    {
        if (getMode().equalsIgnoreCase("draw"))
        {
            startX = event.getX();
            startY = event.getY();
            radius = 0;
            invalidate();
        } else if (getMode().equalsIgnoreCase("move"))
        {
            velocity = VelocityTracker.obtain();
            velocity.addMovement(event);
            moveX = event.getX();
            moveY = event.getY();
        }
        return true;
    }

    private boolean computeActionUp(MotionEvent event)
    {
        if (getMode().equalsIgnoreCase("draw"))
        {
            endX = event.getX();
            endY = event.getY();
            top = startY;
            left = startX;
            bottom = height - top;
            right = width - left;
            ArrayList<Float> min_radius = new ArrayList<Float>();
            min_radius.add(top);
            min_radius.add(bottom);
            min_radius.add(right);
            min_radius.add(left);
            Collections.sort(min_radius);
            if (radius == 0)
                radius = 25;
            else if (radius > min_radius.get(0))
                radius = min_radius.get(0);
            circleList.add(new Circle(startX, startY, radius, getPaint(), 0, 0));
            moveDraw = false;
            invalidate();
        }
        else if (getMode().equalsIgnoreCase("delete"))
        {
            float deleteX = event.getX();
            float deleteY = event.getY();
            for (int i = 0; i < circleList.size(); i++)
            {
                float distance = calculateRadius(circleList.get(i).startX, circleList.get(i).startY, deleteX, deleteY);
                if (distance <= circleList.get(i).radius_circle)
                {
                    deleteCircle.add(circleList.get(i));
                }
            }
            circleList.removeAll(deleteCircle);
            invalidate();
        }
        else if (getMode().equalsIgnoreCase("move"))
        {
            velocity.computeCurrentVelocity(1000);
            xVelocity = velocity.getXVelocity() / 1000;
            yVelocity = velocity.getYVelocity() / 1000;
            for (int i = 0; i < circleList.size(); i++) {
                float distance = calculateRadius(circleList.get(i).startX, circleList.get(i).startY, moveX, moveY);
                if (distance < circleList.get(i).radius_circle)
                {
                    circleList.get(i).moveFlag = true;
                    circleList.get(i).setDx(xVelocity);
                    circleList.get(i).setDy(yVelocity);
                    circleList.get(i).getDx();
                }
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        height = canvas.getHeight();
        width = canvas.getWidth();
        if (moveDraw == true)
            canvas.drawCircle(startX, startY, radius, getPaint());

        if (getMode().equalsIgnoreCase("draw"))
        {
            for (Circle draw_circle : circleList)
            {
                canvas.drawCircle(draw_circle.startX, draw_circle.startY, draw_circle.radius_circle, draw_circle.circle_color);
            }
        }
        else if (getMode().equalsIgnoreCase("move"))
        {
            for (Circle draw_circle : circleList)
            {
                canvas.drawCircle(draw_circle.startX, draw_circle.startY, draw_circle.radius_circle, draw_circle.circle_color);
            }
            for (int i = 0; i < circleList.size(); i++)
            {
                if (circleList.get(i).moveFlag == true)
                {
                    circleList.get(i).startX += circleList.get(i).dx;
                    circleList.get(i).startY += circleList.get(i).dy;
                    if (circleList.get(i).startX + circleList.get(i).radius_circle >= canvas.getWidth() || circleList.get(i).startX - circleList.get(i).radius_circle <= 0)
                    {
                        circleList.get(i).dx = circleList.get(i).dx * -1;
                    }
                    if (circleList.get(i).startY + circleList.get(i).radius_circle >= canvas.getHeight() || circleList.get(i).startY - circleList.get(i).radius_circle <= 0)
                    {
                        circleList.get(i).dy = circleList.get(i).dy * -1;
                    }
                    canvas.drawCircle(circleList.get(i).startX, circleList.get(i).startY, circleList.get(i).radius_circle, circleList.get(i).circle_color);

                }
            }
        }
        else if (getMode().equalsIgnoreCase("delete"))
        {
            for (int i = 0; i < circleList.size(); i++)
            {
                canvas.drawCircle(circleList.get(i).startX, circleList.get(i).startY, circleList.get(i).radius_circle, circleList.get(i).circle_color);
            }
        }
        invalidate();
    }

    private float calculateRadius(float startX, float startY, float endX, float endY)
    {
        radius = (float) Math.sqrt(Math.pow((startX - endX), 2) + Math.pow((startY - endY), 2));
        return radius;
    }

    private boolean onComputeMove(MotionEvent event)
    {
        if (getMode().equalsIgnoreCase("draw"))
        {
            endX = event.getX();
            endY = event.getY();
            radius = calculateRadius(startX, startY, endX, endY);
            invalidate();
            moveDraw = true;
        }
        else if (getMode().equalsIgnoreCase("move"))
        {
            velocity.addMovement(event);

        }
        return true;
    }

    public void setPaint(int c)
    {
        paint_obj = new Paint();
        paint_obj.setColor(c);
        paint_obj.setStyle(Paint.Style.STROKE);
        paint_obj.setStrokeWidth(15.0f);
    }

    public Paint getPaint()
    {

        return this.paint_obj;
    }

    public void setMode(String mode)
    {
        action_mode = mode;
        System.out.println("actionMode:" + action_mode);
    }

    public String getMode()
    {
        return this.action_mode;
    }
}
