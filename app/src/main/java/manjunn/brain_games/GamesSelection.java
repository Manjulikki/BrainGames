package manjunn.brain_games;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamesSelection extends MainActivity {

    ImageView roundImage;
    Random random = new Random();
    static int round = 0, roundScore1, roundScore2, roundScore3;
    static boolean challenging = false;
    static List playedGamesList= new ArrayList();
    TextView loadingResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_selection);
        roundImage = (ImageView) findViewById(R.id.roundImage);
        loadingResult= (TextView) findViewById(R.id.loadingResult);
        if (round == 3) {
            challenging=false;
            roundImage.setVisibility(View.INVISIBLE);
            loadingResult.setVisibility(View.VISIBLE);
            new EnableDisable().execute("1");
        }
        else {
            challenging=true;
            round++;
            if (round == 1)
                roundImage.setImageDrawable(getResources().getDrawable(R.drawable.round1));
            else if (round == 2)
                roundImage.setImageDrawable(getResources().getDrawable(R.drawable.round2));
            else if (round == 3)
                roundImage.setImageDrawable(getResources().getDrawable(R.drawable.round3));
                int rNumber = random.nextInt(13);
            for (int i=0;i<1;i++){
                if (!playedGamesList.contains(rNumber)) {
                    playedGamesList.add(rNumber);
                    mode=rNumber;
                }
                else i--;
            }
            new EnableDisable().execute("0");
        }
    }

    private void loadResultWindow() {
        LayoutInflater layoutInflater
                = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.resultwindow, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.update();
        TextView r1c1 = (TextView) popupView.findViewById(R.id.r1c1);
        TextView r1c2 = (TextView) popupView.findViewById(R.id.r1c2);
        TextView r1c3 = (TextView) popupView.findViewById(R.id.r1c3);
        TextView r2c1 = (TextView) popupView.findViewById(R.id.r2c1);
        TextView r2c2 = (TextView) popupView.findViewById(R.id.r2c2);
        TextView r2c3 = (TextView) popupView.findViewById(R.id.r2c3);
        TextView r3c1 = (TextView) popupView.findViewById(R.id.r3c1);
        TextView r3c2 = (TextView) popupView.findViewById(R.id.r3c2);
        TextView r3c3 = (TextView) popupView.findViewById(R.id.r3c3);
        Button again = (Button) popupView.findViewById(R.id.challengeAgain);
        final Button newC = (Button) popupView.findViewById(R.id.newChallenge);
        Button home = (Button) popupView.findViewById(R.id.goHome);
        ImageView finalScoreImage= (ImageView) popupView.findViewById(R.id.resultImage);
        TextView finalScoreText= (TextView) popupView.findViewById(R.id.resultText);
        r1c1.setText(String.valueOf(roundScore1));
        r2c1.setText(String.valueOf(roundScore2));
        r3c1.setText(String.valueOf(roundScore3));
        r1c2.setText(Home.web[(int) playedGamesList.get(0)]);
        r2c2.setText(Home.web[(int) playedGamesList.get(1)]);
        r3c2.setText(Home.web[(int) playedGamesList.get(2)]);
        r1c3.setText(String.valueOf(roundScore1));
        r2c3.setText(String.valueOf(roundScore2));
        r3c3.setText(String.valueOf(roundScore3));
        int yourTotal=roundScore1+roundScore2+roundScore3;
        int partnerScore=roundScore1+roundScore2+roundScore3;
        if (yourTotal>=partnerScore){
            finalScoreImage.setBackground(getResources().getDrawable(R.drawable.winner));
            finalScoreText.setText("You Won!!!!");
        } else {
            finalScoreImage.setBackground(getResources().getDrawable(R.drawable.lose));
            finalScoreText.setText("You Lose!!!!");
        }
        newC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChallengeMode.class));
            }
        });
        popupWindow.showAsDropDown(hiddenButton, 50, -30);
    }

    class EnableDisable extends AsyncTask<String,Void,Integer> {


        @Override
        protected Integer doInBackground(String... pictureUrl) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (pictureUrl[0].equals("0")) {
                return 0;
            } else {
                return 1;
            }
        }

        protected void onPostExecute(Integer val) {
            if (val == 0) {
                if (mode==-1) startActivity(new Intent(context, MainActivity.class));
                else startActivity(new Intent(context, Home.class));
            } else loadResultWindow();
        }
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
}
