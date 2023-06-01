package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class home_page extends AppCompatActivity {
    TextView name;
    Button logout;
    SharedPreferences sp;

    // for category -------------------------------------
    RecyclerView category_rv;
    String[] categoryarr = {"Grains", "Pulses", "Fruits", "Vegetables", "Dairy products", "Garam masala", "Dry fruits"};
    int[] categoryimg = {R.drawable.grains, R.drawable.pulses, R.drawable.fruits, R.drawable.vegetables, R.drawable.dairy_products, R.drawable.garam_masala, R.drawable.dry_fruits};

    // for category -------------------------------------

    // for products -------------------------------------
    RecyclerView product_rv;
    String[] productname = {"Wheat", "Mung Beans", "Grapes", "Milk", "Chilli", "Ice Cream"};
    int[] productimg = {R.drawable.wheat, R.drawable.mung_bean, R.drawable.grapes, R.drawable.milk, R.drawable.chilli, R.drawable.ice_cream};
    String[] productprice = {"40", "100", "150", "60", "70", "250"};
    String[] productunit = {"1 KG", "1 KG", "500 GM", "1 L", "250 GM", "500 GM"};
    ArrayList<ProductList> productArrayList;
    // for products -------------------------------------

    // for Trending products -------------------------------------
    RecyclerView producttrend_rv;
    String[] producttrendDescriptionArray = {
            "Bread flour and high-gluten flours are commonly made from hard red spring wheat. It is primarily traded on the Minneapolis Grain Exchange.",
            "\n" +
                    "Mung bean - Wikipedia\n" +
                    "Mung bean (Vigna radiata) is a plant species of Fabaceae which is also known as green gram. It is sometimes confused with black gram (Vigna mungo) for their similar morphology, though they are two different species. The green gram is an annual vine with yellow flowers and fuzzy brown pods.",
            "a smooth-skinned juicy light green or deep red to purplish black berry eaten dried or fresh as a fruit or fermented to produce wine. : any of numerous woody vines (genus Vitis of the family Vitaceae, the grape family) that usually climb by tendrils, produce grapes, and are nearly cosmopolitan in cultivation",
            "Milk is the liquid produced by the mammary glands of mammals, including humans. Breast milk is the preferred food for infants, as it is well-tolerated while their digestive tracts develop and mature. Dairy milk may be introduced at later ages if tolerated well.",
            " Chilli is the dried ripe fruit of the genus Capsicum. Capsicum annuum is an annual sub –shrub, the flowers of which are borne singly and fruits usually pendent, which provide red peppers, cayenne, paprika and chillies and sweet pepper (bell pepper) a mild form with large inflated fruits.",
            "Ice cream is a frozen dessert, typically made from milk or cream and flavoured with a sweetener, either sugar or an alternative, and a spice, such as cocoa or vanilla, or with fruit such as strawberries or peaches. It can also be made by whisking a flavored cream base and liquid nitrogen together."
    };
    String[] producttrendname = {"Wheat", "Mung Beans", "Grapes", "Milk", "Chilli", "Ice Cream"};
    int[] producttrendimg = {R.drawable.wheat, R.drawable.mung_bean, R.drawable.grapes, R.drawable.milk, R.drawable.chilli, R.drawable.ice_cream};
    String[] producttrendprice = {"40", "100", "150", "60", "70", "250"};
    String[] producttrendunit = {"1 KG", "1 KG", "500 GM", "1 L", "250 GM", "500 GM"};
    ArrayList<ProductList> producttrendArrayList;

    // for Trending products -------------------------------------

    String[] productDescriptionArray = {
            "Bread flour and high-gluten flours are commonly made from hard red spring wheat. It is primarily traded on the Minneapolis Grain Exchange.",
            "\n" +
                    "Mung bean - Wikipedia\n" +
                    "Mung bean (Vigna radiata) is a plant species of Fabaceae which is also known as green gram. It is sometimes confused with black gram (Vigna mungo) for their similar morphology, though they are two different species. The green gram is an annual vine with yellow flowers and fuzzy brown pods.",
            "a smooth-skinned juicy light green or deep red to purplish black berry eaten dried or fresh as a fruit or fermented to produce wine. : any of numerous woody vines (genus Vitis of the family Vitaceae, the grape family) that usually climb by tendrils, produce grapes, and are nearly cosmopolitan in cultivation",
            "Milk is the liquid produced by the mammary glands of mammals, including humans. Breast milk is the preferred food for infants, as it is well-tolerated while their digestive tracts develop and mature. Dairy milk may be introduced at later ages if tolerated well.",
            " Chilli is the dried ripe fruit of the genus Capsicum. Capsicum annuum is an annual sub –shrub, the flowers of which are borne singly and fruits usually pendent, which provide red peppers, cayenne, paprika and chillies and sweet pepper (bell pepper) a mild form with large inflated fruits.",
            "Ice cream is a frozen dessert, typically made from milk or cream and flavoured with a sweetener, either sugar or an alternative, and a spice, such as cocoa or vanilla, or with fruit such as strawberries or peaches. It can also be made by whisking a flavored cream base and liquid nitrogen together."
    };

    TextView productviewall;
    TextView trendingproductviewall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        name = findViewById(R.id.home_name);
        logout = findViewById(R.id.logout_but);

        sp = getSharedPreferences(constantdata.pref, MODE_PRIVATE);
        name.setText("Welcome " + sp.getString(constantdata.name, ""));

//        Bundle bundle = getIntent().getExtras();
//         name.setText(bundle.getString("NAME"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using remove u can remove single data
                // sp.edit().remove(constantdata.name).commit();
                //using clear you can remove all data
                sp.edit().clear().commit();
                new common(home_page.this, MainActivity.class);
            }
        });
        setcategorydata();
        setproductdaata();
        setproducttrenddata();

    }

    private void setproductdaata() {

        productviewall = findViewById(R.id.home_product_view_all);
        productviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new common(home_page.this, ProductActivity.class);
            }
        });
        product_rv = findViewById(R.id.home_product_recyclerview);
        product_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        product_rv.setItemAnimator(new DefaultItemAnimator());
        product_rv.setNestedScrollingEnabled(false);

        productArrayList = new ArrayList<>();
        for (int i = 0; i < productname.length; i++) {
            ProductList list = new ProductList();
            list.setName(productname[i]);
            list.setImage(productimg[i]);
            list.setPrice(productprice[i]);
            list.setUnit(productunit[i]);
            list.setDescription(productDescriptionArray[i]);

            productArrayList.add(list);
        }

        productAdapter prodAdapter = new productAdapter(home_page.this, productArrayList);
        product_rv.setAdapter(prodAdapter);
    }

    private void setproducttrenddata() {

        trendingproductviewall = findViewById(R.id.home_product_trending_view_all);
        trendingproductviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new common(home_page.this, TrendingProductActivity.class);
            }
        });

        producttrend_rv = findViewById(R.id.home_product_trending_recyclerview);
        producttrend_rv.setLayoutManager(new LinearLayoutManager(home_page.this));
        producttrend_rv.setItemAnimator(new DefaultItemAnimator());
        producttrend_rv.setNestedScrollingEnabled(false);

        producttrendArrayList = new ArrayList<>();
        for (int i = 0; i < producttrendname.length; i++) {
            ProductList list = new ProductList();
            list.setName(producttrendname[i]);
            list.setImage(producttrendimg[i]);
            list.setPrice(producttrendprice[i]);
            list.setUnit(producttrendunit[i]);
            list.setDescription(producttrendDescriptionArray[i]);

            producttrendArrayList.add(list);
        }

        producttrendAdapter prodAdapter = new producttrendAdapter(home_page.this, producttrendArrayList);
        producttrend_rv.setAdapter(prodAdapter);
    }

    private void setcategorydata() {
        category_rv = findViewById(R.id.home_category);
        category_rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        category_rv.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter catAdapter = new categoryAdapter(home_page.this, categoryarr, categoryimg);
        category_rv.setAdapter(catAdapter);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finishAffinity();
    }
}