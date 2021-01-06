package com.projetojava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetojava.domain.Profissao;


@Repository
public interface IProfissaoRepository extends JpaRepository<Profissao, Long>{

}
