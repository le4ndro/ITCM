package br.com.imaginativo.itcm.util;

public class PagamentoParcelaConfig {

    private int parcelas;

    private double valorParcela;

    private boolean primeiraParcelaAVista;

    private int diaVencimento;

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public boolean isPrimeiraParcelaAVista() {
        return primeiraParcelaAVista;
    }

    public void setPrimeiraParcelaAVista(boolean primeiraParcelaAVista) {
        this.primeiraParcelaAVista = primeiraParcelaAVista;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

}
