package com.knu.lab3.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
@Table(name = "bills", schema = "public")
public class Bill {
    @Id
    @GeneratedValue(generator = "bills_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bills_id_seq", sequenceName = "bills_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private Double price;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne
    private User user;
}
