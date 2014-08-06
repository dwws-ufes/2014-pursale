package br.com.pursale.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.pursale.domain.Pedido;
import br.com.pursale.util.BaseJPADAO;

@Stateless
public class PedidoJPADAO extends BaseJPADAO<Pedido> implements PedidoDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Pedido> getDomainClass() {
		return Pedido.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> findByUserAtivos(Long idUser, int tipo) {
		
		String queryString = null;
		
		if(tipo == 0){ // comprador
			queryString = "SELECT p FROM Pedido p " 
						 + "WHERE p.usuario.id = :usuario_id "
	                	 + "AND ( "
	                	 +        "p.status = 0 "
	                	 +        "OR "
	                	 +        "p.status = 2 "
	                	 +     ") ";
		}else{ // vendedor
			queryString =   "SELECT p FROM Pedido p " 
					 	  + "WHERE p.produto.usuario.id = :usuario_id "
					 	  + "AND ( "
					 	  +        "p.status = 0 "
					 	  +        "OR "
					 	  +        "p.status = 1 "
					 	  +     ") ";
		}
		
		Query query = getEntityManager().createQuery(queryString);
		
		query.setParameter("usuario_id", idUser);
		List<Pedido> pedidos = (List<Pedido>)query.getResultList();
		
		return pedidos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> findByUserFinalizados(Long idUser, int tipo) {
		
String queryString = null;
		
		if(tipo == 0){ // comprador
			queryString = "SELECT p FROM Pedido p " 
						 + "WHERE p.usuario.id = :usuario_id "
	                	 + "AND ( "
	                	 +        "p.status = 3 "
	                	 +        "OR "
	                	 +        "p.status = 1 "
	                	 +     ") ";
		}else{ // vendedor
			queryString =   "SELECT p FROM Pedido p " 
					 	  + "WHERE p.produto.usuario.id = :usuario_id "
					 	  + "AND ( "
					 	  +        "p.status = 3 "
					 	  +        "OR "
					 	  +        "p.status = 2 "
					 	  +     ") ";
		}
		
		Query query = getEntityManager().createQuery(queryString);
		
		query.setParameter("usuario_id", idUser);
		List<Pedido> pedidos = (List<Pedido>)query.getResultList();
		
		return pedidos;
	}
}
