package com.projetojava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetojava.domain.Itens;

@Repository
public interface IItensRepository extends JpaRepository<Itens, Long>{

	
}
