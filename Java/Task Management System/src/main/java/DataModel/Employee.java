package DataModel;

import DataAccess.SerializationOperation;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int idEmployee;
    private String name;

    public Employee(int idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){return "Employee name: " + name + " idEmployee: " + idEmployee;}

    public void serialize() throws IOException {
        try{
            SerializationOperation.serialize("Employee", this);
        }
        catch(IOException e){
            throw new IOException("Employee serialization failed");
        }
    }
    public void deserialize() throws Exception{
        try{
            Employee employee = (Employee) SerializationOperation.deserialize("Employee");
            this.name=employee.getName();
            this.idEmployee=employee.getIdEmployee();

        }catch (IOException | ClassNotFoundException e){
            throw new Exception("Employee deserialization failed");
        }
    }
}
