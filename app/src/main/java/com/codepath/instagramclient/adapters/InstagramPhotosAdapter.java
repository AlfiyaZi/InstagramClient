package com.codepath.instagramclient.adapters;

import com.codepath.instagramclient.R;
import com.codepath.instagramclient.helpers.CircleTransform;
import com.codepath.instagramclient.helpers.Utilities;
import com.codepath.instagramclient.models.InstagramPhoto;
import com.squareup.picasso.Picasso;

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

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder; // view lookup cache stored in tag
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Event handlers
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePhoto();
            }
        });
        holder.tvLikesCount.setOnClickListener(new View.OnClickListener() {
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

        holder.tvCaption.setText(Html.fromHtml(caption));
        holder.tvUsername.setText(Html.fromHtml(username));
        holder.tvLikesCount.setText(Html.fromHtml(likes));
        holder.tvRelativeTimeStamp.setText(relativeDateTimeString);
        holder.tvFullName.setText(photo.fullName);

        // Clear out the image views
        holder.ivPhoto.setImageResource(0);
        holder.ivUserIcon.setImageResource(0);

        // Insert the image using picasso
        Picasso.with(getContext()).load(photo.imageURL).placeholder(R.drawable.placeholder).into(holder.ivPhoto);
        Picasso.with(getContext()).load(photo.profilePictureURL).transform(new CircleTransform()).into(holder.ivUserIcon);

        // Return the completed view to render on screen
        return view;
    }

    private void likePhoto() {
        Toast toast = Toast.makeText(getContext(),
                "♥", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // View lookup cache
    static class ViewHolder {

        // Automatically finds each field by the specified ID.
        @Bind(R.id.tvCaption)
        TextView tvCaption;
        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;
        @Bind(R.id.ivUserIcon)
        ImageView ivUserIcon;
        @Bind(R.id.tvUsername)
        TextView tvUsername;
        @Bind(R.id.tvFullName)
        TextView tvFullName;
        @Bind(R.id.tvLikesCount)
        TextView tvLikesCount;
        @Bind(R.id.tvRelativeTimeStamp)
        TextView tvRelativeTimeStamp;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}