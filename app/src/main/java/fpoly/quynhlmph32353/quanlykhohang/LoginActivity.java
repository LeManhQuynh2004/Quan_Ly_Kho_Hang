package fpoly.quynhlmph32353.quanlykhohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.UserDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;

public class LoginActivity extends AppCompatActivity {

    EditText ed_username,ed_password;
    Spinner spinner_role;
    CheckBox chk_rememberPass;
    String role_value;
    int role_position;
    UserDao userDao;

    private boolean isLength(String str){
        return str.matches("[a-z0-9_-]{4,12}$");
    }

    //Kiem tra ky tu dac biet
    public boolean isChuoi(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        spinner_role = findViewById(R.id.spinner_role);
        chk_rememberPass = findViewById(R.id.chk_rememberPass);
        userDao = new UserDao(this);
        ArrayList<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Thủ Kho");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);
        ReadFile();
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                role_position = position;
                role_value = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findViewById(R.id.bt_login).setOnClickListener(view -> {
            String strUsername = ed_username.getText().toString().trim();
            String strPassword = ed_password.getText().toString().trim();
            if(validateFrom(strUsername,strPassword)){
                if(userDao.checkLogin(strUsername,strPassword,String.valueOf(role_position))){
                    User user = userDao.SelectID(strUsername);
                    if(user.getRole() == 0){
                        role_value = "admin";
                    }else{
                        role_value = "thukho";
                    }
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    rememberUser(strUsername, strPassword, chk_rememberPass.isChecked(), role_position);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("role", role_value);
                    intent.putExtra("username",strUsername);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, R.string.login_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void ReadFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        ed_username.setText(sharedPreferences.getString("USERNAME", ""));
        ed_password.setText(sharedPreferences.getString("PASSWORD", ""));
        chk_rememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        spinner_role.setSelection(sharedPreferences.getInt("ROLE", 0));
    }

    private void rememberUser(String strUserName, String strPassWord, boolean checked, int role_position) {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("USERNAME", strUserName);
            editor.putString("PASSWORD", strPassWord);
            editor.putBoolean("REMEMBER", checked);
            editor.putInt("ROLE", role_position);
        }
        editor.commit();
    }
    private boolean validateFrom(String strUsername, String strPassword) {
        boolean isCheck = true;
        if(strUsername.isEmpty() || strPassword.isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }else if(!isChuoi(strUsername) || !isChuoi(strPassword)){
            Toast.makeText(this, "Nhập sai định dạng , không có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        else if(!isLength(strUsername)){
            Toast.makeText(this, "Độ dài 6 đến 12 ký tự , không khoảng trắng và không dấu", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}