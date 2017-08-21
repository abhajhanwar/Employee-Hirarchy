package com;


public class Employee {
    private int id;
    private int mgrId;
    private String empName;

    public Employee(String empName, String id){
        this.id = Integer.parseInt(id);
        this.empName = empName;
    }

    public Employee( String empName,String id, String mgrid) {
        this.id = Integer.parseInt(id);
        this.mgrId = Integer.parseInt(mgrid);
        this.empName = empName;
    }
    int getId() {
        return id;
    }

    int getMgrId() {
        return mgrId;
    }

    String getEmpName(){
        return empName;
    }


}
