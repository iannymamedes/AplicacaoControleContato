package br.com.controlecontato.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.controlecontato.dto.ContatoDTO;
import br.com.controlecontato.dto.PessoaDTO;
import br.com.controlecontato.model.Contato;
import br.com.controlecontato.model.Endereco;
import br.com.controlecontato.model.Pessoa;
import br.com.controlecontato.repository.ContatoRepository;
import br.com.controlecontato.repository.PessoaRepository;
import br.com.controlecontato.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {
	@Autowired
	PessoaRepository repository;
	@Autowired
	ContatoRepository contatoRepository;


	public void salvar(PessoaDTO pessoa) {
	    Pessoa novaPessoa = new Pessoa();
	    novaPessoa.setNome(pessoa.getNome());
	    novaPessoa.setIdade(pessoa.getIdade());
	    novaPessoa.setCpf(pessoa.getCpf());

	    Endereco enderecoPessoa = new Endereco();
	    enderecoPessoa.setEndereco(pessoa.getEndereco().getEndereco());
	    enderecoPessoa.setCep(pessoa.getEndereco().getCep());
	    enderecoPessoa.setCidade(pessoa.getEndereco().getCidade());
	    enderecoPessoa.setUf(pessoa.getEndereco().getUf());
	    novaPessoa.setEndereco(enderecoPessoa);

	    novaPessoa = repository.save(novaPessoa);

	    List<ContatoDTO> listaDTO = pessoa.getContatos();
	    List<Contato> novaListaContato = new ArrayList<>();

	    for (ContatoDTO contato : listaDTO) {
	        Contato novoContato = new Contato();
	        novoContato.setContato(contato.getContato());
	        novoContato.setTipoContato(contato.getTipoContato());
	        novoContato.setPessoa(novaPessoa);
	        novaListaContato.add(novoContato);
	    }

	    contatoRepository.saveAll(novaListaContato);
	}


	@Override
	public PessoaDTO buscarPorId(Long id) throws Exception {
		Optional<Pessoa> pessoa = repository.findById(id);
		if(pessoa.isEmpty()) {
			throw new Exception("Usuário não encontrado");
		}
		PessoaDTO retornoPessoa = PessoaDTO.fromEntity(pessoa.get());
		
		return retornoPessoa;
	}

	@Override
	public List<PessoaDTO> listarTodos() {
		List<Pessoa> pessoa = repository.findAll();
		List<PessoaDTO> listaRetorno = pessoa.stream().map(PessoaDTO::fromEntity).toList();
		return listaRetorno;
	}

	@Override
	public void atualizar(Long id, PessoaDTO pessoa) throws Exception {
		Optional<Pessoa> pessoaModelo = repository.findById(id);
		if(pessoaModelo.isEmpty()) {
			throw new Exception("Usuário não encontrado");
		}
		Pessoa novaPessoa = pessoaModelo.get();
		
		novaPessoa.setNome(pessoa.getNome());
		novaPessoa.setIdade(pessoa.getIdade());
		novaPessoa.setCpf(pessoa.getCpf());
		Endereco enderecoPessoa = new Endereco();
		enderecoPessoa.setEndereco(pessoa.getEndereco().getEndereco());
		enderecoPessoa.setCep(pessoa.getEndereco().getCep());
		enderecoPessoa.setCidade(pessoa.getEndereco().getCidade());
		enderecoPessoa.setUf(pessoa.getEndereco().getUf());
		novaPessoa.setEndereco(enderecoPessoa);

		repository.save(novaPessoa);
		
	}

	@Override
	public void apagar(Long id) throws Exception {
		Optional<Pessoa> pessoaModelo = repository.findById(id);
		if(pessoaModelo.isEmpty()) {
			throw new Exception("Usuário não encontrado");
		}
		Pessoa pessoa = pessoaModelo.get();
		pessoa.setSituacao(0);
		repository.save(pessoa);
	}
}
