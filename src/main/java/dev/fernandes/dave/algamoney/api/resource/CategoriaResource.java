package dev.fernandes.dave.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fernandes.dave.algamoney.api.model.Categoria;
import dev.fernandes.dave.algamoney.api.repository.CategoriaRepository;

@RestController @RequestMapping("/categories")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRespository;
	
	@GetMapping
	public List<Categoria> findAll(){
		return categoriaRespository.findAll();		
	}

}
