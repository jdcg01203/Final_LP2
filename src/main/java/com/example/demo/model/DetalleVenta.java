package com.example.demo.model;

import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "tb_detalle_venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_venta", referencedColumnName = "id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "codigo_postal_origen", referencedColumnName = "codigo_postal")
    private Ciudad codigoPostalOrigen;

    @ManyToOne
    @JoinColumn(name = "codigo_postal_destino", referencedColumnName = "codigo_postal")
    private Ciudad codigoPostalDestino;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "fecha_viaje")
    private Date fechaViaje;

    @Column(name = "fecha_retorno")
    private Date fechaRetorno;

    @Column(name = "sub_total")
    private Double subTotal;
}
