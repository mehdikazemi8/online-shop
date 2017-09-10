package com.veevapp.customer.ui.specialoffers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veevapp.customer.R;
import com.veevapp.customer.data.models.SpecialOffer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialOfferViewAdapter extends RecyclerView.Adapter<SpecialOfferViewAdapter.ViewHolder> {

    private Context context;
    private List<SpecialOffer> items;

    public SpecialOfferViewAdapter(List<SpecialOffer> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.template_special_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.price.setText(String.format(context.getString(R.string.template_price), items.get(position).getProduct().getPrice()));
        holder.productName.setText(items.get(position).getProduct().getName());
        holder.shopName.setText(items.get(position).getSeller().getShopName());
        Glide.with(context).load(items.get(position).getProduct().getPhotoURLs().get(0)).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo)
        ImageView photo;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.shop_name)
        TextView shopName;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
