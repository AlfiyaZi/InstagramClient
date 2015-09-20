package com.codepath.instagramclient;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Look up views to populate data
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikesCount);
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvRelativeTimeStamp);


        // Event handlers
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePhoto();
            }
        });
        tvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePhoto();
            }

        });

        // Insert the model data into each of the view items
        String caption = "<b>" + photo.username + "</b><font color=#8e8e93> " + photo.caption + "</font>";
        String username = "<b>" + photo.username + "</b>";
        String likes = "<b>♥ " + NumberFormat.getInstance().format(photo.likesCount) + " likes</b>";
        String relativeDateTimeString = Utilities.getRelativeTimeSpanString_Formatted(photo.relativeTimeStamp);

        tvCaption.setText(Html.fromHtml(caption));
        tvUsername.setText(Html.fromHtml(username));
        tvLikes.setText(Html.fromHtml(likes));
        tvCreatedTime.setText("\uD83D\uDD57 " + relativeDateTimeString);
        tvFullName.setText(photo.fullName);

        // Clear out the image views
        ivPhoto.setImageResource(0);
        ivUserIcon.setImageResource(0);

        // Insert the image using picasso
        Picasso.with(getContext()).load(photo.imageURL).placeholder(R.drawable.placeholder).into(ivPhoto);
        Picasso.with(getContext()).load(photo.profilePictureURL).transform(new CircleTransform()).into(ivUserIcon);

        return convertView;
    }

    private void likePhoto() {
        Toast toast = Toast.makeText(getContext(),
                "♥", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
