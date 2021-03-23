package com.projetojava.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projetojava.domain.Usuario;
import com.projetojava.repository.ITelefoneRepository;
import com.projetojava.repository.IUsuarioRepository;
import com.projetojava.service.ImplementacaoUserDetailsService;
import com.projetojava.service.ServiceRelatorio;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin
@RestController
@RequestMapping(value = "/usuario")
public class indexController {

	

	@Autowired
	private IUsuarioRepository iusuarioRepository;

	@Autowired
	private ITelefoneRepository itelefoneRepository;
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService; 
	
	@Autowired
	private ServiceRelatorio serviceRelatorio;
	
	/**
	 * Metodo Pesquisa Por id
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	@CachePut("cacheuser")
	public ResponseEntity<Usuario> pesquisaPorId(@PathVariable(value = "id") Long id) {

		Optional<Usuario> usuario = iusuarioRepository.findById(id);

		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	/**
	 * Metodo Deletar Usuario
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deletarUser/{id}", produces = "application/text")
	public String deleteUser(@PathVariable("id") Long id) {

		iusuarioRepository.deleteById(id);

		return "ok";
	}

	/**
	 * Remove Telefone do Usuario
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/removerTelefone/{id}", produces = "application/text")
	public String deletarTelefone(@PathVariable("id") Long id) {

		itelefoneRepository.deleteById(id);

		return "ok";
	}
	
	

	/**
	 * Metodo Pesquisa Todos os Usuarios
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping(value = "/", produces = "application/json")
	@CacheEvict(value = "cacheusuarios", allEntries = true)
	@CachePut("cacheusuarios")
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Page<Usuario>> usuario() throws InterruptedException {

		PageRequest page = PageRequest.of(0, 5, Sort.by("nome"));

		Page<Usuario> list = iusuarioRepository.findAll(page);
		return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
	}

	/**
	 * Lista os usuarios por pagina
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping(value = "/page/{pagina}", produces = "application/json")
	@CacheEvict(value = "cacheusuarios", allEntries = true)
	@CachePut("cacheusuarios")
	public ResponseEntity<Page<Usuario>> usuarioPagina(@PathVariable("pagina") int pagina) throws InterruptedException {

		PageRequest page = PageRequest.of(pagina, 5, Sort.by("nome"));

		Page<Usuario> list = iusuarioRepository.findAll(page);
		return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
	}

	/**
	 * Metodo de pesquisa por nome
	 * @param nome
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping(value = "usuarioPorNome/{nome}", produces = "application/json")
	@CacheEvict(value = "cacheusuarios", allEntries = true)
	@CachePut("cacheusuarios")
	public ResponseEntity<Page<Usuario>> ListUserPorNome(@PathVariable("nome") String nome)
			throws InterruptedException {
		
		PageRequest pageRequest = null;
		Page<Usuario> list = null;
		if (nome == null || (nome != null && nome.trim().isEmpty())) {
			pageRequest = PageRequest.of(0, 5, Sort.by("nome"));
			list = iusuarioRepository.findAll(pageRequest);
		}else {
			pageRequest = PageRequest.of(0, 5, Sort.by("nome"));
			list  = iusuarioRepository.findUserByNomePage(nome, pageRequest);
		}
		
		return new ResponseEntity<Page<Usuario>>(list, HttpStatus.OK);
	}
/*
	/**
	 * Metodo Cadastrar usuario
	 * @param usuario
	 * @return
	 *
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody @Validated Usuario usuario) {

		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		Usuario usuarioSalvo = iusuarioRepository.save(usuario);

		implementacaoUserDetailsService.insereAcessoPadrao(usuarioSalvo.getId());

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

	}*/
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Usuario> salvar(@Validated @RequestBody Usuario usuario){
		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		Usuario usuarioSalvo = iusuarioRepository.save(usuario);
		implementacaoUserDetailsService.insereAcessoPadrao(usuarioSalvo.getId());

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {

		/* outras rotinas antes de atualizar */

		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		Usuario userTemporario = iusuarioRepository.findById(usuario.getId()).get();

		if (!userTemporario.getSenha().equals(usuario.getSenha())) { /* Senhas diferentes */
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}

		Usuario usuarioSalvo = iusuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

	}

	/**
	 * Mertodo Atualizar
	 * @param usuario
	 * @return
	 */
	/*@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

		for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}

		Usuario userTemporario = iusuarioRepository.findById(usuario.getId()).get();

		if (!userTemporario.getSenha().equals(usuario.getSenha())) {
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}

		Usuario usuarioSalvo = iusuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}*/
	
	@GetMapping(value ="/relatorio", produces = "application/text")
	public ResponseEntity<String> downloaRelatorio(HttpServletRequest request) throws Exception{
		
		byte[] pdf = serviceRelatorio.gerarRelatorio("relatorioUsuarioAngular", request.getServletContext());
		
		String base64Pdf = "data:application/pdf;base64," + Base64.encodeBase64String(pdf);
		
		return new ResponseEntity<String>(base64Pdf, HttpStatus.OK);
		
	}
	

}
