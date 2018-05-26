package br.com.luisfernandez.recyclerview.std.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.luisfernandez.recyclerview.std.R;

/**
 * Created by luisfernandez on 26/05/18.
 */
public class StadiumViewHolder extends RecyclerView.ViewHolder implements StadiumAdapter.OnNewItemListener
{
    public static final String TAG = "StadiumViewHolder";

    public static final int CURRENT_WINDOW_SIZE = - 320;

    TextView textName;
    TextView textCurrency;
    TextView textLanguage;

    private int adapterPosition;

    public StadiumViewHolder(View itemView)
    {
        super(itemView);
        Log.d(TAG, "onNew StadiumViewHolder: ");

        this.textName = itemView.findViewById(R.id.textName);
        this.textCurrency = itemView.findViewById(R.id.textCurrency);
        this.textLanguage = itemView.findViewById(R.id.textLanguage);
    }

    public void onChildDraw(
            Canvas canvas,
            RecyclerView recyclerView,
            RecyclerView.ViewHolder viewHolder,
            float dX,
            float dY,
            int actionState,
            boolean isCurrentlyActive, ItemTouchUIUtil defaultUIUtil)
    {
        Log.d(TAG, "LUIS onChildDraw: " + actionState + ", position: " + adapterPosition + ", real: " + viewHolder.getAdapterPosition());

        ((StadiumAdapter) recyclerView.getAdapter()).handleState(viewHolder.getAdapterPosition(), this);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
        {
            View itemView = viewHolder.itemView;

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(v.getContext(), "ITEM CLICKED", Toast.LENGTH_SHORT).show();
                }
            });

            handleBackground(canvas, dX, itemView);
            handleOnDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, defaultUIUtil);
        }
    }

    private void handleBackground(final Canvas canvas, final float dX, final View itemView)
    {
        if (isSwipingToLeft(dX))
        {
            Paint p = new Paint();
            p.setARGB(255, 126, 83, 237);

            float bgDX = dX;

            if (isOpened())
            {
                bgDX += CURRENT_WINDOW_SIZE;
            }

            Log.d(TAG, String.format("handleBackground: [opened: %s], [dX: %f], [lastXPos: %f]", isOpened(), bgDX, getLastXPos()));
            canvas.drawRect(
                    (float) itemView.getRight() + bgDX,
                    (float) itemView.getTop(),
                    (float) itemView.getRight(),
                    (float) itemView.getBottom(),
                    p);

            int iconMargin = 60;

            Drawable d = itemView.getResources().getDrawable(R.drawable.vector_explode);

            int bottomPosition = itemView.getBottom() - iconMargin;
            int topPosition = itemView.getTop() + iconMargin;
            int height = bottomPosition - topPosition;
            int rightPosition = itemView.getRight() - iconMargin;
            int leftPosition = rightPosition - (540 * height / 400);

            d.setBounds(leftPosition,
                        topPosition,
                        rightPosition,
                        bottomPosition);
            d.draw(canvas);
        }
    }

    private boolean isSwipingToLeft(float dX)
    {
        return dX <= 0;
    }

    private void handleOnDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                              boolean isCurrentlyActive, ItemTouchUIUtil defaultUIUtil)
    {
        float realX = dX;

        if (! isCurrentlyActive)
        {
            if (isVirtualOpened())
            {
                if (realX > CURRENT_WINDOW_SIZE)
                {
                    realX = CURRENT_WINDOW_SIZE;
                    setOpened(true);
                    //lastXPos = realX;
                }
                setLastXPos(realX);
            }
            else
            {
                setLastXPos(realX);
            }
        }
        else if (isCurrentlyActive)
        {

            if (realX < CURRENT_WINDOW_SIZE)
            {
                setVirtualOpened(true);
            }
            else
            {
                setVirtualOpened(false);
            }

            if (isOpened())
            {
                realX += CURRENT_WINDOW_SIZE;
                setVirtualOpened(true);
            }

            setLastXPos(realX);
        }

        Log.d(TAG, String.format("onChildDraw: [opened: %s], [virtualOpened: %s], [dX: %f], [lastXPos: %f], [active: %s]", isOpened(), isVirtualOpened(),
                                 realX, getLastXPos(),
                                 isCurrentlyActive));

        defaultUIUtil.onDraw(canvas, recyclerView, viewHolder.itemView, realX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onNewItemFound()
    {
        clearSwipeState();
    }

    public void clearSwipeState()
    {
        setVirtualOpened(false);
        setOpened(false);
        setLastXPos(0);
    }

    public boolean isVirtualOpened()
    {
        return getState().isVirtualOpened();
    }

    public void setVirtualOpened(boolean virtualOpened)
    {
        getState().setVirtualOpened(virtualOpened);
    }

    public boolean isOpened()
    {
        return getState().isOpened();
    }

    public void setOpened(boolean opened)
    {
        getState().setOpened(opened);
    }

    public float getLastXPos()
    {
        return getState().getLastXPos();
    }

    public void setLastXPos(float lastXPos)
    {
        getState().setLastXPos(lastXPos);
    }

    private SwipeToDeleteState getState()
    {
        return (SwipeToDeleteState)itemView.getTag(R.id.tag_state);
    }

    public void setPosition(int adapterPosition)
    {
        this.adapterPosition = adapterPosition;
    }
}
