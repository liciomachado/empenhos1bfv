package com.empenhos1bfv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empenhos1bfv.model.Empenho;

public interface EmpenhoRepository extends JpaRepository<Empenho, Integer> {

	@Query(value="select c.id_empenho,c.numero_empenho,c.destino,b.nome,c.data_empenho,c.empenho_digitalizado,\r\n" + 
			"c.valor_total,sum(nota.valor_total) as utilizado, \r\n" + 
			"round((c.valor_total - sum(nota.valor_total)),2) as saldo \r\n" + 
			"from empenho as c inner join empresa as b on c.empresa_id_empresa = b.id_empresa\r\n" + 
			"left JOIN notafiscal as nota on nota.empenho_id_empenho = c.id_empenho\r\n" + 
			"		where not exists(select a.empenho_id_empenho,sum(round(a.valor_total,2)),c.valor_total from notafiscal as a inner join empenho as b on \r\n" + 
			"		a.empenho_id_empenho = b.id_empenho group by a.empenho_id_empenho HAVING sum(a.valor_total) = c.valor_total and a.empenho_id_empenho = c.id_empenho)\r\n" + 
			"group by c.id_empenho", nativeQuery = true)
	List<Empenho> findEmpenhosPendentes();
	
	@Query("select e from Empenho e where e.valorTotal = saldoUtilizado")
	List<Empenho> findQuitados();
	
	@Query("select e from Empenho e where e.valorTotal != saldoUtilizado")
	List<Empenho> findRestosAPagar();
	
	@Query("select e from Empenho e where e.empresa.idEmpresa = :id")
	List<Empenho> findByEmpresa(@Param("id") int id);
	
	@Query(value="SELECT * FROM empenho where valor_total != saldo_utilizado and data_empenho <= DATE_SUB(now(), INTERVAL 30 DAY)", nativeQuery = true)
	List<Empenho> findVencidos();
	
}
