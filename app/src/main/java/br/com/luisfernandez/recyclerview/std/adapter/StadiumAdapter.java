package br.com.luisfernandez.recyclerview.std.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder> {

    public static final String TAG = "StadiumAdapter";

    private Context context;
    private List<Stadium> stadiumList;

    int currentAdapterPosition = -1;

    public int getCurrentAdapterPosition()
    {
        return currentAdapterPosition;
    }

    public void setCurrentAdapterPosition(int currentAdapterPosition)
    {
        this.currentAdapterPosition = currentAdapterPosition;
    }

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

        public static final int CURRENT_WINDOW_SIZE = -180;

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

        boolean isVirtualOpened = false;
        boolean isOpened = false;
        float lastXPos = 0;

        public void onChildDraw(
                Canvas canvas,
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                float dX,
                float dY,
                int actionState,
                boolean isCurrentlyActive, ItemTouchUIUtil defaultUIUtil)
        {
            int currentAdapterPosition = ((StadiumAdapter) recyclerView.getAdapter()).getCurrentAdapterPosition();
            Log.d(TAG, String.format("positions: [last: %d] [new: %d]", currentAdapterPosition, viewHolder.getAdapterPosition()));

            if (currentAdapterPosition != viewHolder.getAdapterPosition()) {
                recyclerView.getAdapter().notifyItemChanged(currentAdapterPosition);
                isVirtualOpened = isOpened = false;
                lastXPos = 0;

            }
            ((StadiumAdapter)recyclerView.getAdapter()).setCurrentAdapterPosition(viewHolder.getAdapterPosition());

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;

                recyclerView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(v.getContext(), "Wooooowwww", Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(v.getContext(), "asdasdasd", Toast.LENGTH_SHORT).show();
                    }
                });

                handleBackground(canvas, dX, itemView);
                handleOnDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, defaultUIUtil);
            }
        }

        private void handleBackground(final Canvas canvas, final float dX, final View itemView)
        {
            if (isSwipingToLeft(dX)) {
                Paint p = new Paint();
                p.setARGB(155, 0, 0, 255);

                float bgDX = dX;

                if (isOpened) {
                    bgDX += CURRENT_WINDOW_SIZE;
                }

                Log.d(TAG, String.format("handleBackground: [opened: %s], [dX: %f], [lastXPos: %f]", isOpened, bgDX, lastXPos));

                canvas.drawRect(
                        (float) itemView.getRight() + bgDX ,
                        (float) itemView.getTop(),
                        (float) itemView.getRight(),
                        (float) itemView.getBottom(),
                        p);
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

            if (!isCurrentlyActive) {
                if (isVirtualOpened) {

                    if (realX > CURRENT_WINDOW_SIZE) {
                        realX = CURRENT_WINDOW_SIZE;
                        isOpened = true;
                        //lastXPos = realX;
                    }
                    lastXPos = realX;


                } else {
                    lastXPos = realX;
                }
            } else if (isCurrentlyActive) {

                if (realX < CURRENT_WINDOW_SIZE) {
                    isVirtualOpened = true;
                } else {
                    isVirtualOpened = false;
                }

                if (isOpened) {
                    realX += CURRENT_WINDOW_SIZE;
                    isVirtualOpened = true;
                }

                lastXPos = realX;
            }

            Log.d(TAG, String.format("onChildDraw: [opened: %s], [virtualOpened: %s], [dX: %f], [lastXPos: %f], [active: %s]", isOpened, isVirtualOpened,
                                     realX, lastXPos,
                                     isCurrentlyActive));

            defaultUIUtil.onDraw(canvas, recyclerView, viewHolder.itemView, realX, dY, actionState, isCurrentlyActive);






//            if (!isCurrentlyActive) {
//                float realX = dX;
//
//
//                if (isOpened) {
//                    realX = dX + CURRENT_WINDOW_SIZE;
//                }
//
//                Log.d(TAG, String.format("onChildDraw: [opened: %s] [opening: %s], [dX: %f], [dY: %f], [active: %s] ---",
//                                         isOpened,
//                                         isOpening(realX),
//                                         realX,
//                                         dY,
//                                         isCurrentlyActive)
//                     );
//
//                defaultUIUtil.onDraw(canvas, recyclerView, viewHolder.itemView, realX, dY, actionState, isCurrentlyActive);
//            } else {
//                float realX = dX;
//
//                if (isOpening(dX)) {
//                    realX = dX;
//
//                    if (isOpened) {
//                        realX = dX + CURRENT_WINDOW_SIZE;
//                    }
//                }
//
//                Log.d(TAG, String.format("onChildDraw: [opened: %s] [opening: %s], [dX: %f], [dY: %f], [active: %s]",
//                                         isOpened,
//                                         isOpening(realX),
//                                         realX,
//                                         dY,
//                                         isCurrentlyActive)
//                     );
//
//                defaultUIUtil.onDraw(canvas, recyclerView, viewHolder.itemView, realX, dY, actionState, isCurrentlyActive);
//            }
        }

//        private boolean isOpening(float dX)
//        {
//            boolean result = false;
//
//            if (dX <= 0 && dX <= lastXPos) {
//                result = true;
//            }
//
//            //lastXPos = dX;
//            return result;
//        }

        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(TAG, "onSwiped: " + direction);
            lastXPos = 0;
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

        @NonNull
        private String getActionString(int actionState)
        {
            if (actionState == ItemTouchHelper.ACTION_STATE_IDLE)
            {
                return "ACTION_STATE_IDLE";
            }
            else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG)
            {
                return "ACTION_STATE_DRAG";
            }
            else
            {
                return "ACTION_STATE_SWIPE";
            }
        }

    }


}

