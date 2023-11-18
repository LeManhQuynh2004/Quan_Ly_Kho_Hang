package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Adapter.Details_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Adapter.Invoice_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Adapter.Product_Spinner;
import fpoly.quynhlmph32353.quanlykhohang.Dao.DetailsDao;
import fpoly.quynhlmph32353.quanlykhohang.Dao.InvoiceDao;
import fpoly.quynhlmph32353.quanlykhohang.Dao.ProductDao;
import fpoly.quynhlmph32353.quanlykhohang.ItemClickListener;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Invoice_Fragment extends Fragment {

    View view;
    RecyclerView recyclerView_input;
    RecyclerView recyclerView_output;
    ArrayList<Invoice> list_Input = new ArrayList();
    ArrayList<Invoice> list_Output = new ArrayList();
    ArrayList<Invoice> list = new ArrayList<>();
    Invoice_Adapter invoiceAdapter_input,invoiceAdapter_output,invoiceAdapter;
    InvoiceDao invoiceDao;
    EditText ed_number,ed_date;
    Spinner spinner_invoice;
    int position;
    String value;

    public static final String TAG = "Invoice_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_invoice, container, false);
        recyclerView_input = view.findViewById(R.id.RecyclerView_Invoice_Input);
        recyclerView_output = view.findViewById(R.id.RecyclerView_Invoice_Output);
        invoiceDao = new InvoiceDao(getContext());
        list = invoiceDao.SelectAll();
        invoiceAdapter = new Invoice_Adapter(getContext(),list);
        list_Input = invoiceDao.Select_Invoice_Input();
        list_Output = invoiceDao.Select_Invoice_Output();
        invoiceAdapter_input = new Invoice_Adapter(getContext(),list_Input);
        invoiceAdapter_output = new Invoice_Adapter(getContext(),list_Output);
        Layout(recyclerView_input,invoiceAdapter_input);
        Layout(recyclerView_output,invoiceAdapter_output);
        view.findViewById(R.id.fab_add_invoice).setOnClickListener(view1 -> {
            showAddOrUpdateDialog(0,null);
        });
        invoiceAdapter_input.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                Invoice invoice = list_Input.get(position);
                Log.e(TAG, "UpdateItem: "+list_Input.size());
                Log.e(TAG, "UpdateItem: "+list_Output.get(position).getInvoice_id());
                showAddOrUpdateDialog(1,invoice);
            }
        });
        invoiceAdapter_output.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                Invoice invoice = list_Output.get(position);
                Log.e(TAG, "UpdateItem: "+list_Output.get(position).getInvoice_id());
                showAddOrUpdateDialog(1,invoice);
            }
        });
        return view;
    }
    private void Layout(RecyclerView recyclerView,Invoice_Adapter invoiceAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(invoiceAdapter);
    }
    private void showAddOrUpdateDialog(int type, Invoice invoice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_invoice, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        ed_number = dialogView.findViewById(R.id.ed_invoice_number_dialog);
        ed_date = dialogView.findViewById(R.id.ed_create_date_dialog);
        spinner_invoice = dialogView.findViewById(R.id.Spinner_invoice_type_dialog);
        //Create spinner invoice_type
        ArrayList<String> listString = new ArrayList<>();
        listString.add("Phiếu nhập");
        listString.add("Phiếu xuất");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listString);
        spinner_invoice.setAdapter(adapter);

        spinner_invoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                value = listString.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(type != 0){
            ed_number.setText(invoice.getInvoiceNumber()+"");
            ed_date.setText(invoice.getDate());
            spinner_invoice.setSelection(invoice.getInvoiceType());
        }
        dialogView.findViewById(R.id.btnSave_ql_HoaDon).setOnClickListener(view1 -> {
            String number = ed_number.getText().toString();
            String date = ed_date.getText().toString();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LIST_USER", getContext().MODE_PRIVATE);
            String username = sharedPreferences.getString("USERNAME", "");

            if(type == 0){
                Invoice invoiceNew = new Invoice();
                invoiceNew.setInvoiceNumber(Integer.parseInt(number));
                invoiceNew.setInvoiceType(position);
                invoiceNew.setDate(date);
                invoiceNew.setUsername(username);
                if(invoiceDao.insertData(invoiceNew)){
                    Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                    list.add(invoiceNew);
                    updateAdapters();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                }
            }else{
                invoice.setUsername(username);
                invoice.setInvoiceNumber(Integer.parseInt(number));
                invoice.setInvoiceType(position);
                invoice.setDate(date);
                if(invoiceDao.updateData(invoice)){
                    Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                    updateAdapters();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(getContext(), R.string.update_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCancle_ql_HoaDon).setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    public void updateAdapters() {
        list.clear();
        list.addAll(invoiceDao.SelectAll());
        list_Output.clear();
        list_Output.addAll(invoiceDao.Select_Invoice_Output());
        list_Input.clear();
        list_Input.addAll(invoiceDao.Select_Invoice_Input());
        invoiceAdapter_input.notifyDataSetChanged();
        invoiceAdapter_output.notifyDataSetChanged();
        invoiceAdapter.notifyDataSetChanged();
    }
}