package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.UserDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class AddUser_Fragment extends Fragment {
    View view;
    EditText ed_username, ed_password, ed_fullName, ed_email;
    Spinner spinner_role;
    UserDao userDao;
    TextView tv_logout,tv_back;
    String role_value;
    int role_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        ed_username = view.findViewById(R.id.ed_username_add);
        ed_password = view.findViewById(R.id.ed_password_add);
        ed_fullName = view.findViewById(R.id.ed_fullName_add);
        ed_email = view.findViewById(R.id.ed_email_add);
        tv_logout = view.findViewById(R.id.tv_logout);
        tv_back = view.findViewById(R.id.tv_back_addUser);
        tv_logout.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "Exit", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });
        tv_back.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new User_Fragment()).commit();
        });
        userDao = new UserDao(getContext());
        spinner_role = view.findViewById(R.id.spinner_role_add);

        ArrayList<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Thủ Kho");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);

        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), i + "", Toast.LENGTH_SHORT).show();
                role_position = i;
                role_value = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.findViewById(R.id.bt_signUp).setOnClickListener(v -> {
            String strUsername = ed_username.getText().toString().trim();
            String strPassword = ed_password.getText().toString().trim();
            String strFullName = ed_fullName.getText().toString().trim();
            String strEmail = ed_email.getText().toString().trim();
            int position = role_position;
            if (validateFrom(strUsername, strPassword, strEmail, strFullName)) {
                User user = new User(strUsername, strPassword, strFullName, strEmail, position);
                if (userDao.insertData(user)) {
                    Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                    clearFrom();
                } else {
                    Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private boolean validateFrom(String strUsername, String strPassword, String strEmail, String strFullName) {
        boolean isCheck = true;
        if (strUsername.isEmpty() || strPassword.isEmpty() || strFullName.isEmpty() || strEmail.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }

    private void clearFrom() {
        ed_username.setText("");
        ed_password.setText("");
        ed_email.setText("");
        ed_fullName.setText("");
    }
}