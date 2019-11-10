package com.example.a15squares;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

/**15Squares
 *
 * @author Erik Liu
 * @version November 2019
 *
 * added the OnTouchListener to implement the slide puzzle using dragging of the finger
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the listener for the random button and the surface view
        GameBoard board = (GameBoard) findViewById(R.id.board);

        //set the buttons OnClickListener
        Button random = findViewById(R.id.reset);
        random.setOnClickListener(board);

        //set the surface views OnTouchListener
        SurfaceView game = findViewById(R.id.board);
        game.setOnTouchListener(board);
    }

}
