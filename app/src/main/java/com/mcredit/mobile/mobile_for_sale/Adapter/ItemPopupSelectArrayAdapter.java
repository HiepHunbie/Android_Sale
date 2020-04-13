package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.PopUpSelect.Item;
import com.mcredit.mobile.mobile_for_sale.R;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/21/18.
 */

public class ItemPopupSelectArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    private ArrayList<Item> itemList;
    // Constructor of the class
    public ItemPopupSelectArrayAdapter(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        Item item = itemList.get(position);
        if (item.getType() == Item.ItemType.ONE_ITEM) {
            return TYPE_ONE;
        } else if (item.getType() == Item.ItemType.TWO_ITEM) {
            return TYPE_TWO;
        } else {
            return -1;
        }
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_select_popup, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value_select_popup, parent, false);
            return new ViewHolderTwo(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((ViewHolderOne)holder, listPosition);
                break;
            case TYPE_TWO:
                initLayoutTwo((ViewHolderTwo) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutOne(ViewHolderOne holder, int pos) {
        if(itemList.get(pos).getMandory() == 1){
            holder.txtRequite.setVisibility(View.VISIBLE);
        }else {
            holder.txtRequite.setVisibility(View.GONE);
        }
        holder.item.setText(itemList.get(pos).getTitle());
    }

    private void initLayoutTwo(ViewHolderTwo holder, int pos) {
        holder.tvLeft.setText(itemList.get(pos).getTitle());
    }


    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView item;
        public ImageView txtRequite;
        public ViewHolderOne(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.txtTitle);
            txtRequite = (ImageView)itemView.findViewById(R.id.txtRequite);
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        public TextView tvLeft;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            tvLeft = (TextView) itemView.findViewById(R.id.txtValue);
        }
    }
}
