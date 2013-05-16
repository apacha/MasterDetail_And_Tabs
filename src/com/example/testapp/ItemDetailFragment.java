package com.example.testapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a single Item detail screen. This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    FragmentTabHost mTabHost;
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation
     * changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabs_pager, container, false);

        int itemId = 0;

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            itemId = Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
        }

        //mTabHost = new FragmentTabHost(getActivity());
        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(2);

        mTabsAdapter = new TabsAdapter(getActivity(), mTabHost, mViewPager);

        for (int i = 0; i < 3; i++) {
            Bundle b = new Bundle();
            b.putInt("num", itemId * 10 + i);
            mTabsAdapter.addTab(mTabHost.newTabSpec("simple" + itemId + i).setIndicator("Simple " + itemId + i),
                    CountingFragment.class, b);
        }

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

}