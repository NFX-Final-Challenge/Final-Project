package com.company.FinalProject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "t_shirt")
public class Tshirt implements Serializable
{
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int t_shirt_id;

    @NotEmpty(message = "Must provide t-shirt size.")
    @Length(max = 20, message = "Word too long.")
    private String size;

    @NotEmpty(message = "Must provide t-shirt color")
    @Length(max = 20,message = "Word too long.")
    private String color;

    @NotEmpty(message = "Must provide a t-shirt description")
    @Length(max = 255, message = "Description too long.")
    private String description;

    @Max(value = 999, message = "Number too large.")
    private BigDecimal price;

    private int quantity;


    // Getters and Setters

    public int getT_shirt_id() {
        return t_shirt_id;
    }

    public void setT_shirt_id(int t_shirt_id) {
        this.t_shirt_id = t_shirt_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.CEILING);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Equals, Hashcode, and ToString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tshirt tshirt = (Tshirt) o;
        return t_shirt_id == tshirt.t_shirt_id && quantity == tshirt.quantity && Objects.equals(size, tshirt.size) && Objects.equals(color, tshirt.color) && Objects.equals(description, tshirt.description) && Objects.equals(price, tshirt.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t_shirt_id, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "Tshirt{" +
                "t_shirt_id=" + t_shirt_id +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
