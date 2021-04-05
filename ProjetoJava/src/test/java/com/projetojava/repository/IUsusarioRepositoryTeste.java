package com.projetojava.repository;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.projetojava.domain.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IUsusarioRepositoryTeste {

	@Autowired
	private IUsuarioRepository iusuarioRepository;
	
	@Rule
	public ExpectedException thown = ExpectedException.none();
	
	/**
	 * teste metodo salvar usuario
	 */
	@Test
	public void SalvarUsuario() {
		Usuario usuario = new Usuario("joao", "1234", "joao batista");
		this.iusuarioRepository.save(usuario);
		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario.getLogin()).isEqualTo("joao");
		Assertions.assertThat(usuario.getSenha()).isEqualTo("1234");
		Assertions.assertThat(usuario.getNome()).isEqualTo("joao batista");
	}
	/**
	 * teste metodo deletar usuario 
	 */
	@Test
	public void deletarUsuario() {
		Usuario usuario = new Usuario("joao", "1234", "joao batista");
		this.iusuarioRepository.save(usuario);
		iusuarioRepository.delete(usuario);
		Assertions.assertThat(iusuarioRepository.findById(usuario.getId())).isNull();//nao ta executando essa linha
	}
	
	
	/**
	 * teste metodo atualizar usuario
	 */
	@Test
	public void atualizarUsuario() {
		Usuario usuario = new Usuario("joao", "1234", "joao batista");
		this.iusuarioRepository.save(usuario);
		usuario.setNome("joao winchester");
		usuario.setLogin("joao");
		usuario.setSenha("1234");
		usuario = this.iusuarioRepository.save(usuario);
		Assertions.assertThat(usuario.getNome()).isEqualTo("joao winchester");
		Assertions.assertThat(usuario.getLogin()).isEqualTo("joao");
		Assertions.assertThat(usuario.getSenha()).isEqualTo("1234");
	}
	/**
	 * teste metodo lista por nome
	 */
	@Test
	public void findUserByNome() {
		Usuario usuario = new Usuario("jb", "1234", "joao batista");
		//Usuario usuario2 = new Usuario("jw", "123", "joao winchester");
		this.iusuarioRepository.save(usuario);
		//this.iusuarioRepository.save(usuario2);
		List <Usuario> userList = iusuarioRepository.findUserByNome("joao");
		assertThat(userList.size()).isEqualTo(1);
	}
	
}
