package fpoly.quynhlmph32353.quanlykhohang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.btNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Products_Fragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = item.getItemId();
                Fragment fragment = null;
                if (position == R.id.menu_home) {
                    fragment = new Products_Fragment();
                } else if (position == R.id.menu_category) {
                    fragment = new Category_Fragment();
                } else if (position == R.id.menu_invoice) {
                    fragment = new Invoice_Fragment();
                } else if (position == R.id.menu_details) {
                    fragment = new InvoiceDetails_Fragment();
                } else {
                    fragment = new User_Fragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
        });
    }
}