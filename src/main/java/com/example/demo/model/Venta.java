package com.example.demo.model;

import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "tb_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_comprador")
    private String nombreComprador;

    @Column(name = "fecha_venta")
    private Date fechaVenta;

    @Column(name = "monto_total")
    private Double montoTotal;
}
