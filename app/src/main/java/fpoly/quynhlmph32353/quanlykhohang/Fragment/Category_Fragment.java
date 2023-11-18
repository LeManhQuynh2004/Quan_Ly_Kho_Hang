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

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Adapter.Category_Adapter;
import fpoly.quynhlmph32353.quanlykhohang.Dao.CategoryDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Category_Fragment extends Fragment {
    private static final String TAG = "Category_Fragment";
    private View view;
    private EditText ed_name, ed_describe;
    private ImageView img_avt;
    private Uri imageUri;
    private CategoryDao categoryDao;
    private ListView listView_category;
    private Category_Adapter categoryAdapter;
    private ArrayList<Category> list = new ArrayList<>();

    private boolean isString(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryDao = new CategoryDao(getContext());

        listView_category = view.findViewById(R.id.ListView_Category);
        list = categoryDao.SelectAll();
        categoryAdapter = new Category_Adapter(getContext(), list);
        listView_category.setAdapter(categoryAdapter);

        view.findViewById(R.id.fab_add_category).setOnClickListener(v -> showAddOrUpdateDialog(0, null));

        categoryAdapter.setItemClickListener(position -> {
            Category category = list.get(position);
            showAddOrUpdateDialog(1, category);
        });
        return view;
    }

    private void showAddOrUpdateDialog(int type, Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_category, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        ed_name = dialogView.findViewById(R.id.ed_category_name);
        ed_describe = dialogView.findViewById(R.id.ed_category_describe);
        img_avt = dialogView.findViewById(R.id.img_add_dialog_Category);

        if (type != 0) {
            ed_name.setText(category.getCategory_name());
            ed_describe.setText(category.getDescribe());
            Log.d(TAG, "showAddOrUpdateDialog: " + category.getImage());
            Glide.with(getContext())
                    .load(category.getImage())
                    .placeholder(R.drawable.loading)
                    .into(img_avt);
        }
        img_avt.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        dialogView.findViewById(R.id.btnSave_ql_TheLoai).setOnClickListener(view1 -> {
            String name = ed_name.getText().toString().trim();
            String describe = ed_describe.getText().toString().trim();
            //Update không đi qua được validateFrom();
            if (type == 0) {
                if (validateForm(name, describe, imageUri)) {//imageUri == null - Khi chưa được gọi đến
                    String imagePath = imageUri.toString();
                    Category categoryNew = new Category();
                    categoryNew.setCategory_name(name);
                    categoryNew.setDescribe(describe);
                    categoryNew.setImage(imagePath);
                    if (categoryDao.insertData(categoryNew)) {
                        Toast.makeText(getContext(), R.string.add_success, Toast.LENGTH_SHORT).show();
                        list.add(categoryNew);
                        categoryAdapter.notifyDataSetChanged();
                        imageUri = null;
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (type == 1) {
                if (name.isEmpty() || describe.isEmpty()) {
                    Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    category.setCategory_name(name);
                    category.setDescribe(describe);
                    if (imageUri != null) {
                        category.setImage(imageUri.toString());
                    } else {
                        category.setImage(category.getImage());
                    }
                    if (categoryDao.updateData(category)) {
                        Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        updateUI();
                    } else {
                        Toast.makeText(getContext(), R.string.update_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialogView.findViewById(R.id.btnCancle_ql_TheLoai).setOnClickListener(view1 -> alertDialog.dismiss());

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validateForm(String name, String describe, Uri imgUri) {
        boolean isCheck = true;
        try {
            if (name.isEmpty() || describe.isEmpty() || imgUri == null) {
                Toast.makeText(getContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
            if (!isString(name) || !isString(describe)) {
                Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
                isCheck = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isCheck = false;
        }
        return isCheck;
    }

    private void updateUI() {
        list.clear();
        list.addAll(categoryDao.SelectAll());
        categoryAdapter.notifyDataSetChanged();
    }

    public final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
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
}
