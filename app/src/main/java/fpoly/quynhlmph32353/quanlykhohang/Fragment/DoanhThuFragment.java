package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import fpoly.quynhlmph32353.quanlykhohang.Dao.ThongKeDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class DoanhThuFragment extends Fragment {

    View view;
    ThongKeDao thongKeDao;
    EditText edt_tuNgay, edt_denNgay;
    TextView txt_DoanhThu, tv_DoanhThuXuatKho, tv_DoanhThuNhapKho;
    ArrayList<Invoice_details> list = new ArrayList<>();
    public static final String TAG = "DoanhThu_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        edt_tuNgay = view.findViewById(R.id.edt_TuNgay);
        edt_denNgay = view.findViewById(R.id.edt_DenNgay);
//        txt_DoanhThu = view.findViewById(R.id.txt_DoanhThu);
        tv_DoanhThuNhapKho = view.findViewById(R.id.txt_DoanhThuNhapKho);
        tv_DoanhThuXuatKho = view.findViewById(R.id.txt_DoanhThuXuatKho);
        thongKeDao = new ThongKeDao(getContext());
        view.findViewById(R.id.btn_TuNgay).setOnClickListener(v -> {
            showDatePickerDialog(edt_tuNgay);
        });
        view.findViewById(R.id.btn_DenNgay).setOnClickListener(v -> {
            showDatePickerDialog(edt_denNgay);
        });
        view.findViewById(R.id.btn_find).setOnClickListener(v -> {
            String tuNgay = edt_tuNgay.getText().toString();
            String denNgay = edt_denNgay.getText().toString();

            if (!tuNgay.isEmpty() && !denNgay.isEmpty()) {
                list = thongKeDao.getDoanhThu(tuNgay, denNgay);
                int tong = 0, tongXuatkho = 0, tongNhapKho = 0;
                Log.d(TAG, "list.size: " + list.size());
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getInvoice_type() == 0) {
                        tongNhapKho += Integer.parseInt(String.valueOf(list.get(i).getPrice())) * Integer.parseInt(String.valueOf(list.get(i).getQuantity()));
                    }

                    if (list.get(i).getInvoice_type() == 1) {
                        tongXuatkho += Integer.parseInt(String.valueOf(list.get(i).getPrice())) * Integer.parseInt(String.valueOf(list.get(i).getQuantity()));
                    }
                }
//                txt_DoanhThu.setText(tong + " VND");
                tv_DoanhThuXuatKho.setText(tongXuatkho + " VND");
                tv_DoanhThuNhapKho.setText(tongNhapKho + " VND");
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ ngày", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        //Calender lấy ngày tháng hiện tại của máy tính
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //Date pickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
}