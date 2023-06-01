package may.internship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class producttrendAdapter extends RecyclerView.Adapter<producttrendAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> productArrayList;
    SharedPreferences sp;

    public producttrendAdapter(Context context, ArrayList<ProductList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(constantdata.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public producttrendAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_trending, parent, false);
        return new producttrendAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_product_trending_image);
            name = itemView.findViewById(R.id.custom_product_trending_name);
            price = itemView.findViewById(R.id.custom_product_trending_price);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull producttrendAdapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(productArrayList.get(position).getName());
        holder.price.setText(constantdata.PRICE_SYMBOL + productArrayList.get(position).getPrice() + "/" + productArrayList.get(position).getUnit());
        holder.imageView.setImageResource(productArrayList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(constantdata.PRODUCT_NAME,productArrayList.get(position).getName()).commit();
                sp.edit().putString(constantdata.PRODUCT_PRICE,productArrayList.get(position).getPrice()).commit();
                sp.edit().putString(constantdata.PRODUCT_UNIT,productArrayList.get(position).getUnit()).commit();
                sp.edit().putString(constantdata.PRODUCT_IMAGE, String.valueOf(productArrayList.get(position).getImage())).commit();
              //  sp.edit().putString(ConstantData.PRODUCT_DESCRIPTION,productArrayList.get(position).()).commit();
            //    new common(context,ProductDetailActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
