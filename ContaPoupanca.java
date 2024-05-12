package Marcha;

public class ContaPoupanca extends ContaBancaria {
    private double taxaJuros;  // Taxa de juros anual

    public ContaPoupanca(int numeroConta, String titular, double saldoInicial, double taxaJuros) {
        super(numeroConta, titular, saldoInicial);
        this.taxaJuros = taxaJuros;
    }

    public ContaPoupanca(int numeroConta, String titular, double taxaJuros) {
        super(numeroConta, titular);
        this.taxaJuros = taxaJuros;
    }

    // Método para calcular e aplicar juros ao saldo
    public void aplicarJuros() {
        double juros = calcularRendimento();
        depositar(juros);
        System.out.println("Juros aplicados: " + juros);
    }

    // Método para calcular o rendimento
    public double calcularRendimento() {
        return getSaldo() * (taxaJuros / 100);
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
               "número da conta=" + getNumeroConta() +
               ", titular='" + getTitular() + '\'' +
               ", saldo=" + getSaldo() +
               ", taxaJuros=" + taxaJuros +
               '}';
    }
}
