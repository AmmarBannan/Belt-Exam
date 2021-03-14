package com.example.tvshow.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tvshow.models.Rate;
import com.example.tvshow.models.Show;
import com.example.tvshow.models.User;
import com.example.tvshow.repositories.UserRepo;

@Service
public class SerUser {
    @Autowired
    private UserRepo uRepo;
    public User findById(Long id) {
        return this.uRepo.findById(id).orElse(null);
    }
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return this.uRepo.save(user);
    }
    public User getUserByEmail(String email) {
        return this.uRepo.findByEmail(email);
    }
    public boolean authenticateUser(String email, String password) {
    	User user = this.uRepo.findByEmail(email);
        if(user == null)
            return false;

        return BCrypt.checkpw(password, user.getPassword());
    }
    public List<Show> addshowuser(User u,Show s){
    	List<Show> allshow=u.getShowes();
    	allshow.add(s);
    	u.setShowes(allshow);
    	return u.getShowes();
    }
    public List<Rate> addrateuser(User u,Show s,Rate r){
    	List<Rate> rates=u.getRates();
    	rates.add(r);
    	u.setRates(rates);
    	List<Rate> ratess=s.getRates();
    	ratess.add(r);
    	s.setRates(ratess);
    	return s.getRates();
    	
    }
    public User findByEmail(String email) {
        return uRepo.findByEmail(email);
    }
    
    
}