package br.com.luisfernandez.recyclerview.std.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Country;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class StadiumAdapter extends RecyclerView.Adapter<StadiumViewHolder>
{
    private static final String TAG = "StadiumAdapter";

    private Context context;
    private List<Country> stadiumList;

    private int currentAdapterPosition = - 1;

    int getCurrentAdapterPosition()
    {
        return currentAdapterPosition;
    }

    public void setCurrentAdapterPosition(int currentAdapterPosition)
    {
        this.currentAdapterPosition = currentAdapterPosition;
    }

    public StadiumAdapter(Context context, List<Country> stadiumList)
    {
        this.context = context;
        this.stadiumList = stadiumList;
    }

    static int count = 0;

    @Override
    public StadiumViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "TEST onCreateViewHolder: " + (++count));
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_country_list, parent, false);

        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StadiumViewHolder holder, int position)
    {
        Log.d(TAG, "TEST onBindViewHolder: " + holder.getAdapterPosition() + ", tag: " + position);
        //holder.clearSwipeState();


        //holder.itemView.setTag(R.id.tag_position, holder.getAdapterPosition());

        holder.setPosition(position);

        Log.d(TAG, "");

        if (holder.itemView.getTag(R.id.tag_state) == null) {
            holder.itemView.setTag(R.id.tag_state, new SwipeToDeleteState());
            holder.clearSwipeState();
        }

        Country stadium = this.getItem(position);

        holder.textName.setText(stadium.getName());
        holder.textCurrency.setText(stadium.getCurrency());
        holder.textLanguage.setText(stadium.getLanguage());
    }

    @Override
    public int getItemCount()
    {
        return stadiumList.size();
    }

    private Country getItem(int position)
    {
        return stadiumList.get(position);
    }

    public void removeAt(int position) {
        stadiumList.remove(position);
        //notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, stadiumList.size());


        currentAdapterPosition = -1;
    }

    public void handleState(int adapterPosition) {
        this.handleState(adapterPosition, null);
    }

    public void handleState(int adapterPosition, OnNewItemListener onNewItemListener) {
        int currentAdapterPosition = this.getCurrentAdapterPosition();

        Log.d(TAG, String.format("positions: [last: %d] [new: %d]", currentAdapterPosition, adapterPosition));

        if (currentAdapterPosition != adapterPosition && currentAdapterPosition != -1)
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

