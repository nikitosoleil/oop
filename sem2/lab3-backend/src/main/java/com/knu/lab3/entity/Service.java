package com.knu.lab3.entity;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "services", schema = "public")
public class Service {
    @Id
    @GeneratedValue(generator = "services_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "services_id_seq", sequenceName = "services_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

}
