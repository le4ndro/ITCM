package br.com.imaginativo.itcm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ambientes")
public class Ambiente extends NamedEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 499672915991146787L;

    private String descricao;

    private Integer vagas;

    private String obs;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

}
