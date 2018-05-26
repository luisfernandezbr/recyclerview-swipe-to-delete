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
    public boolean onInterceptTouchEvent(MotionEvent e)
    {
        View viewSwipedRight = this.findChildViewUnder(e.getX() - this.getWidth(), e.getY());
        if (viewSwipedRight != null && e.getAction() == MotionEvent.ACTION_DOWN) {
            int position = (int) viewSwipedRight.getTag();

            Log.d(TAG, "onTouchPosition: " + position);
//            StadiumAdapter stadiumAdapter = (StadiumAdapter) getAdapter();
//            stadiumAdapter.removeAt(position);
//            stadiumAdapter.notifyDataSetChanged();
            //stadiumAdapter.notifyItemRemoved(position);

            Toast.makeText(getContext(), "CLICKED", Toast.LENGTH_LONG).show();
        }

        return super.onInterceptTouchEvent(e);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
