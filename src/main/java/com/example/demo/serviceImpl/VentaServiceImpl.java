package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.DetalleVenta;
import com.example.demo.model.Venta;
import com.example.demo.repository.VentaRepository;
import com.example.demo.service.VentaService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> obtenerTodasVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta obtenerVentaPorId(int id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarVentaPorId(int id) {
        ventaRepository.deleteById(id);
    }

	@Override
	public Venta crearVenta(String nombreComprador, List<DetalleVenta> lista_detalles) {
		
		Double monto_total = lista_detalles.stream().mapToDouble(DetalleVenta::getSubTotal).sum();
		LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        
		return Venta.builder()
                .nombreComprador(nombreComprador)
                .fechaVenta(date)
                .montoTotal(monto_total)
                .build();
	}

	
}
