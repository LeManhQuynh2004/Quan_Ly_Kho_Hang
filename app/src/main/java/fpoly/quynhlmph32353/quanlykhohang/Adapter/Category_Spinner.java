package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Category_Spinner extends BaseAdapter {

    Context context;
    ArrayList<Category> list;

    public Category_Spinner(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
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
    private static class TheLoaiViewHolder {
        TextView txt_maLoai;
        TextView txt_tenSach;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        TheLoaiViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category_spinner, viewGroup, false);
            viewHolder = new TheLoaiViewHolder();
            viewHolder.txt_maLoai = convertView.findViewById(R.id.txt_Category_id_Spinner);
            viewHolder.txt_tenSach = convertView.findViewById(R.id.txt_Category_name_Spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TheLoaiViewHolder) convertView.getTag();
        }
        Category category = list.get(i);
        viewHolder.txt_maLoai.setText(String.valueOf(category.getCategory_id()));
        viewHolder.txt_tenSach.setText(category.getCategory_name());
        return convertView;
    }
}
