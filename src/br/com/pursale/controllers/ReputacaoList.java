package br.com.pursale.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;

import br.com.pursale.domain.Reputacao;
import br.com.pursale.persistence.ReputacaoDAO;

@Stateful
@LocalBean
@Model
public class ReputacaoList {
	
	@EJB
	private ReputacaoDAO reputacaoDAO;
	
	private List<Reputacao> reputacoesComprador;
	
	private List<Reputacao> reputacoesVendedor;
	
	private int reputacao;
	
	public List<Reputacao> getReputacoesComprador(){
		return reputacoesComprador;
	}
	
	public List<Reputacao> getReputacoesVendedor(){
		return reputacoesVendedor;
	}
	
	public int getReputacao(){
		return reputacao;
	}
	
	@PostConstruct
	public void init(){
		
		LoginUsuario session = (LoginUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginUsuario");
		
		reputacoesComprador = reputacaoDAO.findByUser(session.getUsuario().getId(), 0);
		reputacoesVendedor = reputacaoDAO.findByUser(session.getUsuario().getId(), 1);
		
		reputacao = session.getUsuario().getReputacao();
	}
	
	public String retornaClassQualificacao(int avaliacao){
		
		if(avaliacao == 0) return "avaliacao_negativa";
		if(avaliacao == 1) return "avaliacao_neutra";
		if(avaliacao == 2) return "avaliacao_positiva";
		
		return "Erro";
	}
}
