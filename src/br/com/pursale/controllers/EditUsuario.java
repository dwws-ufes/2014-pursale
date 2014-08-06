package br.com.pursale.controllers;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Usuario;
import br.com.pursale.persistence.UsuarioDAO;


@Stateful
@LocalBean
@Model
public class EditUsuario {
	
	@EJB
	private UsuarioDAO usuarioDAO;

	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	@PostConstruct
	public void init(){
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		Long id = session.getUsuario().getId();
		
		usuario = usuarioDAO.retrieveById(id);
	}
	
	public String save(){
		
		
		if(usuarioDAO.save(usuario) != null){
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Perfil atualizado com sucesso!"));
			
			return null;
		}
		
		return "failed";
	}
}
