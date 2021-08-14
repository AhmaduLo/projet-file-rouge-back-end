package com.example.ShamblesProject.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ShamblesProject.model.Citation;
import com.example.ShamblesProject.model.User;
import com.example.ShamblesProject.posterDto.CitationDto;

import com.example.ShamblesProject.service.CitationService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CitationController {

	
	@Autowired
	private CitationService citationService;
	
	private final ModelMapper modelMapper = new ModelMapper();

	  /*----------recuperer tout les utilisateurs----------------------------*/
		@GetMapping("/citation")
		public Iterable<Citation>getCitation(){
			return citationService.getCitation();		
			}
		/*-------------recuperer by id-------------------------------*/
		@GetMapping("/citation/{id}")
		public Citation getCitation(@PathVariable ("id") final Long id) {
			Optional<Citation> Citation = citationService.getCitation(id);
			if(Citation.isPresent()) {
				return Citation.get();
			}else {
				return null;
			}
			
		}
		
		/*--------------------post -----------------------------------------------*/

		@PostMapping("/citation/{idUser}")
		public User CreatPoster(@RequestBody Citation citation,
				@PathVariable ("idUser") final Long idUser) {
			
			return citationService.saveCitation(citation,idUser);
	}
		
		/*-----------------delete---------------------------------*/
		/*-------------------je recuper le user qui est connecter ----------------------------------------------------*/
		 @PostMapping("/citation/{id}/user")
		    public void deleteCitation(@PathVariable("id")  final Long id,
		    		Citation citation){
			 citationService.deletCitation(id,citation);
		    }
		 
		 /*------------------------------update------------------------------------------------*/


		    @PutMapping("/citation")
		    public Citation updateCitation(@RequestBody CitationDto citationDto ) {
		    	
		         return citationService.updateCitation(citationDto);
		    		
		    	
		    }
		 
		
	
		
		
}
