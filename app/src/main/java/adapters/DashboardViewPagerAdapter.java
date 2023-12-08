package adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fragments.CategoryFragment;
import fragments.DepartmentFragment;

public class DashboardViewPagerAdapter extends FragmentStateAdapter{

        public DashboardViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CategoryFragment();
                case 1:
                    return new DepartmentFragment();
                default:
                    return new CategoryFragment(); // Default to CategoryFragment
            }
        }

        @Override
        public int getItemCount() {
            return 2; //  Category and Department
        }
}
