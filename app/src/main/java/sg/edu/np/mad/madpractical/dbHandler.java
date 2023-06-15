package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ViewDebug;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_USER_ID = "userid";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_DESC = "userdesc";
    public static final String COLUMN_USER_FOLLOWED = "userfollowed";

    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_DESC + " TEXT, "
                + COLUMN_USER_FOLLOWED + " INTEGER" + ")";

        Log.v("DB", CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE); // executes SQL command for creating table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<User> getUsers()
    {
        // this function returns all the users information from the database as a List
        // Modify your RecyclerView so that it is pre-populated with information from the database only.
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setDescription(cursor.getString(2));
                if (cursor.getInt(3) == 0) {
                    user.setFollowed(false);
                }
                else {
                    user.setFollowed(true);
                }
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        //db.close();

        return userList;

    }

    public void updateUser(User user)
    {
        // this function will receive a User object and update the corresponding value in the database.
        // Modify your MainActivity so that it calls this function to update the database every time the Follow/Unfollow button is clicked.
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                Log.v("DB", String.valueOf(cursor.getInt(0)));
                if (cursor.getInt(0) == user.getId()){

                    ContentValues values = new ContentValues();
                    if(user.getFollowed()) {
                        values.put(COLUMN_USER_FOLLOWED, 1);
                        //db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_USER_FOLLOWED + " = 1 WHERE " + COLUMN_USER_ID + " = " + user.getId());
                    }
                    else{
                        values.put(COLUMN_USER_FOLLOWED, 0);
                        //db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_USER_FOLLOWED + " = 1 WHERE " + COLUMN_USER_ID + " = " + user.getId());
                    }
                    String whereClause = COLUMN_USER_ID + " = ?";
                    String[] whereArgs = { String.valueOf(user.getId()) };
                    db.update(TABLE_NAME, values, whereClause, whereArgs);

                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    // Generates 20 random user objects to be added into the table.
    public void generateUsers()
    {
        Random random = new Random();
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < 20; i++){
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, "Name-" + random.nextInt(9999));
            values.put(COLUMN_USER_DESC, "Description-"+random.nextInt(99999999));
            values.put(COLUMN_USER_FOLLOWED, random.nextInt(2));
            db.insert(TABLE_NAME, null, values);
        }
        //db.close();
    }

    public boolean isEmpty()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        //db.close();

        return count == 0;
    }


}
