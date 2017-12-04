package com.mirri.mirribilandia.ui.base;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirri.mirribilandia.util.LogUtil;

import static com.mirri.mirribilandia.util.LogUtil.makeLogTag;

/**
 * The base class for all fragment classes.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = makeLogTag(BaseFragment.class);

    /**
     * Inflates the layout and binds the view via ButterKnife.
     * @param inflater the inflater
     * @param container the layout container
     * @param layout the layout resource
     * @return the inflated view
     */
    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);
        LogUtil.logD(TAG, ">>> view inflated");
        return view;
    }
}
