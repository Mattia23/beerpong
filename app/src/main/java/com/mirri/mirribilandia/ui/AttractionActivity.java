package com.mirri.mirribilandia.ui;

import android.content.Intent;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.AttractionContent;
import com.mirri.mirribilandia.ui.quote.ArticleDetailActivity;
import com.mirri.mirribilandia.ui.quote.ArticleDetailFragment;
import com.mirri.mirribilandia.ui.quote.ListActivity;

public class AttractionActivity extends ListActivity {

    @Override
    public void onItemSelected(String id) {
        if (twoPaneMode) {
            // Show the quote detail information by replacing the DetailFragment via transaction.
            ArticleDetailFragment fragment = ArticleDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
        } else {
            // Start the detail activity in single pane mode.
            Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
            detailIntent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    protected void setupDetailFragment() {
        ArticleDetailFragment fragment =  ArticleDetailFragment.newInstance(AttractionContent.ITEMS.get(0).id);
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_attraction;
    }
}
