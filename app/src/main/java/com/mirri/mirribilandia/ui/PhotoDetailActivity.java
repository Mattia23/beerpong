package com.mirri.mirribilandia.ui;


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
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;

public class PhotoDetailActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Show the Up button in the action bar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        PhotoDetailFragment fragment =  PhotoDetailFragment.newInstance(getIntent().getStringExtra(PhotoDetailFragment.ARG_ITEM_ID));
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }


    public static class PhotoDetailFragment extends BaseFragment {

        public static final String ARG_ITEM_ID = "item_id";

        TextView distance;
        ImageView backdropImg;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // load dummy item by using the passed item ID.
                //hotelItem = HotelContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            }

            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflateAndBind(inflater, container, R.layout.fragment_photo_detail);
            /*distance = rootView.findViewById(R.id.distance);
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
            }*/

            return rootView;
        }

        private void loadBackdrop() {
            //Glide.with(this).load(hotelItem.photoId).centerCrop().into(backdropImg);
        }

        public static PhotoDetailFragment newInstance(String itemID) {
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle args = new Bundle();
            args.putString(PhotoDetailFragment.ARG_ITEM_ID, itemID);
            fragment.setArguments(args);
            return fragment;
        }

        public PhotoDetailFragment() {}
    }

}
