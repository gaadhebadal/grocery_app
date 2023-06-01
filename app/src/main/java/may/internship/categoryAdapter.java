package may.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyHolder> {
    Context context;
    String[] categoryarr;
    int[] categoryimg;

    public categoryAdapter(Context context, String[] categoryarr, int[] categoryimg) {
        this.context = context;
        this.categoryarr = categoryarr;
        this.categoryimg = categoryimg;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false );
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView imgview;
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.custom_cat_img);
            name = itemView.findViewById(R.id.custom_cat_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imgview.setImageResource(categoryimg[position]);
        holder.name.setText(categoryarr[position]);

    }

    @Override
    public int getItemCount() {
        return categoryarr.length;
    }


}
