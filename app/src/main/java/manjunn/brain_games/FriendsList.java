package manjunn.brain_games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsList extends MainActivity {

    GridView flGrid;
    TextView noFriendText,friendListText;
    Context context=this;
    ImageButton addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        noFriendText= (TextView) findViewById(R.id.noFriendText);
        flGrid= (GridView) findViewById(R.id.friendListGrid);
        friendListText= (TextView) findViewById(R.id.friendListText);
        addFriend= (ImageButton) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showAddWindow();
            }
        });
        SqliteDatabaseOperations sqliteDatabaseOperations = new SqliteDatabaseOperations(context);
        Cursor cr = sqliteDatabaseOperations.getUserDetails(sqliteDatabaseOperations);
        Bitmap[] friendImage = new Bitmap[100];
        String[] name =new String[100],city=new String[100];
        int count=-1;
        do {
            if (cr != null && cr.moveToFirst()) {
                count++;
                friendImage[count]= (stringToBitMap(cr.getString(2)));
                name[count] = "\n" + cr.getString(1) + "\n" + cr.getString(3)+ "," + cr.getString(5);
                city[count] = "\n" + cr.getString(7);
                flGrid.setVisibility(View.VISIBLE);
                friendListText.setVisibility(View.VISIBLE);
                noFriendText.setVisibility(View.INVISIBLE);
            }
        } while (cr.moveToNext());
        String[] finalWeb = new String[count+2];
        Bitmap[] finalImageId = new Bitmap[count+2];
        String[] finalCities = new String[count+2];
        for (int i = 0; i < count+1; i++) {
            finalWeb[i] = name[i];
            finalImageId[i] = friendImage[i];
            finalCities[i] = city[i];
        }
        finalWeb[1]=name[0];
        finalCities[1]=city[0];
        finalImageId[1]=friendImage[0];
        FriendsListCGrid adapter=new FriendsListCGrid(context,finalWeb,finalCities,finalImageId,R.id.fl_text,R.id.fl_cityText,R.id.fl_image,R.layout.friendslist);
        flGrid.setAdapter(adapter);
    }

    private void showAddWindow() {
            int id=0;
            final AlertDialog.Builder myDialog = new AlertDialog.Builder(FriendsList.this);
            myDialog.setMessage("Your unique number is" + String.valueOf(id));
            final EditText input = new EditText(FriendsList.this);
            input.setHint("Enter your friends unique id");
            input.setTextColor(getResources().getColor(R.color.gray));
            input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(50, 20, 60, 0);
            input.setLayoutParams(buttonLayoutParams);
            LinearLayout layout = new LinearLayout(FriendsList.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input);
            myDialog.setView(layout);
            myDialog.setCancelable(false);
            myDialog.setPositiveButton("Add",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(),"Added friend Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
            myDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            myDialog.show();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAlertDialog("Are you sure want to quit?");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
