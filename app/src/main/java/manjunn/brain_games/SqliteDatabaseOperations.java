package manjunn.brain_games;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * Created by manjunn on 4/13/2016.
 */

public class SqliteDatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public static final String valuesDB = "uuu_values";
    public static final String high_scores = "highest_scores_a";
    public static final String settings_values = "s_values";
    public static final String user_values = "g_userDetails";
    public String createINQuery = "CREATE TABLE IF NOT EXISTS " + valuesDB + "(images TEXT, string TEXT, numbers TEXT, ca TEXT, wa TEXT, mode TEXT)";
    public String deleteQuery = "DELETE TABLE " + valuesDB;
    public static final String highScoreQuery = "CREATE TABLE IF NOT EXISTS " + high_scores + "(name TEXT, high_score TEXT, mode TEXT)";
    public static final String userNameQuery = "CREATE TABLE IF NOT EXISTS user_name_a (name TEXT)";
    public static final String timeIntervalQuery = "CREATE TABLE IF NOT EXISTS " + settings_values + "(time_interval TEXT)";
    public String createQuery = "CREATE TABLE IF NOT EXISTS " + user_values + "(" + "user_id" + " TEXT, "+ "user_name" + " TEXT, " + "picture" + " TEXT, " + "age" + " TEXT, " + "dob" + " TEXT," + "gender" + " TEXT," + "email" + " TEXT," + "place" + " TEXT);";
    private int record = 0;

    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(createINQuery);
        sdb.execSQL(highScoreQuery);
        sdb.execSQL(createQuery);
    }

    public void onUpgrade(SQLiteDatabase sdb, int arg1, int arg2) {
        //s sdb.execSQL(updateQuery);
    }

    public SqliteDatabaseOperations(Context context) {
        super(context, "Brain_Games", null, database_version);
    }

    public void insertValues(SqliteDatabaseOperations sqliteDatabaseOperations, Bitmap img, String string, String number, String ca, String wa) {
//        deleteValues(sqliteDatabaseOperations);
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        sql.execSQL(createINQuery);
        if (img != null) {
            insertImg(sqliteDatabaseOperations, img, ca, wa);
        } else if (!number.equals("") && number != null) {
            insertNum(sqliteDatabaseOperations, number, ca, wa);
        } else if (!string.equals("") && string != null)
            insertString(sqliteDatabaseOperations, string, ca, wa);
    }

    private void insertString(SqliteDatabaseOperations sqliteDatabaseOperations, String string, String ca, String wa) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("images", (byte[]) null);
        cv.put("string", string);
        cv.put("numbers", "");
        cv.put("ca", ca);
        cv.put("wa", wa);
        cv.put("mode", "2");
        sql.insert(valuesDB, null, cv);
    }

    public static void deleteValues(SqliteDatabaseOperations sql) {
        SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
        sqLiteDatabase.delete(valuesDB, null, null);
    }

    public void insertNum(SqliteDatabaseOperations sqliteDatabaseOperations, String value, String ca, String wa) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("images", (byte[]) null);
        cv.put("numbers", value);
        cv.put("string", "");
        cv.put("ca", ca);
        cv.put("wa", wa);
        cv.put("mode", "3");
        sql.insert(valuesDB, null, cv);
    }

    public void insertImg(SqliteDatabaseOperations sqliteDatabaseOperations, Bitmap img, String ca, String wa) {

        byte[] data = getBitmapAsByteArray(img); // this is a function
        SQLiteDatabase db = sqliteDatabaseOperations.getWritableDatabase();
        String sql = "INSERT INTO " + valuesDB + " (images,string,numbers,ca,wa,mode) VALUES(?,?,?,?,?,?)";
        SQLiteStatement insertStatement_logo = db.compileStatement(sql);
        insertStatement_logo.bindBlob(1, data);
        insertStatement_logo.bindString(2, "");
        insertStatement_logo.bindString(3, "");
        insertStatement_logo.bindString(4, ca);
        insertStatement_logo.bindString(5, wa);
        insertStatement_logo.bindString(6, "1");
        insertStatement_logo.executeInsert();
        insertStatement_logo.clearBindings();

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Cursor getValues(SqliteDatabaseOperations dop) {
        SQLiteDatabase sql = dop.getReadableDatabase();
        sql.execSQL(createINQuery);
        //return sql.query(Partner.PartnerInfo.TABLE_NAME, null, "user_name=?", new String[]{userName}, null, null, null, null);
        return sql.query(valuesDB, null, null, null, null, null, null);
    }

    public static String getHighScore(SqliteDatabaseOperations sqliteDatabaseOperations, String mode) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getReadableDatabase();
        sql.execSQL(highScoreQuery);
        String hs = "";
        String retrievalQuery = "SELECT * FROM " + high_scores + " where mode= '" + mode + "'";
        Cursor cr = sql.rawQuery(retrievalQuery, null);
        do {
            if (cr.moveToFirst()) {
                hs = cr.getString(1);
            }
        } while (cr.moveToNext());
        // Cursor cr=sql.query(high_scores,null,null,null,null,null,null);
        return hs;
    }

    public static void saveHighScore(SqliteDatabaseOperations sqliteDatabaseOperations, String name, String mode, String high_score) {
        SQLiteDatabase db = sqliteDatabaseOperations.getWritableDatabase();
        // db.execSQL(highScoreQuery);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("high_score", high_score);
        cv.put("mode", mode);
        db.insert(high_scores, null, cv);
    }

    public static void updateHighScore(SqliteDatabaseOperations sqliteDatabaseOperations, String name, String mode, String score) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("high_score", score);
        sql.update(high_scores, cv, "mode=?", new String[]{mode});
    }

    public static void saveTimeInterval(SqliteDatabaseOperations sqliteDatabaseOperations) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("time_interval", "3");
        sql.insert(settings_values, null, cv);
    }

    public void updateTimeInterval(SqliteDatabaseOperations sqliteDatabaseOperations, String value) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("time_interval", value);
        sql.update(settings_values, cv, null, null);
    }

    public static String getTimeInterval(SqliteDatabaseOperations sqliteDatabaseOperations) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getReadableDatabase();
        String time = "3";
        String retrievalQuery = "SELECT * FROM " + settings_values;
        Cursor cr = sql.rawQuery(retrievalQuery, null);
        do {
            if (cr!=null && cr.moveToFirst()) {
                if (!cr.getString(0).equals("")) {
                    time = cr.getString(0);
                }
            } else if (cr.getCount()==0) saveTimeInterval(sqliteDatabaseOperations);
        } while (cr.moveToNext());

        return time;
    }

    public void saveUserName(SqliteDatabaseOperations sqliteDatabaseOperations, String name) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_name", name);
        sql.insert(user_values, null, cv);
    }

    public String getUserName(SqliteDatabaseOperations sqliteDatabaseOperations) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getReadableDatabase();
        String retrievalQuery = "SELECT user_name FROM " + user_values;
        String name = "";
        Cursor cr = sql.rawQuery(retrievalQuery, null);
        if (cr.moveToFirst()) {
            name = cr.getString(0);
        }
        return name;
    }

    public static Cursor getAllHighScore(SqliteDatabaseOperations sqliteDatabaseOperations) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getReadableDatabase();
        String retrievalQuery = "SELECT * FROM " + high_scores;
        return sql.rawQuery(retrievalQuery, null);

    }

    public void createTables(SqliteDatabaseOperations sqliteDatabaseOperations) {
        SQLiteDatabase sql = sqliteDatabaseOperations.getWritableDatabase();
        sql.execSQL(highScoreQuery);
        sql.execSQL(timeIntervalQuery);
        sql.execSQL(userNameQuery);
        sql.execSQL(createQuery);
    }

    public void insertBasicDataToDatabase(SqliteDatabaseOperations db, String id, String user_name, String picture, String dob, String gender, String age, String email, String place) {

        if (!db.getUserName(db).equals(user_name) || db.getUserName(db).equals("")) {
            SQLiteDatabase sql = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("user_id", id);
            cv.put("user_name", user_name);
            cv.put("age", age);
            cv.put("email", email);
            cv.put("place", place);
            cv.put("dob", dob);
            cv.put("gender", gender);
            cv.put("picture", picture);
            sql.insert(user_values, null, cv);
        }
    }

    public Cursor getUserDetails(SqliteDatabaseOperations sqliteDatabaseOperations){
        SQLiteDatabase sql = sqliteDatabaseOperations.getReadableDatabase();
        String retrievalQuery = "SELECT * FROM " + user_values;
        return sql.rawQuery(retrievalQuery, null);
    }
}
