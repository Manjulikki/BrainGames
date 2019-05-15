package manjunn.brain_games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.StringTokenizer;

public class FacebookLogin extends MainActivity {

    CallbackManager callbackManager;
    Context context=this;
    String id,email,picture,dob,age,place,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.setReadPermissions("user_location");
        loginButton.setReadPermissions("user_birthday");
        LoginManager.getInstance().logOut();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                JSONObject userDetails = response.getJSONObject();
                                try {
                                    id = (String) userDetails.get("id");
                                    MainActivity.user_name = (String) userDetails.get("name");
                                    email = (String) userDetails.get("email");
                                    dob = (String) userDetails.get("birthday");
                                    gender = (String) userDetails.get("gender");
                                    picture = (String) userDetails.getJSONObject("picture").getJSONObject("data").get("url");
                                    place= (String) userDetails.getJSONObject("location").get("name");
                                    age= getAge(dob);
                                    new SetProfilePhoto().execute(picture);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(),"Login attempt failed, Please try again",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(),"Error" + String.valueOf(error),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String getAge(String dob) {

        String[] birthYearString=dob.split("/");
        int birthYear=Integer.valueOf(birthYearString[2]);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year - birthYear);
    }

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    class SetProfilePhoto extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... pictureUrl) {
            Bitmap bmp = null;
            try {
                URL url=new URL(pictureUrl[0]);
                bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap bmp) {
            picture=bitMapToString(bmp);
            SqliteDatabaseOperations db= new SqliteDatabaseOperations(context);
            db.insertBasicDataToDatabase(db, id, MainActivity.user_name, picture, dob, gender, age, email, place);
            startActivity(new Intent(context, ChallengeMode.class));
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
