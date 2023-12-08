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
        Expense expense = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.expense_list, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
        TextView tvDepartment = (TextView) convertView.findViewById(R.id.tvDepartment);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);

        tvName.setText(expense.getName());
        tvAmount.setText(String.valueOf(expense.getAmount()));
        tvDepartment.setText(expense.getDepartment());
        tvCategory.setText(expense.getCategory());

        return convertView;
    }
}

