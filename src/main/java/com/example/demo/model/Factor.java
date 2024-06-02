package com.example.demo.model;

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

    @Column(name = "date")
    private Date date;

    @Column(name = "owner")
    private String owner;

//    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL)
    @OneToMany( mappedBy = "factor", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

//    public Order( Long id, Date date, String owner) {
//        this.id = id;
//        this.date = date;
//        this.owner = owner;
//    }

    public Factor( ){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat. format( currentDate);
        this.date = currentDate;
    }

    public Factor( String owner){
        super();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat. format( currentDate);
        this.date = currentDate;

        this.owner = owner;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
