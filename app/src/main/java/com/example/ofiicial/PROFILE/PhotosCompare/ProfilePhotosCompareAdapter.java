package com.example.ofiicial.PROFILE.PhotosCompare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ofiicial.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProfilePhotosCompareAdapter extends RecyclerView.Adapter<ProfilePhotosCompareAdapter.MyViewHolder>
{

    Context context;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] image_to_pass_inByte;

    ArrayList<Photos> photos = new ArrayList<>();

    public ProfilePhotosCompareAdapter()
    {
    }

    @Override
    public ProfilePhotosCompareAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_photos_compare_single_photo, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.PC_single_image.setImageBitmap(photos.get(position).getPhoto_url());
        holder.PC_single_date.setText(photos.get(position).getPhoto_date());
    }

    
    @Override
    public int getItemCount()
    {
        return photos.size();
    }

    public void setPhotos(ArrayList<Photos> photos)
    {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView PC_single_image;
        TextView PC_single_date;


        public MyViewHolder(View itemView)
        {
            super(itemView);
            PC_single_date = itemView.findViewById(R.id.PC_single_date);
            PC_single_image = itemView.findViewById(R.id.PC_single_image);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent  = new Intent(view.getContext(), ProfilePhototComparePhotoShow.class);
                    intent.putExtra("WEIGHT", String.valueOf(photos.get(getAdapterPosition()).getPhoto_weight()));

                    Bitmap image_to_pass = photos.get(getAdapterPosition()).getPhoto_url();
                    objectByteArrayOutputStream = new ByteArrayOutputStream();
                    image_to_pass.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
                    image_to_pass_inByte = objectByteArrayOutputStream.toByteArray();
                    intent.putExtra("IMAGE", image_to_pass_inByte);

                    intent.putExtra("ID", photos.get(getAdapterPosition()).getPhoto_id());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }


}
