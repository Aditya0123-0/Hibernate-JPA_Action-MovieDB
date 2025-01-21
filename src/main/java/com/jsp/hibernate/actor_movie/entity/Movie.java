package com.jsp.hibernate.actor_movie.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Movie {
	@Id
	private int movieId;
	private String movieName;
	private String director;
	private String genre;
	private String verdict;
	private int collection;
	
	@ManyToMany
	private List<Actor> actor;

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", director=" + director + ", genre=" + genre
				+ ", verdict=" + verdict + ", collection=" + collection + ", actors=" + actor + "]";
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}

	public List<Actor> getActors() {
		return actor;
	}

	public void setActors(List<Actor> actors) {
		this.actor = actors;
	}

}
