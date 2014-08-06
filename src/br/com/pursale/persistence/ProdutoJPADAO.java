package br.com.pursale.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.pursale.domain.Produto;
import br.com.pursale.util.BaseJPADAO;

@Stateless
public class ProdutoJPADAO extends BaseJPADAO<Produto> implements ProdutoDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Produto> getDomainClass() {
		return Produto.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findByUser(Long idUser) {
	
		String queryString = "SELECT p FROM Produto p " +
                			 "WHERE p.status = 1 AND p.usuario.id = :usuario_id";
		Query query = getEntityManager().createQuery(queryString);
		
		query.setParameter("usuario_id", idUser);
		List<Produto> produtos = (List<Produto>)query.getResultList();
		
		return produtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findPausedByUser(Long idUser) {
		String queryString = "SELECT p FROM Produto p " +
   			 "WHERE p.status = 0 AND p.usuario.id = :usuario_id";
		Query query = getEntityManager().createQuery(queryString);
		
		query.setParameter("usuario_id", idUser);
		List<Produto> produtos = (List<Produto>)query.getResultList();
		
		return produtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findByName(String key, Long idUser) {
		
		String queryString =  "SELECT p FROM Produto p "
							+ "WHERE p.status = 1 "
							+ "AND p.quantidade > 0 "
	   			 			+ "AND p.usuario.id <> :usuario_id "
	   			 			+ "AND (p.nome LIKE :key "
	   			 			+  "OR p.titulo LIKE :key)";
		Query query = getEntityManager().createQuery(queryString);
			
		query.setParameter("usuario_id", idUser);
		query.setParameter("key", "%" + key + "%");
			
		List<Produto> produtos = (List<Produto>)query.getResultList();
			
		return produtos;
	}

}
