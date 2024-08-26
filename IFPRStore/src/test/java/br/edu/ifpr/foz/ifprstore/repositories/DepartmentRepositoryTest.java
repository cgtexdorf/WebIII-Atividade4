package br.edu.ifpr.foz.ifprstore.repositories;
import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.util.List;


public class DepartmentRepositoryTest {

    @Test
    public void deveInserirUmRegistroNaTabelaDepartment(){
        DepartmentRepository repository = new DepartmentRepository();
        Department departmentFake = new Department();
        departmentFake.setName("Tools");

        Department department = repository.insertDepartment(departmentFake);

    }

    @Test
    public void deveAtualizarUmDepartamento(){
        DepartmentRepository repository = new DepartmentRepository();
        repository.updateDepartment(6, "DigitalBooks");

    }

    @Test
    public void deveDeletarUmDepartamento(){
        DepartmentRepository repository = new DepartmentRepository();
        repository.delete(7);
    }

    @Test
    public void deveExibirUmaListaDeDepartments(){

        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departments = repository.getDepartment();

        for(Department d: departments){
            System.out.println(d);
        }

    }

    @Test
    public void deveRetornarUmDepartmentPeloId(){

        DepartmentRepository repository = new DepartmentRepository();
        Department department = repository.getById(2);
        System.out.println(department);

    }

    @Test
    public void deveRetornarUmaListaDeDepartmentPeloFiltro(){

        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departmentList = repository.findByFilter("book");

        for(Department department: departmentList){
            System.out.println(department);
        }
    }

    @Test
    public void deveRetornarUmaListaDeDepartmentSemSellers(){

        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departmentList = repository.findDepartmentWithoutSellers();

        for(Department department: departmentList){
            System.out.println(department);
        }
    }
}
