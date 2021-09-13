package com.example.ofiicial.PROFILE.PhotosCompare;

import android.graphics.Bitmap;

public class Photos
{
    private Bitmap photo_url;
    private String photo_date;
    private int photo_id;
    private int photo_weight;


    public int getPhoto_id()
    {
        return photo_id;
    }

    public void setPhoto_id(int photo_id)
    {
        this.photo_id = photo_id;
    }

    public Bitmap getPhoto_url()
    {
        return photo_url;
    }

    public void setPhoto_url(Bitmap photo_url)
    {
        this.photo_url = photo_url;
    }

    public String getPhoto_date()
    {
        return photo_date;
    }

    public void setPhoto_date(String photo_date)
    {
        this.photo_date = photo_date;
    }

    public int getPhoto_weight()
    {
        return photo_weight;
    }

    public void setPhoto_weight(int photo_weight)
    {
        this.photo_weight = photo_weight;
    }

    public Photos(Bitmap photo_url, String photo_date, int photo_weight)
    {
        this.photo_url = photo_url;
        this.photo_date = photo_date;
        this.photo_weight = photo_weight;
    }

    @Override
    public String toString() {
        return "ProfilePhotosComparePhoto{" +
                "photo_url='" + photo_url + '\'' +
                ", photo_data='" + photo_date + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", photo_weight=" + photo_weight +
                '}';
    }
}
