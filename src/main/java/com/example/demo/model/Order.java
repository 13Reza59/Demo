package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "owner")
    private String owner;

    @OneToMany( mappedBy = "order")
    private Set<Product> products;

    public Order( Long id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Order( ){}

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
}
