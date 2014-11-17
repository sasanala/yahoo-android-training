package com.example.instagramphotoviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	// takes a data item at a position, converts it to a row in the listview
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// take the data source at position
		// get the data item
		InstagramPhoto photo = getItem(position);
		
		// check if we are using a recycled template
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false); // false means not ready for attaching
		}
		// lookup the subview within the template
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		
		// populate the subview (textfield, imageview) with the correct data
		tvUsername.setText(photo.username);
		tvCaption.setText(photo.caption);
		
		// set the image height before loading
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		
		// reset the image from the recycled view
		imgPhoto.setImageResource(0);
		
		// ask for the photo to be added to the imageview based on the photo url
		// background: send a network request to the url, download the image bytes. convert into bitmap, resizing the image, insert bitmap into the imageview
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		// return the view for that data item
		return convertView;
	}
	
	// getView method (int position)
	
	// default, takes the model (Instagram 
}
