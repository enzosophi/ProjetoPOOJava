import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Usuario validaNomeProprietario(String nomeProprietario, List<Usuario> usuarios) {
        for(Usuario usuario: usuarios) {
            if (usuario.nome.equals(nomeProprietario)) {
                return usuario;
            }
        }
        return null;
    } 
    public static void main(String[] args) {
        Scanner tcl = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<>(); 
        List<Propriedade> propriedades = new ArrayList<>(); 
        String nome, email, senha, titulo, descricao, localizacao, proprietarioNome;
        int disponivel, capacidade;
        double precoPorNoite;
        int achouProp = 0;
        Usuario proprietario;
  

        usuarios.add(new Usuario("João Paulo", "JoaoP123@gmail.com", "123Papel"));
        usuarios.add(new Usuario("Marcio Santos", "MarcioS321@gmail.com", "Level123"));

        propriedades.add(new Propriedade("Casa de Praia", "Casa com vista para o mar", "Praia de Boa Viagem", 5, 1000.00, usuarios.get(0)));

        while(true){
        while(true){
        System.out.print("Digite seu nome: ");
        nome = tcl.nextLine();
        if(!nome.isEmpty())break;
         System.out.println("O nome está vazio");
            continue;
    }
    while(true){
        System.out.print("Digite seu email: ");
        email = tcl.nextLine();
        if(!email.isEmpty())break;
         System.out.println("O email está vazio");
         continue;
    }
    while(true){
        System.out.print("Digite sua senha: ");
        senha = tcl.nextLine();
        if(!senha.isEmpty())break;
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

                    if (opcaoProprietario == 2) {
                        System.out.println("Digite o título da propriedade que deseja ver os detalhes: ");
                        tcl.nextLine();
                        String tituloPropriedade = tcl.nextLine();
                        for(Propriedade propriedade: propriedades) {
                            if (propriedade.titulo.equals(tituloPropriedade)) {
                                propriedade.exibirPropriedade();
                                achouProp = 1;
                            }
                        }

                        if (achouProp == 0) {
                            System.out.println("Propriedade não encontrada!");
                        }
                        else {
                            achouProp = 0;
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

                    if (opcao < 1 || opcao > 4) {
                        System.out.println("Opção inválida!");
                        continue;
                    }

                }
            }

            if (opcao == 5) {
                break;
            }

            if (opcao < 1 || opcao > 5) {
                System.out.println("Opção inválida!");
                continue;
            }

            if (opcao == 3) {
                System.out.print("\nLista de users cadastrados\n");
                for(Usuario usuario: usuarios){
                    usuario.exibirInfo();
                }
                continue;
            }

            if (opcao == 4) {
                System.out.print("\nLista de propriedades cadastradas\n");
                for(Propriedade propriedade: propriedades) {
                    propriedade.exibirPropriedade();
                }
                continue;
            }
        }
        
    }
}
