package com.anvesh.saranamayyappa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Holders.BaseViewHolder;
import com.anvesh.saranamayyappa.adapters.Holders.NearbyPujasHolder;
import com.anvesh.saranamayyappa.model.NearByPujaPojo;

public class NearbyPujasAdapter extends BaseAdapter<NearByPujaPojo>{

    private Context context;
    View.OnClickListener listener;
    int LAYOUT=R.layout.card_event_h;


    public NearbyPujasAdapter(Context context, View.OnClickListener listener,int LAYOUT) {
        this.context = context;
        this.listener = listener;
        this.LAYOUT=LAYOUT;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected BaseViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected BaseViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);
        return new NearbyPujasHolder(context, view, listener);
        //return null;
    }

    @Override
    protected BaseViewHolder createFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindHeaderViewHolder(BaseViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(BaseViewHolder viewHolder, int position) {
        NearbyPujasHolder nearbyPujasHolder = (NearbyPujasHolder) viewHolder;
        nearbyPujasHolder.setData(context, getItem(position));
    }

    @Override
    protected void bindFooterViewHolder(BaseViewHolder viewHolder) {

    }

    @Override
    protected void displayLoadMoreFooter() {

    }

    @Override
    protected void displayErrorFooter() {

    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
        add(new NearByPujaPojo());
    }
    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }
}
