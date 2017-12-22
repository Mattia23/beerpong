package com.mirri.mirribilandia.ui;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.PhotoContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.base.BaseFragment;
import com.mirri.mirribilandia.util.DownloadImageTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

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

        public PhotoDetailFragment() {}

        public static final String ARG_ITEM_ID = "item_id";

        private PhotoContent.PhotoItem photoItem;
        private TextView attraction;
        private TextView date;
        private ImageView image;
        private FloatingActionButton downloadImageButton;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // load dummy item by using the passed item ID.
                photoItem = PhotoContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            }

            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflateAndBind(inflater, container, R.layout.fragment_photo_detail);
            attraction = rootView.findViewById(R.id.attraction);
            date = rootView.findViewById(R.id.date);
            downloadImageButton = rootView.findViewById(R.id.floatingActionButton);
            CollapsingToolbarLayout name = rootView.findViewById(R.id.collapsing_toolbar);
            image = rootView.findViewById(R.id.backdrop);

            if (!((BaseActivity) getActivity()).providesActivityToolbar()) {
                // No Toolbar present. Set include_toolbar:
                ((BaseActivity) getActivity()).setToolbar((Toolbar) rootView.findViewById(R.id.toolbar));
            }

            if (photoItem != null) {
                new DownloadImageTask(image).execute(photoItem.image);
                name.setTitle("Image" + photoItem.id + ".jpg");
                attraction.setText(photoItem.attraction);
                date.setText(photoItem.date);
                downloadImageButton.setOnClickListener(view -> {
                    try {
                        saveFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            return rootView;
        }

        public static PhotoDetailFragment newInstance(String itemID) {
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle args = new Bundle();
            args.putString(PhotoDetailFragment.ARG_ITEM_ID, itemID);
            fragment.setArguments(args);
            return fragment;
        }

        public  void saveFile() throws IOException {
            image.buildDrawingCache();
            Bitmap bm=image.getDrawingCache();
            OutputStream fOut;
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator);
            root.mkdirs();
            File sdImageMainDirectory = new File(root, "Image" + photoItem.id + ".jpg");
            Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(getActivity(),"Immagine salvata nella cartella Download",Toast.LENGTH_SHORT).show();
        }
    }

}
