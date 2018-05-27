package br.com.luisfernandez.recyclerview.std.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import br.com.luisfernandez.recyclerview.std.view.CustomRecyclerView;

/**
 * Created by luisfernandez on 25/05/18.
 */
public class AppSimpleCallback extends ItemTouchHelper.SimpleCallback
{
    public static final String TAG = "AppSimpleCallback";
    private final CustomRecyclerView recyclerView;

    private float swipeThreshold = 0.9f;

    public AppSimpleCallback(int dragDirs, int swipeDirs, CustomRecyclerView recyclerView)
    {
        super(dragDirs, swipeDirs);
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        Log.d(TAG, "onSwiped: " + direction);
        StadiumViewHolder viewHolder1 = (StadiumViewHolder) viewHolder;
        viewHolder1.clearSwipeState();
        ((StadiumAdapter) recyclerView.getAdapter()).removeAt(viewHolder.getAdapterPosition());
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return swipeThreshold;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return 2.1f * defaultValue;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        Log.d(TAG, "clearView: ");
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                boolean isCurrentlyActive)
    {
        Log.d(TAG, "onChildDrawOver: ");
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDraw(
            Canvas c,
            RecyclerView recyclerView,
            RecyclerView.ViewHolder viewHolder,
            float dX,
            float dY,
            int actionState,
            boolean isCurrentlyActive)
    {

        Log.d(TAG, "onChildDraw: ");

        StadiumViewHolder viewHolder1 = (StadiumViewHolder) viewHolder;
        if (!viewHolder1.isOpened() && ! viewHolder1.isVirtualOpened())
        {
            //super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            viewHolder1.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, getDefaultUIUtil());
        }
        else
        {
            viewHolder1.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, getDefaultUIUtil());
        }
    }

    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy)
    {
        Log.d(TAG, String.format("getAnimationDuration: [animateDx: %f], [animateDy: %f], [animationType: %s]",
                                 animateDx,
                                 animateDy,
                                 getAnimationTypeString(animationType)
                                ));
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @NonNull
    private String getAnimationTypeString(int animationType)
    {
        if (animationType == ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS) {
            return "ANIMATION_TYPE_SWIPE_SUCCESS";
        } else {
            return "ANIMATION_TYPE_SWIPE_CANCEL";
        }
    }
}
