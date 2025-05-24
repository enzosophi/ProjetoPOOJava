package com.example;
//Nome dos integrantes: Daniel Borges Valentim - 10427564
//                      João Vitor Golfieri Mendonça - 10434460
//                      Enzo Pinheiro De Oliveira - 10434443
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.example.abstrata.Usuario;
import com.example.abstrata.Propriedade;

public class Main {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Propriedade> propriedades = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>(); // Lista para armazenar todas as reservas
    private static Scanner tcl = new Scanner(System.in);

    public static Usuario buscarUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        inicializarDados();

        while (true) {
            exibirMenuPrincipal();
            int opcao = -1;
            try {
                opcao = Integer.parseInt(tcl.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                continue;
            }

            switch (opcao) {
                case 1: // Proprietário
                    menuProprietario();
                    break;
                case 2: // Cliente
                    menuCliente();
                    break;
                case 3: // Listar usuários cadastrados
                    listarUsuarios();
                    break;
                case 4: // Listar propriedades cadastradas
                    listarPropriedades();
                    break;
                case 5: // Sair
                    System.out.println("Saindo do sistema...");
                    // Salvar dados (persistência) aqui antes de sair
                    salvarDados();
                    tcl.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1 - Acessar como Proprietário");
        System.out.println("2 - Acessar como Cliente");
        System.out.println("3 - Listar Todos os Usuários Cadastrados");
        System.out.println("4 - Listar Todas as Propriedades Cadastradas");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void menuProprietario() {
        System.out.print("Digite seu nome de Proprietário: ");
        String nomeProprietario = tcl.nextLine();
        Usuario usuarioLogado = buscarUsuario(nomeProprietario);

        if (usuarioLogado instanceof Proprietario) {
            Proprietario proprietarioLogado = (Proprietario) usuarioLogado;
            while (true) {
                System.out.println("\n===== MENU DO PROPRIETÁRIO =====");
                System.out.println("1 - Cadastrar Propriedade");
                System.out.println("2 - Exibir Detalhes de Propriedades");
                System.out.println("3 - Listar Propriedades Alugadas");
                System.out.println("4 - Listar Todas as Minhas Propriedades");
                System.out.println("5 - Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");
                int opcaoProprietario = -1;
                try {
                    opcaoProprietario = Integer.parseInt(tcl.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    continue;
                }

                switch (opcaoProprietario) {
                    case 1:
                        cadastrarPropriedade(proprietarioLogado);
                        break;
                    case 2:
                        System.out.print("Digite o título da propriedade para ver detalhes: ");
                        String tituloDetalhes = tcl.nextLine();
                        boolean found = false;
                        for (Propriedade p : proprietarioLogado.getPropriedades()) {
                            if (p.getTitulo().equalsIgnoreCase(tituloDetalhes)) {
                                p.exibirPropriedade();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Propriedade não encontrada entre suas propriedades.");
                        }
                        break;
                    case 3:
                        proprietarioLogado.listarPropriedadesAlugadas();
                        break;
                    case 4:
                        proprietarioLogado.listarPropriedades();
                        break;
                    case 5:
                        return; // Voltar ao menu principal
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } else {
            System.out.println("Usuário não encontrado ou não é um proprietário. Por favor, cadastre-se como proprietário primeiro.");
            System.out.print("Deseja cadastrar-se como Proprietário agora? (S/N): ");
            String resp = tcl.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                cadastrarUsuario(true); // true para Proprietário
            }
        }
    }

    private static void menuCliente() {
        System.out.print("Digite seu nome de Cliente: ");
        String nomeCliente = tcl.nextLine();
        Usuario usuarioLogado = buscarUsuario(nomeCliente);

        if (usuarioLogado instanceof Cliente) {
            Cliente clienteLogado = (Cliente) usuarioLogado;
            while (true) {
                System.out.println("\n===== MENU DO CLIENTE =====");
                System.out.println("1 - Buscar e Fazer Reserva");
                System.out.println("2 - Consultar Minhas Reservas");
                System.out.println("3 - Verificar Propriedades Disponíveis");
                System.out.println("4 - Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");
                int opcaoCliente = -1;
                try {
                    opcaoCliente = Integer.parseInt(tcl.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    continue;
                }

                switch (opcaoCliente) {
                    case 1:
                        realizarReserva(clienteLogado);
                        break;
                    case 2:
                        clienteLogado.listarReservas();
                        break;
                    case 3:
                        clienteLogado.listarPropriedadesDisponiveis(propriedades);
                        break;
                    case 4:
                        return; // Voltar ao menu principal
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } else {
            System.out.println("Usuário não encontrado ou não é um cliente. Por favor, cadastre-se como cliente primeiro.");
            System.out.print("Deseja cadastrar-se como Cliente agora? (S/N): ");
            String resp = tcl.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                cadastrarUsuario(false); // false para Cliente
            }
        }
    }

    private static void cadastrarUsuario(boolean isProprietario) {
        String nome, email, senha;
        System.out.print("Digite o nome: ");
        nome = tcl.nextLine();
        System.out.print("Digite o email: ");
        email = tcl.nextLine();
        System.out.print("Digite a senha: ");
        senha = tcl.nextLine();

        try {
            if (buscarUsuario(nome) != null) {
                System.out.println("Erro: Já existe um usuário com este nome. Escolha um nome diferente.");
                return;
            }
            if (isProprietario) {
                usuarios.add(new Proprietario(nome, email, senha));
                System.out.println("Proprietário " + nome + " cadastrado com sucesso!");
            } else {
                usuarios.add(new Cliente(nome, email, senha));
                System.out.println("Cliente " + nome + " cadastrado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void cadastrarPropriedade(Proprietario proprietario) {
        System.out.println("\n--- Cadastro de Nova Propriedade ---");
        String titulo, descricao, localizacao, imagem;
        int capacidade;
        double precoPorNoite;

        try {
            System.out.print("Título: ");
            titulo = tcl.nextLine();
            System.out.print("Descrição: ");
            descricao = tcl.nextLine();
            System.out.print("Localização: ");
            localizacao = tcl.nextLine();
            System.out.print("Capacidade: ");
            capacidade = Integer.parseInt(tcl.nextLine());
            System.out.print("Preço por Noite: ");
            precoPorNoite = Double.parseDouble(tcl.nextLine());
            System.out.print("URL da Imagem (opcional, digite ENTER se não houver): ");
            imagem = tcl.nextLine();
            if (imagem.isEmpty()) {
                imagem = "N/A";
            }

            System.out.println("Qual o tipo de propriedade?");
            System.out.println("1 - Casa");
            System.out.println("2 - Apartamento");
            System.out.println("3 - Sítio");
            System.out.print("Escolha uma opção: ");
            int tipoPropriedade = Integer.parseInt(tcl.nextLine());

            Propriedade novaPropriedade = null;
            switch (tipoPropriedade) {
                case 1:
                    System.out.print("Possui Piscina? (true/false): ");
                    boolean possuiPiscina = Boolean.parseBoolean(tcl.nextLine());
                    System.out.print("Preço por Pessoa: ");
                    double precoPorPessoa = Double.parseDouble(tcl.nextLine());
                    novaPropriedade = new Casa(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, possuiPiscina, precoPorPessoa);
                    break;
                case 2:
                    System.out.print("Andar: ");
                    int andar = Integer.parseInt(tcl.nextLine());
                    System.out.print("Taxa (e.g., condomínio): ");
                    double taxa = Double.parseDouble(tcl.nextLine());
                    novaPropriedade = new Apartamento(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, andar, taxa);
                    break;
                case 3:
                    System.out.print("Área Total (m²): ");
                    double areaTotal = Double.parseDouble(tcl.nextLine());
                    novaPropriedade = new Sitio(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario, imagem, areaTotal);
                    break;
                default:
                    System.out.println("Tipo de propriedade inválido.");
                    return;
            }

            if (novaPropriedade != null) {
                // Verificar se já existe uma propriedade com o mesmo título e localização para o mesmo proprietário
                boolean existe = false;
                for (Propriedade p : propriedades) {
                    if (p.getTitulo().equalsIgnoreCase(novaPropriedade.getTitulo()) &&
                        p.getLocalizacao().equalsIgnoreCase(novaPropriedade.getLocalizacao()) &&
                        p.getProprietario().equals(novaPropriedade.getProprietario())) {
                        existe = true;
                        break;
                    }
                }
                if (existe) {
                    System.out.println("Erro: Já existe uma propriedade com este título e localização cadastrada por este proprietário.");
                } else {
                    propriedades.add(novaPropriedade);
                    proprietario.cadastrarPropriedade(novaPropriedade);
                    System.out.println("Propriedade cadastrada com sucesso!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de entrada: Por favor, digite um número válido para capacidade, preço, andar, taxa ou área.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar propriedade: " + e.getMessage());
        }
    }

    private static void realizarReserva(Cliente cliente) {
        System.out.println("\n--- Fazer Nova Reserva ---");
        System.out.println("Propriedades disponíveis:");
        cliente.listarPropriedadesDisponiveis(propriedades);

        System.out.print("Digite o título da propriedade que deseja reservar: ");
        String tituloReserva = tcl.nextLine();
        Propriedade propriedadeSelecionada = null;

        for (Propriedade p : propriedades) {
            if (p.getTitulo().equalsIgnoreCase(tituloReserva) && p.isDisponivel()) {
                propriedadeSelecionada = p;
                break;
            }
        }

        if (propriedadeSelecionada != null) {
            System.out.print("Data de Check-in (AAAA-MM-DD): ");
            String checkinStr = tcl.nextLine();
            System.out.print("Data de Check-out (AAAA-MM-DD): ");
            String checkoutStr = tcl.nextLine();

            try {
                LocalDate checkin = LocalDate.parse(checkinStr);
                LocalDate checkout = LocalDate.parse(checkoutStr);

                // Verificar se as datas da reserva não se sobrepõem a reservas existentes
                boolean sobreposicao = false;
                for (Reserva r : reservas) {
                    if (r.getPropriedade().equals(propriedadeSelecionada) &&
                        !(checkout.isBefore(r.getCheckin()) || checkin.isAfter(r.getCheckout()))) {
                        sobreposicao = true;
                        break;
                    }
                }

                if (sobreposicao) {
                    System.out.println("Erro: A propriedade já possui uma reserva para o período solicitado.");
                } else {
                    Reserva novaReserva = new Reserva(propriedadeSelecionada, cliente, checkin, checkout);
                    novaReserva.alugarPropriedade(); // Marca a propriedade como indisponível
                    reservas.add(novaReserva); // Adiciona à lista global de reservas
                    cliente.getReservasRealizadas().add(novaReserva); // Adiciona à lista de reservas do cliente
                    System.out.println("Reserva realizada com sucesso para a propriedade: " + propriedadeSelecionada.getTitulo());
                    System.out.println("Custo total da reserva: R$ " + String.format("%.2f", novaReserva.getCustoTotal()));
                }

            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use AAAA-MM-DD.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao realizar reserva: " + e.getMessage());
            }
        } else {
            System.out.println("Propriedade não encontrada ou não disponível para reserva.");
        }
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\n===== LISTA DE USUÁRIOS =====");
        for (Usuario u : usuarios) {
            u.imprimirDados();
            System.out.println("-------------------------");
        }
    }

    private static void listarPropriedades() {
        if (propriedades.isEmpty()) {
            System.out.println("Nenhuma propriedade cadastrada.");
            return;
        }
        System.out.println("\n===== LISTA DE PROPRIEDADES =====");
        for (Propriedade p : propriedades) {
            p.exibirPropriedade();
            System.out.println("-------------------------");
        }
    }

    private static void inicializarDados() {
        Proprietario prop1 = new Proprietario("Ana Silva", "ana@example.com", "senha123");
        Proprietario prop2 = new Proprietario("Bruno Costa", "bruno@example.com", "senha456");
        usuarios.add(prop1);
        usuarios.add(prop2);

        Cliente cli1 = new Cliente("Carlos Dias", "carlos@example.com", "cliente1");
        Cliente cli2 = new Cliente("Diana Souza", "diana@example.com", "cliente2");
        usuarios.add(cli1);
        usuarios.add(cli2);

        Casa casa1 = new Casa("Casa na Praia", "Linda casa com vista para o mar", "Praia Grande", 6, 250.0, prop1, "url_casa1.jpg", true, 50.0);
        Apartamento apto1 = new Apartamento("Apartamento Central", "Confortável apto perto de tudo", "Centro, São Paulo", 4, 180.0, prop2, "url_apto1.jpg", 10, 20.0);
        Sitio sitio1 = new Sitio("Sitio com Cachoeira", "Natureza exuberante e tranquilidade", "Serra da Mantiqueira", 10, 400.0, prop1, "url_sitio1.jpg", 5000.0);
        Casa casa2 = new Casa("Casa de Campo", "Aconchegante casa no interior", "Gramado", 4, 300.0, prop2, "url_casa2.jpg", false, 0.0);


        propriedades.add(casa1);
        propriedades.add(apto1);
        propriedades.add(sitio1);
        propriedades.add(casa2);

        prop1.cadastrarPropriedade(casa1);
        prop1.cadastrarPropriedade(sitio1);
        prop2.cadastrarPropriedade(apto1);
        prop2.cadastrarPropriedade(casa2);

        try {
            Reserva res1 = new Reserva(casa1, cli1, LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 15));
            res1.alugarPropriedade();
            reservas.add(res1);
            ((Cliente)cli1).getReservasRealizadas().add(res1);

            Reserva res2 = new Reserva(apto1, cli2, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 7));
            res2.alugarPropriedade();
            reservas.add(res2);
            ((Cliente)cli2).getReservasRealizadas().add(res2);

            System.out.println("\n--- Tentando reservar propriedade já ocupada ---");
            Reserva res3 = new Reserva(casa1, cli2, LocalDate.of(2025, 8, 12), LocalDate.of(2025, 8, 18));
            if (casa1.isDisponivel()) {
                 res3.alugarPropriedade();
                 reservas.add(res3);
                 ((Cliente)cli2).getReservasRealizadas().add(res3);
            } else {
                System.out.println("A propriedade '" + casa1.getTitulo() + "' não está disponível para o período solicitado (pré-cadastrado).");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao inicializar reservas: " + e.getMessage());
        }
        System.out.println("Dados iniciais carregados: " + usuarios.size() + " usuários e " + propriedades.size() + " propriedades.");
    }

    private static void salvarDados() {
        // Implementação futura para persistência (arquivos/banco de dados)
        System.out.println("Salvando dados (funcionalidade de persistência ainda não implementada completamente)...");
    }

    private static void carregarDados() {
        // Implementação futura para carregar dados (arquivos/banco de dados)
        System.out.println("Carregando dados (funcionalidade de persistência ainda não implementada completamente)...");
    }
}