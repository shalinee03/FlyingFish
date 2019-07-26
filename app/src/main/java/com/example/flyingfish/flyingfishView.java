package com.example.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyingfishView extends View
{
  private Bitmap fish[]=new Bitmap[2];

  private  Bitmap backgroundImage;

  private Paint scorepaint = new Paint();

  private Bitmap life[] = new Bitmap[2];

  private boolean touch=false;

  private  int fishX =10;
    private  int fishY;
    private int fishSpeed;

    private  int canvasWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed=16;  //initializtion of yellow ball//
    private  Paint yellowPaint = new Paint();

    private  int greenX, greenY, greenSpeed=20;  //initialization of green ball//
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed=24;//initialization of red ball//
    private Paint redPaint= new Paint();

    private int blackX,blackY,blackSpeed=24;//initialization of black ball//
    private Paint blackPaint= new Paint();

    private int score,lifeCounterOfFish;
    public flyingfishView(Context context)
    {
        super(context);

        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage= BitmapFactory.decodeResource(getResources(),R.drawable.background);


        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);//defining green ball specification and color//
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);




        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);

        life[0]= BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]= BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishY=550;
        score=0;
        lifeCounterOfFish=3;//initializing life counter with 3 as 3 hearts //
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight=canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY= fish[0].getHeight();
        int maxFishY = canvasHeight-fish[0].getHeight()*3;
        fishY= fishY +fishSpeed;

        if(fishY<minFishY)
        {
            fishY= minFishY;
        }
        if(fishY>maxFishY)
        {
            fishY= maxFishY;
        }

        fishSpeed=fishSpeed+2;

        if(touch)
        {
             canvas.drawBitmap(fish[1],fishX,fishY,null);
             touch=false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }


        yellowX= yellowX-yellowSpeed;

        if(hitBallChecker(yellowX,yellowY))
        {
            score= score+10;
            yellowX=-100;
        }

        if(yellowX<0)
        {
            yellowX=canvasWidth+21;
            yellowY= (int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);

        greenX= greenX-greenSpeed;

        if(hitBallChecker(greenX,greenY))
        {
            score= score+30;
            greenX=-100;
        }

        if(greenX<0)
        {
            greenX=canvasWidth+21;
            greenY= (int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(greenX,greenY,35,greenPaint);

        redX= redX-redSpeed;

        if(hitBallChecker(redX,redY))
        {
            score= score+50;
            redX=-100;
        }

        if(redX<0)
        {
            redX=canvasWidth+21;
            redY= (int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(redX,redY,20,redPaint);



        blackX= blackX-blackSpeed;
        if(hitBallChecker(blackX,blackY))
        {
           lifeCounterOfFish--;//decrement by one//
            blackX=-100;
            if(lifeCounterOfFish==0)
            {
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);//for moving activity to gamneover//
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//for clearing task//
                gameOverIntent.putExtra("score",score);//to send final score to gameover Activity//
                getContext().startActivity(gameOverIntent);//to start gameiver activity//

            }
        }

        if(blackX<0)
        {
            blackX=canvasWidth+21;
            blackY= (int) Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(blackX,blackY,50,blackPaint);

        canvas.drawText("score:"+score,80,60,scorepaint);


        for(int i =0;i<3;i++)
        {
            int x= (int)(580+life[0].getWidth()*1.5*i);
            int y = 30;

            if(i<lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0],x,y,null);//when fish is live
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);//take black ball and fish die so life is one
            }
        }




        //canvas.drawBitmap(life[0],580,10,null);//these were the three hearts
        //canvas.drawBitmap(life[0],680,10,null);//which are removed and placed inside for loop
        //canvas.drawBitmap(life[0],780,10,null);and if else statement above

    }

    public boolean hitBallChecker(int x, int y)
    {
        if(fishX<x && x <(fishX+fish[0].getWidth())&& fishY<y && y<(fishY+fish[0].getHeight()))
        {
            return  true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)//onetouch event
    {
       if(event.getAction()==MotionEvent.ACTION_DOWN)
       {
           touch= true;

           fishSpeed= -30;//speed of fish

       }
         return true;
    }
}
