package may.internship;

public class ProductList {

    String name;
    String price;
    String unit;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }
    boolean isCart = false;
    boolean isWishlist = false;
    public boolean isCart() {return isCart;}
    public boolean isWishlist() {
        return isWishlist;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
