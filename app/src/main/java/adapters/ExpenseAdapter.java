package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidclass2023.quidquest.R;

import java.util.List;

import model.Expense;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Expense expense = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_list, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
        TextView tvDepartment = (TextView) convertView.findViewById(R.id.tvDepartment);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);

        // Populate the data into the template view using the data object
        tvName.setText(expense.getName());
        tvAmount.setText(String.valueOf(expense.getAmount()));
        tvDepartment.setText(expense.getDepartment());
        tvCategory.setText(expense.getCategory());

        // Return the completed view to render on screen
        return convertView;
    }
}

