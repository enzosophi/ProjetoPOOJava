package com.example;
import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario;
import java.util.ArrayList;
import java.util.List;



public class Proprietario extends Usuario {

    private List<Propriedade> propriedades;

    public Proprietario(String nome, String email, String senha){
        super(nome,email,senha);
        this.propriedades = new ArrayList<>();

    }
    
    public void cadastrarPropriedade(Propriedade propriedade){
        if (propriedade != null){
            propriedades.add(propriedade);
            System.out.println("Propriedade adicionada com sucesso!");
        }
        else {
            System.out.println("Erro ao adicionar propriedade!");
        }
    }

    public void listarPropriedades(){
        if (propriedades.isEmpty()){
            System.out.println("Nenhuma propriedade cadastrada!");
        } else{
            System.out.println("\n--- Propriedades Cadastradas por " + getNome() + " ---");
            for(Propriedade p : propriedades){
                p.exibirPropriedade();
                System.out.println("------------------");
            }
        }
    }

    public void listarPropriedadesAlugadas() {
        boolean encontrouAlugada = false;
        System.out.println("\n--- Propriedades Alugadas de " + getNome() + " ---");
        for (Propriedade p : propriedades) {
            if (!p.isDisponivel()) {
                p.exibirPropriedade();
                System.out.println("------------------");
                encontrouAlugada = true;
            }
        }
        if (!encontrouAlugada) {
            System.out.println("Nenhuma propriedade alugada!");
        }
    }

    @Override 
    public void imprimirDados() {
        System.out.println("Tipo de Usuário: Proprietário");
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + getEmail());
        System.out.println("Número de propriedades cadastradas: " + propriedades.size());
    }

    // public void removerPropriedade(Propriedade propriedade){
    //     if(propriedade !=null){
    //         propriedades.remove(propriedade);
    //         System.out.println("Propriedade removida com sucesso!");
    //     }
    //     else{
    //         System.out.println("Erro ao remover propriedade!");
    //     }
    // }

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }
}
