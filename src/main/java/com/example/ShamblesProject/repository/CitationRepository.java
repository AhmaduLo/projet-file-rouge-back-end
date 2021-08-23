package com.example.ShamblesProject.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ShamblesProject.model.Citation;

public interface CitationRepository extends JpaRepository<Citation, Long>{

	Optional<Citation> findById(Long id);
	
	List<Citation> findAll();

}
