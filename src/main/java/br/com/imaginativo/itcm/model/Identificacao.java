package br.com.imaginativo.itcm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "identificacoes")
public class Identificacao extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 9201525981647815162L;

    private String cpf;

    private String rg;

    @Column(name = "rg_uf")
    private String rgUF;

    @Column(name = "rg_orgao_emissor")
    private String rgOrgaoEmissor;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "rg_data_expedicao")
    private Date rgDataExpedicao;

    @Column(name = "titulo_eleitoral")
    private String tituloEleitoral;

    @Column(name = "titulo_eleitoral_zona")
    private String tituloEleitoralZona;

    @Column(name = "titulo_eleitoral_secao")
    private String tituloEleitoralSecao;

    @Column(name = "certificado_reservista")
    private String certificadoDeReservista;

    @Column(name = "carteira_trabalho")
    private String carteiraDeTrabalho;

    @Column(name = "carteira_trabalho_serie")
    private String carteiraDeTrabalhoSerie;

    @Column(name = "estado_civil")
    private String estadoCivil;

    private String sexo;

    private String naturalidade;
    
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgUF() {
        return rgUF;
    }

    public void setRgUF(String rgUF) {
        this.rgUF = rgUF;
    }

    public String getRgOrgaoEmissor() {
        return rgOrgaoEmissor;
    }

    public void setRgOrgaoEmissor(String rgOrgaoEmissor) {
        this.rgOrgaoEmissor = rgOrgaoEmissor;
    }

    public Date getRgDataExpedicao() {
        return rgDataExpedicao;
    }

    public void setRgDataExpedicao(Date rgDataExpedicao) {
        this.rgDataExpedicao = rgDataExpedicao;
    }

    public String getTituloEleitoral() {
        return tituloEleitoral;
    }

    public void setTituloEleitoral(String tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }

    public String getTituloEleitoralZona() {
        return tituloEleitoralZona;
    }

    public void setTituloEleitoralZona(String tituloEleitoralZona) {
        this.tituloEleitoralZona = tituloEleitoralZona;
    }

    public String getTituloEleitoralSecao() {
        return tituloEleitoralSecao;
    }

    public void setTituloEleitoralSecao(String tituloEleitoralSecao) {
        this.tituloEleitoralSecao = tituloEleitoralSecao;
    }

    public String getCertificadoDeReservista() {
        return certificadoDeReservista;
    }

    public void setCertificadoDeReservista(String certificadoDeReservista) {
        this.certificadoDeReservista = certificadoDeReservista;
    }

    public String getCarteiraDeTrabalho() {
        return carteiraDeTrabalho;
    }

    public void setCarteiraDeTrabalho(String carteiraDeTrabalho) {
        this.carteiraDeTrabalho = carteiraDeTrabalho;
    }

    public String getCarteiraDeTrabalhoSerie() {
        return carteiraDeTrabalhoSerie;
    }

    public void setCarteiraDeTrabalhoSerie(String carteiraDeTrabalhoSerie) {
        this.carteiraDeTrabalhoSerie = carteiraDeTrabalhoSerie;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
