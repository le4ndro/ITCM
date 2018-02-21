package br.com.imaginativo.itcm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alunos")
public class Aluno extends NamedEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 6709269961108327476L;

    private String matricula;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dt_nascimento")
    private Date dataDeNascimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identificacao_id")
    private Identificacao identificacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    private String escolaridade;

    @Column(name = "status_academico")
    private String statusAcademico;

    @Column(name = "status_financeiro")
    private String statusFinanceiro;

    private String obs;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Identificacao getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Identificacao identificacao) {
        this.identificacao = identificacao;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getStatusAcademico() {
        return statusAcademico;
    }

    public void setStatusAcademico(String statusAcademico) {
        this.statusAcademico = statusAcademico;
    }

    public String getStatusFinanceiro() {
        return statusFinanceiro;
    }

    public void setStatusFinanceiro(String statusFinanceiro) {
        this.statusFinanceiro = statusFinanceiro;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
