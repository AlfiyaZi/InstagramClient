package com.codepath.instagramclient.adapters;

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

import com.codepath.instagramclient.R;
import com.codepath.instagramclient.helpers.CircleTransform;
import com.codepath.instagramclient.helpers.Utilities;
import com.codepath.instagramclient.models.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            // Look up views to populate data
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikesCount);
            viewHolder.tvCreatedTime = (TextView) convertView.findViewById(R.id.tvRelativeTimeStamp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Event handlers
        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePhoto();
            }
        });
        viewHolder.tvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePhoto();
            }
        });

        // Populate the data into the template view using the data object
        String caption = "<b>" + photo.username + "</b><font color=#8e8e93> " + photo.caption + "</font>";
        String username = "<b>" + photo.username + "</b>";
        String likes = "<b>♥ " + NumberFormat.getInstance().format(photo.likesCount) + " likes</b>";
        String relativeDateTimeString = Utilities.getRelativeTimeSpanString_Formatted(photo.relativeTimeStamp);

        viewHolder.tvCaption.setText(Html.fromHtml(caption));
        viewHolder.tvUsername.setText(Html.fromHtml(username));
        viewHolder.tvLikes.setText(Html.fromHtml(likes));
        viewHolder.tvCreatedTime.setText(relativeDateTimeString);
        viewHolder.tvFullName.setText(photo.fullName);

        // Clear out the image views
        viewHolder.ivPhoto.setImageResource(0);
        viewHolder.ivUserIcon.setImageResource(0);

        // Insert the image using picasso
        Picasso.with(getContext()).load(photo.imageURL).placeholder(R.drawable.placeholder).into(viewHolder.ivPhoto);
        Picasso.with(getContext()).load(photo.profilePictureURL).transform(new CircleTransform()).into(viewHolder.ivUserIcon);

        // Return the completed view to render on screen
        return convertView;
    }

    private void likePhoto() {
        Toast toast = Toast.makeText(getContext(),
                "♥", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // View lookup cache
    private static class ViewHolder {
        TextView tvCaption;
        ImageView ivPhoto;
        ImageView ivUserIcon;
        TextView tvUsername;
        TextView tvFullName;
        TextView tvLikes;
        TextView tvCreatedTime;
    }
}