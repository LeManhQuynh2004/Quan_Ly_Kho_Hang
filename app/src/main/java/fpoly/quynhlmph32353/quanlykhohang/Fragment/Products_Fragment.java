package fpoly.quynhlmph32353.quanlykhohang.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Adapter.Category_Spinner;
import fpoly.quynhlmph32353.quanlykhohang.Adapter.Product_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Dao.CategoryDao;
import fpoly.quynhlmph32353.quanlykhohang.Dao.ProductDao;
import fpoly.quynhlmph32353.quanlykhohang.ItemClickListener;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Products_Fragment extends Fragment {
    View view;
    EditText ed_name, ed_price, ed_describe, ed_quantity;
    Spinner spinner_category;
    ImageView img_avt;
    ArrayList<Product> list = new ArrayList();
    CategoryDao categoryDao;
    ProductDao productDao;
    Product_Adapter productAdapter;
    Category_Spinner categorySpinner;
    private Uri imageUri;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ArrayList<Category> list_Category = new ArrayList<>();
    int selectedPosition;
    int category_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        productDao = new ProductDao(getContext());
        list = productDao.SelectAll();
        recyclerView = view.findViewById(R.id.RecyclerView_Product);
        recyclerView2 = view.findViewById(R.id.RecyclerView_Product2);
        productAdapter = new Product_Adapter(getContext(), list);
        Layout(recyclerView);
        Layout(recyclerView2);
        view.findViewById(R.id.fab_add_product).setOnClickListener(view1 -> {
            showAddOrUpdateDialog(0, null);
        });
        productAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                Product product = list.get(position);
                showAddOrUpdateDialog(1,product);
            }
        });
        return view;
    }

    private void Layout(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);
    }

    private void showAddOrUpdateDialog(int type, Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        ed_name = dialogView.findViewById(R.id.ed_product_name_dialog);
        ed_price = dialogView.findViewById(R.id.ed_product_price_dialog);
        ed_quantity = dialogView.findViewById(R.id.ed_product_quantity_dialog);
        ed_describe = dialogView.findViewById(R.id.ed_product_describe_dialog);

        spinner_category = dialogView.findViewById(R.id.spinner_product_dialog);
        img_avt = dialogView.findViewById(R.id.img_add_dialog_Product);

        categoryDao = new CategoryDao(getContext());
        list_Category = categoryDao.SelectAll();
        categorySpinner = new Category_Spinner(getContext(), list_Category);
        spinner_category.setAdapter(categorySpinner);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_id = list_Category.get(i).getCategory_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (type != 0) {
            ed_name.setText(product.getProduct_name());
            ed_price.setText(product.getProduct_price() + "");
            ed_describe.setText(product.getDescribe());
            ed_quantity.setText(product.getQuantity() + "");
            for (int i = 0; i < list_Category.size(); i++) {
                if (product.getCategory_id() == list_Category.get(i).getCategory_id()) {
                    selectedPosition = i;
                }
            }
            spinner_category.setSelection(selectedPosition);
            Glide.with(getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.loading)
                    .into(img_avt);
        }

        img_avt.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        dialogView.findViewById(R.id.btnSave_ql_Product).setOnClickListener(view1 -> {
            String name = ed_name.getText().toString().trim();
            String price = ed_price.getText().toString().trim();
            String describe = ed_describe.getText().toString().trim();
            String quantity = ed_quantity.getText().toString().trim();
            if (type == 0) {
                if (validateForm(name, price, describe, quantity) && imageUri != null) {
                    String imagePath = imageUri.toString();

                    Product productNew = new Product();
                    productNew.setProduct_name(name);
                    productNew.setQuantity(Integer.parseInt(quantity));
                    productNew.setProduct_price(Integer.parseInt(price));
                    productNew.setDescribe(describe);
                    productNew.setImage(imagePath);
                    productNew.setCategory_id(category_id);
                    if (productDao.insertData(productNew)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        list.add(productNew);
                        productAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (type == 1) {
                if(validateForm(name,price,describe,quantity)){
                    product.setProduct_name(name);
                    product.setProduct_price(Integer.parseInt(price));
                    product.setQuantity(Integer.parseInt(quantity));
                    product.setDescribe(describe);
                    product.setCategory_id(category_id);
                    if (imageUri != null) {
                        product.setImage(imageUri.toString());
                    } else {
                        product.setImage(product.getImage());
                    }

                    if(productDao.updateData(product)){
                        Toast.makeText(getContext(),R.string.update_success, Toast.LENGTH_SHORT).show();
                        updateUI();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(getContext(),R.string.update_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialogView.findViewById(R.id.btnCancle_ql_Product).setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validateForm(String name, String price, String describe, String quantity) {
        boolean isCheck = true;
        if (name.isEmpty() || price.isEmpty() || describe.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null) {
                        img_avt.setImageURI(imageUri);
                    }
                }
            }
    );
    private void updateUI() {
        list.clear();
        list.addAll(productDao.SelectAll());
        productAdapter.notifyDataSetChanged();
    }
}