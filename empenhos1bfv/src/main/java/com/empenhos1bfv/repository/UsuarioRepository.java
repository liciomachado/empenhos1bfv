package com.empenhos1bfv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empenhos1bfv.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@Query("select u from Usuario u where u.nome = :nome")
	Optional<Usuario> findByNome(String nome);

}
