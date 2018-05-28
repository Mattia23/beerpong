package com.mirri.mirribilandia.ui.attraction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.AttractionContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;
import com.mirri.mirribilandia.util.DownloadImageTask;

import static com.mirri.mirribilandia.util.Utilities.BEACON_ID;

public class AttractionDetailFragment extends BaseFragment {

    public static final String ARG_ITEM_ID = "item_id";
    private AttractionContent.AttractionItem attractionItem;

    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            attractionItem = AttractionContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflateAndBind(inflater, container, R.layout.fragment_attraction_detail);
        TextView description = rootView.findViewById(R.id.description);
        TextView minAge = rootView.findViewById(R.id.minAge);
        TextView minHeight = rootView.findViewById(R.id.minHeight);
        TextView waitingTime = rootView.findViewById(R.id.waitingTime);
        TextView buildYear = rootView.findViewById(R.id.buildYear);
        image = rootView.findViewById(R.id.backdrop);
        FloatingActionButton chatButton = rootView.findViewById(R.id.floatingActionButton);
        CollapsingToolbarLayout name = rootView.findViewById(R.id.collapsing_toolbar);
        if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
            // No Toolbar present. Set include_toolbar:
            ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
        }

        if (attractionItem != null) {
            new DownloadImageTask(image).execute(attractionItem.image);
            name.setTitle(attractionItem.name);
            description.setText(attractionItem.description);
            minAge.setText(attractionItem.minAge + " anni");
            minHeight.setText(attractionItem.minHeight + "cm");
            waitingTime.setText(attractionItem.waitingTime + " minuti");
            buildYear.setText(""+attractionItem.buildYear);

        }

        return rootView;
    }

    public static AttractionDetailFragment newInstance(String itemID) {
        AttractionDetailFragment fragment = new AttractionDetailFragment();
        Bundle args = new Bundle();
        args.putString(AttractionDetailFragment.ARG_ITEM_ID, itemID);
        fragment.setArguments(args);
        return fragment;
    }

    public AttractionDetailFragment() {}
}
