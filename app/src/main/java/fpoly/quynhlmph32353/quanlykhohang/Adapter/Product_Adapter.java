package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.ProductDao;
import fpoly.quynhlmph32353.quanlykhohang.ItemClickListener;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ProductViewHolder>{
    Context context;
    ArrayList<Product> list;
    ProductDao productDao;

    ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public Product_Adapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        productDao = new ProductDao(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = list.get(position);
        holder.tv_name.setText(product.getProduct_name());
        holder.tv_price.setText("$"+product.getProduct_price());
        holder.tv_quantity.setText("Số lượng :"+product.getQuantity());
        Glide.with(context)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.loading)
                .into(holder.img_avt);
        if(product.getQuantity() <= 0){
           holder.tv_quantity.setText("Hết hàng");
           holder.tv_quantity.setTextColor(Color.RED);
        }
        holder.img_Delete.setOnClickListener(view -> {
            ShowDialogDelete(position);
        });
        holder.img_Update.setOnClickListener(view -> {
            ShowDialogUpdate(position);
        });
    }

    private void ShowDialogUpdate(int position) {
        if (itemClickListener != null) {
            itemClickListener.UpdateItem(position);
        }
    }

    private void ShowDialogDelete(int position) {
        Product product = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + product.getProduct_name() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (productDao.deleteData(product)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(product);
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_price,tv_quantity;
        ImageView img_avt,img_Delete,img_Update;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_item_product);
            tv_price = itemView.findViewById(R.id.tv_price_item_product);
            img_avt = itemView.findViewById(R.id.img_avt_item_product);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_item_product);
            img_Update = itemView.findViewById(R.id.img_Update_Product);
            img_Delete = itemView.findViewById(R.id.img_Delete_Product);
        }
    }
}
