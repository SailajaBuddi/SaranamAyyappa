package com.anvesh.saranamayyappa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anvesh.saranamayyappa.Fragments.GroupMembers;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.Holders.BaseViewHolder;
import com.anvesh.saranamayyappa.adapters.Holders.MemberHolder;
import com.anvesh.saranamayyappa.model.GroupMembersPojo;

/**
 * Created by ismuser11 on 6/6/2018.
 */

public class Memb_Adapter extends BaseAdapter<GroupMembersPojo>{

    Context context;
    View view;


    public Memb_Adapter(Context context) {
        this.context=context;

    }



    /*@Override
    public int getItemCount() {
        return s2.length;
    }*/

    @Override
    protected BaseViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected BaseViewHolder createItemViewHolder(ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);

        view=inflater.inflate(R.layout.row_members,parent,false);
        return  new MemberHolder(context,view);
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
        MemberHolder memberHolder = (MemberHolder) viewHolder;
        memberHolder.setData(context, getItem(position));
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
        add(new GroupMembersPojo());
    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }

}
