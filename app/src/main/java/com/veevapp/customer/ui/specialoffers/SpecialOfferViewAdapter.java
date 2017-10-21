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
import com.veevapp.customer.util.AppHandler;
import com.veevapp.customer.util.imageloader.ImageHandler;
import com.veevapp.customer.util.listener.OnItemPositionSelectedListener;
import com.veevapp.customer.view.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecialOfferViewAdapter extends BaseRecyclerAdapter<SpecialOfferViewAdapter.ViewHolderSpecialOffer,SpecialOffer> {

    private Context context;
    private OnItemPositionSelectedListener<SpecialOffer> onItemSelectedListener;

    public SpecialOfferViewAdapter(OnItemPositionSelectedListener<SpecialOffer> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolderSpecialOffer onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.template_special_offer, parent, false);
        return new ViewHolderSpecialOffer(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderSpecialOffer holder, int position) {
        SpecialOffer so = getItem(position);

        //TODO REMOVE
        List<String> photoList = new ArrayList<>();
        photoList.add("http://lorempixel.com/output/people-q-c-480-480-" + ((position%10)+1) + ".jpg");
        so.getProduct().setPhotoURLs(photoList);
        so.getSeller().setRate(new Random(System.currentTimeMillis()).nextFloat()%5);
        so.getSeller().setSellerPhotoUrl("http://lorempixel.com/output/people-q-c-480-480-" + ((position%10)+2) + ".jpg");
        so.getProduct().setPrice("1000");
        switch (position%3){
            case 0 : so.setDuration(90); break;
            case 1 : so.setDuration(3900); break;
            case 2 : so.setDuration(90000); break;
        }



        holder.tvPrice.setText(String.format(context.getString(R.string.template_price), so.getSuggestedPrice() + ""));
        holder.tvPreviousPrice.setText(String.format(context.getString(R.string.template_previous_price), so.getProduct().getPrice() + ""));
        holder.tvProductName.setText(so.getProduct().getName());
        holder.tvShopName.setText(context.getString(R.string.template_shop,so.getSeller().getShopName()));


        List<String> urls = so.getProduct().getPhotoURLs();
        String url = "";
        if(urls!=null && urls.size()>0)
            url = urls.get(0);
        ImageHandler.getInstance(context)
                .loadImage(url,holder.ivImage,
                false,true,true,0);

        ViewCompat.setTransitionName(holder.ivImage, context.getString(R.string.transition_special_offer_photo_index, position));

        holder.refreshTimer();
    }

    class ViewHolderSpecialOffer extends RecyclerView.ViewHolder {

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
            onItemSelectedListener.onSelect(getAllItems().get(getAdapterPosition()), getAdapterPosition());
        }

        public ViewHolderSpecialOffer(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            tvPreviousPrice.setPaintFlags(tvPreviousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public void refreshTimer(){
            AppHandler.RemainingTimeObject rto = AppHandler.getRemainingTime(getItem(getAdapterPosition()));
            tvTimer.setText(AppHandler.getRemainingTimeStr(context,rto));
        }
    }
}
