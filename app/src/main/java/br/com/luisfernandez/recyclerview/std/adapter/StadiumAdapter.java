package br.com.luisfernandez.recyclerview.std.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Country;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class StadiumAdapter extends RecyclerView.Adapter<StadiumViewHolder>
{
    private static final String TAG = "StadiumAdapter";

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

    public StadiumAdapter(List<Country> stadiumList)
    {
        this.stadiumList = stadiumList;
    }

    static int count = 0;

    @Override
    public StadiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: " + (++count));

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_country_list, parent, false);

        return new StadiumViewHolder(view, count);
    }

    @Override
    public void onBindViewHolder(@NonNull StadiumViewHolder holder, int position)
    {
        Log.d(TAG, "onBindViewHolder: " + currentAdapterPosition + ", pos: " + position + ", holderId: " + holder.getViewHolderId());

        // Handle swipe to delete
        holder.setPosition(position);

        holder.itemView.setTag(R.id.tag_state, new SwipeToDeleteState());
        holder.clearSwipeState();

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(v.getContext(), "ITEM CLICKED", Toast.LENGTH_SHORT).show();
                handleState(-1);
            }
        });

        // Bind data
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

