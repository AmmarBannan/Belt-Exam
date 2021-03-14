package com.example.tvshow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tvshow.models.Rate;
import com.example.tvshow.models.Show;
import com.example.tvshow.models.User;
import com.example.tvshow.repositories.RateRepo;
import com.example.tvshow.repositories.ShowRepo;

@Service
public class SerApp {
	private final RateRepo raterepo;
	private final ShowRepo showrepo;
	private int sum=0;
	private int avg=0;
	
	public SerApp(ShowRepo showrepo, RateRepo raterepo) {
		this.showrepo = showrepo;
		this. raterepo= raterepo;
	}
	
	public Show findShowById(Long id) {
		Optional<Show> u = showrepo.findById(id);
		
		if(u.isPresent()) {
	        return u.get();
		} else {
		    return null;
		}
	}
	public int calavg(Show s) {
		List<Rate> rates=s.getRates();
		for(Rate i:rates) {
			this.sum+=i.getStar();
		}
		this.avg=sum/(rates.size()+1);
		s.setAvg(this.avg);
		return s.getAvg();
	}
	
	public Show createshow(Show s) {
		return showrepo.save(s);
	}
	
	public Rate createrate(Rate r,Show s,User u) {
		Rate rate=r;
		rate.setUser(u);
		rate.setShow(s);
		raterepo.save(rate);
		
		return rate;
	}
	
	public Show update(Show show1,Show show) {
		show1.setTitle(show.getTitle());
		show1.setNetwork(show.getNetwork());
		showrepo.save(show1);
		return show1;
	}
	
//	public List<Rate> findrate(Show s,User u) {
//		List<Rate> rates=raterepo.findByShowId(s);
//		List<Rate> ratess=raterepo.findByUserId(u);
//		List<Rate> com=null;
//		for(Rate rate:rates) {
//			for(Rate rated:ratess) {
//				if(rate.getId()==rated.getId()) com.add(rate);
//			}
//		}
//		return com;
//	}
}
