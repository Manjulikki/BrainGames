package manjunn.brain_games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Field;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public Context context = this;
    SqliteDatabaseOperations sqliteDatabaseOperations = new SqliteDatabaseOperations(context);
    Button training, feedback, mindUp, aboutUs, hiddenButton, challenge, cHidden;
    ImageButton highScoreS, helpS;
    static int mode = -1;
    static String time_interval = "3";
    static String user_name = "";
    static int[] imageArray;
    static String[] nameArray;
    // TextView  highScore, highScoreText;
    Map<Integer, String[]> questionMap;
    Map<Integer, String[]> numberMap;
    boolean fbLogin = false;

    String help = "\n BUTTON HIGH SCORE: Gives detailed High scores of all the games.\n" + "\n" +
            "BUTTON TIME INTERVAL: You can set time intervals for games [min 2 and max 5 seconds] Default 3 seconds.\n" +
            "\n" + "\n" +
            "ABOUT GAMES:\n" +
            "IMAGES AND MATH:  Click the Right Image/math answer to continue game before the timer expires\n" + "\n" +
            "IMAGES: Click the Right Image answer to continue game before the timer expires\n" + "\n" +
            "MATH: Click the Right math answer to continue game before the timer expires\n" + "\n" +
            "GENERAL KNOWLEDGE: Click the Quiz answer to continue game before the timer expires\n" + "\n" +
            "MEMORY CHECK: Click on Yes if the image shown has appeared atleast once else Click No\n" + "\n" +
            "PREVIOUS CHECK: Click on Yes only if the image shown has appeared Previously else Click No\n" + "\n" +
            "GREATER LESSER: Swipe UP if the number is greater than previous number and Swipe DOWN if the number is lesser than previous number" + "\n";

    String aboutUS = "\t\t\t\tABOUT MIND UP: \n " +
            "\t\t\t\t\t\t Mind Up app is a collection of brain games. With this you can test your basic general knowledge, concentration, speed, visual memory, short-term memory, mathemetical calculation, etc \n " +

            "\n\t\t\t\t\t\t Mind Up has 7 games, and we are working to bring up more tricky attractive games to test your mental skills. It`s a game of all ages. Lets check it out. \n " +

            "\n\t\t\t\t\t\t We want to hear your feedback and comments so we can continue to make this App better. Mail us on below id's. \n " +

            "\n \t\t\t\tAbout us: \n " +

            "\t\t\t\t\t\t Mind Up is a fun work of 3 guys with different skills, expertise, experience and with a common goal 'Explore and Innovate'  \n" +

            " \n Creators : \n\n\t\t\t\t\t\t mAnju \n" +
            "\t\t\t\t\t\t hArsha \n" +
            "\t\t\t\t\t\t kAsi \n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        sqliteDatabaseOperations.createTables(sqliteDatabaseOperations);
        // time_interval = SqliteDatabaseOperations.getTimeInterval(sqliteDatabaseOperations);
        user_name = sqliteDatabaseOperations.getUserName(sqliteDatabaseOperations);
        if (!user_name.equals("")) fbLogin = true;
        mindUp = (Button) findViewById(R.id.mindUP);
        cHidden= (Button) findViewById(R.id.cHiddenB);
        challenge = (Button) findViewById(R.id.challengeButton);
        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNetworkAvailable()) showAlert();
                else {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) context
                            .getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popupView = layoutInflater.inflate(R.layout.challengelayout, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                    Button challengeB= (Button) popupView.findViewById(R.id.challenge);
                    Button challengeFriend= (Button) popupView.findViewById(R.id.challengeFriend);
                    challengeB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            if (fbLogin)
                                startActivity(new Intent(getApplicationContext(), ChallengeMode.class));
                            else startActivity(new Intent(getApplicationContext(), FacebookLogin.class));
                        }
                    });
                    challengeFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(), FriendsList.class));
                        }
                    });
                    popupWindow.showAsDropDown(cHidden, 50, -30);
                }
            }
        });
        training = (Button) findViewById(R.id.trainingButton);
        hiddenButton = (Button) findViewById(R.id.hiddenButton);
        aboutUs = (Button) findViewById(R.id.aboutUs);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.layout, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.update();
                Button clButton = (Button) popupView.findViewById(R.id.closeBottom);
                clButton.setVisibility(View.INVISIBLE);
                TextView abtUs = (TextView) popupView.findViewById(R.id.displayText);
                abtUs.setText(aboutUS);
                Button close = (Button) popupView.findViewById(R.id.closeWindow);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(hiddenButton, 50, -30);
            }
        });
        //    settings = (Button) findViewById(R.id.settingsButton);
        feedback = (Button) findViewById(R.id.feedbackButton);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.popupwindow, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        AbsListView.LayoutParams.WRAP_CONTENT,
                        AbsListView.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.update();
                Button send = (Button) popupView.findViewById(R.id.sendFB);
                Button cancel = (Button) popupView.findViewById(R.id.closeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                send.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        TextView bm = (TextView) popupView.findViewById(R.id.feedBack);
                        String bmgs = bm.getText().toString();
                        if (!bmgs.equals("")) {
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"3iappz@gmail.com"});
                            email.putExtra(Intent.EXTRA_SUBJECT, "Feedback: " + user_name);
                            email.putExtra(Intent.EXTRA_TEXT, bmgs);
                            email.setType("message/rfc822");
                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            Toast.makeText(context, "Thank you for your Feedback", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "Please give us a feedback", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(hiddenButton, 50, -30);
            }
        });
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImages();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
        helpS = (ImageButton) findViewById(R.id.help);
        helpS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.layout, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.update();
                TextView abtUs = (TextView) popupView.findViewById(R.id.displayText);
                abtUs.setText(help);
                Button closeButton = (Button) popupView.findViewById(R.id.closeBottom);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                Button close = (Button) popupView.findViewById(R.id.closeWindow);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(hiddenButton, 50, -30);
            }
        });
        highScoreS = (ImageButton) findViewById(R.id.hss);
        highScoreS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String highScores[] = {"Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played", "Never Played"};
                for (int i = 0; i < highScores.length; i++) {
                    String hs = sqliteDatabaseOperations.getHighScore(sqliteDatabaseOperations, String.valueOf(i));
                    if (!hs.equals("")) highScores[i] = hs;
                }
                LayoutInflater layoutInflater
                        = (LayoutInflater) context
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.highscorelayout, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.update();
                Button cancel = (Button) popupView.findViewById(R.id.closeButtonHS);
                TextView imagesAndNumber = (TextView) popupView.findViewById(R.id.INScore);
                TextView images = (TextView) popupView.findViewById(R.id.IScore);
                TextView number = (TextView) popupView.findViewById(R.id.NScore);
                TextView gk = (TextView) popupView.findViewById(R.id.GKScore);
                TextView memoryCheck = (TextView) popupView.findViewById(R.id.MCScore);
                TextView previousCheck = (TextView) popupView.findViewById(R.id.PCScore);
                TextView gretterLesser = (TextView) popupView.findViewById(R.id.GLScore);
                TextView matching = (TextView) popupView.findViewById(R.id.matchingScore);
                TextView color = (TextView) popupView.findViewById(R.id.colorScore);
                TextView addition = (TextView) popupView.findViewById(R.id.additionScore);
                TextView touchNumber = (TextView) popupView.findViewById(R.id.touchScore);
                TextView touchNumberPlus = (TextView) popupView.findViewById(R.id.touchPlusScore);
                if (highScores.length != 0) {
                    imagesAndNumber.setText(highScores[0]);
                    images.setText(highScores[1]);
                    number.setText(highScores[2]);
                    gk.setText(highScores[3]);
                    memoryCheck.setText(highScores[4]);
                    previousCheck.setText(highScores[5]);
                    gretterLesser.setText(highScores[6]);
                    matching.setText(highScores[7]);
                    color.setText(highScores[8]);
                    addition.setText(highScores[9]);
                    touchNumber.setText(highScores[10]);
                    touchNumberPlus.setText(highScores[11]);

                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(hiddenButton, 50, -30);
            }
        });

//        settingS = (ImageButton) findViewById(R.id.ss);
//        settingS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Settings.class));
//            }
//        });
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Settings.class));
//            }
//        });
//                if (getIntent().getBooleanExtra("Exit me", false)) {
//                    finish();
//                }
//                intent = PendingIntent.getActivity(getBaseContext(), 0,
//                        new Intent(getIntent()), PendingIntent.FLAG_ONE_SHOT);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable e) {
//                handleUncaughtException(thread, e);
//            }
//        });
    }

//    public void uncaughtException(Thread t, Throwable e) {
//        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, intent);
//        System.exit(2);
//    }

//    public void handleUncaughtException(Thread thread, Throwable e) {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Exit me", true);
//        startActivity(intent);
//        finish();
//    }

    private void startNewActivity() {
        if (mode < 0)
            startActivity(new Intent(getApplicationContext(), Home.class));
        else if (mode == 0 || mode == 1 || mode == 2 || mode == 3) {
            startActivity(new Intent(getApplicationContext(), ThinkingGames.class));
        } else if (mode == 4 || mode == 5)
            startActivity(new Intent(getApplicationContext(), MemoryGames.class));
        else if (mode == 6)
            startActivity(new Intent(getApplicationContext(), SwipeGames.class));
        else startActivity(new Intent(getApplicationContext(), TouchGames.class));
    }

    private void loadValues() {

        SaveInputs saveInputs = new SaveInputs();
        numberMap = saveInputs.saveNumbers();
        questionMap = saveInputs.saveStrings();
        loadImages();
    }

    public void loadImages() {
        imageArray = null;
        Field[] fields = R.raw.class.getFields();
        imageArray = new int[fields.length];
        nameArray = new String[fields.length];
        for (int i = 1; i < fields.length; i++) {
            try {
                if (fields[i].getInt(null) != 0) {
                    imageArray[i] = fields[i].getInt(null);
                    nameArray[i] = fields[i].getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateScores(int score) {
        if (GamesSelection.round == 1) GamesSelection.roundScore1 = score;
        else if (GamesSelection.round == 2) GamesSelection.roundScore2 = score;
        else if (GamesSelection.round == 3) GamesSelection.roundScore3 = score;
    }

    public void showMainAlertDialog(String text) {
        new AlertDialog.Builder(this,R.style.MyDialogTheme)
                .setTitle("Quit")
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setIcon(R.drawable.exit)
                .show();
    }

    public void showAlertDialog(String text) {
        GamesSelection.challenging = false;
        mode = -1;
        if (MemoryGames.counterFlag) MemoryGames.aCounter.cancel();
        if (TouchGames.counterFlag) TouchGames.timer.cancel();
        if (ThinkingGames.counterFlag) ThinkingGames.aCounter.cancel();
        if (SwipeGames.counterFlag) SwipeGames.aCounter.cancel();
        GamesSelection.round = 0;
        GamesSelection.roundScore1 = 0;
        GamesSelection.roundScore2 = 0;
        GamesSelection.roundScore3 = 0;

        new AlertDialog.Builder(this,R.style.MyDialogTheme)
                .setTitle("Quit game")
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Your game has restarted", Toast.LENGTH_SHORT).show();
                        startNewActivity();
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mode = -1;
                        GamesSelection.challenging = false;
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setIcon(R.drawable.alert)
                .setCancelable(false)
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showMainAlertDialog("Are you sure want to leave application?");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showResultAlert(final int score) {
        if (GamesSelection.challenging) {
            updateScores(score);
            mode = -1;
            updateHighScore(score);
            startActivity(new Intent(getApplicationContext(), GamesSelection.class));
        } else {
            new AlertDialog.Builder(context,R.style.MyDialogTheme)
                    .setMessage("\t \t \t \t \t \t \t \t \t \t \t \t Time Over!!!! \n \t \t \t \t \t \t \t \t \t \t \t Your score is " + score)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    })
                    .setCancelable(false)
                    .setIcon(R.drawable.alert)
                    .show();
            updateHighScore(score);
        }
    }

    private void updateHighScore(int score) {
        String hs = SqliteDatabaseOperations.getHighScore(sqliteDatabaseOperations, String.valueOf(mode));
        if (hs != null && !hs.equals("")) {
            if (score > Integer.valueOf(hs)) {
                Toast.makeText(getBaseContext(), "New High score" + String.valueOf(score), Toast.LENGTH_SHORT).show();
                SqliteDatabaseOperations.updateHighScore(sqliteDatabaseOperations, "manjuu", String.valueOf(mode), String.valueOf(score));
            }
        } else {
            SqliteDatabaseOperations.saveHighScore(sqliteDatabaseOperations, "manjuu", String.valueOf(mode), String.valueOf(score));
        }
    }

    private boolean isNetworkAvailable() {  // verifying the network availability
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showAlert() {
        new AlertDialog.Builder(context,R.style.MyDialogTheme)
                .setTitle("Alert")
                .setMessage("No internet connection, Please connect to internet and try again")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(context, MainActivity.class));
                    }
                })
                .setIcon(R.drawable.alert)
                .setCancelable(false)
                .show();
    }

}
