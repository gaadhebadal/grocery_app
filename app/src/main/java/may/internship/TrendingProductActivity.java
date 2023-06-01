package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TrendingProductActivity extends AppCompatActivity {
    RecyclerView producttrend_rv;
    String[] producttrendname={"Wheat","Mung Beans","Grapes","Milk","Chilli","Ice Cream","Wheat","Mung Beans","Grapes","Milk","Chilli","Ice Cream"};
    int[] producttrendimg={R.drawable.wheat,R.drawable.mung_bean,R.drawable.grapes,R.drawable.milk,R.drawable.chilli,R.drawable.ice_cream,R.drawable.wheat,R.drawable.mung_bean,R.drawable.grapes,R.drawable.milk,R.drawable.chilli,R.drawable.ice_cream};
    String[] producttrendprice={"40","100","150","60","70","250","40","100","150","60","70","250"};
    String[] producttrendunit={"1 KG","1 KG","500 GM","1 L","250 GM","500 GM","1 KG","1 KG","500 GM","1 L","250 GM","500 GM"};
    String[] producttrendDescriptionArray = {
            "Bread flour and high-gluten flours are commonly made from hard red spring wheat. It is primarily traded on the Minneapolis Grain Exchange.",
            "\n" +
                    "Mung bean - Wikipedia\n" +
                    "Mung bean (Vigna radiata) is a plant species of Fabaceae which is also known as green gram. It is sometimes confused with black gram (Vigna mungo) for their similar morphology, though they are two different species. The green gram is an annual vine with yellow flowers and fuzzy brown pods.",
            "a smooth-skinned juicy light green or deep red to purplish black berry eaten dried or fresh as a fruit or fermented to produce wine. : any of numerous woody vines (genus Vitis of the family Vitaceae, the grape family) that usually climb by tendrils, produce grapes, and are nearly cosmopolitan in cultivation",
            "Milk is the liquid produced by the mammary glands of mammals, including humans. Breast milk is the preferred food for infants, as it is well-tolerated while their digestive tracts develop and mature. Dairy milk may be introduced at later ages if tolerated well.",
            " Chilli is the dried ripe fruit of the genus Capsicum. Capsicum annuum is an annual sub –shrub, the flowers of which are borne singly and fruits usually pendent, which provide red peppers, cayenne, paprika and chillies and sweet pepper (bell pepper) a mild form with large inflated fruits.",
            "Ice cream is a frozen dessert, typically made from milk or cream and flavoured with a sweetener, either sugar or an alternative, and a spice, such as cocoa or vanilla, or with fruit such as strawberries or peaches. It can also be made by whisking a flavored cream base and liquid nitrogen together.",
            "Bread flour and high-gluten flours are commonly made from hard red spring wheat. It is primarily traded on the Minneapolis Grain Exchange.",
            "\n" +
                    "Mung bean - Wikipedia\n" +
                    "Mung bean (Vigna radiata) is a plant species of Fabaceae which is also known as green gram. It is sometimes confused with black gram (Vigna mungo) for their similar morphology, though they are two different species. The green gram is an annual vine with yellow flowers and fuzzy brown pods.",
            "a smooth-skinned juicy light green or deep red to purplish black berry eaten dried or fresh as a fruit or fermented to produce wine. : any of numerous woody vines (genus Vitis of the family Vitaceae, the grape family) that usually climb by tendrils, produce grapes, and are nearly cosmopolitan in cultivation",
            "Milk is the liquid produced by the mammary glands of mammals, including humans. Breast milk is the preferred food for infants, as it is well-tolerated while their digestive tracts develop and mature. Dairy milk may be introduced at later ages if tolerated well.",
            " Chilli is the dried ripe fruit of the genus Capsicum. Capsicum annuum is an annual sub –shrub, the flowers of which are borne singly and fruits usually pendent, which provide red peppers, cayenne, paprika and chillies and sweet pepper (bell pepper) a mild form with large inflated fruits.",
            "Ice cream is a frozen dessert, typically made from milk or cream and flavoured with a sweetener, either sugar or an alternative, and a spice, such as cocoa or vanilla, or with fruit such as strawberries or peaches. It can also be made by whisking a flavored cream base and liquid nitrogen together."
    };
    ArrayList<ProductList> producttrendArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_product);

        producttrend_rv=findViewById(R.id.trending_product_recyclerview);
        producttrend_rv.setLayoutManager(new LinearLayoutManager(TrendingProductActivity.this));
        producttrend_rv.setItemAnimator(new DefaultItemAnimator());
        producttrend_rv .setNestedScrollingEnabled(false);

        producttrendArrayList=new ArrayList<>();
        for (int i=0;i<producttrendname.length;i++){
            ProductList list=new ProductList();
            list.setName(producttrendname[i]);
            list.setImage(producttrendimg[i]);
            list.setPrice(producttrendprice[i]);
            list.setUnit(producttrendunit[i]);
            list.setDescription(producttrendDescriptionArray[i]);

            producttrendArrayList.add(list);
        }

        producttrendAdapter prodAdapter = new producttrendAdapter(TrendingProductActivity.this,producttrendArrayList);
        producttrend_rv.setAdapter(prodAdapter);
    }
}