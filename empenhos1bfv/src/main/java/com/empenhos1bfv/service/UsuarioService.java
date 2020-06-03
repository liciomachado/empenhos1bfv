package com.empenhos1bfv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscarPorEmailEAtivo(username).orElseThrow(() -> new UsernameNotFoundException("Usuario "+username + " não encontrado"));
		return new User(
			usuario.getNome(),
			usuario.getSenha(),
			AuthorityUtils.createAuthorityList(usuario.getSecao().getNomeSecao())
		);
	}
	

	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmailEAtivo(String email){
		
		return repository.findByNome(email);
	}

}
