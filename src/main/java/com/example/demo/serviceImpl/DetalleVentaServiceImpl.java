package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.DetalleVenta;
import com.example.demo.repository.DetalleVentaRepository;
import com.example.demo.service.DetalleVentaService;

import java.util.List;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Override
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public List<DetalleVenta> obtenerTodosDetallesVenta() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta obtenerDetalleVentaPorId(int id) {
        return detalleVentaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarDetalleVentaPorId(int id) {
        detalleVentaRepository.deleteById(id);
    }
}
