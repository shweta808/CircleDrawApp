package com.example.shwetashahane.assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.graphics.Color;
import android.widget.*;

public class DrawActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        setContentView(new DrawCircleView(this));
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        DrawCircleView drawCircleView = new DrawCircleView(this);
        switch (item.getItemId())
        {
            case R.id.red:
                drawCircleView.setPaint(Color.RED);
                return true;
            case R.id.green:
                drawCircleView.setPaint(Color.GREEN);
                return true;
            case R.id.blue:
                drawCircleView.setPaint(Color.BLUE);
                return true;
            case R.id.black:
                drawCircleView.setPaint(Color.BLACK);
                return true;
            case R.id.delete:
                drawCircleView.setMode("delete");
                Toast.makeText(this, "Delete Mode is On", Toast.LENGTH_LONG).show();
                return true;
            case R.id.draw:
                drawCircleView.setMode("draw");
                Toast.makeText(this, "Draw Mode is On", Toast.LENGTH_LONG).show();
                return true;
            case R.id.move:
                drawCircleView.setMode("move");
                Toast.makeText(this, "Move Mode is On", Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
