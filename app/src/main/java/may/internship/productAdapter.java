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

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyHolder> {
    Context context;
    ArrayList<ProductList> productArrayList;
    SharedPreferences sp ;
    public productAdapter(Context context, ArrayList<ProductList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;

        sp=context.getSharedPreferences(constantdata.pref,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new MyHolder(view);
    }
    public class MyHolder extends  RecyclerView.ViewHolder {
        TextView name,price;
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.custom_product_image);
            name=itemView.findViewById(R.id.custom_product_name);
            price=itemView.findViewById(R.id.custom_product_price);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(productArrayList.get(position).getName());
        holder.price.setText(constantdata.PRICE_SYMBOL+productArrayList.get(position).getPrice()+"/"+productArrayList.get(position).getUnit());
        holder.imageView.setImageResource(productArrayList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString(constantdata.PRODUCT_NAME,productArrayList.get(position).getName()).commit();
                sp.edit().putString(constantdata.PRODUCT_PRICE,productArrayList.get(position).getPrice()).commit();
                sp.edit().putString(constantdata.PRODUCT_UNIT,productArrayList.get(position).getUnit()).commit();
                sp.edit().putString(constantdata.PRODUCT_IMAGE, String.valueOf(productArrayList.get(position).getImage())).commit();
                sp.edit().putString(constantdata.PRODUCT_DESCRIPTION,productArrayList.get(position).getDescription()).commit();
                new common(context, ProductDetailsActivity.class);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }


}
