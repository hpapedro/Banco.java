package Marcha;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OperacaoBancaria {
    private int id;
    private int numeroConta;
    private String tipoOperacao;
    private double saldoAntes;
    private double saldoDepois;
    private Date data;

    public OperacaoBancaria(int id, int numeroConta, String tipoOperacao, double saldoAntes, double saldoDepois,
            Date data) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.tipoOperacao = tipoOperacao;
        this.saldoAntes = saldoAntes;
        this.saldoDepois = saldoDepois;
        this.data = data;
    }

    public String formatarRegistro() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return String.format(
                "ID: %d | Número da Conta: %d | Tipo de Operação: %s | Saldo Antes: %.2f | Saldo Depois: %.2f | Data: %s%n",
                id, numeroConta, tipoOperacao, saldoAntes, saldoDepois, sdf.format(data));
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public double getSaldoAntes() {
        return saldoAntes;
    }

    public double getSaldoDepois() {
        return saldoDepois;
    }

    public Date getData() {
        return data;
    }
}
