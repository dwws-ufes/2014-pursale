package br.com.pursale.persistence;

import java.util.List;

import br.com.pursale.domain.Pedido;
import br.com.pursale.util.BaseDAO;

public interface PedidoDAO extends BaseDAO<Pedido> {
	public List<Pedido> findByUserAtivos(Long idUser, int tipo);
	
	public List<Pedido> findByUserFinalizados(Long idUser, int tipo);
}
