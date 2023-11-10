package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;

public class InvoiceDao {
    Db_Helper dbHelper;

    public static final String TABLE_NAME = "Invoice";
    public static final String COLUMN_ID = "invoice_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NUMBER = "invoiceNumber";
    public static final String COLUMN_TYPE = "invoiceType";
    public static final String COLUMN_DATE = "date";

    public InvoiceDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Invoice invoice) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NUMBER, invoice.getInvoiceNumber());
        contentValues.put(COLUMN_USERNAME, invoice.getUsername());
        contentValues.put(COLUMN_TYPE, invoice.getInvoiceType());
        contentValues.put(COLUMN_DATE, invoice.getDate());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        invoice.setInvoice_id((int) check);
        return check != -1;
    }

    public boolean deleteData(Invoice invoice) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(invoice.getInvoice_id())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Invoice invoice) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(invoice.getInvoice_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NUMBER, invoice.getInvoiceNumber());
        contentValues.put(COLUMN_USERNAME, invoice.getUsername());
        contentValues.put(COLUMN_TYPE, invoice.getInvoiceType());
        contentValues.put(COLUMN_DATE, invoice.getDate());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public ArrayList<Invoice> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Invoice> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int number = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                list.add(new Invoice(id, username, number, type, date));
            }
        }
        return list;
    }

    public ArrayList<Invoice> SelectAll(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        return getAll(query);
    }

    public ArrayList<Invoice> Select_Invoice_Input() {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + "= 0";
        return getAll(query);
    }

    public ArrayList<Invoice> Select_Invoice_Output() {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + "= 1";
        return getAll(query);
    }
    public Invoice Select_ID(String id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=?";
        ArrayList<Invoice> list = getAll(query, id);
        return list.get(0);
    }
}
