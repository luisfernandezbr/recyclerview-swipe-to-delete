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
//https://stackoverflow.com/questions/44965278/recyclerview-itemtouchhelper-buttons-on-swipe/45062745
//https://stackoverflow.com/questions/30820806/adding-a-colored-background-with-text-icon-under-swiped-row-when-using-androids/34687548
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

    private void handleOnChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                   boolean isCurrentlyActive)
    {
        Drawable deleteIcon = ContextCompat.getDrawable(recyclerView.getContext(), android.R.drawable.ic_delete);
        int intrinsicWidth = deleteIcon.getIntrinsicWidth();
        int  intrinsicHeight = deleteIcon.getIntrinsicHeight();
        ColorDrawable background = new ColorDrawable();
        int backgroundColor = Color.parseColor("#f44336");
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCanceled = dX == 0f && !isCurrentlyActive;

        if (isCanceled) {
            clearCanvas(c, itemView.getRight() + dX, (float)itemView.getTop(), (float)itemView.getRight(), (float)itemView.getBottom(), clearPaint);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else {
// Draw the red delete background
            background.setColor(backgroundColor);
            background.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            background.draw(c);

            // Calculate position of delete icon
            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;

            // Draw the delete icon
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteIcon.draw(c);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom, Paint clearPaint)
    {
        if (c != null) {
            c.drawRect(left, top, right, bottom, clearPaint);
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
