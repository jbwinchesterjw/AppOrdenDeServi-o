package com.projetojava.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetojava.domain.Profissao;
import com.projetojava.repository.IProfissaoRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "/profissao")
public class ProfissaoController {

	@Autowired
	private IProfissaoRepository profissaoRepository;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Profissao>> profissoes() {

		List<Profissao> lista = profissaoRepository.findAll();

		return new ResponseEntity<List<Profissao>>(lista, HttpStatus.OK);

	}

}
