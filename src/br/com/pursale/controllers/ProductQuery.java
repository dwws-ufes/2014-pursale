package br.com.pursale.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Produto;
import br.com.pursale.persistence.ProdutoDAO;

@Stateful
@LocalBean
@Model
public class ProductQuery {
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	private List<Produto> produtos;
	
	private String produtoQuery;
	
	public String getProdutoQuery(){
		return produtoQuery;
	}
	
	public List<Produto> getProdutos(){
		return produtos;
	}

	@PostConstruct
	public void init(){
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		produtoQuery = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("produto");
		
		produtos = produtoDAO.findByName(produtoQuery, session.getUsuario().getId());
	}
	
	public String detalhes(){
		return "/product/view.faces?faces-redirect=true";
	}
}
