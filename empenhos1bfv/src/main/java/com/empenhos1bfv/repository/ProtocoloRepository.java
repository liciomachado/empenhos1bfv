package com.empenhos1bfv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.empenhos1bfv.model.Protocolo;

public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {

	@Query("select p from Protocolo p where p.etapaProtocolo = :etapa and p.secao.idSecao = :secao")
	List<Protocolo> notasProtocoRecebido(@Param("etapa") int etapa,@Param("secao") int secao);

	@Transactional(readOnly = false)
	@Modifying
	@Query(value = "delete from protocolo where nota_fiscal_id_nota_fiscal = :id", nativeQuery = true)
	void deletePorIdNf(@Param("id") String id);
}
