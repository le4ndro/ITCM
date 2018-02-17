package br.com.imaginativo.itcm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "atividades")
public class Atividade extends NamedEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3643647106394900164L;

    private String tipo;

    private String nota;

    @Column(name = "dt_criacao")
    private Date dataCriacao;

    @Column(name = "dt_execucao")
    private Date dataExecucao;

    @ManyToOne
    @JoinColumn(name = "interesse_id")
    private Interesse interesse;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

}
