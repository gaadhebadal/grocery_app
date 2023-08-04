package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    public static int iTotalAmount = 0;
    public static TextView totalAmount;
    public static Button checkout;
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
    ArrayList<CartList> productArrayList;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        sp = getActivity().getSharedPreferences(constantdata.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("first_app",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

            recyclerView = view.findViewById(R.id.cart_recyclerview);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);


        String selectQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(constantdata.CONTACT,"")+"' AND ORDERID='0'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            productArrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                for(int i =0 ; i<productNameArray.length;i++){
                    if(cursor.getString(2).equalsIgnoreCase(productNameArray[i])){
                        CartList list = new CartList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        list.setQty(cursor.getString(3));
                        iTotalAmount += Integer.parseInt(productPriceArray[i]);
                        productArrayList.add(list);
                    }
                }
            }
            CartAdapter prodAdapter = new CartAdapter(getActivity(), productArrayList);
            recyclerView.setAdapter(prodAdapter);
        }
        else{
            iTotalAmount=0;
        }

        totalAmount = view.findViewById(R.id.cart_total);
        checkout = view.findViewById(R.id.cart_checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(constantdata.CART_TOTAL, String.valueOf(iTotalAmount)).commit();
//                new common(getActivity(),ShippingActivity.class);
            }
        });

        totalAmount.setText("Total : "+constantdata.PRICE_SYMBOL+iTotalAmount);

        if(iTotalAmount>0){
            totalAmount.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.VISIBLE);
        }
        else{
            totalAmount.setVisibility(View.GONE);
            checkout.setVisibility(View.GONE);
        }

        return view;

    }
}