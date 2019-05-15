package manjunn.brain_games;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ThinkingGames extends MainActivity {

    static String correct = "";
    ImageView imageView;
    TextView textView, timeView,imageGuidelines;
    Button button1, button2;
    static int score = 0, randomValue;
    static CountDownTimer aCounter;
    Random randomGenerator = new Random();
    int time, i = -1,a=0;
    String time_interval;
    String[] number = new String[1];
    String[] question = new String[1];
    static boolean firstTime = true, thinking = false, counterFlag = false;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thinking_mode);
        String imageAndNumberGuidelines="Look the image and click on name of the image. Calculate the number and choose the correct answer";
        String images="Look the image and click on name of the image";
        String numbers="Calculate the math and choose the correct answer";
        String gk="Read the question and choose the correct answer";
        imageGuidelines= (TextView) findViewById(R.id.image_guidelines);
        switch (mode){
            case 0: imageGuidelines.setText(imageAndNumberGuidelines);
                break;
            case 1: imageGuidelines.setText(images);
                break;
            case 2: imageGuidelines.setText(numbers);
                break;
            case 3: imageGuidelines.setText(gk);
                break;
        }
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.option1);
        button2 = (Button) findViewById(R.id.option2);
     //   scoreView = (TextView) findViewById(R.id.score);
//        time = Integer.valueOf(MainActivity.time_interval);
         timeView = (TextView) findViewById(R.id.time);
//        time_interval = String.valueOf(time) + "000";
//        timeView.setText("30");
        score = 0;
        SaveInputs saveInputs = new SaveInputs();
        numberMap = saveInputs.saveNumbers();
        questionMap = saveInputs.saveStrings();
        firstTimeTimer();
    }

    private void loadImages(int randomInt) {
        textView.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(SaveInputs.decodeSampledBitmapFromResource(getResources(), imageArray[randomInt], 100, 100));
    }

    private void loadNumbers() {
        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        if (mode == 3)
            textView.setText(question[0]);
        else textView.setText(number[0]);
    }

    private void loadStrings() {
        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        textView.setText(question[0]);
    }

    private void loadRandomValues() {

            int randomInt = randomGenerator.nextInt(95 - 1) + 1;
            if (randomInt == randomValue || randomInt == 0)
                loadRandomValues();
            randomValue = randomInt;
            i++;
            number = numberMap.get(randomInt);
            question = questionMap.get(randomInt);
            if (MainActivity.mode == 0) {
                if (i % 2 == 0) {
                    loadImages(randomInt);
                } else if (randomValue < 56) {
                    loadNumbers();
                } else loadRandomValues();

            } else if (MainActivity.mode == 1) {
                if (randomValue < 82)
                    loadImages(randomInt);
                else loadRandomValues();
            } else if (MainActivity.mode == 2) {
                if (randomValue < 56) {
                    loadNumbers();
                } else {
                    loadRandomValues();
                }
            } else if (mode == 3) {
                if (randomValue < 86) {
                    loadStrings();
                } else loadRandomValues();
            }
            if (randomInt % 2 == 0) {
                correct = "b1";
                if ((i % 2 == 0 && mode == 0) || mode == 1) {
                    button1.setText(nameArray[randomValue]);
                    if (randomValue == 1)
                        button2.setText(nameArray[randomValue + 1]);
                    else button2.setText(nameArray[randomValue - 1]);
                } else if ((mode == 0 && i % 2 != 0) || mode == 2) {
                    button1.setText(number[1]);
                    button2.setText(number[2]);
                } else if (mode == 3) {
                    button1.setText(question[1]);
                    button2.setText(question[2]);
                }
            } else {
                correct = "b2";
                if ((i % 2 == 0 && mode == 0) || mode == 1) {
                    button2.setText(nameArray[randomValue]);
                    if (randomValue == 1)
                        button1.setText(nameArray[randomValue + 1]);
                    else button1.setText(nameArray[randomValue - 1]);
                } else if ((mode == 0 && i % 2 != 0) || mode == 2) {
                    button2.setText(number[1]);
                    button1.setText(number[2]);
                } else if (mode == 3) {
                    button2.setText(question[1]);
                    button1.setText(question[2]);
                }
            }

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!firstTime) {
//                        a++;
//                        aCounter.cancel();
//                    }
                    firstTime = false;
                 //   timeView.setText(MainActivity.time_interval);
                    if (correct.equals("b1")) {
                        score++;
                      //  scoreView.setText(String.valueOf(score));
                        loadRandomValues();
                    } else {
//                        thinking = true;
//                        showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                        score--;
                        v.vibrate(500);
                        loadRandomValues();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (!firstTime) {
//                        a++;
//                        aCounter.cancel();
//                    }
                    firstTime = false;
                //    timeView.setText(MainActivity.time_interval);
                    if (correct.equals("b2")) {
                        score++;
                      //  scoreView.setText(String.valueOf(score));
                        loadRandomValues();
                    } else {
//                        thinking = true;
//                        showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                         score--;
                        v.vibrate(500);
                        loadRandomValues();
                    }
                }
            });
        }

    private void firstTimeTimer() {
        timeView.setText("6");
        counterFlag = true;
        aCounter = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                if (sec >= 0)
                    timeView.setText(String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                firstTime=false;
                imageGuidelines.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
             //   scoreView.setVisibility(View.VISIBLE);
             //   scoreView.setText("0");
                timer();
                loadRandomValues();
            }
        }.start();
    }

    public void timer() {
        timeView.setText("30");
        aCounter = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                if (sec >= 0)
                    timeView.setText(String.valueOf(sec));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAlertDialog("Are you sure want to quit?");
            firstTime = true;
            thinking = false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

