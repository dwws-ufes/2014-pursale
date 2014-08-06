package br.com.pursale.controllers;


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
public class AddUsuario {
	
	@EJB
	private UsuarioDAO usuarioDAO;

	private Usuario usuario = new Usuario();
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public String save(){
		
		
		if(usuarioDAO.save(usuario) != null){
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Usuario \"" + usuario.getLogin() + "\" cadastrado com sucesso!"));
			
			this.usuario = new Usuario();
			
			return "success";
		}
		
		return "failed";
	}
}
