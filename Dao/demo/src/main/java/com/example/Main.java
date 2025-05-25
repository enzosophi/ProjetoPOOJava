package com.example;
// DANIEL BORGES VALENTIM - 10427564
// JOÃO VITOR GOLFIERI MENDONÇA - 10434460
// ENZO PINHEIRO DE OLIVEIRA - 10434443

import java.util.*;

import com.example.abstrata.Propriedade;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IClienteDAO clienteDAO = DAOFactory.getClienteDAO();
        IProprietarioDAO proprietarioDAO = DAOFactory.getProprietarioDAO();
        IPropriedadeDAO propriedadeDAO = DAOFactory.getPropriedadeDAO();
        IReservaDAO reservaDAO = DAOFactory.getReservaDAO();

        try {
            DadosIniciais.carregar(clienteDAO, proprietarioDAO, propriedadeDAO, reservaDAO);
        } catch (SQLException e) {
            System.out.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }

        boolean executando = true;
        while (executando) {
            System.out.println("\n==== MACKNB - Plataforma de Locação ====");
            System.out.println("1. Login como Cliente");
            System.out.println("2. Login como Proprietário");
            System.out.println("3. Sair");
            System.out.println("4. Listar todos os usuários");
            System.out.println("5. Listar todas as propriedades");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuCliente(scanner, clienteDAO, propriedadeDAO, reservaDAO);
                case 2 -> menuProprietario(scanner, proprietarioDAO, propriedadeDAO);
                case 3 -> {
                    System.out.println("Saindo... Obrigado por usar a plataforma!");
                    executando = false;
                }
                case 4 -> {
                    try {
                        System.out.println("== Clientes ==");
                        clienteDAO.buscarTodos().forEach(c -> 
                            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Email: " + c.getEmail())
                        );

                        System.out.println("\n== Proprietários ==");
                        proprietarioDAO.buscarTodos().forEach(p -> 
                            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | Email: " + p.getEmail())
                        );
                    } catch (SQLException e) {
                        System.out.println("Erro ao listar usuários: " + e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        propriedadeDAO.buscarTodas().forEach(Propriedade::exibirPropriedade);
                    } catch (SQLException e) {
                      System.out.println("Erro ao buscar propriedades: " + e.getMessage());
                    }
                }
                default -> System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void menuCliente(Scanner scanner, IClienteDAO clienteDAO, IPropriedadeDAO propriedadeDAO, IReservaDAO reservaDAO) {
        System.out.println("Digite seu ID de cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteDAO.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Criando novo cadastro...");
            System.out.println("Email: ");
            String email = scanner.nextLine();
            System.out.println("Senha: ");
            String senha = scanner.nextLine();
            System.out.println("Nome: ");
            String nome = scanner.nextLine();
            cliente = new Cliente(nome, email, senha);
            clienteDAO.inserir(cliente);
        }

        boolean ativo = true;
        while (ativo) {
            System.out.println("\n-- Menu Cliente --");
            System.out.println("1. Ver propriedades disponíveis");
            System.out.println("2. Fazer reserva");
            System.out.println("3. Ver minhas reservas");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            int op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1 -> {
                    try {
                        List<Propriedade> props = propriedadeDAO.buscarDisponiveis();
                        props.forEach(Propriedade::exibirPropriedade);
                    } catch (SQLException e) {
                        System.out.println("Erro ao buscar propriedades: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        List<Propriedade> props = propriedadeDAO.buscarDisponiveis();
                        for (int i = 0; i < props.size(); i++) {
                            System.out.println((i + 1) + ". " + props.get(i).getTitulo());
                        }
                        System.out.println("Escolha uma propriedade (número): ");
                        int escolha = scanner.nextInt();
                        scanner.nextLine();
                        Propriedade selecionada = props.get(escolha - 1);

                        System.out.println("Data check-in (dd/MM/yyyy): ");
                        String checkinStr = scanner.nextLine();
                        System.out.println("Data check-out (dd/MM/yyyy): ");
                        String checkoutStr = scanner.nextLine();

                        LocalDate checkin = ConversorDeData.deStringParaDate(checkinStr).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate checkout = ConversorDeData.deStringParaDate(checkoutStr).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        Reserva reserva = new Reserva(selecionada, cliente, checkin, checkout);
                        reservaDAO.inserir(reserva);
                    } catch (Exception e) {
                        System.out.println("Erro ao realizar reserva: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        List<Reserva> reservas = reservaDAO.buscarPorCliente(cliente.getId());
                        reservas.forEach(System.out::println);
                    } catch (SQLException e) {
                        System.out.println("Erro ao buscar reservas: " + e.getMessage());
                    }
                }
                case 4 -> ativo = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuProprietario(Scanner scanner, IProprietarioDAO proprietarioDAO, IPropriedadeDAO propriedadeDAO) {
        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();
        try {
            Proprietario proprietario = proprietarioDAO.buscarPorNome(nome);
            if (proprietario == null) {
                System.out.println("Proprietário não encontrado. Criando novo cadastro...");
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Senha: ");
                String senha = scanner.nextLine();
                proprietario = new Proprietario(nome, email, senha);
                proprietarioDAO.inserir(proprietario);
            }

            boolean ativo = true;
            while (ativo) {
                System.out.println("\n-- Menu Proprietário --");
                System.out.println("1. Cadastrar nova propriedade");
                System.out.println("2. Ver minhas propriedades");
                System.out.println("3. Voltar");
                System.out.print("Escolha uma opção: ");
                int op = scanner.nextInt();
                scanner.nextLine();

                switch (op) {
                    case 1 -> {
                        System.out.println("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.println("Localização: ");
                        String loc = scanner.nextLine();
                        System.out.println("Capacidade: ");
                        int cap = scanner.nextInt();
                        System.out.println("Preço por noite: ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Imagem (nome do arquivo): ");
                        String imagem = scanner.nextLine();
                        scanner.nextLine();

                        System.out.println("Qual tipo de propriedade deseja cadastrar?");
                        System.out.println("1. Casa");
                        System.out.println("2. Apartamento");
                        System.out.println("3. Sítio");
                        System.out.print("Escolha uma opção: ");
                        int tipo = scanner.nextInt();
                        scanner.nextLine();

                        Propriedade nova = null;

                        switch (tipo) {
                            case 1 -> {
                                System.out.print("Possui piscina (true/false): ");
                                boolean piscina = scanner.nextBoolean();
                                System.out.print("Preço por pessoa: ");
                                double precoPessoa = scanner.nextDouble();
                                nova = new Casa(titulo, desc, loc, cap, preco, proprietario, imagem, piscina, precoPessoa);
                            }
                            case 2 -> {
                                System.out.print("Andar: ");
                                int andar = scanner.nextInt();
                                System.out.print("Taxa de condomínio: ");
                                double taxa = scanner.nextDouble();
                                nova = new Apartamento(titulo, desc, loc, cap, preco, proprietario, imagem, andar, taxa);
                            }
                            case 3 -> {
                                System.out.print("Área total do sítio (m²): ");
                                double area = scanner.nextDouble();
                                nova = new Sitio(titulo, desc, loc, cap, preco, proprietario, imagem, area);
                            }
                            default -> System.out.println("Tipo inválido.");
                        }
                        if (nova != null) {
                            try {
                                propriedadeDAO.inserir(nova);
                            } catch (SQLException e) {
                                System.out.println("Erro ao cadastrar propriedade: " + e.getMessage());
                            }
                        }
                    }
                    case 2 -> {
                        try {
                            List<Propriedade> minhas = propriedadeDAO.buscarPorProprietario(proprietario.getId());
                            minhas.forEach(Propriedade::exibirPropriedade);
                        } catch (SQLException e) {
                            System.out.println("Erro ao buscar propriedades: " + e.getMessage());
                        }
                    }
                    case 3 -> ativo = false;
                    default -> System.out.println("Opção inválida.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar proprietário: " + e.getMessage());
        }
    }
}
