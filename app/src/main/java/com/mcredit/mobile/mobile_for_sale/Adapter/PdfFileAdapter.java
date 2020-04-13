package com.mcredit.mobile.mobile_for_sale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.ImageUpload.ImageUpload;
import com.mcredit.mobile.mobile_for_sale.R;

import java.io.File;
import java.util.List;

/**
 * Created by hiephunbie on 5/23/18.
 */

public class PdfFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItems;
    private Activity mContext;
    private PdfFileAdapter.PostItemListener mItemListener;
    private PdfFileAdapter.PostLongItemListener postLongItemListener;
    private RecyclerView mRecyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView title;
        ProgressBar progressLoading_not_invite;
        PdfFileAdapter.PostItemListener mItemListener;
        PdfFileAdapter.PostLongItemListener mPostLongItemListener;

        public ViewHolder(View itemView, PdfFileAdapter.PostItemListener postItemListener, final PdfFileAdapter.PostLongItemListener postLongItemListener) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imgView);
            title = (TextView)itemView.findViewById(R.id.title); 
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);

            this.mItemListener = postItemListener;
            this.mPostLongItemListener = postLongItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mPostLongItemListener.onPostLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            String item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public PdfFileAdapter(Activity context, List<String> posts, RecyclerView recyclerView, PdfFileAdapter.PostItemListener itemListener,PdfFileAdapter.PostLongItemListener longItemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
        mRecyclerView = recyclerView;
        postLongItemListener = longItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_list_pdf, parent, false);

        viewHolder = new PdfFileAdapter.ViewHolder(postView, this.mItemListener,postLongItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PdfFileAdapter.ViewHolder) {
            String item = mItems.get(position);

            ImageView imageView = ((PdfFileAdapter.ViewHolder) holder).imageView;
            TextView title = ((ViewHolder) holder).title;
            title.setText(item);
            final ProgressBar progressBar = ((PdfFileAdapter.ViewHolder) holder).progressLoading_not_invite;
            progressBar.setVisibility(View.GONE);
        }

    }

    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
            Glide.with(mContext).load(url)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loadImage(url,imageView,progressBar);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);

                            return false;
                        }

                    })
                    .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateTermLoanCode(String termLoanCode) {
//        mtermLoanCode = termLoanCode;
        notifyDataSetChanged();
    }

    private String getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    public interface PostLongItemListener {
        void onPostLongClick(long id);
        void onChangeImageClick(long id);
    }
}
