package com.projetojava.repository.teste;


import java.util.Optional;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.projetojava.domain.Usuario;
import com.projetojava.repository.IUsuarioRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
public class IUsusarioRepositoryTeste {

	@Autowired
	private IUsuarioRepository iusuarioRepository;
	
	@Rule
	public ExpectedException thown = ExpectedException.none();

	@Test
	public void SalvarUsuarioShouldPersistData() {
		Usuario usuario = new Usuario("jj", "1234", "teste joao");
		this.iusuarioRepository.save(usuario);
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario.getLogin()).isEqualTo("jb");
		Assertions.assertThat(usuario.getSenha()).isEqualTo("123");
		Assertions.assertThat(usuario.getNome()).isEqualTo("joao");
	}
	/*
	@Test
	public void deleteUserShouldPersistData() {
		Usuario usuario = new Usuario("jj", "1234", "teste joao");
		this.iusuarioRepository.save(usuario);
		iusuarioRepository.delete(usuario);
		Assertions.assertThat(iusuarioRepository.findById(usuario.getId())).isNull();
	}
	
	

	@Test
	public void atualizarUsuarioShouldPersistData() {
		Usuario usuario = new Usuario("jj", "1234", "teste joao");
		this.iusuarioRepository.save(usuario);
		usuario.setNome("batista");
		usuario.setLogin("jw");
		usuario.setSenha("123");
		this.iusuarioRepository.save(usuario);
		this.iusuarioRepository.findById(usuario.getId());
		Assertions.assertThat(usuario.getNome()).isEqualTo("joao batista");
		Assertions.assertThat(usuario.getLogin()).isEqualTo("jwb");
		Assertions.assertThat(usuario.getSenha()).isEqualTo("1234");
		Assertions.assertThat(iusuarioRepository.findById(usuario.getId())).isNull();
	}*/

	
}
