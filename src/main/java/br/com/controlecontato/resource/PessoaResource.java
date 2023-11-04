package br.com.controlecontato.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.controlecontato.dto.PessoaDTO;
import br.com.controlecontato.model.Pessoa;
import br.com.controlecontato.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	@Autowired
	PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<List<PessoaDTO>> listarPessoas(){
		List<PessoaDTO> pessoas = pessoaService.listarTodos();
		if(pessoas == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pessoas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long id) throws Exception{
		try {
			PessoaDTO pessoa = pessoaService.buscarPorId(id);
			return ResponseEntity.ok(pessoa);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody PessoaDTO pessoa) throws Exception{
		try {
			pessoaService.salvar(pessoa);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody PessoaDTO pessoa) throws Exception{
		try {
			pessoaService.atualizar(id , pessoa);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws Exception{
		try {
			pessoaService.apagar(id);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
}
