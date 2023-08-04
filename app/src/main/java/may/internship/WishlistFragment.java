package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class  WishlistFragment extends Fragment {

    RecyclerView productTrendingRecyclerview;

    ArrayList<ProductList> productTrendingArrayList;

    SQLiteDatabase db;
    SharedPreferences sp;

    String[] productNameArray = {"Wheat", "Mung Beans", "Grapes", "Milk", "Chilli", "Ice Cream"};
    int[] productImageArray = {R.drawable.wheat, R.drawable.mung_bean, R.drawable.grapes, R.drawable.milk, R.drawable.chilli, R.drawable.ice_cream};
    String[] productPriceArray = {"40", "100", "150", "60", "70", "250"};
    String[] productUnitArray = {"1 KG", "1 KG", "500 GM", "1 L", "250 GM", "500 GM"};
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

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        sp = getActivity().getSharedPreferences(constantdata.PREF, MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("first_app", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        productTrendingRecyclerview = view.findViewById(R.id.wishlist_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        productTrendingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productTrendingRecyclerview.setNestedScrollingEnabled(false);

        String selectQuery = "SELECT * FROM WISHLIST WHERE CONTACT='" + sp.getString(constantdata.CONTACT, "") + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            productTrendingArrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                for (int i = 0; i < productNameArray.length; i++) {
                    if (cursor.getString(1).equalsIgnoreCase(productNameArray[i])) {
                        ProductList list = new ProductList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        productTrendingArrayList.add(list);
                    }
                }
            }
            WishlistAdapter prodAdapter = new WishlistAdapter(getActivity(), productTrendingArrayList);
            productTrendingRecyclerview.setAdapter(prodAdapter);
        }
        return view;
    }
    }