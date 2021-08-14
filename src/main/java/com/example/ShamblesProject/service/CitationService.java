package com.example.ShamblesProject.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ShamblesProject.model.Citation;
import com.example.ShamblesProject.model.User;
import com.example.ShamblesProject.posterDto.CitationDto;
import com.example.ShamblesProject.repository.CitationRepository;
import com.example.ShamblesProject.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class CitationService {

	@Autowired
	private CitationRepository citationRepository;

	@Autowired
	private UserRepository userRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	/*----------pour obtenir un seul object--------------------*/

	public Optional<Citation> getCitation(final Long id) {
		return citationRepository.findById(id);
	}

	/*----------pour obtenir tout les objects--------------------*/
	public Iterable<Citation> getCitation() {
		return citationRepository.findAll();
	}

	/*----------pour supprimer un object--------------------*/
	/*-------------------je recuper le user qui est connecter ----------------------------------------------------*/
	public void deletCitation(final Long id, Citation citation) {
		User user = userRepository.findById(id).get();
		user.getCitations().remove(citation);
		userRepository.save(user);

	}
	/*----------pour enregistrer tout les objects--------------------*/
	// ------------------relier les user a leurs
	// citations-----------------------------------------

	public User saveCitation(Citation citation, Long idUser) {
		Optional<User> userOptional = userRepository.findById(idUser);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			user.getCitations().add(citation);
			return userRepository.save(user);

		}

		return user;
	}
	/*----------update--------------------*/
	/*----------------on transforme notre entity citation en Dto puis l'envoyer dans la bdd----------------------------------------------------*/

	public Citation updateCitation(CitationDto citationDto) {
		Citation citation = modelMapper.map(citationDto, Citation.class);
		return citationRepository.save(citation);
	}

}
