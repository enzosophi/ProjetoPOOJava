package com.example;
import com.example.abstrata.Propriedade;
import com.example.abstrata.Usuario;
import java.util.ArrayList;
import java.util.List;



public class Proprietario extends Usuario{

    private List<Propriedade> propriedades;

    public Proprietario(String nome, String email, String senha){
        super(nome,email,senha);
        this.propriedades = new ArrayList<>();

    }
    public void addPropriedade(Propriedade propriedade){
        if(propriedade !=null){
        propriedades.add(propriedade);
        System.out.println("Propriedade adicionada com sucesso!");
        }
        else{
            System.out.println("Erro ao adicionar propriedade!");
        }
    }

    public void listarPropriedades(){
        if(propriedades.isEmpty()){
            System.out.println("Nenhuma propriedade cadastrada!");
        } else{
            for(Propriedade p : propriedades){
                p.exibirPropriedade();
                System.out.println("------------------");
            }
        }
    }

    public void removerPropriedade(Propriedade propriedade){
        if(propriedade !=null){
            propriedades.remove(propriedade);
            System.out.println("Propriedade removida com sucesso!");
        }
        else{
            System.out.println("Erro ao remover propriedade!");
        }
    }

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }
}
