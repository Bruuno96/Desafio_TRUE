package br.com.fiap.epictask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fiap.epictask.model.Usuario;
import br.com.fiap.epictask.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@RequestMapping("/cadastrarUser")
	public String cadastrarUser() {
		return "cadastrarUser";
	}
	
	@PostMapping("/cadastrarUser")
	public String cadastrar(Usuario user) {
		repository.save(user);
		return "cadastrarUser";
	}
	
	

}
