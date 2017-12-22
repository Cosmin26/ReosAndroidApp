package ro.ubb.reosandroidapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ro.ubb.reosandroidapp.ItemClickListener;
import ro.ubb.reosandroidapp.R;

/**
 * Created by CristianCosmin on 21.12.2017.
 */

public class ApartmentAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleTextView;
    public ImageView coverImageView;
    ItemClickListener itemClickListener;

    public ApartmentAdapterViewHolder(View v) {
        super(v);
        titleTextView = (TextView) v.findViewById(R.id.titleTextView);
        coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
