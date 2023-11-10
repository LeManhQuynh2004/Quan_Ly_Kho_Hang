package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Dao.InvoiceDao;
import fpoly.quynhlmph32353.quanlykhohang.ItemClickListener;
import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Invoice_Adapter extends RecyclerView.Adapter<Invoice_Adapter.InvoiceViewHolder>{
    Context context;
    ArrayList<Invoice> list;

    ItemClickListener itemClickListener;

    InvoiceDao invoiceDao;
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public Invoice_Adapter(Context context, ArrayList<Invoice> list) {
        this.context = context;
        this.list = list;
        invoiceDao = new InvoiceDao(context);
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invoice,parent,false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice invoice = list.get(position);
        holder.id.setText("Mã số: "+invoice.getInvoice_id());
        holder.number.setText(invoice.getInvoiceNumber()+"");
        if(invoice.getInvoiceType() == 0){
            holder.type.setText("Phiếu nhập kho");
            holder.type.setTextColor(Color.BLUE);
            holder.imgAvt.setImageResource(R.drawable.invoice2);
        }else{
            holder.type.setText("Phiếu xuất kho");
            holder.type.setTextColor(Color.RED);
            holder.imgAvt.setImageResource(R.drawable.invoice2);
        }
        holder.date.setText(invoice.getDate()+"");
        holder.imgDelete.setOnClickListener(view -> {
            showDeleteDialog(position);
        });
        holder.imgUpdate.setOnClickListener(view -> {
            ShowDialogUpdate(position);
        });
    }
    public void showDeleteDialog(int position) {
        Invoice invoice = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + invoice.getInvoice_id() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (invoiceDao.deleteData(invoice)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(invoice);
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
    private void ShowDialogUpdate(int position) {
        if (itemClickListener != null) {
            itemClickListener.UpdateItem(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InvoiceViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvt,imgDelete,imgUpdate;
        TextView id,number,type,date;
        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = itemView.findViewById(R.id.img_avt_item_Invoice);
            number = itemView.findViewById(R.id.tv_number_item_Invoice);
            type = itemView.findViewById(R.id.tv_type_item_Invoice);
            id = itemView.findViewById(R.id.tv_id_item_Invoice);
            date = itemView.findViewById(R.id.tv_date_item_Invoice);
            imgUpdate = itemView.findViewById(R.id.img_Update_Invoice);
            imgDelete = itemView.findViewById(R.id.img_Delete_Invoice);
        }
    }
}
