package br.com.pursale.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.pursale.domain.Produto;
import br.com.pursale.persistence.ProdutoDAO;

@RequestScoped
@ManagedBean
public class EditProduto {
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	@Inject
	private Produto produto;
	
	private Long id;
	
	public Produto getProduto(){
		return this.produto;
	}
	
	public void setProduto(Produto p){
		produto = p;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@PostConstruct
	public void init(){
		
		String param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		id = Long.valueOf(param);
		
		produto = produtoDAO.retrieveById(id);
	}
	
	public String save(){ 
		
		
		if(produtoDAO.save(produto) != null){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("\"" + produto.getNome() + "\" editado com sucesso!"));
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro ao editar o produto \"" + produto.getNome() + "\"."));		
		}
		
		return null;
	}
}
