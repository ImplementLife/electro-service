package com.electroService.entitys.order;

import com.electroService.services.defaultRestData.DefaultEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter

@Entity
@Table(name = "statuses")
public class Status implements DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
