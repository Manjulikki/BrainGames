package manjunn.brain_games;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Settings extends MainActivity {

    NumberPicker np;
    Button save;
    static int time_interval = 3000;
    static String time = "3";
    SqliteDatabaseOperations sqliteDatabaseOperations = new SqliteDatabaseOperations(this);
    String string_interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(4);
        np.setValue(Integer.valueOf(time));
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                string_interval = String.valueOf(newVal) + "000";
                time = String.valueOf(newVal);
                time_interval = Integer.valueOf(string_interval);
            }
        });
        save = (Button) findViewById(R.id.setting_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThinkingGames.thinking = false;
                mode = -1;
                sqliteDatabaseOperations.updateTimeInterval(sqliteDatabaseOperations, String.valueOf(time));
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Now your time interval is " + String.valueOf(time) + " sec", Toast.LENGTH_SHORT).show();
                MainActivity.time_interval=time;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            ThinkingGames.firstTime = true;
            ThinkingGames.thinking = false;
            showAlertDialog("Are you sure want to quit?");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
