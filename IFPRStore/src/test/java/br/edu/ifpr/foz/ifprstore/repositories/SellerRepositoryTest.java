package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class SellerRepositoryTest {

    @Test
    public void deveExibirUmaListaDeSellers(){

        SellerRepository repository = new SellerRepository();
        List<Seller> sellers = repository.getSellers();

        for(Seller s: sellers){
            System.out.println(s);
        }

    }

    @Test
    public void deveInserirUmRegistroNaTabelaSeller(){
        SellerRepository repository = new SellerRepository();
        Seller sellerFake = new Seller();
        sellerFake.setName("Frodo");
        sellerFake.setEmail("frodo@gmail.com");
        sellerFake.setBirthDate(LocalDate.of(2000, 9, 17));
        sellerFake.setBaseSalary(12000.0);

        Seller seller = repository.insert(sellerFake);

    }

    @Test
    public void deveAtualizarOSalarioDeUmSelleDeUmDepartamento(){
        SellerRepository repository = new SellerRepository();
        repository.updateSalary(1, 1500.0);

    }

    @Test
    public void deveDeletarUmSeller(){
        SellerRepository repository = new SellerRepository();
        repository.delete(9);
    }

    @Test
    public void deveRetornarUmSellerPeloId(){

        SellerRepository repository = new SellerRepository();
        Seller seller = repository.getById(2);
        System.out.println(seller);
        System.out.println("Departamento: ------------");
        System.out.println(seller.getDepartment());

    }

    @Test
    public void deveRerornarUmaListaDeSellersPeloIdDoDepartamento(){
        SellerRepository repository = new SellerRepository();
        List<Seller> sellerList = repository.findByDepartment(2);

        for(Seller seller: sellerList){
            System.out.println(seller);
            System.out.println("Departamento: ------------");
            System.out.println(seller.getDepartment());
        }
    }
}
