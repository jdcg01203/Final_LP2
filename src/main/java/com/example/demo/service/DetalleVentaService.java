package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DetalleVenta;

public interface DetalleVentaService {
    DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta);
    List<DetalleVenta> obtenerTodosDetallesVenta();
    DetalleVenta obtenerDetalleVentaPorId(int id);
    void eliminarDetalleVentaPorId(int id);
}
