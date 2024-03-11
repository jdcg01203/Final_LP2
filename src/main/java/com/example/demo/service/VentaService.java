package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DetalleVenta;
import com.example.demo.model.Venta;

public interface VentaService {
    Venta guardarVenta(Venta venta);
    List<Venta> obtenerTodasVentas();
    Venta obtenerVentaPorId(int id);
    void eliminarVentaPorId(int id);
    
    Venta crearVenta(String nombreComprador, List<DetalleVenta> lista_detalles);
}
