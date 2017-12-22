package com.mirri.mirribilandia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.PhotoContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.util.DownloadImageTask;

public class PhotoGridActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_photos);
        setupToolbar();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new PhotoListAdapter());
        gridview.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailIntent = new Intent(PhotoGridActivity.this, PhotoDetailActivity.class);
            detailIntent.putExtra(PhotoDetailActivity.PhotoDetailFragment.ARG_ITEM_ID,
                    PhotoContent.ITEMS.get(position).id);
            startActivity(detailIntent);
        });
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_photos;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    public class PhotoListAdapter extends BaseAdapter {

        public int getCount() {
            return PhotoContent.ITEMS.size();
        }

        public Object getItem(int position) {
            return PhotoContent.ITEMS.get(position);
        }

        public long getItemId(int position) {
            return PhotoContent.ITEMS.get(position).id.hashCode();
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            final PhotoContent.PhotoItem photoItem = (PhotoContent.PhotoItem)  getItem(position);
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(getApplicationContext());
                imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            new DownloadImageTask(imageView).execute(photoItem.image);
            return imageView;
        }

    }
}
