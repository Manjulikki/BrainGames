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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static manjunn.brain_games.ThinkingGames.score;

public class MemoryGames extends MainActivity {

    Button button1, button2;
    TextView  timeView, guidelines;
    ImageView imageView;
    List<Integer> showedImages = new ArrayList<Integer>();
    SqliteDatabaseOperations sqliteDatabaseOperations;
    Context ctx = this;
    static int randomInt;
    static CountDownTimer aCounter;
    static String correct = "";
    static boolean counterFlag = false;
    int time;
    String time_interval;
    List numbers=new ArrayList();
    int numberFlag=-1;
    Random randomGenerator = new Random();
    Vibrator v;
    GenerateRandomNumber generateRandamNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        String memory_guidelines = "Click on Yes if the image has appeared earlier. \n" + "Memorize the first and wait for second";
        String check_prev_guidelines = "Click on Yes if the image is same as previous image. \n" + "Memorize the first and wait for second";
        guidelines = (TextView) findViewById(R.id.memory_guidelines);
        v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        generateRandamNumber=new GenerateRandomNumber(90);
        if (mode == 4) guidelines.setText(memory_guidelines);
        else if (mode == 5) guidelines.setText(check_prev_guidelines);
        score = 0;
        sqliteDatabaseOperations = new SqliteDatabaseOperations(ctx);
        button1 = (Button) findViewById(R.id.memory_option1);
        button2 = (Button) findViewById(R.id.memory_option2);
       // scoreView = (TextView) findViewById(R.id.memory_score);
        timeView = (TextView) findViewById(R.id.memory_time);
        imageView = (ImageView) findViewById(R.id.memory_imageView);
//        time = Integer.valueOf(MainActivity.time_interval);
//        time_interval = String.valueOf(time) + "000";
        //timeView.setText(MainActivity.time_interval);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button1_memory_check();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2_memory_check();
            }
        });
        firstTimeTimer();
    }

    private void button1_memory_check() {
//        if (!ThinkingGames.firstTime) {
//            aCounter.cancel();
//        }
        ThinkingGames.firstTime = false;
       // timeView.setText(MainActivity.time_interval);
        if (correct.equals("b1")) {
            if (showedImages.contains(imageArray[randomInt])) {
                score++;
              //  scoreView.setText(String.valueOf(score));
                if (MainActivity.mode == 5) showedImages.clear();
                showedImages.add(imageArray[randomInt]);
                loadRandomValues();
            } else {
//                ThinkingGames.thinking = true;
//                showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                score--;
                v.vibrate(500);
                loadRandomValues();
            }
        } else {
            if (!showedImages.contains(imageArray[randomInt])) {
                score++;
              //  scoreView.setText(String.valueOf(score));
                if (MainActivity.mode == 5) showedImages.clear();
                showedImages.add(imageArray[randomInt]);
                loadRandomValues();
            } else {
//                ThinkingGames.thinking = true;
//                showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                score--;
                v.vibrate(500);
                loadRandomValues();
            }
        }
    }

    private void button2_memory_check() {
//        if (!ThinkingGames.firstTime) {
//            aCounter.cancel();
//        }
        ThinkingGames.firstTime = false;
      //  timeView.setText(MainActivity.time_interval);
        if (correct.equals("b2")) {
            if (showedImages.contains(imageArray[randomInt])) {
                score++;
              //  scoreView.setText(String.valueOf(score));
                if (MainActivity.mode == 5) showedImages.clear();
                showedImages.add(imageArray[randomInt]);
                loadRandomValues();
            } else {
//                ThinkingGames.thinking = true;
//                showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                score--;
                v.vibrate(500);
                loadRandomValues();
            }
        } else {
            if (!showedImages.contains(imageArray[randomInt])) {
                score++;
              //  scoreView.setText(String.valueOf(score));
                if (MainActivity.mode == 5) showedImages.clear();
                showedImages.add(imageArray[randomInt]);
                loadRandomValues();
            } else {
//                ThinkingGames.thinking = true;
//                showResultAlert("\t \t \t \t \t \t \t \t \t \t \t \t Game Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score);
                score--;
                v.vibrate(500);
                loadRandomValues();
            }
        }
    }

    private void loadRandomValues() {

            if (mode == 4)
                randomInt = generateRandamNumber.get_repeat_rand();
            else if (mode == 5)
                randomInt = generateRandamNumber.get_prev_repeat_rand();
            //getRandomNumber();
            if (randomInt == 0) loadRandomValues();
            loadImages(randomInt);
            if (randomInt % 2 == 0) {
                correct = "b1";
                button1.setText("Yes");
                button2.setText("No");
            } else {
                correct = "b2";
                button1.setText("No");
                button2.setText("Yes");
            }
    }

    private void loadImages(int randomInt) {
        imageView.setImageBitmap(SaveInputs.decodeSampledBitmapFromResource(getResources(), imageArray[randomInt], 100, 100));
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
                guidelines.setVisibility(View.INVISIBLE);
             //   scoreView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                SaveInputs saveInputs = new SaveInputs();
                numberMap = saveInputs.saveNumbers();
                questionMap = saveInputs.saveStrings();
                timer();
                loadRandomValues();
            }
        }.start();
    }

    public void timer() {
        timeView.setText("30");
        aCounter = new CountDownTimer(33000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (!ThinkingGames.firstTime) {
                    int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                    if (sec >= 0)
                        timeView.setText(String.valueOf(sec));
                } else {
                    try {
                        Thread.sleep(2000);
                        try {
                            button2.setVisibility(View.VISIBLE);
                            button1.setVisibility(View.VISIBLE);
                            showedImages.add(imageArray[randomInt]);
                            numbers.add(randomInt);
                            ThinkingGames.firstTime = false;
                            timeView.setText("30");
                            loadRandomValues();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onFinish() {
                ThinkingGames.firstTime=true;
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
            ThinkingGames.firstTime = true;
            ThinkingGames.thinking = false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class GenerateRandomNumber {
        Random rn;
        int prev;
        int length;
        int rand_num;
        public int[] rep_num;
        int repeat_count;

        public GenerateRandomNumber(int num) {
            rn = new Random();
            prev = 0;
            length = 0;
            rand_num = num;
            rep_num = new int[num];
            repeat_count = 0;
        }

        public int get_rand(int i) {
            return rn.nextInt(i);

        }

        public int get_repeat_rand() {
            int num = rand_num;
            //System.out.println("debug prev"+prev+"mod"+prev%2);
            if (length > 2 && prev % 2 == 0) {
                num = length;
                prev = get_rand(num);
                //System.out.println("debug");
                repeat_count++;
                return rep_num[prev];

            }
            prev = get_rand(num);
            rep_num[(length++) % rand_num] = prev;
            return prev;
        }

        public int get_repeat_cnt() {
            return repeat_count;
        }

        public int get_prev_repeat_rand() {
            numberFlag++;
            if (numberFlag%2==0 || numberFlag%5==0){
                return randomInt;
            }
            return get_rand(rand_num);
        }
    }
}
