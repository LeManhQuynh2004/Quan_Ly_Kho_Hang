package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.CategoryDao;
import fpoly.quynhlmph32353.quanlykhohang.Fragment.Category_Fragment;
import fpoly.quynhlmph32353.quanlykhohang.ItemClickListener;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Category_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Category> list;
    CategoryDao categoryDao;
    Category_Fragment categoryFragment;
    private ItemClickListener itemClickListener;

    private static final String TAG = "TheLoai_Adapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public Category_Adapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
        categoryDao = new CategoryDao(context);
        categoryFragment = new Category_Fragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        TextView tv_id, tv_name, tv_describe;
        ImageView img_avt;

        tv_id = view.findViewById(R.id.tv_id_item_category);
        tv_name = view.findViewById(R.id.tv_name_item_category);
        tv_describe = view.findViewById(R.id.tv_describe_item_category);
        img_avt = view.findViewById(R.id.img_item_category);
        if (list.get(position) != null) {
            tv_id.setText(list.get(position).getCategory_id() + "");
            tv_name.setText(list.get(position).getCategory_name());
            tv_describe.setText(list.get(position).getDescribe());
            Glide.with(context)
                    .load(list.get(position).getImage())
                    .placeholder(R.drawable.loading)
                    .into(img_avt);
        }
        View finalView = view;
        view.setOnClickListener(view1 -> {
            PopupMenu popupMenu = new PopupMenu(context, finalView);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.menu_delete){
                    showDeleteDialog(position);
                }else{
                    try {
                        if (itemClickListener != null) {
                            itemClickListener.UpdateItem(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onBindViewHolder: " + e);
                    }
                }
                return false;
            });
            popupMenu.show();
        });
        return view;
    }
    public void showDeleteDialog(int position) {
        Category category = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + category.getCategory_name() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (categoryDao.deleteData(category)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(category);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
