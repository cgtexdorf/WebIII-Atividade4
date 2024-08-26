package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseIntegrityException;
import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    Connection connection;

    public DepartmentRepository() {
        connection = ConnectionFactory.getConnection();
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM department WHERE Id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e){
            throw new DatabaseIntegrityException(e.getMessage());

        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Department insertDepartment(Department department){

        String sql = "INSERT INTO department (Name) VALUES(?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());

            /*statement.setString(2, seller.getEmail());
            statement.setDate(3, Date.valueOf(seller.getBirthDate()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, 1);*/

            Integer rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                ResultSet id = statement.getGeneratedKeys();
                id.next();
                Integer departmentId = id.getInt(1);
                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + departmentId);
                department.setId(department.getId());
            }

        } catch (Exception e){
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        return department;

    }

    public void updateDepartment(Integer departmentId, String name){
        String sql = "UPDATE department SET Name = ? WHERE Id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql); //ctrl + alt + v

            statement.setString(1, name);
            statement.setInt(2, departmentId);

            Integer rowsUpdate = statement.executeUpdate();

            if(rowsUpdate > 0){
                System.out.println("Rows updated: " + rowsUpdate);
            }

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

    }

    public List<Department> getDepartment(){

        List<Department> departments = new ArrayList<>();


        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM department");
            while(result.next()){

                Department department = instantiateDepartment(result);

                departments.add(department);

            }

            result.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.closeConnection();
        }

        return departments;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException{

        Department departament = new Department();
        departament.setId(resultSet.getInt("Id"));
        departament.setName(resultSet.getString("Name"));

        return departament;

    }

    public Department getById(Integer id){

        Department departament;

        String sql = "SELECT * " +
                "FROM department " +
                "WHERE Id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

                departament = this.instantiateDepartment(resultSet);

            } else {
                throw new DatabaseException("Departamento não encontrado");
            }

        } catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }

        return departament;

    }

    public List<Department> findByFilter(String filtro){

        List<Department> departments = new ArrayList<>();

        String sql = "SELECT * FROM department WHERE Name LIKE ? ORDER BY Name";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + filtro + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Department department = this.instantiateDepartment(resultSet);
                departments.add(department);
            }

        } catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }

        if (departments.isEmpty()) {
            throw new DatabaseException("Nenhum departamento encontrado");
        }

        return departments;

    }

    public List<Department> findDepartmentWithoutSellers(){

        List<Department> departments = new ArrayList<>();

        String sql = "SELECT d.* FROM department d LEFT JOIN seller s ON d.Id = s.DepartmentId "
        + " WHERE s.Id IS NULL ORDER BY d.Name";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Department department = this.instantiateDepartment(resultSet);
                departments.add(department);
            }

        } catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        if (departments.isEmpty()) {
            throw new DatabaseException("Nenhum departamento encontrado");
        }

        return departments;

    }
}
