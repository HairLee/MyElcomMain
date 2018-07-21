package com.elcom.hailpt.view.ui.fragment.contact;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.elcom.hailpt.R;
import com.elcom.hailpt.util.NetworkConnectionChecker;
import com.elcom.hailpt.util.Toaster;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment  implements  NetworkConnectionChecker.OnConnectivityChangedListener{


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  AllContactFragment allContactFragment;
    private NetworkConnectionChecker networkConnectionChecker;
    public ContactFragment() {
        // Required empty public constructor
    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_contact, container, false);
            allContactFragment = new AllContactFragment();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkConnectionChecker = new NetworkConnectionChecker(getActivity().getApplication());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(allContactFragment, "Tất Cả");
        adapter.addFragment(new OnlineContactFragment(), "Đang Hoạt Động");
        adapter.addFragment(new FavouriteContactFragment(), "Đã Đánh Dấu");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void connectivityChanged(boolean availableNow) {
        if (availableNow){
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            Toaster.shortToast("check Internet");
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        networkConnectionChecker.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        networkConnectionChecker.unregisterListener(this);
    }
}
