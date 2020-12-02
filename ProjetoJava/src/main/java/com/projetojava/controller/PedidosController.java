package com.projetojava.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projetojava.domain.Pedido;
import com.projetojava.repository.IItensRepository;
import com.projetojava.repository.IPedidoRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/pedido")
public class PedidosController {

	@Autowired
	private IPedidoRepository ipedidoRepository;
	
	@Autowired
	private IItensRepository iitensRepository;
	
	/**
	 * Metodo Lista Todos os Pedidos
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping(value = "/", produces = "application/json")
	@CacheEvict(value = "cachepedidos", allEntries = true)
	@CachePut("cachepedidos")
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Pedido>> listarPedidos() throws InterruptedException {
		
		 List<Pedido> list = (List<Pedido>) ipedidoRepository.findAll();
		return new ResponseEntity<List<Pedido>>(list, HttpStatus.OK);
	}
	/**
	 * Metodo Buscar Pedido Por Id
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	@CachePut("cacheuser")
	public ResponseEntity<Pedido> pesquisaPedidoPorId(@PathVariable(value = "id") Long id) {

		Optional<Pedido> pedido = ipedidoRepository.findById(id);

		return new ResponseEntity<Pedido>(pedido.get(), HttpStatus.OK);
	}
	
	/**
	 * Metodo Cadastrar Pedido
	 * @param pedido
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Pedido> cadastrar(@RequestBody @Validated Pedido pedido) {
		
		for (int pos = 0; pos < pedido.getItens().size(); pos++) {
			pedido.getItens().get(pos).setPedidos(pedido);
		}
		
		Pedido pedidoSalvo = ipedidoRepository.save(pedido);

		return new ResponseEntity<Pedido>(pedidoSalvo, HttpStatus.OK);

	}
	
	/**
	 * Metodo Deletar Itens Referente ao Pedido
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/removerItens/{id}", produces = "application/text")
	public String deletarItens(@PathVariable("id") Long id) {

		iitensRepository.deleteById(id);

		return "ok";
	}	
	
	/**
	 * Metodo Deletar Pedido
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deletarPedido/{id}", produces = "application/text")
	public String deletarPedido(@PathVariable("id") Long id) {

		ipedidoRepository.deleteById(id);

		return "ok";
	}	
	/**
	 * Metodo Atualizar Status Aprovado
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/aprovaStatus/{id}", method=RequestMethod.PATCH)
	public ResponseEntity<?> atualizaStatusAprovado(@PathVariable String status, @PathVariable("id") Long id) {
		
		Pedido putStatus = ipedidoRepository.aprovarStatus(status, id);
		return new ResponseEntity<Pedido>(putStatus, HttpStatus.OK);
	}
	
	/**
	 * Metodo Atualizar Status Cancelado
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/canselaStatus/{id}", method=RequestMethod.PATCH)
	public ResponseEntity<?> atualizaStatusCancelado(@PathVariable String status, @PathVariable("id") Long id) {
		
		Pedido putStatus = ipedidoRepository.cancelaStatus(status, id);
		return new ResponseEntity<Pedido>(putStatus, HttpStatus.OK);
	}
	
}









