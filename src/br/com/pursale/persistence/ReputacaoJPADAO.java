package br.com.pursale.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.pursale.domain.Reputacao;
import br.com.pursale.util.BaseJPADAO;

@Stateless
public class ReputacaoJPADAO extends BaseJPADAO<Reputacao> implements ReputacaoDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Reputacao> getDomainClass() {
		return Reputacao.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reputacao> findByUser(Long idUser, int tipo) {
		
		String queryString;
		
		if(tipo == 0){ // Qualificacoes como comprador
			queryString = "SELECT r FROM Reputacao r "
		     	        + "WHERE r.tipo = 2 "
		     	        + "AND r.comprador.id = :usuario_id";
		}else{ // Qualificacoes como vendedor
			queryString = "SELECT r FROM Reputacao r "
		     	        + "WHERE r.tipo = 1 "
		     	        + "AND r.vendedor.id = :usuario_id";
		}
		
		Query query = getEntityManager().createQuery(queryString);
		
		query.setParameter("usuario_id", idUser);
		List<Reputacao> avaliacoes = (List<Reputacao>)query.getResultList();
		
		return avaliacoes;
	}

}
