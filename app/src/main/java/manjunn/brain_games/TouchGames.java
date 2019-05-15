package manjunn.brain_games;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TouchGames extends MainActivity {

    Random random=new Random();;
    int[] imageArr, finalArr;
    String[] nameArr;
    Context ctx=this;
    GridView touchGrid;
    List selected;
    int count=1;
    boolean match1=false,match2=false,firstTime=true;
    int score=0;
    TextView timeView, guidelines, additionNumber;
    static CountDownTimer timer;
    static boolean counterFlag;
    String matchingGuidelines="Select two matching pairs";
    String colorGuidelines="Select incorrect text and color";
    String additionGuidelines="Add the digits to make a given number";
    String touchNumberGuidelines="Touch Numbers in Ascending order if numbers are black color. Touch Numbers in descending order if numbers are orange color.";
    String touchNumberPlusGuidelines="Touch Numbers in Ascending order and add those numbers";
    String startEnd="Touch the Numbers which appeared First 'FIFO'";
    String endStart="Touch the Numbers which appeared Last 'LIFO'";
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_games);
        timeView= (TextView) findViewById(R.id.touchTime);
        guidelines= (TextView) findViewById(R.id.touchGuidelines);
        guidelines.setText(matchingGuidelines);
        touchGrid= (GridView) findViewById(R.id.touchGrid);
        additionNumber= (TextView) findViewById(R.id.additionNumber);
        v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        if (mode==7) {
            guidelines.setText(matchingGuidelines);
            loadGridValuesFMatching();
        }else if (mode==8) {
            guidelines.setText(colorGuidelines);
            loadGridValuesFColors();
        }  else if (mode==9) {
            guidelines.setText(additionGuidelines);
            loadGridValuesFAddition();
        } else if (mode==10) {
            guidelines.setText(touchNumberGuidelines);
            loadGridValuesFTouchNumber();
        } else if (mode==11) {
            guidelines.setText(touchNumberPlusGuidelines);
            loadGridValuesFTouchNumberPlus();
        } else if (mode==12) {
            guidelines.setText(startEnd);
            loadGridValuesFStartEnd();
        } else if (mode==13) {
            guidelines.setText(endStart);
            loadGridValuesFEndStart();
        }
        //startFirstTimeTimer();
    }

    private void startFirstTimeTimer() {
        timeView.setText("6");
        counterFlag=true;
        timer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                if (sec >= 0)
                    timeView.setText(String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                guidelines.setVisibility(View.INVISIBLE);
                touchGrid.setVisibility(View.VISIBLE);
                if (mode==9) additionNumber.setVisibility(View.VISIBLE);
                startTimer();
            }
        }.start();

    }

    private void startTimer() {
        timeView.setText("30");
        counterFlag=true;
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                int sec = Integer.valueOf(timeView.getText().toString()) - 1;
                if (sec >= 0)
                    timeView.setText(String.valueOf(sec));
            }

            @Override
            public void onFinish() {
                timeView.setText("0");
                imageArr=null;
                showResultAlert(score);
            }
        }.start();
    }

    private void loadGridValuesFEndStart() {
        touchGrid.setNumColumns(3);
        if (firstTime) {
            firstTime=false;
            startFirstTimeTimer();
        }
        imageArr=new int[9];
        selected=new ArrayList();
        int rand=0;
        if (score<4) rand=3;
        else if (score<7) rand=4;
        else if (score<12) rand=5;
        else if (score<15) rand=6;
        else rand=7;
        addImagesNumbersToArray();
        for (int i=0;i<rand;i++){
            TouchGamesCustomGrid adapter=new TouchGamesCustomGrid(ctx,new int[]{imageArr[i]},R.id.matchingImage,R.layout.matchinggrid);
            touchGrid.setAdapter(adapter);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadGridValuesFStartEnd() {
        touchGrid.setBackground(getResources().getDrawable(R.color.gray));
        touchGrid.setNumColumns(3);
        if (firstTime) {
            firstTime=false;
            startFirstTimeTimer();
            new LoadNext().execute();
        }
        selected=new ArrayList();
        int rand=0;
        if (score<4) rand=3;
        else if (score<7) rand=4;
        else if (score<12) rand=5;
        else if (score<15) rand=6;
        else rand=7;
        imageArr=new int[9];
        finalArr=new int[9];
        addImagesNumbersWBToArray();
        for (int i=0;i<count;i++){
            int randomInt=random.nextInt(9 -1) + 1;
            if (!selected.contains(randomInt)){
                finalArr[randomInt]=imageArr[randomInt];
                selected.add(randomInt);
            }
            else i--;
        }
        if (count<=rand) {
            TouchGamesCustomGrid adapter = new TouchGamesCustomGrid(ctx,finalArr,R.id.matchingImage, R.layout.matchinggrid);
            touchGrid.setAdapter(adapter);
            touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setVisibility(View.INVISIBLE);
                }
            });
            if (!firstTime)
            new LoadNext().execute();
        }
    }

    private void loadGridValuesFMatching() {
        touchGrid.setNumColumns(3);
        if (firstTime) {
            firstTime=false;
            startFirstTimeTimer();
        }
        imageArr=new int[9];
        selected=new ArrayList();
        final int int1=random.nextInt(9 -1) + 1;
        int int2=random.nextInt(9 -1) + 1;
        if (int1==int2 && int1<9) int2++;
        else if (int1==int2 && int1==9) int2--;
        int randIntFImage=random.nextInt(118 -96 )+96;
        List randIntArr=new ArrayList();
        randIntArr.add(randIntFImage);
        for (int i=0;i<9;i++){
          int randomInt=random.nextInt(118 - 96) + 96;
            if (!randIntArr.contains(randomInt)){
                imageArr[i]=imageArray[randomInt];
                randIntArr.add(randomInt);
            }
            else i--;
        }
        imageArr[int1]=imageArray[randIntFImage];
        imageArr[int2]=imageArray[randIntFImage];
        int gridType = R.layout.matchinggrid;
        TouchGamesCustomGrid adapter = new TouchGamesCustomGrid(ctx, imageArr, R.id.matchingImage, gridType);
        touchGrid.setAdapter(adapter);
        final int finalInt2 = int2;
        touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (int1 == i) match1 = true;
                else if (finalInt2 == i) match2 = true;
                    if (selected.contains(i)) {
                        selected.remove(0);
                        match2 = false;
                        match1 = false;
                        view.setBackground(getResources().getDrawable(R.color.white));
                    } else {
                        selected.add(i);
                        view.setBackground(getResources().getDrawable(R.color.colorPrimary));
                    }
                    if (match1 && match2) {
                        score++;
                        onSelect();
                    }
                    else if (selected.size() == 2) {
                        v.vibrate(500);
                        score--;
                        onSelect();
                    }
            }
        });
    }

    private void onSelect() {
        touchGrid.setAdapter(null);
        match1=false;
        match2=false;
        loadGridValuesFMatching();
    }

    private void loadGridValuesFColors() {
        touchGrid.setNumColumns(2);
        touchGrid.setBackground(getResources().getDrawable(R.color.white));
        if (firstTime) {
            startFirstTimeTimer();
            firstTime=false;
        }
        imageArr=new int[4];
        nameArr=new String[4];
        selected=new ArrayList();
        String inCorrectText="";
        for (int i=0;i<5;i++){
            int randomNum=random.nextInt(129 - 119) + 119;
            if (selected.contains(randomNum)){
                i--;
            } else {
                if (i==4) inCorrectText=nameArray[randomNum].substring(3, nameArray[randomNum].length());
                else {
                    imageArr[i] = imageArray[randomNum];
                    nameArr[i] = nameArray[randomNum].substring(3, nameArray[randomNum].length());
                    selected.add(randomNum);
                }
            }
        }
        final int nameRandomNum=random.nextInt(4 - 1) + 1;
        nameArr[nameRandomNum]=inCorrectText;
        int gridType = R.layout.color_grid;
        HomeCustomGrid adapter = new HomeCustomGrid(ctx,nameArr, imageArr, R.id.colorGridText ,R.id.colorGridImage, gridType);
        touchGrid.setAdapter(adapter);
        touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (nameRandomNum==i) {
                    score++;
                    onCorrect();
                }
                else {
                    score--;
                    v.vibrate(500);
                    onCorrect();
                }
            }
        });
    }

    private void onCorrect() {
        touchGrid.setAdapter(null);
        selected.clear();
        loadGridValuesFColors();
    }

    private void loadGridValuesFAddition(){
        if (firstTime) {
            firstTime=false;
            startFirstTimeTimer();
        }
        final int[] count = {0};
        addImagesNumbersToArray();
        final int randomNumber=random.nextInt(29 -10) + 10;
        additionNumber.setText(String.valueOf(randomNumber));
        int gridId=R.layout.matchinggrid;
        TouchGamesCustomGrid adapter=new TouchGamesCustomGrid(ctx,imageArr,R.id.matchingImage,gridId);
        touchGrid.setAdapter(adapter);
        touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                count[0]+= i+1;
                if (count[0]==randomNumber) {
                    score++;
                    onAddition();
                } else if (count[0]>randomNumber){
                    score--;
                    v.vibrate(500);
                    onAddition();
                }
                view.setVisibility(View.INVISIBLE);
            }
        });
    }



    private void onAddition() {
        touchGrid.setAdapter(null);
        loadGridValuesFAddition();
    }

    private void addImagesNumbersToArray() {
        imageArr=new int[9];
        imageArr[0]=imageArray[113];
        imageArr[1]=imageArray[118];
        imageArr[2]=imageArray[117];
        imageArr[3]=imageArray[104];
        imageArr[4]=imageArray[103];
        imageArr[5]=imageArray[115];
        imageArr[6]=imageArray[114];
        imageArr[7]=imageArray[101];
        imageArr[8]=imageArray[112];
    }

    private void addImagesNumbersWBToArray() {
        imageArr=new int[9];
        imageArr[0]=imageArray[130];
        imageArr[1]=imageArray[131];
        imageArr[2]=imageArray[132];
        imageArr[3]=imageArray[133];
        imageArr[4]=imageArray[134];
        imageArr[5]=imageArray[135];
        imageArr[6]=imageArray[136];
        imageArr[7]=imageArray[137];
        imageArr[8]=imageArray[138];
    }

    public void loadGridValuesFTouchNumber(){
        if (firstTime){
            firstTime=false;
            startFirstTimeTimer();
        }
        final boolean isAscending;
        imageArr=new int[9];
        final int[] flag = {0};
        final int  randomNumber=random.nextInt(9 - 4) + 4;
        if (randomNumber%2==0) {
            isAscending=true;
            addImagesNumbersWBToArray();
        }else {
            isAscending=false;
            addImagesNumbersToArray();
        }
        finalArr=new int[randomNumber];
        for (int i=0;i<randomNumber;i++){
            finalArr[i]=imageArr[i];
        }
        shuffleArray(finalArr);
        TouchGamesCustomGrid adapter=new TouchGamesCustomGrid(ctx,finalArr,R.id.matchingImage,R.layout.matchinggrid);
        touchGrid.setAdapter(adapter);
        touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isAscending && imageArr[flag[0]]!=finalArr[i]) {
                    v.vibrate(500);
                    score--;
                   onTouch();
                } else if (!isAscending && imageArr[finalArr.length-flag[0]-1]!=finalArr[i]){
                    v.vibrate(500);
                    score--;
                    onTouch();
                } else if (flag[0]+1==randomNumber){
                    score++;
                    onTouch();
                }
                view.setVisibility(View.INVISIBLE);
                flag[0]++;
            }
        });
    }

    private void onTouch() {
       touchGrid.setAdapter(null);
        loadGridValuesFTouchNumber();
    }
    public void loadGridValuesFTouchNumberPlus(){
        if (firstTime){
            firstTime=false;
            startFirstTimeTimer();
        }
        int count=0;
        imageArr=new int[9];
        final int[] flag = {0};
        final int randomNumber=random.nextInt(5 -2) + 2;
        addImagesNumbersToArray();
        finalArr=new int[randomNumber];
        final List numberList=new ArrayList();
        for (int i=0;i<randomNumber;i++){
            int number=random.nextInt(9 - 1) + 1;
            if (!numberList.contains(number)) {
                numberList.add(number);
                count += number+1;
            } else i--;
        }
        for (int i=0;i<randomNumber;i++){
            finalArr[i]=imageArr[(int) numberList.get(i)];
        }

        Collections.sort(numberList, new Comparator<Integer>() {
            @Override
            public int compare(Integer integer1, Integer integer2) {
               return integer1.compareTo(integer2);
            }
        });
        TouchGamesCustomGrid adapter=new TouchGamesCustomGrid(ctx,finalArr,R.id.matchingImage,R.layout.matchinggrid);
        touchGrid.setAdapter(adapter);
        final int finalCount = count;

        touchGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imageArr[(int) numberList.get(flag[0])]!=finalArr[i]) {
                    v.vibrate(500);
                    score--;
                    onAddPlus();
                }  else if (flag[0]+1==randomNumber){
                    score++;
                    //onTouch();
                    showAdditionDialog(finalCount, numberList);
                }
                view.setVisibility(View.INVISIBLE);
                flag[0]++;
            }
        });
    }

    private void onAddPlus() {
        touchGrid.setAdapter(null);
        loadGridValuesFTouchNumberPlus();
    }

    private void showAdditionDialog(int answer, final List numberList) {
        LayoutInflater layoutInflater
                = (LayoutInflater) context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.addtionlayout, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.update();
        Button op1 = (Button) popupView.findViewById(R.id.op1);
        Button op2 = (Button) popupView.findViewById(R.id.op2);
        Button op3 = (Button) popupView.findViewById(R.id.op3);
        Button op4 = (Button) popupView.findViewById(R.id.op4);
        final TextView hintView = (TextView) popupView.findViewById(R.id.hint);
        int ansRand=random.nextInt(4 -1) + 1;
        int correct=0;
        if (ansRand==1){
            correct=1;
            op1.setText(String.valueOf(answer));
            op2.setText(String.valueOf(answer+2));
            op3.setText(String.valueOf(answer-3));
            op4.setText(String.valueOf(answer+4));
        } else if (ansRand==2){
            correct=2;
            op1.setText(String.valueOf(answer+2));
            op2.setText(String.valueOf(answer));
            op3.setText(String.valueOf(answer-3));
            op4.setText(String.valueOf(answer+4));
        } else if (ansRand==3){
            correct=3;
            op1.setText(String.valueOf(answer-3));
            op2.setText(String.valueOf(answer+2));
            op3.setText(String.valueOf(answer));
            op4.setText(String.valueOf(answer+4));
        } else {
            correct=4;
            op1.setText(String.valueOf(answer+4));
            op2.setText(String.valueOf(answer+2));
            op3.setText(String.valueOf(answer-3));
            op4.setText(String.valueOf(answer));
        }
        final int finalCorrect = correct;
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onclickPlus(finalCorrect,1,numberList,hintView))
                popupWindow.dismiss();
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onclickPlus(finalCorrect,2,numberList,hintView))
                popupWindow.dismiss();
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onclickPlus(finalCorrect,3,numberList,hintView))
                popupWindow.dismiss();
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onclickPlus(finalCorrect,4,numberList,hintView))
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(hiddenButton, 50, -30);
    }

    private boolean onclickPlus(int correct,int val, List numberList, TextView hintView) {
        if (correct ==val) {
            score++;
            onAddPlus();
            return true;
        } else {
            score--;
            String hint="";
            for (int i=0;i<numberList.size();i++) {
                hint+=String.valueOf((int)numberList.get(i) + 1);
                hint+=" + ";
            }
            hintView.setText(hint.substring(0,hint.length()-2));
            v.vibrate(500);
            return false;
        }
    }


    private void shuffleArray(int[] array)
    {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

//    @Override
//    protected void onPause() {
//        int count = touchGrid.getCount();
//        for (int i = 0; i < count; i++) {
//            RelativeLayout gridViewElement = (RelativeLayout) touchGrid.getChildAt(i);
//            if (gridViewElement != null) {
//                ImageView imageView = (ImageView) gridViewElement.findViewById(R.id.matchingImage);
//                if (imageView!=null) {
//                    imageView.getDrawable().setCallback(null);
//                    imageView = null;
//                }
//            }
//        }
//        super.onPause();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime=false;
            showAlertDialog("Are you sure want to quit?");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class LoadNext extends AsyncTask<String,Void,Integer> {

        @Override
        protected Integer doInBackground(String... pictureUrl) {
           try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }

        protected void onPostExecute(Integer val) {
                count++;
                loadGridValuesFStartEnd();
        }
    }
}
