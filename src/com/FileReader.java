package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {
    Map<Integer, Employee> employees;
    Employee topEmployee;


    public static void main(String[] args) throws IOException {
        FileReader thisClass = new FileReader();
        thisClass.process();
    }

    private void process() throws IOException {
        employees = new HashMap<>();
        readDataAndCreateEmployees();
        validateEmployeesData();
        System.out.print(buildHierarchy(topEmployee, 1));
    }


    private void readDataAndCreateEmployees() throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.FileReader("src/Files/employees.txt"));
        String line = reader.readLine();
        while (line != null) {
            Employee employee = createEmployee(line);
            employees.put(employee.getId(), employee);
            if (employee.getMgrId() == 0) {
                topEmployee = employee;
            }
            line = reader.readLine();
        }
    }

    private void validateEmployeesData() {
        List<Integer> invalidEmployees = new ArrayList<>();
        for(Employee e: employees.values()){
            if(e.getId() == e.getMgrId()){
                System.out.println("Employee "+e.getEmpName()+" is its own manager. It is not possible");
                invalidEmployees.add(e.getId());
            }
            else if(e.getMgrId()!=0 && employees.get(e.getMgrId())==null){
                System.out.println("Employee "+e.getEmpName()+"'s manager with manager id "+e.getMgrId()+" does not exists");
                invalidEmployees.add(e.getId());
            }
        }

        for(Integer invalid: invalidEmployees){
            employees.remove(invalid);
        }
    }

    private String buildHierarchy(Employee topEmployee, int level) {
        String result = topEmployee.getEmpName();
        List<Employee> employees1 = findAllEmployeesHavingMgrId(topEmployee.getId());

        for (Employee e : employees1) {
            result+="\n";
            for(int i=0;i<level;i++){
                result+="\t\t";
            }
            result+=buildHierarchy(e, level+1);
        }
        return result;
    }

    public List<Employee> findAllEmployeesHavingMgrId(int mgrid) {
        List<Employee> sameMgrEmployees = new ArrayList<>();
        for (Employee e : employees.values()) {
            if (e.getMgrId() == mgrid) {
                sameMgrEmployees.add(e);
            }
        }
        return sameMgrEmployees;
    }

    private Employee createEmployee(String line) {
        String[] values = line.split(" ");
        Employee employee = null;
        try {
            if (values.length ==2) {
                employee = new Employee(values[0], values[1]);
            }else if(values.length==3){
                employee = new Employee(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            System.out.println("Unable to create Employee as the data is " + values);
        }
        return employee;
    }
}
