package com.example.demo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factors")
public class Factor {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    private String date;

    @Column(length = 32)
    private String owner;

    @OneToMany( mappedBy = "factor", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    private double sumTotal;

    public Factor( ){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format( currentDate);
        this.date = currentDateTime;
    }

    public Factor( String owner){
        super();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format( currentDate);
        this.date = currentDateTime;

        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Factor [id= " + id + ", date= " + date + ", owner= " + owner + ", products= " + products.size() + ", sumTotal= " + sumTotal + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts( List<Product> products) {
        this.products = products;
    }

    public double getSumTotal() {
        CalculateSumTotal();
        return sumTotal;
    }

    public void setSumTotal(double sumTotal) {
        this.sumTotal = sumTotal;
    }

    private void CalculateSumTotal() {
        sumTotal = 0.0;
        for ( Product product : products )
            sumTotal += product.getPrice();

    }
}
