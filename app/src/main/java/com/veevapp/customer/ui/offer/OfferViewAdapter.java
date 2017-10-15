package com.veevapp.customer.ui.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.util.imageloader.ImageHandler;
import com.veevapp.customer.util.listener.OnItemSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferViewAdapter extends RecyclerView.Adapter<OfferViewAdapter.ViewHolder> {

    private Context context;
    private List<BuyRequestOffer> items;
    private OnItemSelectedListener<BuyRequestOffer> onItemSelectedListener;

    public OfferViewAdapter(List<BuyRequestOffer> items, OnItemSelectedListener<BuyRequestOffer> onItemSelectedListener) {
        this.items = items;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.template_offer2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuyRequestOffer offer = items.get(position);
        String shopName = !TextUtils.isEmpty(offer.getSeller().getShopName())?offer.getSeller().getShopName():"-";
        holder.tvShopName.setText(context.getString(R.string.shop) + " " + shopName);
        holder.tvPrice.setText(context.getString(R.string.price) + " : " + context.getString(R.string.toman));
        holder.rbShopRate.setRating(offer.getSeller().getRate());
        ImageHandler.getInstance(context).loadImage(offer.getSeller().getSellerPhotoUrl(),
                holder.ivShopImage,true,true,true,0);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_shopName)
        TextView tvShopName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_shopImage)
        ImageView ivShopImage;
        @BindView(R.id.rb_shopRate)
        RatingBar rbShopRate;

        @OnClick(R.id.root_view)
        public void rootViewOnClick() {
            onItemSelectedListener.onSelect(items.get(getAdapterPosition()));
        }

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
