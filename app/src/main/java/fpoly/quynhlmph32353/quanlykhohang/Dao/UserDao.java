package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;

public class UserDao {
    Db_Helper dbHelper;

    private static final String TABLE_NAME = "User";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role";

    public UserDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_FULLNAME, user.getFullName());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_ROLE, user.getRole());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean deleteData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {user.getUsername()};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_USERNAME + "=?", dk);
        return check != -1;
    }

    public boolean updateData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {user.getUsername()};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_FULLNAME, user.getFullName());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_ROLE, user.getRole());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_USERNAME + "=?", dk);
        return check != -1;
    }

    public ArrayList<User> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
                list.add(new User(username, password, fullname, email,role));
            }
        }
        return list;
    }

    public ArrayList<User> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }

    public User SelectID(String id) {
        String query = "SELECT * FROM User WHERE username = ?";
        ArrayList<User> list = getAll(query,id);
        return list.get(0);
    }
    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM User WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + " = ?";
        String[] selectionArgs = new String[]{username, password, role};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }

}
