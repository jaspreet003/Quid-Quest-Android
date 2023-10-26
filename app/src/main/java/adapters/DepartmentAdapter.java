package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidclass2023.quidquest.R;

import java.util.List;

import model.CategoryItem;
import model.DepartmentItem;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {


    private List<DepartmentItem> departmentItems;

    public DepartmentAdapter( List<DepartmentItem> departmentItems) {
        this.departmentItems = departmentItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentAdapter.ViewHolder holder, int position) {
        DepartmentItem currentItem = departmentItems.get(position);
        holder.tvDepartmentItemName.setText(currentItem.getName());
        holder.tvDepartmentItemPrice.setText(String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return departmentItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDepartmentItemName;
        public TextView tvDepartmentItemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDepartmentItemName = itemView.findViewById(R.id.departmentItemName);
            tvDepartmentItemPrice = itemView.findViewById(R.id.departmentItemPrice);
        }
    }
}
