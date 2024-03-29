package com.veevapp.customer.ui.buyrequests;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.util.listener.OnItemSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyRequestViewAdapter extends RecyclerView.Adapter<BuyRequestViewAdapter.ViewHolder> {

    private Context context;
    private List<BuyRequest> items;
    private OnItemSelectedListener<BuyRequest> onItemSelectedListener;

    public BuyRequestViewAdapter(List<BuyRequest> items, OnItemSelectedListener<BuyRequest> onItemSelectedListener) {
        this.items = items;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.template_buy_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuyRequest br = items.get(position);
        holder.tvName.setText(br.getProduct().getName());
        holder.tvDesc.setText(br.getDescription());
        holder.tvCount.setText(String.valueOf(br.getOfferCount()));
        holder.tvRequestCount.setText(context.getString(R.string.template_count,String.valueOf(br.getCount())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_requestCount)
        TextView tvRequestCount;

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
