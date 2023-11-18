package fpoly.quynhlmph32353.quanlykhohang.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhlmph32353.quanlykhohang.Model.Category;
import fpoly.quynhlmph32353.quanlykhohang.Model.Invoice;
import fpoly.quynhlmph32353.quanlykhohang.R;

public class Invoice_Spinner extends BaseAdapter {
    Context context;
    ArrayList<Invoice> list;
    public Invoice_Spinner(Context context, ArrayList<Invoice> list) {
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
    private static class InvoiceViewHolder {
        TextView tv_invoice_id;
        TextView tv_invoice_type;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        InvoiceViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_invoice_spinner, viewGroup, false);
            viewHolder = new InvoiceViewHolder();
            viewHolder.tv_invoice_id = convertView.findViewById(R.id.tv_id_Spinner_Invoice);
            viewHolder.tv_invoice_type = convertView.findViewById(R.id.tv_type_Spinner_Invoice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (InvoiceViewHolder) convertView.getTag();
        }
        Invoice invoice = list.get(i);
        Log.e("TAG", "getView: "+invoice.getInvoice_id());
        viewHolder.tv_invoice_id.setText(String.valueOf(invoice.getInvoice_id()));
        if(invoice.getInvoiceType() == 0){
            viewHolder.tv_invoice_type.setText("Phiếu nhập kho");
        }else{
            viewHolder.tv_invoice_type.setText("Phiếu xuất kho");
        }
        return convertView;
    }
}
