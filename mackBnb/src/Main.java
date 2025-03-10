import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tcl = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<>(); // O array que lista os users que vão ser criado.
        String nome, email, senha;
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
        System.out.print("\nLista de users cadastrados");
            for(Usuario usuario: usuarios){
             usuario.exibirInfo();
             }

    }
}
