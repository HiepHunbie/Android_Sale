package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.AppNotesEntry;
import com.mcredit.mobile.mobile_for_sale.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hiephunbie on 6/6/18.
 */

public class CaseNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<AppNotesEntry> mItems;
    private Context mContext;
    private CaseNoteAdapter.PostItemListener mItemListener;
    private RecyclerView mRecyclerView;
    public final int AD_TYPE = 1;
    public final int DEFAULT_TYPE = 0;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView txtMessage,txtOther,txtDate;
        private LinearLayout layout_date;
        CaseNoteAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, CaseNoteAdapter.PostItemListener postItemListener) {
            super(itemView);

            txtMessage = (TextView) itemView.findViewById(R.id.txt_message);
            txtOther = (TextView) itemView.findViewById(R.id.txt_other);
            layout_date = (LinearLayout)itemView.findViewById(R.id.layout_date);
            txtDate = (TextView)itemView.findViewById(R.id.txtDate);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AppNotesEntry item = getItem(getAdapterPosition());
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

    public CaseNoteAdapter(Activity context, ArrayList<AppNotesEntry> posts, RecyclerView recyclerView, CaseNoteAdapter.PostItemListener itemListener) {
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

        postView = inflater.inflate(R.layout.item_list_note, parent, false);

        viewHolder = new CaseNoteAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CaseNoteAdapter.ViewHolder) {
            AppNotesEntry item = mItems.get(position);

            TextView txtMessage = ((ViewHolder) holder).txtMessage;
            TextView txtDate = ((ViewHolder) holder).txtDate;
            TextView txtOther = ((ViewHolder) holder).txtOther;
            LinearLayout layout_date = ((CaseNoteAdapter.ViewHolder) holder).layout_date;
            txtOther.setText(item.getUsrUid().toString());
            txtMessage.setText(item.getNoteContent().toString());
            String dateStr = item.getNoteDate().substring(0,18);
            try {
                SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date dateObj = null;
                dateObj = curFormater.parse(dateStr);
                SimpleDateFormat postFormater = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
                String newDateStr = postFormater.format(dateObj);
                txtDate.setText(newDateStr);

            } catch (ParseException e) {
                e.printStackTrace();
            }

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

    public void updateAnswers(ArrayList<AppNotesEntry> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private AppNotesEntry getItem(int adapterPosition) {
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
            String dateString1 = mItems.get(position).getNoteDate().substring(0,18);
            String dateString2 = mItems.get(position-1).getNoteDate().substring(0,18);
            if(dateString1.equals(dateString2)){
                return DEFAULT_TYPE;
            }else {
                return AD_TYPE;
            }

        }
    }
}
