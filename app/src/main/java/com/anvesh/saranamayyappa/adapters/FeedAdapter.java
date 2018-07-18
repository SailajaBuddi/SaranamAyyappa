package com.anvesh.saranamayyappa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Holders.BaseViewHolder;
import com.anvesh.saranamayyappa.adapters.Holders.FeedHolder;
import com.anvesh.saranamayyappa.model.FeedPojo;

public class FeedAdapter extends BaseAdapter<FeedPojo> {
    private Context context;

    public FeedAdapter(Context context){
        this.context = context;
    }


    @Override
    protected BaseViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected BaseViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card, parent, false);
        return new FeedHolder(view);
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
        FeedHolder feedHolder = (FeedHolder)viewHolder;
        feedHolder.setData(context,getItem(position));

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
        isFooterAdded  = true;
        add(new FeedPojo());

    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }
}
