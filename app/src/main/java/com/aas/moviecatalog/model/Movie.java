package com.aas.moviecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
	private int id;
	private String title;
	private double voteAverage;
	private String posterPath;
	private String releaseDate;
	private String overview;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.title);
		dest.writeDouble(this.voteAverage);
		dest.writeString(this.posterPath);
		dest.writeString(this.releaseDate);
		dest.writeString(this.overview);
	}

	public Movie() {
	}

	protected Movie(Parcel in) {
		this.id = in.readInt();
		this.title = in.readString();
		this.voteAverage = in.readDouble();
		this.posterPath = in.readString();
		this.releaseDate = in.readString();
		this.overview = in.readString();
	}

	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}