package com.example.demo.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_ciudad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ciudad {
    @Id
    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "nombre")
    private String nombre;
}
