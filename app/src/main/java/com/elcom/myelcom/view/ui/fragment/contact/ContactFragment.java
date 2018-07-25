package com.elcom.myelcom.view.ui.fragment.contact;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.elcom.myelcom.R;
import com.elcom.myelcom.util.NetworkConnectionChecker;
import com.elcom.myelcom.util.Toaster;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment  implements  NetworkConnectionChecker.OnConnectivityChangedListener{


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText edtSearch;
    private RelativeLayout rlSearch;
    private int mPagePos = 0;
    private  AllContactFragment allContactFragment;
    private OnlineContactFragment onlineContactFragment;
    private FavouriteContactFragment favouriteContactFragment;
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
            onlineContactFragment = new OnlineContactFragment();
            favouriteContactFragment = new FavouriteContactFragment();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkConnectionChecker = new NetworkConnectionChecker(getActivity().getApplication());
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        edtSearch = view.findViewById(R.id.edtSearch);
        rlSearch = view.findViewById(R.id.rlSearch);
        rlSearch.setVisibility(View.GONE);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagePos = position;
                switch (position){
                    case 0:{
                        rlSearch.setVisibility(View.GONE);
                        break;
                    }

                    case 1:{
                        rlSearch.setVisibility(View.VISIBLE);
                        break;
                    }

                    case 2:{
                        rlSearch.setVisibility(View.VISIBLE);
                        break;
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("hailpt"," addTextChangedListener "+s.toString());
                switch (mPagePos){

                    case 0:
                        allContactFragment.doSearch(s.toString());

                        break;

                    case  1:
                        onlineContactFragment.doSearch(s.toString());
                        rlSearch.setVisibility(View.GONE);
                        break;

                    case 2:
                        favouriteContactFragment.doSearch(s.toString());
                        break;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(allContactFragment, "Tất Cả");
        adapter.addFragment( onlineContactFragment, "Hoạt Động");
        adapter.addFragment( favouriteContactFragment, "Đánh Dấu");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void connectivityChanged(boolean availableNow) {
        if (availableNow){
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            Toaster.shortToast(R.string.check_internet_plz);
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
