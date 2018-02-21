package br.com.imaginativo.itcm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "perfis")
public class Perfil extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8089995169389441535L;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
	
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
