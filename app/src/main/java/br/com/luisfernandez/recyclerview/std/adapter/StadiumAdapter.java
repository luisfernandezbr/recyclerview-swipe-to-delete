package br.com.luisfernandez.recyclerview.std.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class StadiumAdapter extends RecyclerView.Adapter<StadiumViewHolder>
{
    private static final String TAG = "StadiumAdapter";

    private Context context;
    private List<Stadium> stadiumList;

    private int currentAdapterPosition = - 1;

    int getCurrentAdapterPosition()
    {
        return currentAdapterPosition;
    }

    public void setCurrentAdapterPosition(int currentAdapterPosition)
    {
        this.currentAdapterPosition = currentAdapterPosition;
    }

    public StadiumAdapter(Context context, List<Stadium> stadiumList)
    {
        this.context = context;
        this.stadiumList = stadiumList;
    }

    @Override
    public StadiumViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "TEST onCreateViewHolder: ");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_stadium_list, parent, false);

        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StadiumViewHolder holder, int position)
    {
        Log.d(TAG, "TEST onBindViewHolder: ");
        //holder.clearSwipeState();


        holder.itemView.setTag(position);

        if (holder.itemView.getTag(R.id.tag_state) == null) {
            holder.itemView.setTag(R.id.tag_state, new SwipeToDeleteState());
        }

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
    public int getItemCount()
    {
        return stadiumList.size();
    }

    private Stadium getItem(int position)
    {
        return stadiumList.get(position);
    }

    public void removeAt(int position) {
        stadiumList.remove(position);
    }

    public void handleState(int adapterPosition) {
        this.handleState(adapterPosition, null);
    }

    public void handleState(int adapterPosition, OnNewItemListener onNewItemListener) {
        int currentAdapterPosition = this.getCurrentAdapterPosition();

        Log.d(TAG, String.format("positions: [last: %d] [new: %d]", currentAdapterPosition, adapterPosition));

        if (currentAdapterPosition != adapterPosition)
        {
            notifyItemChanged(currentAdapterPosition);

            if (onNewItemListener != null) {
                onNewItemListener.onNewItemFound();
            }
        }

        setCurrentAdapterPosition(adapterPosition);
    }

    public interface OnNewItemListener {
        void onNewItemFound();
    }


}

