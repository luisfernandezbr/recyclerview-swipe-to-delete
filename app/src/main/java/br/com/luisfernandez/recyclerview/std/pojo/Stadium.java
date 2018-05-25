package br.com.luisfernandez.recyclerview.std.pojo;

import java.io.Serializable;

public class Stadium implements Serializable
{
    private static final long serialVersionUID = 7897923575963932477L;

    private String name;
    private String foundation;
    private String iconUrl;
    private int capacity;
    private int likes;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFoundation()
    {
        return this.foundation;
    }

    public void setFoundation(String foundation)
    {
        this.foundation = foundation;
    }

    public String getIconUrl()
    {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getLikes()
    {
        return this.likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }
}
