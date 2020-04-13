package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.PopUpSelect.Item;
import com.mcredit.mobile.mobile_for_sale.R;

import java.util.List;


/**
 * Created by hiephunbie on 3/21/18.
 */

public class ItemPopupSelectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> mItems;
    private Context mContext;
    private ItemPopupSelectedAdapter.PostItemListener mItemListener;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtTitle,txtValue;
        public ImageView txtRequite,img_status;
        public View view;
        public RelativeLayout layout_title;
        ItemPopupSelectedAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, ItemPopupSelectedAdapter.PostItemListener postItemListener) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

            txtValue= (TextView) itemView.findViewById(R.id.txtValue);
            txtRequite = (ImageView) itemView.findViewById(R.id.txtRequite);
            img_status = (ImageView)itemView.findViewById(R.id.img_status);
            view = (View) itemView.findViewById(R.id.view);
            layout_title = (RelativeLayout)itemView.findViewById(R.id.layout_title);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public ItemPopupSelectedAdapter(Activity context, List<Item> posts, RecyclerView recyclerView, ItemPopupSelectedAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_popup_select, parent, false);

        viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Item item = mItems.get(position);
            TextView txtTitle = ((ViewHolder) holder).txtTitle;
            TextView txtValue = ((ViewHolder) holder).txtValue;
            View view = ((ViewHolder) holder).view;
            txtTitle.setText(item.getTitle());
            txtValue.setText(item.getValue());
            ImageView txtRequite = ((ViewHolder) holder).txtRequite;
            ImageView img_status = ((ViewHolder) holder).img_status;
            RelativeLayout layout_title = ((ViewHolder)holder).layout_title;
            if(item.getMandory()==0){
                txtRequite.setVisibility(View.GONE);
            }else {
                txtRequite.setVisibility(View.VISIBLE);
            }
        if(item.getStatus()==0){
            img_status.setVisibility(View.GONE);
        }else {
            img_status.setVisibility(View.VISIBLE);
        }
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                layout_title.setVisibility(View.VISIBLE);
                break;
            case TYPE_TWO:
                layout_title.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        Item item = mItems.get(position);
        if (item.getType() == Item.ItemType.ONE_ITEM) {
            return TYPE_ONE;
        } else if (item.getType() == Item.ItemType.TWO_ITEM) {
            return TYPE_TWO;
        } else {
            return -1;
        }
    }
}
