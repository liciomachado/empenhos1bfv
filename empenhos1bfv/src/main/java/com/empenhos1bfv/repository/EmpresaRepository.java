package com.empenhos1bfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.empenhos1bfv.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

	@Query(value="SELECT sum(e.valor_total) FROM empenho as e where e.empresa_id_empresa = :id ",nativeQuery = true)
	double getValorTotalPorEmpresa(@Param("id") int id);
	
	@Query(value="SELECT avg(datediff(n.data_recebido,e.data_empenho)) FROM empenho as e "
			+ "inner join notafiscal as n on e.id_empenho = n.empenho_id_empenho where e.empresa_id_empresa = :id",nativeQuery = true)
	int getTempoMedioPorEmpresa(@Param("id") int id);
}
