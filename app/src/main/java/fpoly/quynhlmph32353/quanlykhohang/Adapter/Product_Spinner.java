package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Product_Spinner extends BaseAdapter {

    Context context;
    ArrayList<Product> list;

    public Product_Spinner(Context context, ArrayList<Product> list) {
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
    private static class ProductViewHolder {
        TextView tv_product_id;
        TextView tv_product_name;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ProductViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_spinner, viewGroup, false);
            viewHolder = new ProductViewHolder();
            viewHolder.tv_product_id = convertView.findViewById(R.id.tv_id_Spinner_Product);
            viewHolder.tv_product_name = convertView.findViewById(R.id.tv_name_Spinner_Product);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductViewHolder) convertView.getTag();
        }
        Product product = list.get(i);
        viewHolder.tv_product_id.setText(String.valueOf(product.getProduct_id()));
        viewHolder.tv_product_name.setText(product.getProduct_name());
        return convertView;
    }
}
