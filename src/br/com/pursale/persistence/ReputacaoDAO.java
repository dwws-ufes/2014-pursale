package br.com.pursale.persistence;

import java.util.List;

import br.com.pursale.domain.Reputacao;
import br.com.pursale.util.BaseDAO;

public interface ReputacaoDAO extends BaseDAO<Reputacao> {
	List<Reputacao> findByUser(Long idUser, int tipo); // 0 - comprador, 1 - vendedor
}
