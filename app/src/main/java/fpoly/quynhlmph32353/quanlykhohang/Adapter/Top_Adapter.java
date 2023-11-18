package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.ProductDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.Model.Top;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Top_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Top> list;

    ProductDao productDao;

    public Top_Adapter(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
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

    private class Top10ViewHolder {
        TextView tv_name, tv_quantity, tv_price;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Top10ViewHolder top10ViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_topproduct, viewGroup, false);
            top10ViewHolder = new Top10ViewHolder();
            top10ViewHolder.tv_name = view.findViewById(R.id.tv_name_Top);
            top10ViewHolder.tv_quantity = view.findViewById(R.id.tv_price_Top);
            top10ViewHolder.tv_price = view.findViewById(R.id.tv_quantity_Top);
            view.setTag(top10ViewHolder);
        } else {
            top10ViewHolder = (Top10ViewHolder) view.getTag();
        }
        Top top = list.get(i);
        if (top != null) {
            Product product = productDao.SelectID(String.valueOf(top.getProduct_id()));
            top10ViewHolder.tv_name.setText("" + product.getProduct_name());
            top10ViewHolder.tv_quantity.setText("" + top.getSoLuong());
            top10ViewHolder.tv_price.setText("" + top.getPrice());
        }
        return null;
    }
}
