package fpoly.quynhlmph32353.quanlykhohang.Model;
public class Invoice_details {
    private int detail_id;
    private int invoice_id;
    private int product_id;
    private int quantity;
    private int price;

    public Invoice_details() {
    }

    public Invoice_details(int detail_id, int invoice_id, int product_id, int quantity, int price) {
        this.detail_id = detail_id;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
