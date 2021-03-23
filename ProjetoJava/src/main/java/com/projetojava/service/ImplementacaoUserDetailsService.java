package com.projetojava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projetojava.domain.Usuario;
import com.projetojava.repository.IUsuarioRepository;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService{

	@Autowired
	private IUsuarioRepository iusuarioRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//consulta usuario no banco
		Usuario usuario = iusuarioRepository.findUserByLogin(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não foi encontrado !");
		}
		return new User(usuario.getLogin(), usuario.getPassword(),usuario.getAuthorities());	
	}

	public void insereAcessoPadrao(Long id) {
		  
		  //Descobre qual a constraint de restricao
		  String constraint = iusuarioRepository.consultaConstraintRole();
		  
		  if (constraint != null) {
			  //Remove a constraint
			  jdbcTemplate.execute(" alter table usuarios_role  DROP CONSTRAINT " + constraint);
		  }
		  
		  //Insere os acessos padrÃ£o
		  iusuarioRepository.insereAcessoRolePadrao(id);
		  
	}
	
	
}
