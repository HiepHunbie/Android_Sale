package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.CaseReturnArr;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class ReturnCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CaseReturnArr> mItems;
    private Context mContext;
    private ReturnCaseAdapter.PostItemListener mItemListener;
    private RecyclerView mRecyclerView;
    public final int AD_TYPE = 1;
    public final int DEFAULT_TYPE = 0;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView fieldCaseAssignmentLV_txt_fullname,fieldCaseAssignmentLV_txt_phone,fieldCaseAssignmentLV_txt_money,
                fieldCaseAssignmentLV_txt_scheme,fieldCaseAssignmentLV_txt_caseId_Number,txtDate;
        private LinearLayout layout_date;
        ProgressBar progressLoading_not_invite;
        ReturnCaseAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, ReturnCaseAdapter.PostItemListener postItemListener) {
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
            CaseReturnArr item = getItem(getAdapterPosition());
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

    public ReturnCaseAdapter(Activity context, ArrayList<CaseReturnArr> posts, RecyclerView recyclerView, ReturnCaseAdapter.PostItemListener itemListener) {
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

        viewHolder = new ReturnCaseAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReturnCaseAdapter.ViewHolder) {
            CaseReturnArr item = mItems.get(position);

            TextView fieldCaseAssignmentLV_txt_fullname = ((ReturnCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_fullname;
            TextView fieldCaseAssignmentLV_txt_phone = ((ReturnCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_phone;
            TextView fieldCaseAssignmentLV_txt_money = ((ReturnCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_money;
            TextView fieldCaseAssignmentLV_txt_scheme = ((ReturnCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_scheme;
            TextView fieldCaseAssignmentLV_txt_caseId_Number = ((ReturnCaseAdapter.ViewHolder) holder).fieldCaseAssignmentLV_txt_caseId_Number;
            TextView txtDate = ((ReturnCaseAdapter.ViewHolder) holder).txtDate;
            LinearLayout layout_date = ((ViewHolder) holder).layout_date;

            fieldCaseAssignmentLV_txt_fullname.setText(item.getDataEntrySales().getMobileCustomerName());
            fieldCaseAssignmentLV_txt_phone.setText(item.getDataEntrySales().getMobileCitizenId());
            fieldCaseAssignmentLV_txt_money.setText(Edittext.convertTextToCommas(item.getDataEntrySales().getMobileLoanAmount()+""));
            fieldCaseAssignmentLV_txt_scheme.setText(item.getDataEntrySales().getMobileSchemaProductName().toString());
            fieldCaseAssignmentLV_txt_caseId_Number.setText(item.getDataEntrySales().getAppNumber()+"");
            long millisecond = Long.parseLong(item.getDataEntrySales().getCreatedDate()+"");
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

    public void updateAnswers(ArrayList<CaseReturnArr> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private CaseReturnArr getItem(int adapterPosition) {
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
