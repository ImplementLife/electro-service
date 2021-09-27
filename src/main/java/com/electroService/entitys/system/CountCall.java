package com.electroService.entitys.system;

import com.electroService.services.defaultRestData.DefaultEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "count_calls")
public class CountCall implements DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private int count;
    private String name;

    public CountCall() {
        this.count = 0;
    }

    public CountCall increment() {
        this.count++;
        return this;
    }

}
