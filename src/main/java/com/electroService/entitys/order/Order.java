package com.electroService.entitys.order;

import com.electroService.services.defaultRestData.DefaultEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@Setter
@Getter

@Entity
@Table(name = "orders")
public class Order implements DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreate;
    private Date dateComplete;

    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private String city;
    private String address;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
