package com.warehouse;

public class Customer {
    private int id_customer;
    private String cust_phone, name_cust;

    public Customer(int id_customer,String cust_phone, String name_cust){
        this.id_customer = id_customer;
        this.cust_phone = cust_phone;
        this.name_cust = name_cust;
    }

    public int getId_customer(){
        return id_customer;
    }
    public String getCust_phone(){
        return cust_phone;
    }
    public String getName_cust(){
        return name_cust;
    }
}
