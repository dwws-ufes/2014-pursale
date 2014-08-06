package br.com.pursale.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pursale.domain.Usuario;
import br.com.pursale.util.BaseJPADAO;

/*
@Stateless
@LocalBean
*/
@Stateless
public class UsuarioJPADAO extends BaseJPADAO<Usuario> implements UsuarioDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Usuario> getDomainClass() {
		return Usuario.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario findByLogin(String login, String senha) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		
		Root<Usuario> root = cq.from(Usuario.class);
		
		cq.select(root);
		
		Predicate pEqualsLogin = cb.equal(root.get("login"), login);
		Predicate pEqualsSenha = cb.equal(root.get("senha"), senha);
		
		Predicate pAnd = cb.and(pEqualsLogin, pEqualsSenha);
		
		cq.where(pAnd);
		
		Query query = em.createQuery(cq);
		
		List<Usuario> usuario = query.getResultList();
		
		if(usuario == null || usuario.size() == 0) return null;
		else return usuario.get(0);
	}

}
