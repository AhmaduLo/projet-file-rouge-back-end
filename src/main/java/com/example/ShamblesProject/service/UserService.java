package com.example.ShamblesProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ShamblesProject.model.User;

import com.example.ShamblesProject.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
/*----------pour obtenir un seul object--------------------*/
	
	public Optional<User>getUser(final Long id){
		return userRepository.findById(id);
	}
	
	public Iterable<User>getUser(){
		return userRepository.findAll();
	}
	/*----------pour supprimer un object--------------------*/
	public void deletUser(final Long id) {
		userRepository.deleteById(id);
	}
	
	public User saveUser( User  user ) {
		return userRepository.save(user);
		
	}
	
	/*----------pour update un object--------------------*/
	public Optional<User>updateUser(Long id){
		return userRepository.findById(id);
	}
	
	//-------------------ajouter un post au user---------------------------------------------------
//	public Poster savePost( Poster  poster,String username ) {
//		
//		Optional<User> user =  userRepository.findByUsername(username);
//		user.get().addPosts(poster);
//		return posterRepository.save(poster);
//		
//	}

}
