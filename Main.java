package Marcha;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            BancoDeDados conexao = new BancoDeDados();

            // Adicionando uma conta poupança
            // int numeroContaPoupanca = 12345;
            // String titularPoupanca = "Ana Paula";
            // double saldoInicialPoupanca = 1179000;
            // double taxaJurosPoupanca = 100;

            // conexao.inserirContaPoupanca(numeroContaPoupanca, titularPoupanca,
            // saldoInicialPoupanca, taxaJurosPoupanca);

            // Adicionando uma conta corrente
            // int numeroContaCorrente = 54321;
            // String titularCorrente = "João Silva";
            // double saldoInicialCorrente = 5000;
            // double limiteChequeEspecialCorrente = 2000;

            // conexao.inserirContaCorrente(numeroContaCorrente, titularCorrente,
            // saldoInicialCorrente,
            // limiteChequeEspecialCorrente);

            // Sacar 50 da conta poupança
            int numeroContaSaque = 12345;
            double valorSaque = 350.00;

            conexao.sacar(numeroContaSaque, valorSaque);

            // Depositar 1000 na conta corrente
            // int numeroContaDeposito = 1;
            // double valorDeposito = 50.00;

            // conexao.depositar(numeroContaDeposito, valorDeposito);

            // Listar todas as contas
            try {
                System.out.println("\nListando todas as contas:");
                conexao.listarContas();
            } catch (SQLException e) {
                System.out.println("Erro ao listar contas: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
