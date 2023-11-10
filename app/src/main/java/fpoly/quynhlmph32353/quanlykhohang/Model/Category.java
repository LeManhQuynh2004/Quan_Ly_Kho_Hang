package fpoly.quynhlmph32353.quanlykhohang.Model;

public class Category {
    private int category_id;
    private String category_name;
    private String image;
    private String describe;

    public Category() {
    }

    public Category(int category_id, String category_name,String image,String describe) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.image = image;
        this.describe = describe;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
