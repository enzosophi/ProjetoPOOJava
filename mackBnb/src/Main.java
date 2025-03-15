import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Usuario validaNomeProprietario(String nomeProprietario, List<Usuario> usuarios) {
        for(Usuario usuario: usuarios) {
            if (usuario.nome.equalsIgnoreCase(nomeProprietario)) {
                return usuario;
            }
        }
        return null;
    } 

    public static Usuario buscarUsuario(String nome, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.nome.equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner tcl = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<>(); 
        List<Propriedade> propriedades = new ArrayList<>(); 
        List<Reserva> reservas = new ArrayList<>();
        String nome, email, senha, titulo, descricao, localizacao, proprietarioNome;
        int disponivel, capacidade;
        double precoPorNoite;
        int achouProp = 0;
        Usuario proprietario;

        usuarios.add(new Usuario("João Paulo", "JoaoP123@gmail.com", "123Papel"));
        usuarios.add(new Usuario("Daniel Borges", "dani2014games@gmail.com", "CalculoDiferencialIntegraleLegal123"));
        usuarios.add(new Usuario("Marcio Santos", "MarcioS321@gmail.com", "Level123"));
        usuarios.add(new Usuario("Leandro Donizetti", "LeandroDoniGames2015@gmail.com", "Vish123"));

        propriedades.add(new Propriedade("Casa de Praia", "Casa com vista para o mar", "Praia de Boa Viagem", 5, 1000.00, usuarios.get(2)));
        propriedades.add(new Propriedade("Casa de Campo", "Casa com vista para a montanha", "Serra da Mantiqueira", 10, 2000.00, usuarios.get(3)));

        Reserva reservaTeste = new Reserva(LocalDate.parse("2021-10-10"), LocalDate.parse("2021-10-15"), propriedades.get(0), usuarios.get(1));
        reservaTeste.alugarPropriedade();
        reservas.add(reservaTeste);

        while(true){
            while(true){
                System.out.print("Digite seu nome: ");
                nome = tcl.nextLine();
                if(!nome.isEmpty()) {
                    break;
                } 
                System.out.println("O nome está vazio");
                continue;
            }
            while(true){
                System.out.print("Digite seu email: ");
                email = tcl.nextLine();
                if(!email.isEmpty()) {
                    break;
                }
                System.out.println("O email está vazio");
                continue;
            }
            while(true){
                System.out.print("Digite sua senha: ");
                senha = tcl.nextLine();
                if(!senha.isEmpty()) {
                    break;
                }
                System.out.println("A senha está vazio");
                continue;
            }

            usuarios.add(new Usuario(nome, email, senha));

            System.out.println("Deseja adicionar outro usuario? S/N ?");
            String resposta = tcl.nextLine().trim().toLowerCase();
            if(resposta.equals("n")){   
                break;
            }
        }

        while(true) {
            System.out.println("MENU DE OPÇÕES");
            System.out.println("1 - Quero ser um proprietário");
            System.out.println("2 - Quero ser um usuário");
            System.out.println("3 - Listar users cadastrados");
            System.out.println("4 - Listar propriedades cadastradas");
            System.out.println("5 - Sair");
            System.out.print("Digite a opção desejada: ");
            int opcao = tcl.nextInt();
            tcl.nextLine();

            if (opcao == 1) {
                while(true) {
                    System.out.println("MENU DO PROPRIETÁRIO");
                    System.out.println("1 - Cadastrar propriedade");
                    System.out.println("2 - Exibir de detalhes de propriedades");
                    System.out.println("3 - Listar propriedades alugadas");
                    System.out.println("4 - Sair");
                    System.out.print("Digite a opção desejada: ");
                    int opcaoProprietario = tcl.nextInt();

                    if (opcaoProprietario == 1) {
                        tcl.nextLine();
                        System.out.print("Digite o título da propriedade: ");
                        titulo = tcl.nextLine();
                        System.out.print("Digite a descrição da propriedade: ");
                        descricao = tcl.nextLine();
                        System.out.print("Digite a localização da propriedade: ");
                        localizacao = tcl.nextLine();
                        System.out.print("Digite a capacidade da propriedade: ");
                        capacidade = tcl.nextInt();
                        tcl.nextLine();
                        System.out.print("Digite o preço por noite da propriedade: ");
                        precoPorNoite = tcl.nextDouble();
                        tcl.nextLine();
                        System.out.print("Digite o nome do proprietário: ");
                        proprietarioNome = tcl.nextLine();
                        proprietario = validaNomeProprietario(proprietarioNome, usuarios);
                        if (proprietario == null) {
                            System.out.println("Proprietário não encontrado!");
                            continue;
                        }
                        propriedades.add(new Propriedade(titulo, descricao, localizacao, capacidade, precoPorNoite, proprietario));
                    }

                    if (opcaoProprietario == 2) { // encurtei a opção 2 mas a função é a mesma
                        System.out.println("Digite o título da propriedade que deseja ver os detalhes: ");
                        tcl.nextLine();
                        String tituloPropriedade = tcl.nextLine();

                        boolean achouPropriedade = false;
                        for(Propriedade propriedade: propriedades) {
                            if (propriedade.titulo.equalsIgnoreCase(tituloPropriedade)) {
                                propriedade.exibirPropriedade();
                                achouPropriedade = true;
                                break;
                            }
                        }
                        if(!achouPropriedade){
                            System.out.println("Propriedade não achada");
                        }
                    }

                    if (opcaoProprietario == 3) {
                        System.out.print("\nLista de propriedades alugadas\n");
                        for(Propriedade propriedade: propriedades) {
                            System.out.printf("A propriedade %s tem o status de: \n", propriedade.titulo);
                            propriedade.verificarAlugada();
                        }
                    }

                    if (opcaoProprietario == 4) {
                        break;
                    }

                    if (opcaoProprietario < 1 || opcaoProprietario > 4) {
                        System.out.println("Opção inválida!");
                        continue;
                    }

                }
            } //quebra do terminal proprietário

            if (opcao == 5) {
                break;
            }

            if (opcao < 1 || opcao > 5) {
                System.out.println("Opção inválida!");
                continue;
            }

            if (opcao == 2) {
                while(true){
                System.out.println("MENU DE CLIENTE");
                System.out.println("1 - Fazer uma reserva");
                System.out.println("2 - Consultar minhas reservas");
                System.out.println("3 - Verificar propriedades disponíveis para aluguel");
                System.out.println("4 - Sair");
                System.out.print("Digite a opção desejada: ");
                int opcaoUsuario = tcl.nextInt();

                if(opcaoUsuario == 1){
                    System.out.println("\nPropriedades disponíveis para reserva:");
                    boolean encontrouDisponivel = false;
                
                    for (Propriedade propriedade : propriedades) {
                        if (propriedade.getDisponivel() == 1) {
                            propriedade.exibirPropriedade();
                            encontrouDisponivel = true;
                        }
                    }
                
                    if (!encontrouDisponivel) {
                        System.out.println("Nenhuma propriedade disponível no momento.");
                    } else {
                        tcl.nextLine();
                        System.out.println("Qual propriedade deseja reservar: ");
                        String reservar = tcl.nextLine();
                
                        System.out.print("Digite seu nome para confirmar a reserva: ");
                        String nomeUsuario = tcl.nextLine();
                
                        Usuario usuarioAtual = buscarUsuario(nomeUsuario, usuarios);
                        if (usuarioAtual == null) {
                            System.out.println("Usuário não encontrado.");
                            continue;
                        }
                
                        boolean propriedadeAchada = false;
                        for (Propriedade propriedade : propriedades) {
                            if (propriedade.getTitulo().equalsIgnoreCase(reservar)) {
                                propriedadeAchada = true;
                                if (propriedade.getDisponivel() == 1) {
                                    System.out.print("Digite a data de check-in (AAAA-MM-DD): ");
                                    LocalDate checkin = LocalDate.parse(tcl.nextLine());
                
                                    System.out.print("Digite a data de check-out (AAAA-MM-DD): ");
                                    LocalDate checkout = LocalDate.parse(tcl.nextLine());
                
                                    Reserva novaReserva = new Reserva(checkin, checkout, propriedade, usuarioAtual);
                                    novaReserva.alugarPropriedade();
                
                                    reservas.add(novaReserva); // Armazena a reserva
                                    System.out.println("Reserva realizada com sucesso!");
                                } else {
                                    System.out.println("Propriedade já alugada.");
                                }
                                break;
                            }
                        }
                        if (!propriedadeAchada) {
                            System.out.println("Erro: Propriedade não encontrada!");
                        }
                    }
                }

                if(opcaoUsuario == 2) {
                    System.out.println("Digite seu nome para consultar suas reservas: ");
                    tcl.nextLine();
                    String nomeUsuario = tcl.nextLine();
                    Usuario usuarioAtual = buscarUsuario(nomeUsuario, usuarios);
                    if (usuarioAtual == null) {
                        System.out.println("Usuário não encontrado.");
                        continue;
                    }
                    boolean encontrouReserva = false;
                    for (Reserva reserva : reservas) {
                        if (reserva.usuario == usuarioAtual) {
                            reserva.exibirReserva();
                            encontrouReserva = true;
                        }
                    }
                    if (!encontrouReserva) {
                        System.out.println("Nenhuma reserva encontrada para este usuário.");
                    }
                }

                if(opcaoUsuario == 3) {
                    System.out.println("Propriedades disponíveis para aluguel:");
                    for(Propriedade propriedade: propriedades) {
                        System.out.printf("A propriedade %s tem o status de: \n", propriedade.titulo);
                        propriedade.verificarDisponibilidade();
                    }
                }

                if (opcaoUsuario < 1 || opcaoUsuario > 4) {
                    System.out.println("Opção inválida!");
                    continue;
                }

                if(opcaoUsuario == 4){
                    break;
                }
            }
        }
    
            if (opcao == 3) {
                System.out.print("\nLista de users cadastrados\n");

                if(usuarios.isEmpty()) {
                    System.out.println("Não tem usuário Cadastrado\n"); 
                }

                else {
                    for(Usuario usuario: usuarios){
                        usuario.exibirInfo();
                    }
                }
                continue;
            }

            if (opcao == 4) {
                System.out.print("\nLista de propriedades cadastradas\n");

                if(propriedades.isEmpty()) {
                    System.out.println("Não tem propriedade Cadastrada\n"); 
                }

                else {
                    for(Propriedade propriedade: propriedades) {
                        propriedade.exibirPropriedade();
                    }
                }
                continue;
            }
        }
        
    }
}
