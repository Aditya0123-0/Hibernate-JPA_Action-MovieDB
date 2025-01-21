package com.jsp.hibernate.actor_movie.entity;

import com.jsp.hibernate.actor_movie.dao.ActionDao;
import com.jsp.hibernate.actor_movie.dao.MovieDao;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        ActionDao a = new ActionDao();
//        a.findAllActorBetweenAge();
    	
    	MovieDao movieDao = new MovieDao();
//    	movieDao.addMovie();
    	movieDao.findMovieByName();
    }
}
