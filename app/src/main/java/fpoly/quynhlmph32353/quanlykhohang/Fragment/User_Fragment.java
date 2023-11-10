package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fpoly.quynhlmph32353.quanlykhohang.Dao.UserDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.User;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class User_Fragment extends Fragment {

    View view;
    UserDao userDao;
    TextView txt_username;

    String role_value;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        txt_username = view.findViewById(R.id.username_user);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");
        userDao = new UserDao(getContext());
        User user = userDao.SelectID(username);
        if(user.getRole() == 0){
            role_value = "Quản lý";
        }else{
            role_value = "Thủ Kho";
        }
        txt_username.setText(user.getFullName()+"("+role_value+")");
        view.findViewById(R.id.bt_themNguoiDung).setOnClickListener(view1 -> {
            ShowItem(new AddUser_Fragment());
        });
        view.findViewById(R.id.bt_doimatkhau).setOnClickListener(view1 -> {
            ShowItem(new UpdatePass_Fragment());
        });
        view.findViewById(R.id.bt_Thoat).setOnClickListener(view1 -> {
            requireActivity().finish();
        });
        return view;
    }
    private void ShowItem(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}