package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;

public class CategoryDao {
    Db_Helper dbHelper;
    private final static String TABLE_NAME = "Category";
    private final static String COLUMN_ID = "category_id";
    private final static String COLUMN_NAME = "category_name";
    private final static String COLUMN_IMAGE = "image";
    private final static String COLUMN_DESCRIBE = "describe";
    public CategoryDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Category category) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, category.getCategory_name());
        contentValues.put(COLUMN_IMAGE,category.getImage());
        contentValues.put(COLUMN_DESCRIBE,category.getDescribe());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        category.setCategory_id((int) check);
        return check != -1;
    }

    public boolean deleteData(Category category) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(category.getCategory_id())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Category category) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(category.getCategory_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, category.getCategory_name());
        contentValues.put(COLUMN_IMAGE,category.getImage());
        contentValues.put(COLUMN_DESCRIBE,category.getDescribe());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public ArrayList<Category> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int category_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                String category_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                String describe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIBE));
                list.add(new Category(category_id, category_name,image,describe));
            }
        }
        return list;
    }
    public ArrayList<Category> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }

    public Category SelectID(String id) {
        String query = "SELECT * FROM Category WHERE category_id = ?";
        ArrayList<Category> list = getAll(query, id);
        return list.get(0);
    }
}
