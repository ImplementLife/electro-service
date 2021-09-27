package com.electroService.entitys.system;

import com.electroService.services.defaultRestData.DefaultEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "conversion")
public class Conversion implements DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int countView;
    private int countOrders;
    private float value;
    private Date date;
    private boolean allTime;

    public Conversion(int countView, int countOrders) {
        this.countView = countView;
        this.countOrders = countOrders;
        this.mathConverse();
    }

    public void mathConverse() {
        value = (float) countOrders / (float) countView;
        if (countView == 0 || countOrders == 0) {
            value = 0;
            return;
        }
        value = BigDecimal.valueOf(value).setScale(2, RoundingMode.DOWN).floatValue();
    }

}
