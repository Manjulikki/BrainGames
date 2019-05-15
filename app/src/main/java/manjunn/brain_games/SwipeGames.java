package manjunn.brain_games;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SwipeGames extends MainActivity implements GestureDetector.OnGestureListener {

    TextView number, timeView, guideLines;
    static int randomInt;
    Random randomGenerator = new Random();
    static CountDownTimer aCounter;
    int prev = 0, curr = 0, score;
    Boolean firstTime = true, scrollFlag = true;
    static boolean counterFlag = false;
    private GestureDetectorCompat gestureDetectorCompat;
    Vibrator v;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_games);
        ThinkingGames.score = 0;
        number = (TextView) findViewById(R.id.swipeNumber);
//        scoreView = (TextView) findViewById(R.id.swipeScore);
        timeView = (TextView) findViewById(R.id.swipeTimeView);
        guideLines = (TextView) findViewById(R.id.swipeGuideline);
//        play = (TextView) findViewById(R.id.swipePlay);
        gestureDetectorCompat = new GestureDetectorCompat(this, this);
        final String guidelines = "Swipe up if number is greater than the previous number \n Swipe down if the number is lesser than the previous number";
        guideLines.setText(guidelines);
        v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        firstTimeTimer();
 //       play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });
    }

    private void firstTimeTimer() {
        timeView.setText("6");
        counterFlag=true;
        aCounter = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                if (sec >= 0)
                    timeView.setText(String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                guideLines.setVisibility(View.INVISIBLE);
                number.setVisibility(View.VISIBLE);
                prev = curr = 0;
                timer();
                loadRandomValues();
            }
        }.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void loadRandomValues() {
        randomInt = randomGenerator.nextInt(100);
        curr = randomInt;

        if (firstTime) {
            number.setText("0");
            curr = 0;
        } else {
            number.setText(String.valueOf(curr));
        }
    }

    public void timer() {
        counterFlag = true;
        timeView.setText("30");
        aCounter = new CountDownTimer(31000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (!firstTime) {
                    int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                    if (sec >= 0)
                        timeView.setText(String.valueOf(sec));
                } else {
                    try {
                        firstTime = false;
                        Thread.sleep(2000);
                        timeView.setText("30");
                        loadRandomValues();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            public void onFinish() {
                firstTime=true;
                imageArray=null;
                nameArray=null;
                showResultAlert(score);
            }
        }.start();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        scrollFlag = true;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (scrollFlag) {
            if (distanceY < 0) {
                if (curr < prev) {
                    score++;
                    firstTime = false;
                    prev = curr;
                    scrollFlag = false;
                    loadRandomValues();
                } else {
                    score--;
                    v.vibrate(500);
                    loadRandomValues();
//                    aCounter.cancel();
//                    ThinkingGames.thinking = true;
//                    showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + ThinkingGames.score);
                }
            } else {
                if (curr > prev) {
                    score++;
                    firstTime = false;
                    prev = curr;
                    scrollFlag = false;
                    loadRandomValues();
                } else {
                    score--;
                    v.vibrate(500);
                    loadRandomValues();
                   // showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + ThinkingGames.score);
                }
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        scrollFlag = true;
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ThinkingGames.firstTime = true;
            ThinkingGames.thinking = false;
            showAlertDialog("Are you sure want to quit?");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
