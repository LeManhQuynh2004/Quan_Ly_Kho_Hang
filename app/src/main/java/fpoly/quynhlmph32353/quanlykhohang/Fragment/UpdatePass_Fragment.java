package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.quynhlmph32353.quanlykhohang.Dao.UserDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class UpdatePass_Fragment extends Fragment {

    View view;

    EditText ed_username, ed_password, ed_confirm_pass;

    CheckBox chkCheck;

    UserDao userDao;

    SharedPreferences sharedPreferences;

    private static final String TAG = "UpdatePass";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_pass, container, false);
        ed_username = view.findViewById(R.id.ed_username_UpdatePass);
        ed_password = view.findViewById(R.id.ed_password_UpdatePass);
        ed_confirm_pass = view.findViewById(R.id.ed_Confirm_password_UpdatePass);
        chkCheck = view.findViewById(R.id.chk_UpdatePass);
        userDao = new UserDao(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        view.findViewById(R.id.bt_updatePass).setOnClickListener(view1 -> {
            String username = ed_username.getText().toString().trim();
            String password = ed_password.getText().toString().trim();
            String confirm = ed_confirm_pass.getText().toString().trim();
            if (ValidateForm(username, password, confirm, chkCheck.isChecked())) {
                User user = userDao.SelectID(username);
                user.setPassword(password);
                if (userDao.updateData(user)) {
                    Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PASSWORD", password);
                    editor.apply();
                    clearFrom();
                } else {
                    Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean ValidateForm(String username, String password, String Confirm, Boolean isChecker) {
        boolean isCheck = true;
        if (username.isEmpty() || password.isEmpty() || Confirm.isEmpty() || isChecker.equals(false)) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else {
            String old_username = sharedPreferences.getString("USERNAME", "");
            if (!username.equals(old_username)) {
                Toast.makeText(getContext(), "Tên đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!password.equals(Confirm)) {
                Toast.makeText(getContext(), "Nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
        }
        return isCheck;
    }

    private void clearFrom() {
        ed_username.setText("");
        ed_password.setText("");
        ed_confirm_pass.setText("");
    }
}