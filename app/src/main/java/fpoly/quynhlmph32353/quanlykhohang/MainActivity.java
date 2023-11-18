package fpoly.quynhlmph32353.quanlykhohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import fpoly.quynhlmph32353.quanlykhohang.Fragment.AddUser_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.Category_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.InvoiceDetails_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.Invoice_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.Products_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.UpdatePass_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.User_Fragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView tv_title,tv_back;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Chào mừng đến với trang chủ", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);
        tv_title = findViewById(R.id.tv_title);
        tv_back = findViewById(R.id.tv_back);
        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        tv_back.setOnClickListener(v ->{
            finish();
        });
        bottomNavigationView = findViewById(R.id.btNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Products_Fragment()).commit();
        tv_title.setText("Sản phẩm");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                Fragment fragment = null;
                if (position == R.id.menu_home) {
                    fragment = new Products_Fragment();
                    tv_title.setText("Sản phẩm");
                } else if (position == R.id.menu_category) {
                    fragment = new Category_Fragment();
                    tv_title.setText("Thể loại");
                } else if (position == R.id.menu_invoice) {
                    fragment = new Invoice_Fragment();
                    tv_title.setText("Hóa đơn");
                } else if (position == R.id.menu_details) {
                    fragment = new InvoiceDetails_Fragment();
                    tv_title.setText("Chi tiết");
                } else {
                    fragment = new User_Fragment();
                    tv_title.setText("Cá nhân");
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        String role =  intent.getStringExtra("role");
        if(role.equals("admin")){
            ShowMenuAdmin(menu);
        }else{
            ShowMenuThuKho(menu);
        }
        return true;
    }

    private void ShowMenuAdmin(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
    }
    private void ShowMenuThuKho(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_add_user){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddUser_Fragment()).commit();
            tv_title.setText("Thêm");
        }
        return true;
    }
}