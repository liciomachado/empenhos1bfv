package com.empenhos1bfv.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.empenhos1bfv.model.Notafiscal;

public interface NotaFiscalRepository extends JpaRepository<Notafiscal, Integer> {

	@Query(value="select * from notafiscal as a inner join empenho as b on a.empenho_id_empenho = b.id_empenho \r\n" + 
			"inner join  empresa as empr on empr.id_empresa = b.empresa_id_empresa order by a.id_nota_fiscal desc", nativeQuery = true)
	List<Notafiscal> findEmpenhosRecebidos();
	
	@Query("select n from Notafiscal n where n.dataProtocolado is null and n.secao.idSecao = :secao")
	List<Notafiscal> notasProtocolar(@Param("secao") int secao);
	
	@Query("select n from Notafiscal n where n.dataProtocolado is null")
	List<Notafiscal> notasProtocoRecebido();

	@Query("select n from Notafiscal n where n.secao.idSecao = :secao")
	List<Notafiscal> findNotasPorSecao(@Param("secao") int secao);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("update Notafiscal p set p.dataProtocolado = :data where p.idNotaFiscal = :id")
	void alteraStatus(@Param("id") int valor,@Param("data") LocalDate data);

	
	
}
