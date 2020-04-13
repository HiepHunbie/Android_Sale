package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.Notification.Message;
import com.mcredit.mobile.mobile_for_sale.Models.Notification.NotificationResult;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ArrCase;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> mItems;
    private Context mContext;
    private NotificationAdapter.PostItemListener mItemListener;
    private RecyclerView mRecyclerView;
    public final int AD_TYPE = 1;
    public final int DEFAULT_TYPE = 0;

    public final int VIEW_TYPE_MULTI_ID = 2;
    public final int VIEW_TYPE = 3;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView fieldCaseAssignmentLV_txt_fullname,fieldCaseAssignmentLV_txt_phone,fieldCaseAssignmentLV_txt_money,
                fieldCaseAssignmentLV_txt_scheme,fieldCaseAssignmentLV_txt_caseId_Number,txtDate,fieldCaseAssignmentLV_txt_status,fieldCaseAssignmentLV_txt_mobile_app_code;
        private LinearLayout layout_date;
        ProgressBar progressLoading_not_invite;
        NotificationAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, NotificationAdapter.PostItemListener postItemListener) {
            super(itemView);

            fieldCaseAssignmentLV_txt_fullname = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_fullname);
            fieldCaseAssignmentLV_txt_phone = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_phone);
            fieldCaseAssignmentLV_txt_money = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_money);
            fieldCaseAssignmentLV_txt_scheme = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_scheme);
            fieldCaseAssignmentLV_txt_caseId_Number = (TextView) itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_caseId_Number);
            fieldCaseAssignmentLV_txt_status = (TextView)itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_status);
            fieldCaseAssignmentLV_txt_mobile_app_code = (TextView)itemView.findViewById(R.id.fieldCaseAssignmentLV_txt_mobile_app_code);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);
            layout_date = (LinearLayout)itemView.findViewById(R.id.layout_date);
            txtDate = (TextView)itemView.findViewById(R.id.txtDate);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Message item = getItem(getAdapterPosition());
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

    public NotificationAdapter(Activity context, ArrayList<Message> posts, RecyclerView recyclerView, NotificationAdapter.PostItemListener itemListener) {
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

        postView = inflater.inflate(R.layout.item_notification, parent, false);

        viewHolder = new NotificationAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationAdapter.ViewHolder) {
            Message item = mItems.get(position);

            TextView fieldCaseAssignmentLV_txt_fullname = ((NotificationAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_fullname;
            TextView fieldCaseAssignmentLV_txt_phone = ((NotificationAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_phone;
            TextView fieldCaseAssignmentLV_txt_money = ((NotificationAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_money;
            TextView fieldCaseAssignmentLV_txt_scheme = ((NotificationAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_scheme;
            TextView fieldCaseAssignmentLV_txt_caseId_Number = ((NotificationAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_caseId_Number;
            TextView fieldCaseAssignmentLV_txt_status = ((NotificationAdapter.ViewHolder)holder).fieldCaseAssignmentLV_txt_status;
            TextView fieldCaseAssignmentLV_txt_mobile_app_code = ((NotificationAdapter.ViewHolder)holder).fieldCaseAssignmentLV_txt_mobile_app_code;
            TextView txtDate = ((NotificationAdapter.ViewHolder) holder).txtDate;
            LinearLayout layout_date = ((NotificationAdapter.ViewHolder) holder).layout_date;

            fieldCaseAssignmentLV_txt_fullname.setText(item.getSsoId());
            fieldCaseAssignmentLV_txt_scheme.setText(item.getMessage().toString());
            fieldCaseAssignmentLV_txt_caseId_Number.setText(item.getAppNumber()+"");
            long millisecond = Long.parseLong(item.getCreatedDate()+"");
            // or you already have long value of date, use this instead of milliseconds variable.
            String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
            txtDate.setText(dateString);
            int viewType = getItemViewType(position);
            switch (viewType) {
                case AD_TYPE:
                    layout_date.setVisibility(View.VISIBLE);
                    break;
                case DEFAULT_TYPE:
                    layout_date.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(ArrayList<Message> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Message getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
        void onLongClick(long id);
    }
    @Override
    public int getItemViewType(int position)
    {
        if (position == 0){
            return AD_TYPE;
        }else {
            long millisecond1 = Long.parseLong(mItems.get(position).getCreatedDate()+"");
            String dateString1 = DateFormat.format("dd/MM/yyyy", new Date(millisecond1)).toString();
            long millisecond2 = Long.parseLong(mItems.get(position-1).getCreatedDate()+"");
            String dateString2 = DateFormat.format("dd/MM/yyyy", new Date(millisecond2)).toString();
            if(dateString1.equals(dateString2)){
                return DEFAULT_TYPE;
            }else {
                return AD_TYPE;
            }

        }
    }
}
