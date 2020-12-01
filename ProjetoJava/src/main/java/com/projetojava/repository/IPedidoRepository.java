package com.projetojava.repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.projetojava.domain.Pedido;

@Repository
@EnableJpaRepositories 
public interface IPedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="UPDATE pedido SET status = 'Aprovado' WHERE id = ?1")
	Pedido aprovarStatus(String status, Long id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value ="UPDATE pedido SET status = 'Reprovado' WHERE id = ?1")
	Pedido cancelaStatus(String status, Long id);

}
