package com.jsp.hibernate.actor_movie.dao;

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
import javax.persistence.criteria.CriteriaUpdate;
//import javax.persistence.criteria.From;
import javax.persistence.criteria.Root;

import com.jsp.hibernate.actor_movie.entity.Actor;

public class ActionDao {
	
			 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql-config");
			 Scanner sc = new Scanner(System.in);
			public void addActor() {
				
				Actor actor = new Actor();
				System.out.println("Enter Actor Id:");
				actor.setActorId(sc.nextInt());
				System.out.println("Enter Actor Name:");
				actor.setActorName(sc.next());
				System.out.println("Enter Actor Age:");
				actor.setActorAge(sc.nextInt());
				System.out.println("Enter Actor Industry");
				actor.setIndustry(sc.next());
				System.out.println("Enter Actor Nationality:");
				actor.setNationality(sc.next());
				System.out.println("Enter Actor Salary:");
				actor.setSalary(sc.nextInt());
				
				
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				entityManager.persist(actor);
				
				transaction.commit();
				entityManager.close();

			}
			
			public void findActorById() {
				
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				System.out.println("Enter the EmployeeId");
				Actor actor = entityManager.find(Actor.class, sc.nextInt());
				if(actor!=null)
					System.out.println(actor);
				else {
					System.err.println("Actor not found");
				}
				transaction.commit();
				entityManager.close();
				
			}
			
			public void findActorByName() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				Query query = entityManager.createQuery("select a from Actor a where a.actorName=:name");
				System.out.println("Enter Actor Name:");
				query.setParameter("name", sc.next());
				Actor actor =(Actor) query.getSingleResult();
				
				System.out.println(actor);
				
				transaction.commit();
				entityManager.close();
				
				
			}
			
			public void findAllActorByIndustry() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
				Root<Actor> from = query.from(Actor.class);
				System.out.println("Enter the industry");
				query.where(criteriaBuilder.equal(from.get("industry"), sc.next()));
				
				TypedQuery<Actor> typedQuery = entityManager.createQuery(query);
				
				List<Actor> resultList = typedQuery.getResultList();
				for (Actor actor : resultList) {
					System.out.println(actor);
				}
				
				transaction.commit();
				entityManager.close();
				
			}
			
			public void findAllActorBetweenAge() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
				Root<Actor> from = query.from(Actor.class);
				System.out.println("Enter Actor Age Range:");
				query.where(criteriaBuilder.between(from.get("actorAge"), sc.nextInt(), sc.nextInt()));		
				
				TypedQuery<Actor> typedQuery = entityManager.createQuery(query);
				List<Actor> resultList = typedQuery.getResultList();
				for (Actor actor : resultList) {
					System.out.println(actor);
				}
				
				transaction.commit();
				entityManager.close();
			}
			
			public void updateActorNationalityById() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				Query query = entityManager.createQuery("update Actor a set a.nationality=:nation where a.actorId=:id");
				System.out.println("Enter Actor id:");
				query.setParameter("id",sc.nextInt());
				System.out.println("Update Actor Nationality:");
				query.setParameter("nation", sc.next());
				
				int executeUpdate = query.executeUpdate();
				if(executeUpdate>0)
					System.out.println("Actor Nationality Updated");
				else
					System.out.println("Not Updated");
				
				transaction.commit();
				entityManager.close();
				
				
			}
			
			public void deleteActorByIndustry() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				Query query = entityManager.createQuery("Delete from Actor a where a.industry=:value");
				System.out.println("Enter the Industry:");
				query.setParameter("value", sc.next());
				
				int executeDelete = query.executeUpdate();
				if(executeDelete>0)
					System.out.println(executeDelete+" Actors Deleted");
				else
					System.out.println("Actors not found");
				
				transaction.commit();
				entityManager.close();
			}
			
			public void findAllActorByMovieName() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Actor> query = criteriaBuilder.createQuery(Actor.class);
				Root<Actor> from = query.from(Actor.class);
				query.where(criteriaBuilder.equal(from.get("movieName"), sc.next()));
				
				TypedQuery<Actor> typedQuery = entityManager.createQuery(query);
				List<Actor> actorList = typedQuery.getResultList();
				for (Actor actor : actorList) {
					System.out.println(actor);
				}
				
				transaction.commit();
				entityManager.close();
				
			}
			
			public void updateAllActorSalaryByMovieId() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaUpdate<Actor> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Actor.class);
				Root<Actor> from = criteriaUpdate.from(Actor.class);
				System.out.println("Enter the Salary:");
				criteriaUpdate.set(from.get("salary"), sc.nextInt());
				System.out.println("Enter the movie name:");
				criteriaUpdate.where(criteriaBuilder.equal(from.get("movieName"), sc.next()));
				
				Query query = entityManager.createQuery(criteriaUpdate);
				int executeUpdate = query.executeUpdate();
				if(executeUpdate>0)
					System.out.println(executeUpdate+" Actors Salary Updated");
				else
					System.out.println("No Salary Updated");
				
				transaction.commit();
				entityManager.close();
				
			}
			
			public void deleteAllActorsByMovieName() {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				EntityTransaction transaction = entityManager.getTransaction();
				transaction.begin();
				
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaDelete<Actor> criteriaDelete = criteriaBuilder.createCriteriaDelete(Actor.class);
				Root<Actor> from = criteriaDelete.from(Actor.class);
				System.out.println("Enter the Movie name");
				criteriaDelete.where(criteriaBuilder.equal(from.get("movieName"), sc.next()));
				
				Query query = entityManager.createQuery(criteriaDelete);
				int executeDelete = query.executeUpdate();
				if(executeDelete>0)
					System.out.println(executeDelete+" Actors Deleted");
				else
					System.out.println("No actors found");
				
				transaction.commit();
				entityManager.close();
			}

}
