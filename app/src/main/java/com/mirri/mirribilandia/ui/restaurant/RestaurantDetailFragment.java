package com.mirri.mirribilandia.ui.restaurant;

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
import com.mirri.mirribilandia.item.RestaurantContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;

/**
 * Shows the name detail page.
 */
public class RestaurantDetailFragment extends BaseFragment {

    /**
     * The argument represents the dummy item ID of this fragment.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content of this fragment.
     */
    private RestaurantContent.RestaurantItem restaurantItem;

    TextView description;
    ImageView backdropImg;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // load dummy item by using the passed item ID.
            restaurantItem = RestaurantContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_restaurant_detail);
        description = rootView.findViewById(R.id.description);
        backdropImg = rootView.findViewById(R.id.backdrop);
        collapsingToolbar = rootView.findViewById(R.id.collapsing_toolbar);
        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (restaurantItem != null) {
            loadBackdrop();
            collapsingToolbar.setTitle(restaurantItem.name);
            description.setText(restaurantItem.description);
        }

        return rootView;
    }

    private void loadBackdrop() {
        Glide.with(this).load(restaurantItem.photoId).centerCrop().into(backdropImg);
    }

    public static RestaurantDetailFragment newInstance(String itemID) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putString(RestaurantDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantDetailFragment() {}
}
