package br.com.imaginativo.itcm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "cursos")
public class Curso extends NamedEntity {

    private String descricao;

    @Column(name = "pre_requisitos")
    private String preRequisitos;

    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    private String ementa;

    private Double valor;

    private String tags;

    private String status;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Curso() {
        super();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
    public String toString() {
        return super.getName();
    }

}
