package Marcha;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BancoDeDados {

    private static final String LOG_FILE = "log_operacoes.txt";
    private static final String URL = "jdbc:mysql://localhost:3306/Banco";
    private static final String USUARIO = "root";
    private static final String SENHA = "positivo";
    private Connection conexao = null;

    BancoDeDados() throws SQLException {
        this.conectar();
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void inserirCliente(String nome) throws SQLException {
        String sql = "INSERT INTO Cliente (nome) VALUES (?)";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
        }
    }

    public void inserirContaCorrente(int numeroConta, String titular, double saldo, double limiteChequeEspecial)
            throws SQLException {
        String sql = "INSERT INTO ContaBancaria (numeroConta, titular, saldo, limiteChequeEspecial) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroConta);
            pstmt.setString(2, titular);
            pstmt.setDouble(3, saldo);
            pstmt.setDouble(4, limiteChequeEspecial);
            pstmt.executeUpdate();
        }
    }

    public void inserirContaPoupanca(int numeroConta, String titular, double saldo, double taxaJuros)
            throws SQLException {
        String sql = "INSERT INTO ContaBancaria (numeroConta, titular, saldo, taxaJuros) VALUES (?, ?, ?, ?)";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroConta);
            pstmt.setString(2, titular);
            pstmt.setDouble(3, saldo);
            pstmt.setDouble(4, taxaJuros);
            pstmt.executeUpdate();
        }
    }

    public void listarContas() throws SQLException {
        String sql = "SELECT numeroConta, titular, saldo, limiteChequeEspecial FROM ContaBancaria";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int numeroConta = rs.getInt("numeroConta");
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");
                double limiteChequeEspecial = rs.getDouble("limiteChequeEspecial");
                System.out.println("Conta No.: " + numeroConta + ", Titular: " + titular + ", Saldo: " + saldo +
                        ", Limite Cheque Especial: " + limiteChequeEspecial);
            }
        }
    }

    public void registrarOperacao(OperacaoBancaria operacao) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(operacao.formatarRegistro());
        } catch (IOException e) {
            System.err.println("Erro ao registrar operação bancária: " + e.getMessage());
        }
    }

    private boolean contaExiste(int numeroConta) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM ContaBancaria WHERE numeroConta = ?";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroConta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }

    private double obterSaldo(int numeroConta) throws SQLException {
        String sql = "SELECT saldo FROM ContaBancaria WHERE numeroConta = ?";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numeroConta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("saldo");
                }
            }
        }
        return 0.0;
    }

    private void atualizarSaldo(int numeroConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE ContaBancaria SET saldo = ? WHERE numeroConta = ?";
        try (Connection conn = conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, novoSaldo);
            pstmt.setInt(2, numeroConta);
            pstmt.executeUpdate();
        }
    }

    public void sacar(int numeroConta, double valor) {
        try {
            if (contaExiste(numeroConta)) {
                double saldoAtual = obterSaldo(numeroConta);
                if (saldoAtual >= valor) {
                    double novoSaldo = saldoAtual - valor;
                    atualizarSaldo(numeroConta, novoSaldo);
                    OperacaoBancaria operacao = new OperacaoBancaria(numeroConta, numeroConta, "Saque", saldoAtual,
                            novoSaldo,
                            new Date());
                    registrarOperacao(operacao);
                    System.out.println("Saque de " + valor + " realizado com sucesso na conta " + numeroConta);
                } else {
                    System.out.println("Saldo insuficiente para realizar o saque na conta " + numeroConta);
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar saque: " + e.getMessage());
        }
    }

    int numeroConta;

    public void depositar(int numeroConta, double valor) {
        try {
            if (contaExiste(numeroConta)) {
                double saldoAtual = obterSaldo(numeroConta);
                double novoSaldo = saldoAtual + valor;
                atualizarSaldo(numeroConta, novoSaldo);
                OperacaoBancaria operacao = new OperacaoBancaria(numeroConta, numeroConta, "Depósito", saldoAtual,
                        novoSaldo,
                        new Date());
                registrarOperacao(operacao);
                System.out.println("Depósito de " + valor + " realizado com sucesso na conta " + numeroConta);
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar depósito: " + e.getMessage());
        }
    }
}
