package br.com.luisfernandez.recyclerview.std.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder> {

    private Context context;
    private List<Stadium> stadiumList;

    public StadiumAdapter(Context context, List<Stadium> stadiumList) {
        this.context = context;
        this.stadiumList = stadiumList;
    }

    @Override
    public StadiumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_stadium_list, parent, false);

        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StadiumViewHolder holder, int position) {
        Stadium stadium = this.getItem(position);

        holder.textName.setText(stadium.getName());
        holder.textCapacity.setText(String.valueOf(stadium.getCapacity()));
        holder.textFoundationDate.setText(stadium.getFoundation());


        Resources res = context.getResources();
        String quantityString = res.getQuantityString(R.plurals.text_like_plural, stadium.getLikes(), stadium.getLikes());
        holder.textLikeCount.setText(quantityString);

        Picasso.with(context).load(stadium.getIconUrl()).into(holder.imageStadiumPhoto);
    }

    @Override
    public int getItemCount() {
        return stadiumList.size();
    }

    private Stadium getItem(int position) {
        return stadiumList.get(position);
    }

    public static class StadiumViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textCapacity;
        TextView textFoundationDate;
        TextView textLikeCount;
        ImageView imageStadiumPhoto;

        public StadiumViewHolder(View itemView) {
            super(itemView);

            this.textName = itemView.findViewById(R.id.textName);
            this.textCapacity = itemView.findViewById(R.id.textCapacity);
            this.textFoundationDate = itemView.findViewById(R.id.textFoundationDate);
            this.textLikeCount = itemView.findViewById(R.id.textLikes);
            this.imageStadiumPhoto = itemView.findViewById(R.id.imageIcon);
        }
    }
}

