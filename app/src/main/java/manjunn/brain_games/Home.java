package manjunn.brain_games;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class Home extends MainActivity {

    GridView homeGrid;
    Context ctx = this;
    TextView guildLines, name;
    static String[] web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeGrid = (GridView) findViewById(R.id.homeGrid);
        name = (TextView) findViewById(R.id.displayName);
        name.setText("Welcome " + user_name);
        int[] imageId = {R.drawable.imagesandnumb, R.drawable.peacock, R.drawable.math1, R.drawable.quiz, R.drawable.memorycheck, R.drawable.previouscheck, R.drawable.math, R.drawable.matching, R.drawable.color, R.drawable.addition, R.drawable.touchnum, R.drawable.touchnumberplus,R.drawable.follow,R.drawable.unfollow};
        web = new String[]{"Images and Math", "Images", "Math", "General Knowledge", "Memory Check", "Previous Check", "Greater Lesser", "Matching Pairs", "Color and Text", "Addition", "Touch Number", "Touch Number+", "Start to End", "End to Start"};
        if (GamesSelection.challenging) {
            loadImages();
            onClick(mode);
        }
        else {
            int gridType = R.layout.home_grid;
            HomeCustomGrid adapter = new HomeCustomGrid(ctx, web, imageId, R.id.grid_text, R.id.grid_image, gridType);
            homeGrid.setAdapter(adapter);
            homeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onClick(position);
                }
            });
        }
    }

    public void onClick(int position){
                switch (position) {
                    case 0:
                        MainActivity.mode = 0;
                        startActivity(new Intent(getApplicationContext(), ThinkingGames.class));
                        break;
                    case 1:
                        MainActivity.mode = 1;
                        startActivity(new Intent(getApplicationContext(), ThinkingGames.class));
                        break;
                    case 2:
                        MainActivity.mode = 2;
                        startActivity(new Intent(getApplicationContext(), ThinkingGames.class));
                        break;
                    case 3:
                        MainActivity.mode = 3;
                        startActivity(new Intent(getApplicationContext(), ThinkingGames.class));
                        break;
                    case 4:
                        MainActivity.mode = 4;
                        ThinkingGames.firstTime = true;
                        startActivity(new Intent(getApplicationContext(), MemoryGames.class));
                        break;
                    case 5:
                        MainActivity.mode = 5;
                        ThinkingGames.firstTime = true;
                        startActivity(new Intent(getApplicationContext(), MemoryGames.class));
                        break;
                    case 6:
                        MainActivity.mode = 6;
                        startActivity(new Intent(getApplicationContext(), SwipeGames.class));
                        break;
                    case 7:
                        MainActivity.mode = 7;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 8:
                        MainActivity.mode = 8;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 9:
                        MainActivity.mode = 9;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 10:
                        MainActivity.mode = 10;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 11:
                        MainActivity.mode = 11;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 12:
                        MainActivity.mode = 12;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                    case 13:
                        MainActivity.mode = 13;
                        startActivity(new Intent(getApplicationContext(), TouchGames.class));
                        break;
                }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mode = -1;
            ThinkingGames.thinking = false;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
