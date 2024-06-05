package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 64)
    private String name;

    private double price;

    @ManyToOne
    private Factor factor;

    public Product( String name, double price) {
        super();

        this.name = name;
        this.price = price;
    }

    public Product() {}

    @Override
    public String toString() {
        return "Product [id= " + id + ", name= " + name + ", price= " + price + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
