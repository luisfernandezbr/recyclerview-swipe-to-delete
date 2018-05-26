package br.com.luisfernandez.recyclerview.std.adapter;

import java.io.Serializable;

/**
 * Created by luisfernandez on 26/05/18.
 */

public class SwipeToDeleteState implements Serializable
{
    private boolean isVirtualOpened = false;
    private boolean isOpened = false;
    private float lastXPos = 0;

    public boolean isVirtualOpened()
    {
        return isVirtualOpened;
    }

    public void setVirtualOpened(boolean virtualOpened)
    {
        isVirtualOpened = virtualOpened;
    }

    public boolean isOpened()
    {
        return isOpened;
    }

    public void setOpened(boolean opened)
    {
        isOpened = opened;
    }

    public float getLastXPos()
    {
        return lastXPos;
    }

    public void setLastXPos(float lastXPos)
    {
        this.lastXPos = lastXPos;
    }
}
