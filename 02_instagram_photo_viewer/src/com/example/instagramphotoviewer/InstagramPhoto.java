package com.example.instagramphotoviewer;

public class InstagramPhoto {
	// username. caption, image_url, height, likes_count
	
	public String username;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likesCount;
	
	public String toString() {
		return "image url: " + imageUrl;
	}
}
