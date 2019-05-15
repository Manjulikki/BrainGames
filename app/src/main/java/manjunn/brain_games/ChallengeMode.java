package manjunn.brain_games;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class ChallengeMode extends MainActivity {

    Context context = this;
    ImageView challengerPP, challengePP;
    TextView challengerName, challengerAge, challengerPlace, challengeName, challengeAge, challengePlace, loadingPartner;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_mode);
        challengerPP = (ImageView) findViewById(R.id.challengerPP);
        challengePP = (ImageView) findViewById(R.id.challengePP);
        challengeName = (TextView) findViewById(R.id.challengeName);
        challengerName = (TextView) findViewById(R.id.challengerName);
        challengeAge = (TextView) findViewById(R.id.challengeGenderAge);
        challengerAge = (TextView) findViewById(R.id.challengerGenderAge);
        challengePlace = (TextView) findViewById(R.id.challengePlace);
        challengerPlace = (TextView) findViewById(R.id.challengerPlace);
        loadingPartner = (TextView) findViewById(R.id.loadingPartner);
        relativeLayout = (RelativeLayout) findViewById(R.id.cRelativeLayout);
        SqliteDatabaseOperations sqliteDatabaseOperations = new SqliteDatabaseOperations(context);
        Cursor cr = sqliteDatabaseOperations.getUserDetails(sqliteDatabaseOperations);
        do {
            if (cr != null && cr.moveToFirst()) {
                challengerName.setText(cr.getString(1));
                challengerPlace.setText(cr.getString(7));
                challengerAge.setText(cr.getString(3) + "," + cr.getString(5));
                challengerPP.setImageBitmap(stringToBitMap(cr.getString(2)));
                challengePP.setImageBitmap(stringToBitMap(cr.getString(2)));
                challengeName.setText(cr.getString(1));
                challengePlace.setText(cr.getString(7));
                challengeAge.setText(cr.getString(5) + "," + cr.getString(3));
            }
        } while (cr.moveToNext());
        new EnableDisable().execute("0");
    }

    class EnableDisable extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... pictureUrl) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            if (pictureUrl[0].equals("0")) return 0;
            else return 1;
        }

        protected void onPostExecute(Integer val) {
            if (val == 0) {
                enableRL(0);
            } else {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(context, GamesSelection.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
        }

        public Bitmap stringToBitMap(String encodedString) {
            try {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

        private void enableRL(int i) {
            loadingPartner.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            new EnableDisable().execute("1");
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
