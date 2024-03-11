package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ciudad;
import com.example.demo.model.DetalleVenta;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.DetalleVentaRepository;
import com.example.demo.service.DetalleVentaService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;

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

	@Override
	public DetalleVenta crearDetalleVentaConDatos(String origen, String destino, String fechaSalida, String fechaRetorno,
			int cantidad, String nombreComprador) {
		
		 	Ciudad ciudad_origen = ciudadRepository.findById(origen).orElse(null);
	        Ciudad ciudad_destino = ciudadRepository.findById(destino).orElse(null);
	        
	        LocalDate fechaSalidaLocalDate = LocalDate.parse(fechaSalida);
	        LocalDate fechaRetornoLocalDate = LocalDate.parse(fechaRetorno);
	        
	        Date fechaSalidaSql = Date.valueOf(fechaSalidaLocalDate);
	        Date fechaRetornoSql = Date.valueOf(fechaRetornoLocalDate);
	        
	        Double precio_voleto = 50.00;
	        
	        
		return DetalleVenta.builder()
                .venta(null)
                .codigoPostalOrigen(ciudad_origen)
                .codigoPostalDestino(ciudad_destino)
                .cantidad(cantidad)
                .fechaViaje(fechaSalidaSql)
                .fechaRetorno(fechaRetornoSql)
                .subTotal(cantidad * precio_voleto)
                .build();
	}
}
