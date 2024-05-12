
package Marcha;

import java.util.ArrayList;

public class Banco {
    private ArrayList<ContaBancaria> contas; // objetos arm obj ContaBancaria

    public Banco() {
        this.contas = new ArrayList<>();// i lista
    }

    // Método para adicionar uma conta ao banco
    public void adicionarConta(ContaBancaria conta) { // recebe obj CB e add
        contas.add(conta);
        System.out.println("Conta adicionada com sucesso.");
    }

    // Método para excluir uma conta do banco
    public void excluirConta(ContaBancaria conta) {
        if (contas.remove(conta)) {
            System.out.println("Conta removida com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Método para gerenciar uma conta no banco
    public void gerenciarConta(int numeroConta, double valor) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumeroConta() == numeroConta) {
                if (valor > 0) {
                    conta.depositar(valor);
                } else if (valor < 0) {
                    conta.sacar(-valor);
                } else {
                    System.out.println("O valor deve ser diferente de zero.");
                }
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    // Método para imprimir todas as contas do banco
    public void imprimirContas() {
        System.out.println("Contas no banco:");
        for (ContaBancaria conta : contas) {
            System.out.println(conta);
        }
    }

}
