package com.example.ShamblesProject.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ShamblesProject.model.Citation;

public interface CitationRepository extends JpaRepository<Citation, Long>{

	

}
