package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Adapter.Top_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Dao.ThongKeDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Top;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Top_Fragment extends Fragment {

    View view;
    ListView listView;
    ThongKeDao thongKeDao;
    Top_Adapter topAdapter;

    public static final String TAG = "Top_Fragment";
    ArrayList<Top> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top_, container, false);
        thongKeDao = new ThongKeDao(getContext());
        listView = view.findViewById(R.id.ListView_Top);
        list = thongKeDao.getTop();
        topAdapter = new Top_Adapter(getContext(),list);
        listView.setAdapter(topAdapter);
        return view;
    }
}