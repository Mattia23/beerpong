package com.mirri.mirribilandia.ui.gironi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.GironiContent;

/**
 * Shows a list of all available quotes.
 */
public class GironiListFragment extends ListFragment {

    private Callback callback = dummyCallback;

    /**
     * A callback interface. Called whenever a item has been selected.
     */
    public interface Callback {
        void onItemSelected(String id);
    }

    /**
     * A dummy no-op implementation of the Callback interface. Only used when no active Activity is present.
     */
    private static final Callback dummyCallback = new Callback() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new GironiListAdapter());
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // notify callback about the selected list item
        callback.onItemSelected(GironiContent.ITEMS.get(position).id);
    }

    /**
     * onAttach(Context) is not called on pre API 23 versions of Android.
     * onAttach(Activity) is deprecated but still necessary on older devices.
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /**
     * Deprecated on API 23 but still necessary for pre API 23 devices.
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /**
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (!(context instanceof Callback)) {
            throw new IllegalStateException("Activity must implement callback interface.");
        }

        callback = (Callback) context;
    }

    private class GironiListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return GironiContent.GIRONI_ITEMS.size();
        }

        @Override
        public Object getItem(int position) {
            return position+1;
        }

        @Override
        public long getItemId(int position) {
            return GironiContent.GIRONI_ITEMS.get(position+1).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_article, container, false);
            }
            final int gir = (int) getItem(position);
            ((TextView) convertView.findViewById(R.id.article_title)).setText("Girone "+gir);
            String elenco = "";
            for(String s : GironiContent.GIRONI_ITEMS.get(gir)) {
                elenco = elenco + s + ", ";
            }
            elenco = elenco.substring(0, elenco.length() - 2);
            ((TextView) convertView.findViewById(R.id.article_subtitle)).setText(elenco);

            return convertView;
        }
    }
}