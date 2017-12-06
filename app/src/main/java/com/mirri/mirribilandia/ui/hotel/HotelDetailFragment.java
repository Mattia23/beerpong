package com.mirri.mirribilandia.ui.hotel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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

public class HotelDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";
    private HotelContent.HotelItem hotelItem;

    private ImageView image;
    private FloatingActionButton phoneButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            hotelItem = HotelContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_hotel_detail);

        TextView description = rootView.findViewById(R.id.description);
        TextView distance = rootView.findViewById(R.id.distance);
        FloatingActionButton phoneButton = rootView.findViewById(R.id.floatingActionButton);
        image = rootView.findViewById(R.id.backdrop);
        CollapsingToolbarLayout name = rootView.findViewById(R.id.collapsing_toolbar);
        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (hotelItem != null) {
            loadBackdrop();
            name.setTitle(hotelItem.name);
            description.setText(hotelItem.description);
            distance.setText(hotelItem.distance + "Km");
            phoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + hotelItem.phone));
                    startActivity(callIntent);
                }
            });
        }

        return rootView;
    }

    private void loadBackdrop() {
        Glide.with(this).load(hotelItem.image).centerCrop().into(image);
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
