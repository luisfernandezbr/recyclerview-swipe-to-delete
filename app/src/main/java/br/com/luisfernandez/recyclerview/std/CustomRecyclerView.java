package br.com.luisfernandez.recyclerview.std;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import br.com.luisfernandez.recyclerview.std.adapter.StadiumAdapter;
import br.com.luisfernandez.recyclerview.std.adapter.StadiumViewHolder;

/**
 * Created by luisfernandez on 25/05/18.
 */

public class CustomRecyclerView extends RecyclerView
{

    public static final String TAG = "CustomRecyclerView";

    public CustomRecyclerView(Context context)
    {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        Log.d(TAG, "onInterceptTouchEvent: " + e.getAction());

        View viewSwipedRight = this.findChildViewUnder(e.getX() - this.getWidth(), e.getY());

        if (viewSwipedRight != null && e.getAction() == MotionEvent.ACTION_UP) {
            ViewHolder holder = findContainingViewHolder(viewSwipedRight);
            StadiumViewHolder viewHolder = (StadiumViewHolder) holder;
            viewHolder.clearSwipeState();
            ((StadiumAdapter) getAdapter()).removeAt(viewHolder.getAdapterPosition());

            Toast.makeText(getContext(), "CLICKED", Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        return super.onInterceptTouchEvent(e);
    }

    private StadiumAdapter getStadiumAdapter() {
        return (StadiumAdapter) getAdapter();
    }

    @Override
    public void onScrollStateChanged(int state)
    {
        if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            getStadiumAdapter().handleState(-1);
        }
        super.onScrollStateChanged(state);
    }
}
