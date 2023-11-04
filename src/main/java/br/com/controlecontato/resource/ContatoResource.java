package br.com.controlecontato.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.controlecontato.dto.ContatoDTO;
import br.com.controlecontato.dto.PessoaDTO;
import br.com.controlecontato.model.Contato;
import br.com.controlecontato.model.Pessoa;
import br.com.controlecontato.service.ContatoService;
import br.com.controlecontato.service.PessoaService;

@RestController
@RequestMapping("/contato")
public class ContatoResource {
	
	@Autowired
	ContatoService contatoService;
	
	@GetMapping
	public ResponseEntity<List<ContatoDTO>> listarContatos(){
		List<ContatoDTO> contatos = contatoService.buscarTodos();
		if(contatos == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(contatos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContatoDTO> buscarPorId(@PathVariable Long id) throws Exception{
		try {
			ContatoDTO contato = contatoService.buscarPorId(id);
			return ResponseEntity.ok(contato);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Contato> salvar(@RequestBody ContatoDTO contato) throws Exception{
		try {
			contatoService.salvar(contato);
			
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato contato) throws Exception{
		try {
			contatoService.atualizar(id , contato);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) throws Exception{
		try {
			contatoService.deletar(id);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
