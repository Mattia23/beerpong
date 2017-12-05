package com.mirri.mirribilandia.ui.hotel;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.HotelContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;

/**
 * Shows the waitTime detail page.
 */
public class HotelDetailFragment extends BaseFragment {

    /**
     * The argument represents the dummy item ID of this fragment.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content of this fragment.
     */
    private HotelContent.HotelItem hotelItem;

    TextView distance;
    ImageView backdropImg;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // load dummy item by using the passed item ID.
            hotelItem = HotelContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_hotel_detail);
        distance = rootView.findViewById(R.id.distance);
        backdropImg = rootView.findViewById(R.id.backdrop);
        collapsingToolbar = rootView.findViewById(R.id.collapsing_toolbar);
        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (hotelItem != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(hotelItem.name);
            distance.setText(hotelItem.description);
        }

        return rootView;
    }

    private void loadBackdrop() {
        Glide.with(this).load(hotelItem.photoId).centerCrop().into(backdropImg);
    }

    public static HotelDetailFragment newInstance(String itemID) {
        HotelDetailFragment fragment = new HotelDetailFragment();
        Bundle args = new Bundle();
        args.putString(HotelDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public HotelDetailFragment() {}
}
