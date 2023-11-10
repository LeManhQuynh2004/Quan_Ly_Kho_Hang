package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.Call;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;

public class DetailsDao {
    Db_Helper dbHelper;
    public static final String TABLE_NAME = "Invoice_details";
    public static final String COLUMN_ID = "detail_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_INVOICE_ID = "invoice_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";

    ProductDao productDao;
    InvoiceDao invoiceDao;

    public DetailsDao(Context context) {
        dbHelper = new Db_Helper(context);
        productDao = new ProductDao(context);
        invoiceDao = new InvoiceDao(context);
    }

    public boolean insertData(Invoice_details details) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INVOICE_ID, details.getInvoice_id());
        contentValues.put(COLUMN_PRODUCT_ID, details.getProduct_id());
        contentValues.put(COLUMN_QUANTITY, details.getQuantity());
        contentValues.put(COLUMN_PRICE, details.getPrice());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        details.setDetail_id((int) check);
        return check != -1;
    }

    public boolean deleteData(Invoice_details details) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(details.getDetail_id())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Invoice_details details) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(details.getDetail_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INVOICE_ID, details.getInvoice_id());
        contentValues.put(COLUMN_PRODUCT_ID, details.getProduct_id());
        contentValues.put(COLUMN_QUANTITY, details.getQuantity());
        contentValues.put(COLUMN_PRICE, details.getPrice());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues,COLUMN_ID + "=?", dk);
        return check != -1;
    }
    public ArrayList<Invoice_details> getAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Invoice_details> list = new ArrayList<>();

        String query = "SELECT *" +
                "FROM Invoice_details " +
                "INNER JOIN Invoice ON Invoice_details.invoice_id = Invoice.invoice_id " +
                "INNER JOIN Product ON Invoice_details.product_id = Product.product_id";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int details_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));


                Invoice_details invoiceDetails = new Invoice_details();
                list.add(invoiceDetails);
            }
        }
        return list;
    }
}
