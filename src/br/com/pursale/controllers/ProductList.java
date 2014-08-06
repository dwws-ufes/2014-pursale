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
public class ProductList {
	
	@EJB
	private ProdutoDAO produtoDAO;
	
	private List<Produto> produtos;
	
	private Produto produtoSelecionado;
	
	public Produto getProdutoSelecionado(){
		return produtoSelecionado;
	}
	
	public void setProdutoSelecionado(Produto p){
		produtoSelecionado = p;
	}
	
	public List<Produto> getProdutos(){
		return produtos;
	}

	@PostConstruct
	public void init(){

		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		produtos = produtoDAO.findByUser(session.getUsuario().getId());
	}
	
	public String pausaProduto(Long id){
		Produto selected = produtoDAO.retrieveById(id);
		
		produtos.remove(selected);
		
		selected.setStatus(0);
		
		produtoDAO.save(selected);

		return "list.faces?faces-redirect=true";
	}
	
	public boolean removeProduto(Produto p){
		int n = produtos.size();
		
		for(int i = 0; i < n; i++){
			Produto aux = produtos.get(i);
			
			System.out.println(aux.getId()+ " -- " + p.getId());
			
			if(aux.getId() == p.getId()){
				produtos.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public String apagaProduto(Long id){
		Produto selected = produtoDAO.retrieveById(id);
		
		removeProduto(selected);
		
		produtoDAO.delete(selected);
		
		return "";
	}
}
