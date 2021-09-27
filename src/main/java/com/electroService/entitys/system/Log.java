package com.electroService.entitys.system;

import com.electroService.services.defaultRestData.DefaultEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "logs")
public class Log implements DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private String action;

    public Log(String action) {
        this.action = action;
        this.date = new Date();
    }
}
