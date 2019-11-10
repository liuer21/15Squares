package com.example.a15squares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard extends SurfaceView implements View.OnClickListener, View.OnTouchListener {

    //variables used in this program
    Paint boardPaint = new Paint(Color.BLACK);
    Paint numPaint = new Paint();
    int[][] pieces = new int[4][4]; //holds the values of the puzzle
    static int numOfSquares = 15; //tells how many numbers the puzzle will hold
    static int length = 4; //size of one side of the 4 by 4 puzzle
    //these variables are used for shifiting the pieces on the board from the OnTouch method
    static int startX = -1;
    static int startY = -1;
    static int endX = -1;
    static int endY = -1;

    public GameBoard(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        setWillNotDraw(false);

        //initialize the board
        boardPaint.setStrokeWidth(10);
        boardPaint.setStyle(Paint.Style.STROKE);
        numPaint.setTextSize(50);
        randomize();
    }


    /**onDraw
     *
     * draws the squares, numbers, and the if the game is won
     *
     * @param canvas
     */
    public void onDraw(Canvas canvas)
    {
        win(); //checks to see if the board is in a winning state to set the background color
        drawBoard(canvas); //draws all the squares
        printPieces(canvas); //draws all the numbers of the puzzle
    }

    /**drawBoard
     *
     * draws the squares for the slide puzzle
     *
     * @param canvas
     */
    public void drawBoard(Canvas canvas)
    {
        // draw each square on the board
        int row, col;
        int size = 200; //for the 4 by 4 grid, squares have a length and width of 200
        for(row = 0; row < length; row++)
        {
            for(col = 0; col < length; col++)
            {
                canvas.drawRect(row*size + 200, col*size + 300, row*size + 400, col*size + 500, boardPaint);
            }
        }
    }

    /**printPieces
     *
     * prints the numbers that are stored in the 2d array onto the surface view
     *
     * @param canvas
     */
    public void printPieces(Canvas canvas)
    {
        int row, col;

        for(row = 0; row < length; row++)
        {
            for(col = 0; col < length; col++)
            {
                //based on if the number is 1 or 2 digits change where the number is printed
                if(pieces[row][col] == 0);
                else if(pieces[row][col] < 10)
                    canvas.drawText(String.valueOf(pieces[row][col]), 290 + row*200, 410 + col*200, numPaint);
                else
                    canvas.drawText(String.valueOf(pieces[row][col]), 270 + row*200, 410 + col*200, numPaint);
            }
        }

    }

    /**win
     *
     * checks to see if the puzzle is complete
     *
     */
    public void win()
    {
        int i, j;
        int index = 1;
        int errors = 0;
        for(i = 0; i < length; i++)
        {
            for(j = 0; j < length; j++)
            {
                if(pieces[j][i] != index)
                    errors += 1;
                index++;
            }
        }

        //checks to see if the board is complete. Since the board goes from 1 - 15 one piece should
        //be different (bottom right)
        if(errors == 1) {
            setBackgroundColor(Color.GREEN);
            invalidate();
        }
        else {
            setBackgroundColor(Color.WHITE);
        }
    }

    /**randomize
     *
     * randomizes the numbers that are on the board
     *
     */
    public void randomize()
    {
        ArrayList<Integer> values = new ArrayList<Integer>();

        //sets an array list with values from 1 - 15 and then shuffles the numbers
        int i = 1;
        for(i = 1; i <= numOfSquares; i++)
            values.add(i);
        Collections.shuffle(values);
        values.add(0); //add a zero for the blank space

        //copy the values in the array list over to the 2d array that holds the numbers
        i = 0;
        int row, col;
        for(row = 0; row < length; row++)
        {
            for(col = 0; col < length; col++)
            {
                pieces[row][col] = values.get(row + col*length);
            }
        }
    }

    /**onTouch
     *
     * checks to see where the user touches on the surface view
     * if the move was valid, then the board will change
     *
     * @param v
     * @param event
     * @return
     */
    public boolean onTouch(View v, MotionEvent event)
    {

        //collects the start and end coordinates for the on touch
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                startX = roundX(startX);
                startY = roundY(startY);
                return true;
            case MotionEvent.ACTION_UP:
                endX = (int) event.getX();
                endY = (int) event.getY();
                endX = roundX(endX);
                endY = roundY(endY);
                break;
        }

        //makes sure that where the touch happened was on the board
        if(startX != -1 && startY != -1 && endX != -1 && endY != -1)
        {
            if(pieces[endX][endY] == 0)//if the drag ended on the blank space
            {
                //check to make sure that the piece that is going to be moved is touching
                if((Math.abs(endX - startX) == 1 && Math.abs(endY - startY) == 0)||(Math.abs(endX - startX) == 0 && Math.abs(endY - startY) == 1))
                {
                    //swap the pieces
                    pieces[endX][endY] = pieces[startX][startY];
                    pieces[startX][startY] = 0;
                    invalidate();
                    return false;
                }
            }

        }

        return true;
    }

    /**roundX
     *
     * rounds the X value for where the user touched to be used to shift the puzzle
     *
     * @param toRound the spot where the user touched
     * @return returns what spot on the board the array corresponds to
     */
    public int roundX(int toRound)
    {
        if(toRound > 200 && toRound < 400)
            return 0;
        else if(toRound > 400 && toRound < 600)
            return 1;
        else if(toRound > 600 && toRound < 800)
            return 2;
        else if(toRound > 800 && toRound < 1000)
            return 3;
        return -1;
    }

    /**roundY
     *
     * rounds the y value for where the user touched to be used to shift the puzzle
     *
     * @param toRound the spot where the user touched
     * @return returns what spot on the board the array corresponds to
     */
    public int roundY(int toRound)
    {
        if(toRound > 300 && toRound < 500)
            return 0;
        else if(toRound > 500 && toRound < 700)
            return 1;
        else if(toRound > 700 && toRound < 900)
            return 2;
        else if(toRound > 900 && toRound < 1100)
            return 3;
        return -1;
    }


    /**onClick
     *
     * checks to see if the button was pressed
     * if pressed, the board will be randomized
     *
     * @param v
     */
    public void onClick(View v)
    {
        randomize();
        invalidate();
    }
}
