package br.com.pursale.controllers;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Produto;
import br.com.pursale.persistence.ProdutoDAO;

@Stateful
@LocalBean
@Model
public class AddProduto {
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	private Produto produto = new Produto();
	
	public Produto getProduto(){
		return this.produto;
	}
	
	public String save(){ 
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		produto.setUsuario(session.getUsuario());
		
		produto.setStatus(1);
		
		if(produtoDAO.save(produto) != null){

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("\"" + produto.getNome() + "\" cadastrado com sucesso!"));
			
			produto = new Produto();
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro ao cadastrar o produto \"" + produto.getNome() + "\"."));
		}
		
		return "list";
	}
}
