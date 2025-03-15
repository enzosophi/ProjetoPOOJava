import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
class Reserva {
    Propriedade propriedade;
    Usuario usuario;
    String checkin;
    String checkout;
    double custoTotal;
    public Reserva(LocalDate checkin, LocalDate checkout, Propriedade propriedade, Usuario usuario) {
        if (checkin == null || checkout == null) {
            throw new IllegalArgumentException("As datas de checkin e checkout não podem ser nulas.");
        }
        if (checkin.isAfter(checkout)) {
            throw new IllegalArgumentException("A data de checkin deve ser anterior à data de checkout.");
        }
        if (propriedade == null) {
            throw new IllegalArgumentException("A propriedade não pode ser nula.");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        this.propriedade = propriedade;
        this.usuario = usuario;
        this.checkin = checkin;
        this.checkout = checkout;
        this.custoTotal = propriedade.precoPorNoite * ChronoUnit.DAYS.between(checkin, checkout);
    }

    public void exibirReserva() {
        System.out.println("Propriedade: " + propriedade.getTitulo());
        System.out.println("Usuário: " + usuario.nome);
        System.out.println("Checkin: " + checkin);
        System.out.println("Checkout: " + checkout);
        System.out.println("Custo total: " + custoTotal);
    }

    public void alugarPropriedade(){
        if(getDisponivel() ==1){
            setDisponivel(0);
            System.out.println("Propriedade " + getTitulo() + " foi alugada com sucesso!");
        }
        else{
            System.out.println("Erro: Esta propriedade já está alugada!");  
        }
    }
}
