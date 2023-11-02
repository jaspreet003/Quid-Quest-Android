package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidclass2023.quidquest.R;

import java.util.List;

import model.CategoryItem;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryItem> categoryItems;

    public CategoryAdapter( List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem currentItem = categoryItems.get(position);
        holder.textViewName.setText(currentItem.getName());
        holder.textViewPrice.setText(String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.categoryItemName);
            textViewPrice = itemView.findViewById(R.id.categoryItemPrice);
        }
    }

}
