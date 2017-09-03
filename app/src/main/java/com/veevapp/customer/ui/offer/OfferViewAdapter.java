package com.veevapp.customer.ui.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.BuyRequestOffer;
import com.veevapp.customer.util.listener.OnItemSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        View view = LayoutInflater.from(context).inflate(R.layout.template_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.suggestedPrice.setText(String.valueOf(items.get(position).getSuggestedPrice()));
        holder.description.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.suggested_price)
        TextView suggestedPrice;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
