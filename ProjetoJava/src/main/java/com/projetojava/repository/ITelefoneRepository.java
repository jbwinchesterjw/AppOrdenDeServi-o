package com.projetojava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projetojava.domain.Telefone;

@Repository
public interface ITelefoneRepository extends CrudRepository<Telefone, Long>{

}
