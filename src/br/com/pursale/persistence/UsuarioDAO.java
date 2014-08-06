package br.com.pursale.persistence;

import br.com.pursale.domain.Usuario;
import br.com.pursale.util.BaseDAO;

public interface UsuarioDAO extends BaseDAO<Usuario>{
	Usuario findByLogin(String login, String senha);
}
