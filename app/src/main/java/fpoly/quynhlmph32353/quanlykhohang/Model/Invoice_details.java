package fpoly.quynhlmph32353.quanlykhohang.Model;
public class Invoice_details {
    private int detail_id;
    private int invoice_id;
    private int product_id;
    private int quantity;
    private int price;
    private String product_name;
    private String image;
    private String date;
    private int invoice_type;
    private String username;

    public Invoice_details() {
    }

    public Invoice_details(int detail_id, int invoice_id, int product_id, int quantity, int price) {
        this.detail_id = detail_id;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Invoice_details(int detail_id, int invoice_id, int product_id, int quantity, int price, String product_name, String image, String date, int invoice_type, String username) {
        this.detail_id = detail_id;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.product_name = product_name;
        this.image = image;
        this.date = date;
        this.invoice_type = invoice_type;
        this.username = username;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
