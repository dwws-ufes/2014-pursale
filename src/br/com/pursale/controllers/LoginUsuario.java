package br.com.pursale.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.pursale.domain.Usuario;
import br.com.pursale.persistence.UsuarioDAO;

@SessionScoped 
@ManagedBean
public class LoginUsuario {
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private Usuario usuario;
	
	private boolean logado = false;
	
	public boolean getLogado(){
		return logado;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public Usuario setUsuario() {
		return usuario;
	}
	
	public String find(){
		
		Usuario findUser = usuarioDAO.findByLogin(this.usuario.getLogin(), this.usuario.getSenha());
		
		if(findUser != null){
			this.usuario = findUser;
			this.logado = true;
			return "success";
		}
		else{
			return "index";
		}
		
	}
	
	public String logout(){
		usuario = new Usuario();
		
		logado = false;
		
		return "/index.faces?faces-redirect=true";
	}
}
