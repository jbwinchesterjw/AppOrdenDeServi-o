package com.projetojava.controller;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetojava.ObjetoErro;
import com.projetojava.domain.Usuario;
import com.projetojava.repository.IUsuarioRepository;
import com.projetojava.service.ServiceEnviaEmail;


@RestController
@RequestMapping(value = "/recuperar")
public class RecuperaController {
	
	@Autowired
	private IUsuarioRepository iusuarioRepository;
	
	
	@Autowired
	private ServiceEnviaEmail serviceEnviaEmail;
	
	@ResponseBody
	@PostMapping(value="/")
	public ResponseEntity<ObjetoErro> recuperar(@RequestBody Usuario login) throws Exception{
		
		ObjetoErro objetoError = new ObjetoErro();
		
		Usuario user = iusuarioRepository.findUserByLogin(login.getLogin());
		
		if (user == null) {
			objetoError.setCode("404");
			objetoError.setError("Usuário não encontrado");
		}else {			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String senhaNova = dateFormat.format(Calendar.getInstance().getTime());
			
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senhaNova);
			iusuarioRepository.updateSenha(senhaCriptografada, user.getId());

			
			serviceEnviaEmail.
			enviarEmail("Recuperação de senha",
					    user.getLogin(),
					    "Sua nova senha é : "+ senhaNova);
			
			objetoError.setCode("200");
			objetoError.setError("Acesso enviado para seu e-mail");
		}
		return new ResponseEntity<ObjetoErro>(objetoError, HttpStatus.OK);
		
	}
	

}
