package com.jsp.hibernate.actor_movie.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.jsp.hibernate.actor_movie.entity.Actor;
import com.jsp.hibernate.actor_movie.entity.Movie;

public class MovieDao {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql-config");
	 Scanner sc = new Scanner(System.in);
	 
	 public void addMovie() {
		 Movie movie = new Movie();
			System.out.println("Enter Movie Id:");
			movie.setMovieId(sc.nextInt());
			System.out.println("Enter Movie Name:");
			movie.setMovieName(sc.next());
			System.out.println("Enter Director:");
			movie.setDirector(sc.next());
			System.out.println("Enter Gener");
			movie.setGenre(sc.next());
			System.out.println("Enter Verdict:");
			movie.setVerdict(sc.next());
			System.out.println("Enter Movie Collection:");
			movie.setCollection(sc.nextInt());
			
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			System.out.println();
			System.out.println("-------------------All Actor Details------------------------");
			System.out.println();
			Query query = entityManager.createQuery("From Actor");
			List<Actor> actorList = query.getResultList();
			for (Actor actor : actorList) {
				System.out.println(actor);
			}
			System.out.println();
			System.out.println("Choose Actor Ids to be added to the movie");
			
			List<Actor> actorListMovie = new ArrayList<Actor>();
			
			String ids=sc.next();
			for(String id:ids.split(",")) {
				int actorId = Integer.parseInt(id);
				Actor actor = entityManager.find(Actor.class, actorId);
				actorListMovie.add(actor);
			}
			
			movie.setActors(actorListMovie);
			entityManager.persist(movie);
			
			transaction.commit();
			entityManager.close();
			
			
	 }
	 
	 public void findMovieByName() {
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			Query query = entityManager.createQuery("Select m from Movie m where movieName=:name");
			System.out.println("Enter Movie Name:");
			query.setParameter("name",sc.next());
			Movie movie = (Movie)query.getSingleResult();
			
			System.out.println(movie);
			
			transaction.commit();
			entityManager.close();
	 }
	 public void findAllMovieByGener() {
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
			Root<Movie> from = query.from(Movie.class);
			query.where(criteriaBuilder.equal(from.get("genre"), sc.next()));
			
			
			TypedQuery<Movie> typedQuery = entityManager.createQuery(query);
			 List<Movie> movieList = typedQuery.getResultList();
			 for (Movie movie : movieList) {
				System.out.println(movie);
			}
			 
			 transaction.commit();
			 entityManager.close();
		 
	 }
	 public void findAllMovieByDirector() {
		    EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			Query query = entityManager.createQuery("select m form Movie m where m.director=:name");
			System.out.println("Enter Director Name:");
			query.setParameter("name", sc.next());
			List<Movie> movieList = query.getResultList();
			for(Movie movie : movieList) {
				System.out.println(movie);
			}
			
			transaction.commit();
			entityManager.close();
		 
	 }
	 
	 public void findAllMovieWithCollectionGreaterThan() {
		 
		 	EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
			Root<Movie> from = query.from(Movie.class);
			
			System.out.println("Find movie greater than given collection:");
			query.where(criteriaBuilder.greaterThan(from.get("collection"), sc.nextInt()));
			
			TypedQuery<Movie> typedQuery = entityManager.createQuery(query);
			List<Movie> movieList = typedQuery.getResultList();
			for(Movie movie: movieList) {
				System.out.println(movie);
			}
			
			transaction.commit();
			entityManager.close();
		 
	 }
	 
	 public void updateMovieCollectionByVerdict() {
		 	EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
//			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//			CriteriaUpdate<Movie> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Movie.class);
//			Root<Movie> from = criteriaUpdate.from(Movie.class);
//			System.out.println("Enter the Updated collection:");
//			criteriaUpdate.set(from.get("collection"), sc.nextInt());
//			System.out.println("Enter the verdict where you want to update collection:");
//			criteriaUpdate.where(criteriaBuilder.equal(from.get("verdict"), sc.next()));
			
			Query query = entityManager.createQuery("update Movie m set m.collection=:value where m.verdict=:ver");
			System.out.println("Enter the Verdict:");
			query.setParameter("ver", sc.next());
			System.out.println("Update the collection:");
			query.setParameter("value", sc.nextInt());
			int executeUpdate = query.executeUpdate();
			if(executeUpdate>0)
				System.out.println(executeUpdate+" Collection Updated");
			else
				System.out.println("No collection Updated");
			
			transaction.commit();
			entityManager.close();
			
		 
	 }
	 
	 public void findAllMoviesByActorId() {
		 	EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
			Root<Movie> from = query.from(Movie.class);
			System.out.println("Enter the Actor Id:");
			query.where(criteriaBuilder.equal(from.get("actorId"),sc.nextInt()));
			
			TypedQuery<Movie> typedQuery = entityManager.createQuery(query);
			List<Movie> movietList = typedQuery.getResultList();
			for(Movie movie: movietList) {
				System.out.println(movie);
			}
			
			transaction.commit();
			entityManager.close();
	 }
	 
	 public void deleteAllMovieByActorName() {
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
		 EntityTransaction transaction = entityManager.getTransaction();
		 transaction.begin();
		 
		 Query query = entityManager.createQuery("Delete from Movie m where m.actorName=:name");
		 System.out.println("Enter the Actor name:");
		 query.setParameter("name", sc.next());
		 int executeUpdate = query.executeUpdate();
		 
		 if(executeUpdate>0)
			 System.out.println(executeUpdate+" Movies Deleted");
		 else
			 System.out.println("No movies found");
		 
		 transaction.commit();
		 entityManager.close();
		 
	 }
	 
	 public void deleteAllMovieCollectionLessThan() {
		 EntityManager entityManager = entityManagerFactory.createEntityManager();
		 EntityTransaction transaction = entityManager.getTransaction();
		 transaction.begin();
		 
		 CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		 CriteriaDelete<Movie> criteriaDelete = criteriaBuilder.createCriteriaDelete(Movie.class);
		 Root<Movie> from = criteriaDelete.from(Movie.class);
		 System.out.println("Enter the Collection:");
		 criteriaDelete.where(criteriaBuilder.lessThan(from.get("collection"), sc.nextInt()));
		 
		 Query query = entityManager.createQuery(criteriaDelete);
		 int executeDelete = query.executeUpdate();
		 
		 if(executeDelete>0)
			 System.out.println(executeDelete+" Movies Deleted");
		 else
			 System.out.println("No movies found");
		 
		 transaction.commit();
		 entityManager.close();
			 
	 }
	 
	 

}
