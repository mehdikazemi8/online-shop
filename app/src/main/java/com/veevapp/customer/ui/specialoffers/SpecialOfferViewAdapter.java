package com.veevapp.customer.ui.specialoffers;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.util.imageloader.ImageHandler;
import com.veevapp.customer.util.listener.OnItemPositionSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecialOfferViewAdapter extends RecyclerView.Adapter<SpecialOfferViewAdapter.ViewHolder> {

    private Context context;
    private List<SpecialOffer> items;
    private OnItemPositionSelectedListener<SpecialOffer> onItemSelectedListener;

    public SpecialOfferViewAdapter(List<SpecialOffer> items, OnItemPositionSelectedListener<SpecialOffer> onItemSelectedListener) {
        this.items = items;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.template_special_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpecialOffer so = items.get(position);
        holder.tvPrice.setText(String.format(context.getString(R.string.template_price), so.getSuggestedPrice() + ""));
        holder.tvPreviousPrice.setText(String.format(context.getString(R.string.template_previous_price), so.getProduct().getPrice() + ""));
        holder.tvProductName.setText(items.get(position).getProduct().getName());
        holder.tvShopName.setText(context.getString(R.string.template_shop,items.get(position).getSeller().getShopName()));


        //TODO REMOVE
        List<String> photoList = new ArrayList<>();
        photoList.add("http://lorempixel.com/output/people-q-c-480-480-" + ((position%10)+1) + ".jpg");
        so.getProduct().setPhotoURLs(photoList);


        List<String> urls = so.getProduct().getPhotoURLs();
        String url = "";
        if(urls!=null && urls.size()>0)
            url = urls.get(0);
        ImageHandler.getInstance(context)
                .loadImage(url,holder.ivImage,
                false,true,true,0);

        ViewCompat.setTransitionName(holder.ivImage, context.getString(R.string.transition_special_offer_photo_index, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_timer)
        TextView tvTimer;
        @BindView(R.id.tv_productName)
        TextView tvProductName;
        @BindView(R.id.tv_previousPrice)
        TextView tvPreviousPrice;
        @BindView(R.id.tv_shopName)
        TextView tvShopName;

        @OnClick(R.id.root_view)
        public void rootViewOnClick() {
            onItemSelectedListener.onSelect(items.get(getAdapterPosition()), getAdapterPosition());
        }

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            tvPreviousPrice.setPaintFlags(tvPreviousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
