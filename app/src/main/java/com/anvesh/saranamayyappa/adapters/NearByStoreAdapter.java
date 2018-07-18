package com.anvesh.saranamayyappa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Holders.BaseViewHolder;
import com.anvesh.saranamayyappa.adapters.Holders.NearByStoreHolder;
import com.anvesh.saranamayyappa.model.NearByStorePojo;

public class NearByStoreAdapter extends BaseAdapter<NearByStorePojo> {

    private Context context;
    View.OnClickListener listener;

    public NearByStoreAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected BaseViewHolder createHeaderViewHolder(ViewGroup parent) {
        return  null;
    }

    @Override
    protected BaseViewHolder createItemViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comm_stor, parent, false);
        return new NearByStoreHolder(context, view, listener);
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
        NearByStoreHolder nearByStoreHolder = (NearByStoreHolder) viewHolder;
        nearByStoreHolder.setData(context,getItem(position));

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
        add(new NearByStorePojo());

    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }
}
