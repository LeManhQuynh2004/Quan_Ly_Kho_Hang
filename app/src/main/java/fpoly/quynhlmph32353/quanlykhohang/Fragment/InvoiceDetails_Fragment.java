package fpoly.quynhlmph32353.quanlykhohang.Fragment;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Adapter.Details_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Adapter.Invoice_Spinner;
import fpoly.quynhlmph32353.quanlykhohang.Adapter.Product_Spinner;
import fpoly.quynhlmph32353.quanlykhohang.Dao.DetailsDao;
import fpoly.quynhlmph32353.quanlykhohang.Dao.InvoiceDao;
import fpoly.quynhlmph32353.quanlykhohang.Dao.ProductDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;


public class InvoiceDetails_Fragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    Spinner spinner_invoice, spinner_product;
    Invoice_Spinner invoiceSpinner;
    Product_Spinner productSpinner;
    ArrayList<Invoice> list_invoice = new ArrayList<>();
    ArrayList<Invoice_details> list_detail = new ArrayList<>();
    ArrayList<Product> list_product = new ArrayList<>();
    ProductDao productDao;
    InvoiceDao invoiceDao;
    EditText ed_quantity;
    DetailsDao detailsDao;
    int product_id, invoice_id, price;

    Details_Adapter detailsAdapter;
    public static final String TAG  = "InvoiceDetails_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_invoice_details, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_Details);
        detailsDao = new DetailsDao(getContext());
        list_detail = detailsDao.getAll();
        detailsAdapter = new Details_Adapter(list_detail,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(detailsAdapter);
        view.findViewById(R.id.fab_add_details).setOnClickListener(view1 -> {
            ShowDialogAddOrUpdate(0, null);
        });
        return view;
    }

    private void ShowDialogAddOrUpdate(int type, Invoice_details invoiceDetails) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_detail, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        //Anh xa
        spinner_invoice = dialogView.findViewById(R.id.spinner_invoice);
        spinner_product = dialogView.findViewById(R.id.spinner_product);
        ed_quantity = dialogView.findViewById(R.id.ed_quantity_dialog_detail);

        //create spinner invoice
        invoiceDao = new InvoiceDao(getContext());
        list_invoice = invoiceDao.SelectAll();
        Log.e("TAG", "ShowDialogAddOrUpdate: " + list_invoice.size());
        invoiceSpinner = new Invoice_Spinner(getContext(), list_invoice);
        spinner_invoice.setAdapter(invoiceSpinner);

        spinner_invoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                invoice_id = list_invoice.get(i).getInvoice_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //create spinner product
        productDao = new ProductDao(getContext());
        list_product = productDao.SelectAll();
        productSpinner = new Product_Spinner(getContext(), list_product);
        spinner_product.setAdapter(productSpinner);
        spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                product_id = list_product.get(i).getProduct_id();
                price = list_product.get(i).getProduct_price();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialogView.findViewById(R.id.btnSave_ql_Detail).setOnClickListener(view1 -> {
            Product product = productDao.SelectID(String.valueOf(product_id));
            String quantity = ed_quantity.getText().toString();
            Invoice invoice = invoiceDao.Select_ID(String.valueOf(invoice_id));
            int quantity_product = product.getQuantity();
            if(invoice.getInvoiceType() == 1){//Phieu xuat kho
                if(Integer.parseInt(quantity) > quantity_product){
                    Toast.makeText(getContext(), "Vượt quá số lượng có trong kho", Toast.LENGTH_SHORT).show();
                }else{
                    if (type == 0) {
                        Invoice_details invoiceDetailsNew = new Invoice_details();
                        invoiceDetailsNew.setInvoice_id(invoice_id);
                        invoiceDetailsNew.setProduct_id(product_id);
                        invoiceDetailsNew.setPrice(price);
                        invoiceDetailsNew.setQuantity(Integer.parseInt(quantity));
                        if (detailsDao.insertData(invoiceDetailsNew)) {
                            Log.e(TAG, "InvoiceDetails_Fragment: "+invoiceDetailsNew.getDetail_id());
                            Toast.makeText(getContext(),
                                    R.string.add_success, Toast.LENGTH_SHORT).show();
                            UpdateRecyclerView();
                            product.setQuantity(quantity_product - Integer.parseInt(quantity));
                            productDao.updateData(product);
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(),
                                    R.string.add_not_success, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        invoiceDetails.setInvoice_id(invoice_id);
                        invoiceDetails.setProduct_id(product_id);
                        invoiceDetails.setQuantity(Integer.parseInt(quantity));
                        invoiceDetails.setPrice(price);
                        if(detailsDao.updateData(invoiceDetails)){
                            Toast.makeText(getContext(),
                                    R.string.update_success, Toast.LENGTH_SHORT).show();
                            UpdateRecyclerView();
                        }else{
                            Toast.makeText(getContext(),
                                    R.string.update_not_success, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }else{// khi phiếu nhập kho
                if (type == 0) {
                    Invoice_details invoiceDetailsNew = new Invoice_details();
                    invoiceDetailsNew.setInvoice_id(invoice_id);
                    invoiceDetailsNew.setProduct_id(product_id);
                    invoiceDetailsNew.setPrice(price);
                    invoiceDetailsNew.setQuantity(Integer.parseInt(quantity));
                    if (detailsDao.insertData(invoiceDetailsNew)) {
                        Toast.makeText(getContext(),
                                R.string.add_success, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "InvoiceDetails_Fragment: "+invoiceDetailsNew.getDetail_id());
                        UpdateRecyclerView();
                        product.setQuantity(quantity_product + Integer.parseInt(quantity));
                        productDao.updateData(product);
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(),
                                R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    invoiceDetails.setInvoice_id(invoice_id);
                    invoiceDetails.setProduct_id(product_id);
                    invoiceDetails.setQuantity(Integer.parseInt(quantity));
                    invoiceDetails.setPrice(price);
                    if(detailsDao.updateData(invoiceDetails)){
                        Toast.makeText(getContext(),
                                R.string.update_success, Toast.LENGTH_SHORT).show();
                        UpdateRecyclerView();
                    }else{
                        Toast.makeText(getContext(),
                                R.string.update_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialogView.findViewById(R.id.btnCancle_ql_Detail).setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    private void UpdateRecyclerView() {
        list_detail.clear();
        list_detail.addAll(detailsDao.getAll());
        detailsAdapter.notifyDataSetChanged();
    }
}