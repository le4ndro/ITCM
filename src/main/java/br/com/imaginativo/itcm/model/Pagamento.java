package br.com.imaginativo.itcm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "pagamentos")
public class Pagamento extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -4740333057831327912L;

    private String formaPagamento;

    private Double valorBruto;

    private Double desconto;

    private Double valorPago;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL)
    @OrderBy("numeroParcela")
    private List<Parcela> parcelas;

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Double getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(Double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

}
