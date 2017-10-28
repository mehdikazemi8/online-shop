package com.veevapp.customer.ui.specialoffers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.Slider;
import com.veevapp.customer.util.imageloader.ImageHandler;
import com.veevapp.customer.util.listener.OnItemSelectedListener;
import com.veevapp.customer.view.adapter.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SliderViewAdapter extends BaseRecyclerAdapter<SliderViewAdapter.ViewHolder, Slider> {

    private Context context;
    private OnItemSelectedListener<Slider> onItemSelectedListener;

    public SliderViewAdapter(OnItemSelectedListener<Slider> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.template_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageHandler.getInstance(context).loadImage(
                getItem(position).getPhotoURL(),
                holder.photo,
                false,
                true,
                true,
                0
        );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @OnClick(R.id.iv_photo)
        public void itemOnClick() {
            onItemSelectedListener.onSelect(getItem(getAdapterPosition()));
        }

        @BindView(R.id.iv_photo)
        ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
