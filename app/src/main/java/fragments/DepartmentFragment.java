package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidclass2023.quidquest.R;

import java.util.ArrayList;
import java.util.List;


import adapters.DepartmentAdapter;

import model.DepartmentItem;

public class DepartmentFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.departmentRecyclerView);

//        for testing -- will be replaced by database data
        List<DepartmentItem> items = new ArrayList<>();
        items.add(new DepartmentItem("Technology", 400));
        items.add(new DepartmentItem("Sales", 300 ));

        DepartmentAdapter adapter = new DepartmentAdapter(items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_department, container, false);
    }
}
