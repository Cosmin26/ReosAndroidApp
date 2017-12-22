package ro.ubb.reosandroidapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ro.ubb.reosandroidapp.DetailActivity;
import ro.ubb.reosandroidapp.ItemClickListener;
import ro.ubb.reosandroidapp.R;
import ro.ubb.reosandroidapp.model.Apartment;

/**
 * Created by CristianCosmin on 21.12.2017.
 */

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapterViewHolder> {
    private List<Apartment> apartments;
    private Context context;

    public ApartmentAdapter(List<Apartment> Data, Context context) {
        this.context = context;
        apartments = Data;
    }

    @Override
    public ApartmentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_items, parent, false);
        return new ApartmentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApartmentAdapterViewHolder holder, final int position) {

        final Apartment apartment = apartments.get(position);
//        final int positionToSent = position;

        holder.titleTextView.setText(apartment.getTitle());
        holder.coverImageView.setImageBitmap(BitmapFactory.decodeByteArray(apartment.getImage(),
                0, apartment.getImage().length));
        holder.coverImageView.setTag(apartment.getImage());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick() {
                openDetailActivity(apartment.getId());
            }
        });
    }

    public void openDetailActivity(Long id) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);

        Intent i = new Intent(context, DetailActivity.class);

        //PACK DATA
//        i.putExtra("TITLE_KEY", title);
//        i.putExtra("IMAGE_KEY", byteArrayOutputStream.toByteArray());
        i.putExtra("ID_KEY", id);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }
}