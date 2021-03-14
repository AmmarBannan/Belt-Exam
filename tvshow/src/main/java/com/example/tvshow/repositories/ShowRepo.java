package com.example.tvshow.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tvshow.models.Show;
import com.example.tvshow.models.User;

@Repository
public interface ShowRepo extends CrudRepository<Show, Long>{
	List<Show> findAll();
	List<Show> findByUsers(User u);
}
