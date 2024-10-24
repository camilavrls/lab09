package com.example.lab09.lab09.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab09.lab09.model.Disciplina;

@RestController
class DisciplinaController {

	private List<Disciplina> disciplinas;

	public DisciplinaController() {
		this.disciplinas = new ArrayList<>();
		disciplinas.add(new Disciplina(1, "Programação Orientada a Objetos", "POO", "Ciência da Computação", 2));
		disciplinas.add(new Disciplina(2, "Estruturas de Dados", "ED", "Ciência da Computação", 3));
		disciplinas.add(new Disciplina(3, "Banco de Dados", "BD", "Sistema de Informação", 4));
	}

	@GetMapping("/api/disciplinas")
	Iterable<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}
	
	@GetMapping("/api/disciplinas/{id}")
	Optional<Disciplina> getDisciplina(@PathVariable long id) {
		for (Disciplina d: disciplinas) {
			if (d.getId() == id) {
				return Optional.of(d);
			}
		}
		return Optional.empty();
	}
	
	@PostMapping("/api/disciplinas")
	Disciplina createDisciplina(@RequestBody Disciplina p) {
		long maxId = 1;
		for (Disciplina disc: disciplinas) {
			if (disc.getId() > maxId) {
				maxId = disc.getId();
			}
		}
		p.setId(maxId+1);
		disciplinas.add(p);
		return p;
	}
	
	@PutMapping("/api/disciplinas/{disciplinaId}")
	Optional<Disciplina> updatedisciplina(@RequestBody Disciplina disciplinaRequest, @PathVariable long disciplinaId) {
		Optional<Disciplina> opt = this.getDisciplina(disciplinaId);
		if (opt.isPresent()) {
			Disciplina disciplina = opt.get();
			disciplina.setNome(disciplinaRequest.getNome());
			disciplina.setSigla(disciplinaRequest.getSigla());
			disciplina.setCurso(disciplinaRequest.getCurso());
			disciplina.setSemestre(disciplinaRequest.getSemestre());
		}

		return opt;				
	}	
	
	@DeleteMapping(value = "/api/disciplinas/{id}")
	void deleteDisciplina(@PathVariable long id) {
		disciplinas.removeIf(p -> p.getId() == id);
	}		
}

