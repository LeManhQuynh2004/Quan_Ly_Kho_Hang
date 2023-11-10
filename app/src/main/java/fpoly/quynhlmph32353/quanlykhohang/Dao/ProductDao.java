package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;

public class ProductDao {
    Db_Helper dbHelper;
    private final static String TABLE_NAME = "Product";
    private final static String COLUMN_ID = "product_id";
    private final static String COLUMN_NAME = "product_name";
    private final static String COLUMN_PRICE = "product_price";
    private final static String COLUMN_CATEGORY_ID = "category_id";
    private final static String COLUMN_QUANTITY = "quantity";
    private final static String COLUMN_DESCRIBE = "describe";

    private final static String COLUMN_IMAGE = "image";

    public ProductDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_ID, product.getCategory_id());
        contentValues.put(COLUMN_NAME, product.getProduct_name());
        contentValues.put(COLUMN_PRICE, product.getProduct_price());
        contentValues.put(COLUMN_QUANTITY, product.getQuantity());
        contentValues.put(COLUMN_DESCRIBE, product.getDescribe());
        contentValues.put(COLUMN_IMAGE,product.getImage());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        product.setProduct_id((int) check);
        return check != -1;
    }
    public boolean deleteData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(product.getProduct_id())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(product.getProduct_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_ID, product.getCategory_id());
        contentValues.put(COLUMN_NAME, product.getProduct_name());
        contentValues.put(COLUMN_PRICE, product.getProduct_price());
        contentValues.put(COLUMN_QUANTITY, product.getQuantity());
        contentValues.put(COLUMN_DESCRIBE, product.getDescribe());
        contentValues.put(COLUMN_IMAGE,product.getImage());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public ArrayList<Product> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                String describe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIBE));
                int category_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
//                public Product(int product_id, int category_id, String product_name, int product_price, int quantity, String describe) {
                list.add(new Product(id,category_id,name,price,quantity,describe,image));
            }
        }
        return list;
    }

    public ArrayList<Product> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }

    public Product SelectID(String id) {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        ArrayList<Product> list = getAll(query, id);
        return list.get(0);
    }
}
