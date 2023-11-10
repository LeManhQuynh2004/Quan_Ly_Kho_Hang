package fpoly.quynhlmph32353.quanlykhohang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_Helper extends SQLiteOpenHelper {
    private final static String DATEBASE_NAME = "quanlykhohang.db";
    private final static int DATEBASE_VERSION = 2;

    public Db_Helper(@Nullable Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableCategory =
                "CREATE TABLE Category(" +
                        "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "image TEXT NOT NULL,"+
                        "describe TEXT NOT NULL,"+
                        "category_name TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableCategory);
        String CreateTableProduct =
                "CREATE TABLE Product(" +
                        "product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "category_id INTEGER NOT NULL," +
                        "product_name TEXT NOT NULL," +
                        "product_price INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "describe TEXT NOT NULL," +
                        "image TEXT NOT NULL,"+
                        "FOREIGN KEY(category_id) REFERENCES Category(category_id))";
        sqLiteDatabase.execSQL(CreateTableProduct);
        String CreateTableUser =
                "CREATE TABLE User (username TEXT PRIMARY KEY ," +
                        "password TEXT NOT NULL," +
                        "fullname TEXT NOT NULL," +
                        "role INTEGER NOT NULL," +
                        "email TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableUser);
        String insertDefaultUser = "INSERT INTO User (username, password, fullname ,email,role) VALUES ('admin', 'admin','lemanhquynh','quynhlm.dev@gmail.com',0)";
        sqLiteDatabase.execSQL(insertDefaultUser);
        String CreateTableInvoice =
                "CREATE TABLE Invoice(invoice_id INTEGER PRIMARY KEY," +
                        "username TEXT NOT NULL," +
                        "invoiceNumber INTEGER NOT NULL," +
                        "invoiceType INTEGER NOT NULL," +
                        "date TEXT NOT NULL," +
                        "FOREIGN KEY(username) REFERENCES User(username))";
        sqLiteDatabase.execSQL(CreateTableInvoice);
        String CreateTableInvoice_details =
                "CREATE TABLE Invoice_details(detail_id INTEGER PRIMARY KEY," +
                        "invoice_id INTEGER NOT NULL," +
                        "product_id INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "price INTEGER NOT NULL," +
                        "FOREIGN KEY(invoice_id) REFERENCES Invoice(invoice_id)," +
                        "FOREIGN KEY(product_id) REFERENCES Product(product_id))";
        sqLiteDatabase.execSQL(CreateTableInvoice_details);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Category");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Product");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Invoice");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Invoice_details");
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
