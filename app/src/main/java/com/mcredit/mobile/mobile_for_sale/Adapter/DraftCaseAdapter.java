package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftCase;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;

import java.util.List;

/**
 * Created by hiephunbie on 4/4/18.
 */

public class DraftCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DraftCase> mItems;
    private Context mContext;
    private DraftCaseAdapter.PostItemListener mItemListener;
    private RecyclerView mRecyclerView;
    public final int AD_TYPE = 1;
    public final int DEFAULT_TYPE = 0;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView fieldCaseAssignmentLV_txt_fullname,fieldCaseAssignmentLV_txt_phone,fieldCaseAssignmentLV_txt_money
                ,fieldCaseAssignmentLV_txt_scheme,fieldCaseAssignmentLV_txt_caseId_Number,txtDate;
        ProgressBar progressLoading_not_invite;
        DraftCaseAdapter.PostItemListener mItemListener;
        private LinearLayout layout_date;
        public ViewHolder(View itemView, DraftCaseAdapter.PostItemListener postItemListener) {
            super(itemView);

            fieldCaseAssignmentLV_txt_fullname = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_fullname);
            fieldCaseAssignmentLV_txt_phone = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_phone);
            fieldCaseAssignmentLV_txt_money = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_money);
            fieldCaseAssignmentLV_txt_scheme = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_scheme);
            fieldCaseAssignmentLV_txt_caseId_Number = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_caseId_Number);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);
            layout_date = (LinearLayout)itemView.findViewById(R.id.layout_date);
            txtDate = (TextView)itemView.findViewById(R.id.txtDate);
            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DraftCase item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }


        @Override
        public boolean onLongClick(View v) {
            this.mItemListener.onLongClick(getAdapterPosition());
            notifyDataSetChanged();
            return false;
        }
    }

    public DraftCaseAdapter(Activity context, List<DraftCase> posts, RecyclerView recyclerView, DraftCaseAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        View postView = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        postView = inflater.inflate(R.layout.item_return_file, parent, false);

        viewHolder = new DraftCaseAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DraftCaseAdapter.ViewHolder) {
            DraftCase item = mItems.get(position);

            TextView fieldCaseAssignmentLV_txt_fullname = ((DraftCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_fullname;
            TextView fieldCaseAssignmentLV_txt_phone = ((DraftCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_phone;
            TextView fieldCaseAssignmentLV_txt_money = ((DraftCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_money;
            TextView fieldCaseAssignmentLV_txt_scheme = ((DraftCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_scheme;
            TextView fieldCaseAssignmentLV_txt_caseId_Number = ((DraftCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_caseId_Number;
            TextView txtDate = ((DraftCaseAdapter.ViewHolder) holder).txtDate;
            LinearLayout layout_date = ((DraftCaseAdapter.ViewHolder) holder).layout_date;
            fieldCaseAssignmentLV_txt_fullname.setText(item.getFullname());
            fieldCaseAssignmentLV_txt_phone.setText(item.getCard());
            fieldCaseAssignmentLV_txt_money.setText(Edittext.convertTextToCommas(item.getMoney()));
            fieldCaseAssignmentLV_txt_scheme.setText(item.getDocument_type_value());
            fieldCaseAssignmentLV_txt_caseId_Number.setText(item.getMobileAppCode());
            layout_date.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<DraftCase> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private DraftCase getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
        void onLongClick(long id);
    }
    @Override
    public int getItemViewType(int position)
    {
        if (position % 6 == 0){
            return AD_TYPE;
        }else {
            return DEFAULT_TYPE;
        }
    }
}
