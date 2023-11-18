package fpoly.quynhlmph32353.quanlykhohang.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Database.Db_Helper;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.Model.Top;

public class ThongKeDao {
    Db_Helper dbHelper;
    public static final String TAG = "DoanhThuDao";

    ProductDao productDao;

    public ThongKeDao(Context context) {
        dbHelper = new Db_Helper(context);
        productDao = new ProductDao(context);
    }

    public ArrayList<Invoice_details> getDoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Invoice_details> list = new ArrayList<>();
        String query = "SELECT " +
                "Invoice.invoice_id, " +
                "Invoice.username, " +
                "Invoice.date, " +
                "Invoice.invoiceType," +
                "Invoice_details.product_id, " +
                "Invoice_details.quantity AS invoice_quantity, " +
                "Invoice_details.price, " +
                "Invoice_details.detail_id, " +
                "Product.product_name, " +
                "Product.image " +
                "FROM Invoice_details " +
                "INNER JOIN Invoice ON Invoice_details.invoice_id = Invoice.invoice_id " +
                "INNER JOIN Product ON Invoice_details.product_id = Product.product_id " +
                "WHERE Invoice.date BETWEEN ? AND ?";

        String dk[] = {tuNgay, denNgay};
        Cursor cursor = sqLiteDatabase.rawQuery(query, dk);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int invoiceId = cursor.getInt(cursor.getColumnIndexOrThrow("invoice_id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int invoice_type = cursor.getInt(cursor.getColumnIndexOrThrow("invoiceType"));

                //product
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("invoice_quantity"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                int details_id = cursor.getInt(cursor.getColumnIndexOrThrow("detail_id"));

                Invoice_details invoiceDetails = new Invoice_details();
                invoiceDetails.setDetail_id(details_id);
                invoiceDetails.setInvoice_id(invoiceId);
                invoiceDetails.setUsername(username);
                invoiceDetails.setDate(date);
                invoiceDetails.setProduct_id(productId);
                invoiceDetails.setQuantity(quantity);
                invoiceDetails.setPrice(price);
                invoiceDetails.setImage(image);
                invoiceDetails.setInvoice_type(invoice_type);
                invoiceDetails.setProduct_name(productName);
                list.add(invoiceDetails);
            }
        }
        return list;
    }

    public ArrayList<Top> getTop() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Top> list = new ArrayList<>();
        String query = "SELECT " +
                "Invoice.invoice_id, " +
                "Invoice.username, " +
                "Invoice.date, " +
                "Invoice.invoiceType," +
                "Invoice_details.quantity AS invoice_quantity, " +
                "Invoice_details.price, " +
                "Invoice_details.detail_id, " +
                "Product.product_name, " +
                "Product.image ," +
                "Invoice_details.product_id, " +
                "COUNT(Invoice_details.product_id) AS soLuong " +
                "FROM Invoice_details " +
                "INNER JOIN Invoice ON Invoice_details.invoice_id = Invoice.invoice_id " +
                "INNER JOIN Product ON Invoice_details.product_id = Product.product_id " +
                "GROUP BY Invoice_details.product_id ORDER BY soLuong DESC LIMIT 10";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int invoiceId = cursor.getInt(cursor.getColumnIndexOrThrow("invoice_id"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("soLuong"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("invoice_quantity"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));
                int details_id = cursor.getInt(cursor.getColumnIndexOrThrow("detail_id"));

                Top top = new Top();
                top.setDetail_id(details_id);
                top.setInvoice_id(invoiceId);
                top.setProduct_id(productId);
                top.setQuantity(quantity);
                top.setPrice(price);
                top.setSoLuong(soLuong);
                list.add(top);
            }
        }
        return list;
    }
}
