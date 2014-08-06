package br.com.pursale.persistence;

import java.util.List;

import br.com.pursale.domain.Produto;
import br.com.pursale.util.BaseDAO;

public interface ProdutoDAO extends BaseDAO<Produto> {
	List<Produto> findByUser(Long idUser);
	
	List<Produto> findPausedByUser(Long idUser);
	
	List<Produto> findByName(String key, Long idUser);
}
