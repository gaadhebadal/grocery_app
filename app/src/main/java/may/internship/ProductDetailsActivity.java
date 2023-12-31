package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name,price,description;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        sp = getSharedPreferences(constantdata.PREF,MODE_PRIVATE);

        imageView = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);

        name.setText(sp.getString(constantdata.PRODUCT_NAME,""));
        price.setText(constantdata.PRICE_SYMBOL+sp.getString(constantdata.PRODUCT_PRICE,"")+"/"+sp.getString(constantdata.PRODUCT_UNIT,""));
        imageView.setImageResource(Integer.parseInt(sp.getString(constantdata.PRODUCT_IMAGE,"")));
        description.setText(sp.getString(constantdata.PRODUCT_DESCRIPTION,""));
    }
}