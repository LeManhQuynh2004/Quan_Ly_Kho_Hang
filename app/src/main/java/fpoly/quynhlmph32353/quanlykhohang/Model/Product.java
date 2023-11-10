package fpoly.quynhlmph32353.quanlykhohang.Model;

public class Product {
    private int product_id;
    private int category_id;
    private String product_name;
    private int product_price;
    private int quantity;
    private String describe;

    private String image;

    public Product() {
    }

    public Product(int product_id, int category_id, String product_name, int product_price, int quantity, String describe,String image) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.quantity = quantity;
        this.describe = describe;
        this.image = image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
