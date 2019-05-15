package manjunn.brain_games;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by manjunn on 5/27/2016.
 */
public class SaveInputs extends MainActivity{

    Map<Integer, String[]> questionMap;
    Map<Integer, String[]> numberMap;
    public Map saveNumbers() {
        numberMap = new HashMap<>(56);
        if (numberMap.size() < 56) {
            numberMap.put(1, new String[]{"-2+8", "6", "-6"});
            numberMap.put(2, new String[]{"4?(5-2)", ">", "<"});
            numberMap.put(3, new String[]{"12+9=21", "true", "false"});
            numberMap.put(4, new String[]{"15<?", "16", "14"});
            numberMap.put(5, new String[]{"(10-5)-6", "-1", "1"});
            numberMap.put(6, new String[]{"4<7", "true", "false"});
            numberMap.put(7, new String[]{"13 __ 5 = 18 ", "+", "-"});
            numberMap.put(8, new String[]{"2 __ 6 = -4", "-", "+"});
            numberMap.put(9, new String[]{"3 __ 3 = 9", "*", "/"});
            numberMap.put(10, new String[]{"9 __ 9 = 0", "-", "+"});
            numberMap.put(11, new String[]{"11 __ 5 = 55", "*", "/"});
            numberMap.put(12, new String[]{"28 __ 4 = 7", "/", "+"});
            numberMap.put(13, new String[]{"3 __ 7 = -4", "-", "*"});
            numberMap.put(14, new String[]{"3 __ 7 = 21", "*", "+"});
            numberMap.put(15, new String[]{"8 __ 4 = 12", "+", "*"});
            numberMap.put(16, new String[]{"33 __ 3 = 11", "/", "-"});
            numberMap.put(17, new String[]{"11 __ 3 = 33", "*", "+"});
            numberMap.put(18, new String[]{"(6+8)-1", "13", "15"});
            numberMap.put(19, new String[]{"(1+2+3)-6", "0", "-1"});
            numberMap.put(20, new String[]{"3-3", "0", "9"});
            numberMap.put(21, new String[]{"5+1", "6", "4"});
            numberMap.put(22, new String[]{"(5-3) ? 6", "<", ">"});
            numberMap.put(23, new String[]{"(2+3)+3", "8", "7"});
            numberMap.put(24, new String[]{"12 __ 5 = 7", "-", "/"});
            numberMap.put(25, new String[]{"4 - 4 = 0", "-", "/"});
            numberMap.put(26, new String[]{"1 * 1", "1", "0"});
            numberMap.put(27, new String[]{"2 __ 2 = 4", "+", "/"});
            numberMap.put(28, new String[]{"8 + 3 + 5", "16", "15"});
            numberMap.put(29, new String[]{"1 - 1 = 0", "true", "false"});
            numberMap.put(30, new String[]{"33/3 = 11", "true", "false"});
            numberMap.put(31, new String[]{"129 __ 3 = 43", "/", "-"});
            numberMap.put(32, new String[]{"10 > -10", "true", "false"});
            numberMap.put(33, new String[]{"13 + 4 ? 17", "=", ">"});
            numberMap.put(34, new String[]{"444 > 4444", "false", "true"});
            numberMap.put(35, new String[]{"17 - 12 = 27", "false", "true"});
            numberMap.put(36, new String[]{"17 + 12 = 27", "true", "false"});
            numberMap.put(37, new String[]{"3 + 3 - 3 * 3", "-3", "18"});
            numberMap.put(38, new String[]{"(9 - 3) + 2", "8", "4"});
            numberMap.put(39, new String[]{"22 + 20 = 3", "false", "true"});
            numberMap.put(40, new String[]{"12 __ 4 = 16", "+", "*"});
            numberMap.put(41, new String[]{"8 __ 9 = 17", "+", "*"});
            numberMap.put(42, new String[]{"4 __ 1 = 4", "*", "+"});
            numberMap.put(43, new String[]{"1 __ 1 = 1", "/", "-"});
            numberMap.put(44, new String[]{"1 __ 2 = -1", "-", "+"});
            numberMap.put(45, new String[]{"2 __ 1 = 2", "/", "+"});
            numberMap.put(46, new String[]{"444 __ 4 = 111", "/", "-"});
            numberMap.put(47, new String[]{"15 __ 5 = 3", "/", "-"});
            numberMap.put(48, new String[]{"366 __ 6 = 61", "/", "*"});
            numberMap.put(49, new String[]{"21 / 3", "7", "9"});
            numberMap.put(50, new String[]{"5 * 9", "45", "54"});
            numberMap.put(51, new String[]{"12 * 3", "36", "15"});
            numberMap.put(52, new String[]{"32 - 32", "0", "1"});
            numberMap.put(53, new String[]{"644 __ 4 = 161", "/", "-"});
            numberMap.put(54, new String[]{"12 / 4", "3", "4"});
            numberMap.put(55, new String[]{"33 > (25+8)", "false", "true"});
            numberMap.put(56, new String[]{"2 < (12-5)", "true", "false"});
        }
        return numberMap;
    }

    public Map saveStrings() {
        questionMap = new HashMap<>(86);
        if (questionMap.size() < 86) {
            questionMap.put(1, new String[]{"Which is the largest continent", "Asia", "Europe"});
            questionMap.put(2, new String[]{"What is the currency of China ", "Yuan", "Yen"});
            questionMap.put(3, new String[]{"Which is the heavier metal of these two?", "gold", "silver"});
            questionMap.put(4, new String[]{"What are female elephants called?", "cows", "elephants"});
            questionMap.put(5, new String[]{"How many legs the Crab has", "10", "12"});
            questionMap.put(6, new String[]{"What is the capital of Afghanistan", "Kabul", "Tehran"});
            questionMap.put(7, new String[]{"What is the currency of Australia", "Dollar", "PESO"});
            questionMap.put(8, new String[]{"Which is the biggest City in the world", "New york", "Beijing"});
            questionMap.put(9, new String[]{"Which is the fastest bird", "Swift", "Ostrich"});
            questionMap.put(10, new String[]{"Which is the largest continent", "Asia", "Europe"});
            questionMap.put(11, new String[]{"Which is the largest country by size", "Russia", "China"});
            questionMap.put(12, new String[]{"Which is the largest country by population", "China", "Russia"});
            questionMap.put(13, new String[]{"Which is the largest desert", "Sahara", "Thar"});
            questionMap.put(14, new String[]{"Which is the smallest bird", "Humming bird", "Ostrich"});
            questionMap.put(15, new String[]{"What is the capital of Malaysia", "Kuala Lumpur", "Astana"});
            questionMap.put(16, new String[]{"Which is the smallest city", "Vatican City", "Grenada"});
            questionMap.put(17, new String[]{"Which is the smallest country by population", "Vatican City", "Grenada"});
            questionMap.put(18, new String[]{"Which is the highest valued currency", "Kuwait dinar", "Euro"});
            questionMap.put(19, new String[]{"Which is the most sensitive organ in our body?", "Skin", "Eyes"});
            questionMap.put(20, new String[]{"Which is the strongest part in our body?", "Teeth", "Bones"});
            questionMap.put(21, new String[]{"What are the two holes in the nose called?", "Nostrils", "Mane"});
            questionMap.put(22, new String[]{"What is the perimeter of a circle known as?", "Circumference", "Radius"});
            questionMap.put(23, new String[]{"What is the capital of Thailand", "Bangkok", "Thailand"});
            questionMap.put(24, new String[]{"What is the currency of France", "Euro", "Franc"});
            questionMap.put(25, new String[]{"Can bats be classified as birds or mammals", "Mammals", "Birds"});
            questionMap.put(26, new String[]{"What is the average life-span of a mice?", "2Years", "20Years"});
            questionMap.put(27, new String[]{"What is the average life-span of a tortoise?", "150years", "50years"});
            questionMap.put(28, new String[]{"Which is the Land of the Rising Sun?", "Japan", "Taiwan"});
            questionMap.put(29, new String[]{"The largest ocean in the world is", "The Pacific Ocean", "Atlantic Ocean\n"});
            questionMap.put(30, new String[]{"Are rabbits born blind?", "Yes", "No"});
            questionMap.put(31, new String[]{"She has _______ to start her driving lessons", "already", "Yet"});
            questionMap.put(32, new String[]{"What is the capital of Indonesia", "Jakarta", "tehran"});
            questionMap.put(33, new String[]{"What is the currency of Germany", "Euro", "Germ"});
            questionMap.put(34, new String[]{"Spider has", "8Legs", "6Legs"});
            questionMap.put(35, new String[]{"How many legs the Crab has", "10", "12"});
            questionMap.put(36, new String[]{"What is the capital of Iraq", "Baghdad", "Amman"});
            questionMap.put(37, new String[]{"What is the currency of Japan", "Yen", "Yuan"});
            questionMap.put(38, new String[]{"What is the capital of Japan", "Tokyo", "Muscat"});
            questionMap.put(39, new String[]{"What is the currency of Kuwait", "Dinar", "kuwaiti"});
            questionMap.put(40, new String[]{"What is the currency of United Kingdom", "pound", "Euro"});
            questionMap.put(41, new String[]{"What is the capital of Singapore", "Singapore", "Seoul"});
            questionMap.put(42, new String[]{"What is the currency of USA", "Dollar", "Dinar"});
            questionMap.put(43, new String[]{"What is the capital of Bangladesh", "Dhaka", "Baghdad"});
            questionMap.put(44, new String[]{"What is the currency of SriLanka", "Rupee", "Rupiah"});
            questionMap.put(45, new String[]{"What is the capital of China", "Beijing", "Shanghai"});
            questionMap.put(46, new String[]{"What is the currency of Malaysia", "ringgid", "Kwa+C35cha"});
            questionMap.put(47, new String[]{"What is the capital of Australia", "Canberra", "Vienna"});
            questionMap.put(48, new String[]{"What is the capital of France", "Paris", "Berlin"});
            questionMap.put(49, new String[]{"What is the capital of Russia", "Moscow", "Berlin"});
            questionMap.put(50, new String[]{"What is the capital of South Africa", "Cape town", "Durban"});
            questionMap.put(51, new String[]{"Who introduced Cricket to world", "England", "Australia"});
            questionMap.put(52, new String[]{"Who introduced Hockey to world", "England", "India"});
            questionMap.put(53, new String[]{"Who introduced Volley ball to world", "America", "India"});
            questionMap.put(54, new String[]{"Who introduced Kabaddi", "India", "Pakistan"});
            questionMap.put(55, new String[]{"Who introduced Basket ball to world", "Canada", "America"});
            questionMap.put(56, new String[]{"Who introduced Football to world", "England", "Brazil"});
            questionMap.put(57, new String[]{"Who was the 1st woman Prime minister of India", "Indira Gandhi", "Pratibha patil"});
            questionMap.put(58, new String[]{"Who was the 1st woman President of India", "Pratibha patil", "Indira Gandhi"});
            questionMap.put(59, new String[]{"Moon is a ", "Satellite", "Planet"});
            questionMap.put(60, new String[]{"The first metal used by the man was", "Copper", "Iron"});
            questionMap.put(61, new String[]{"Sun is a", "Star", "Planet"});
            questionMap.put(62, new String[]{"Collection of stars is ", "constellations", "Galaxy"});
            questionMap.put(63, new String[]{"Coldest location in the earth", "East Antarctica", "Ethiopia"});
            questionMap.put(64, new String[]{"Hottest place in the earth", "Ethiopia", "East Antarctica"});
            questionMap.put(65, new String[]{"Nearest star to planet earth", "Sun", "Son"});
            questionMap.put(66, new String[]{"Butterflies are ", "insects", "reptiles"});
            questionMap.put(67, new String[]{"Dolphin is", "Mammal", "Fish"});
            questionMap.put(68, new String[]{"Shark is ", "Fish", "Mammal"});
            questionMap.put(69, new String[]{"Butterflies have", "6 Legs", "4 Legs"});
            questionMap.put(70, new String[]{"Largest mammal is", "Blue whale", "Dolphin"});
            questionMap.put(71, new String[]{"Smallest ocean is ", "Arctic", "Antartic"});
            questionMap.put(72, new String[]{"First Indian Satellite is ", "Aryabhatta", "Rohinh"});
            questionMap.put(73, new String[]{"Brighest planet is", "Venus", "Jupiter"});
            questionMap.put(74, new String[]{"Biggest planet is", "Jupiter", "Jaipur"});
            questionMap.put(75, new String[]{"Pink city in india", "Jaipur", "Mysore"});
            questionMap.put(76, new String[]{"Entomology is related to ", "Insects", "human beings"});
            questionMap.put(77, new String[]{"Where is the Biggest Museum", "UK", "US"});
            questionMap.put(78, new String[]{"Coldest planet is", "Neptune", "nephew"});
            questionMap.put(79, new String[]{"What is the capital of Italy", "Rome", "Rose"});
            questionMap.put(80, new String[]{"Clotting of blood requires", "Vitamin K", "Vitamin C"});
            questionMap.put(81, new String[]{"Egypt is in ", "Africa", "Europe"});
            questionMap.put(82, new String[]{"Headquarters of UNO ", "New york", "Washington DC"});
            questionMap.put(83, new String[]{"Grand Central Terminal is", "largest railway station", "largest runway"});
            questionMap.put(84, new String[]{"Garampani sanctuary is at", "Assam", "Assad"});
            questionMap.put(85, new String[]{"Country with no independence yet", "Scotland", "Vatican city"});
            questionMap.put(86, new String[]{"Australia won Cricket world cup", "5 times", "4 Times"});
        }
        return questionMap;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
