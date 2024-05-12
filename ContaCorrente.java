package Marcha;

public class ContaCorrente extends ContaBancaria {
    private double limiteChequeEspecial;

    public ContaCorrente(int numeroConta, String titular, double saldoInicial, double limiteChequeEspecial) {
        super(numeroConta, titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public ContaCorrente(int numeroConta, String titular, double limiteChequeEspecial) {
        super(numeroConta, titular);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    // Método para calcular rendimento de uma aplicação em CDB com liquidez diária
    public double calcularRendimentoCDB(double taxaAnual) {
        double rendimentoDiario = taxaAnual / 365;
        return getSaldo() * rendimentoDiario;
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && (getSaldo() + limiteChequeEspecial >= valor)) {
            super.sacar(valor);
        } else if (valor <= 0) {
            System.out.println("Valor de saque deve ser positivo.");
        } else {
            System.out.println("Saldo e limite de cheque especial insuficientes.");
        }
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                "número da conta=" + getNumeroConta() +
                ", titular='" + getTitular() + '\'' +
                ", saldo=" + getSaldo() +
                ", limiteChequeEspecial=" + limiteChequeEspecial +
                '}';
    }
}
