package com.empenhos1bfv.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empenhos1bfv.dto.EmpenhoDTO;


public interface EmpenhoDTORepository  extends JpaRepository<EmpenhoDTO, Integer>  {

	@Query(value="SELECT e.id_empenho,e.numero_empenho,e.empresa_id_empresa,e.destino,e.valor_total,e.data_empenho,e.etapa,e.usuario_id_usuario,\r\n" + 
			"e.saldo,e.saldo_utilizado FROM empenho as e", nativeQuery = true)
	List<EmpenhoDTO> findAllWithoutFIle(Pageable page);
	
	@Query(value="SELECT e.id_empenho,e.numero_empenho,e.empresa_id_empresa,e.destino,e.valor_total,e.data_empenho,e.etapa,e.usuario_id_usuario,\r\n" + 
			"e.saldo,e.saldo_utilizado FROM empenho as e", nativeQuery = true)
	List<EmpenhoDTO> findAllWithoutFIle();
	
	@Query(value="select c.id_empenho,c.numero_empenho,c.destino,b.nome,c.data_empenho,\r\n" + 
			"c.valor_total,sum(nota.valor_total) as saldo_utilizado, \r\n" + 
			"round((c.valor_total - sum(nota.valor_total)),2) as saldo \r\n" + 
			"from empenho as c inner join empresa as b on c.empresa_id_empresa = b.id_empresa\r\n" + 
			"left JOIN notafiscal as nota on nota.empenho_id_empenho = c.id_empenho\r\n" + 
			"		where not exists(select a.empenho_id_empenho,sum(round(a.valor_total,2)),c.valor_total from notafiscal as a inner join empenho as b on \r\n" + 
			"		a.empenho_id_empenho = b.id_empenho group by a.empenho_id_empenho HAVING sum(a.valor_total) = c.valor_total and a.empenho_id_empenho = c.id_empenho)\r\n" + 
			"group by c.id_empenho", nativeQuery = true)
	List<EmpenhoDTO> findEmpenhosPendentes();
	
	@Query(value="select c.id_empenho,c.valor_total,sum(nota.valor_total) as saldo_utilizado, \r\n" + 
			"round((c.valor_total - sum(nota.valor_total)),2) as saldo \r\n" + 
			"from empenho as c inner join empresa as b on c.empresa_id_empresa = b.id_empresa\r\n" + 
			"left JOIN notafiscal as nota on nota.empenho_id_empenho = c.id_empenho\r\n" + 
			"		where not exists(select a.empenho_id_empenho,sum(round(a.valor_total,2)),c.valor_total from notafiscal as a inner join empenho as b on \r\n" + 
			"		a.empenho_id_empenho = b.id_empenho group by a.empenho_id_empenho HAVING sum(a.valor_total) = c.valor_total and a.empenho_id_empenho = c.id_empenho)\r\n" + 
			"group by c.id_empenho", nativeQuery = true)
	List<Object[]> atualizaPendentes();
	
	@Query(value="select c.id_empenho,c.valor_total,sum(nota.valor_total) as saldo_utilizado, \r\n" + 
			"round((c.valor_total - sum(nota.valor_total)),2) as saldo \r\n" + 
			"from empenho as c inner join empresa as b on c.empresa_id_empresa = b.id_empresa\r\n" + 
			"left JOIN notafiscal as nota on nota.empenho_id_empenho = c.id_empenho\r\n" + 
			"		where exists(select a.empenho_id_empenho,sum(round(a.valor_total,2)),c.valor_total from notafiscal as a inner join empenho as b on \r\n" + 
			"		a.empenho_id_empenho = b.id_empenho group by a.empenho_id_empenho HAVING sum(a.valor_total) = c.valor_total and a.empenho_id_empenho = c.id_empenho)\r\n" + 
			"group by c.id_empenho", nativeQuery = true)
	List<Object[]> atualizaRecebidos();
	
	@Query(value="SELECT e.id_empenho,e.numero_empenho,e.empresa_id_empresa,e.destino,e.valor_total,e.data_empenho,e.etapa,e.usuario_id_usuario,"
			+ "e.saldo,e.saldo_utilizado FROM empenho as e where e.saldo = 0", nativeQuery = true)
	List<EmpenhoDTO> findQuitados();
	
	@Query(value="SELECT e.id_empenho,e.numero_empenho,e.empresa_id_empresa,e.destino,e.valor_total,e.data_empenho,e.etapa,e.usuario_id_usuario,"
			+ "e.saldo,e.saldo_utilizado FROM empenho as e where e.saldo != 0", nativeQuery = true)
	List<EmpenhoDTO> findRestosAPagar();
	
	@Query("select e from Empenho e where e.empresa.idEmpresa = :id")
	List<EmpenhoDTO> findByEmpresa(@Param("id") int id);
	
	@Query(value="SELECT e.id_empenho,e.numero_empenho,e.empresa_id_empresa,e.destino,e.valor_total,e.data_empenho,e.etapa,e.usuario_id_usuario,"
			+ "e.saldo,e.saldo_utilizado FROM empenho as e where e.saldo != 0 and e.data_empenho <= DATE_SUB(now(), INTERVAL 30 DAY)", nativeQuery = true)
	List<EmpenhoDTO> findVencidos();
}
