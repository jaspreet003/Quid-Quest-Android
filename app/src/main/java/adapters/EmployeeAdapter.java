package adapters;

import static android.text.TextUtils.replace;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidclass2023.quidquest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeAdapter extends ArrayAdapter<String> {

    private int viewType;
    DatabaseReference employeeDatabase;

    public EmployeeAdapter(Context context, ArrayList<String> employees, int viewType) {
        super(context, 0, employees);

        this.viewType = viewType;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_item, parent, false);
        }
        String employee = getItem(position);

        DatabaseReference employeeDatabase = FirebaseDatabase.getInstance().getReference().child("USERS")
                .child(employee);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);

        // Get the data item for this position
        String email = employee.replace("-", "@").replace("_", ".");
        employeeDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = null;
                if (snapshot.child("fullName").getValue() != null) {
                    name = snapshot.child("fullName").getValue().toString();
                } else if (snapshot.child("Name").getValue() != null) {
                    name = snapshot.child("Name").getValue().toString();
                } else if (snapshot.child("firstName").getValue() != null) {
                    name = snapshot.child("firstName").getValue().toString();
                }

                if (name != null) {
                    tvName.setText(name);
                } else {
                    tvName.setText("No name available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvEmail.setText(email);
        return convertView;
    }
}
