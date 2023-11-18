package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.DetailsDao;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice_details;
import fpoly.quynhlmph32353.quanlykhohang.Model.Product;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Details_Adapter extends RecyclerView.Adapter<Details_Adapter.Details_ViewHolder> {
    ArrayList<Invoice_details> list;
    Context context;
    DetailsDao detailsDao;
    public static final String TAG  = "Details_Adapter";

    public Details_Adapter(ArrayList<Invoice_details> list, Context context) {
        this.list = list;
        this.context = context;
        detailsDao = new DetailsDao(context);
    }

    @NonNull
    @Override
    public Details_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_cteate_invoice, parent, false);
        return new Details_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Details_ViewHolder holder, int position) {
        Invoice_details invoiceDetails = list.get(position);
        holder.tv_name.setText("Tên sản phẩm :"+invoiceDetails.getProduct_name());
        if(invoiceDetails.getInvoice_type() == 0){
            holder.tv_type.setText("Phiếu nhấp kho");
            holder.tv_type.setTextColor(Color.RED);
        }else{
            holder.tv_type.setText("Phiếu xuất kho");
            holder.tv_type.setTextColor(Color.BLUE);
        }
        Log.d("TAG", "onBindViewHolder: "+invoiceDetails.getImage());
        Glide.with(context)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.loading)
                .into(holder.img_avt);
        holder.tv_price.setText("Giá :"+invoiceDetails.getPrice());
        holder.tv_quantity.setText("Số lượng :"+invoiceDetails.getQuantity()+"");
        int sumPrice = Integer.parseInt(String.valueOf(invoiceDetails.getPrice()))* Integer.parseInt(String.valueOf(invoiceDetails.getQuantity()));
        holder.tv_sumPrice.setText("Tổng tiền :"+sumPrice);
        holder.tv_sumPrice.setTextColor(Color.RED);
        holder.tv_date.setText(invoiceDetails.getDate());

        holder.itemView.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.menu_delete){
                    showDeleteDialog(invoiceDetails);
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void showDeleteDialog(Invoice_details invoiceDetails) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + invoiceDetails.getDetail_id() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (detailsDao.deleteData(invoiceDetails)) {
                        Log.e(TAG, "Details_Adapter: "+invoiceDetails.getDetail_id());
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(invoiceDetails);
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

    class Details_ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_type, tv_quantity, tv_price, tv_sumPrice, tv_date;
        ImageView img_avt;

        public Details_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_item_detail);
            tv_date = itemView.findViewById(R.id.tv_date_item_detail);
            tv_price = itemView.findViewById(R.id.tv_price_item_detail);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_item_detail);
            tv_type = itemView.findViewById(R.id.tv_status_item_detail);
            tv_sumPrice = itemView.findViewById(R.id.tv_sum_price_item_detail);
            img_avt  = itemView.findViewById(R.id.img_avt_item_detail);
        }
    }
}
