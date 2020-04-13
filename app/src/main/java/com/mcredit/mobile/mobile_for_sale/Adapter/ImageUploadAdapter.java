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
 * Created by hiephunbie on 3/21/18.
 */

public class ImageUploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private List<ImageUpload> mItems;
    private Activity mContext;
    private ImageUploadAdapter.PostItemListener mItemListener;
    private ImageUploadAdapter.PostLongItemListener postLongItemListener;
    private RecyclerView mRecyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        ProgressBar progressLoading_not_invite;
        ImageUploadAdapter.PostItemListener mItemListener;
        ImageUploadAdapter.PostLongItemListener mPostLongItemListener;

        public ViewHolder(View itemView, ImageUploadAdapter.PostItemListener postItemListener, final ImageUploadAdapter.PostLongItemListener postLongItemListener) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imgView);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);

            this.mItemListener = postItemListener;
            this.mPostLongItemListener = postLongItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogCall.showConfirmChangeImageDialog(mContext,mContext.getString(R.string.confirm_delete_image),new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_delete) {
                                mPostLongItemListener.onPostLongClick(getAdapterPosition());
                                notifyDataSetChanged();
                            }
                            else if (id == R.id.btn_cancel) {
                            }
                            else if (id == R.id.btn_change) {
                                mPostLongItemListener.onChangeImageClick(getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        }
                    });

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

    public ImageUploadAdapter(Activity context, List<ImageUpload> posts, RecyclerView recyclerView, ImageUploadAdapter.PostItemListener itemListener,ImageUploadAdapter.PostLongItemListener longItemListener) {
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

        View postView = inflater.inflate(R.layout.item_recommend, parent, false);

        viewHolder = new ImageUploadAdapter.ViewHolder(postView, this.mItemListener,postLongItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageUploadAdapter.ViewHolder) {
            String item = mItems.get(position).getUrl();

            ImageView imageView = ((ImageUploadAdapter.ViewHolder) holder).imageView;
            final ProgressBar progressBar = ((ImageUploadAdapter.ViewHolder) holder).progressLoading_not_invite;
            loadImage(item,imageView,progressBar);
        }

    }

    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
        if(url.contains("/storage/emulated")){
            File myFile = new File(url);
            if(myFile.exists()){
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
            }else {
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.image_not_exists);
            }
        }else {
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
        return mItems.get(adapterPosition).getUrl();
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    public interface PostLongItemListener {
        void onPostLongClick(long id);
        void onChangeImageClick(long id);
    }
}
