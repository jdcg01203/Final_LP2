package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.model.Ciudad;
import com.example.demo.model.DetalleVenta;
import com.example.demo.model.Venta;
import com.example.demo.service.CiudadService;
import com.example.demo.service.DetalleVentaService;
import com.example.demo.service.VentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class VuelosController {
	
	@Autowired
	private CiudadService ciudadService;
	
	@Autowired
	private DetalleVentaService detalleVentaService;
	
	@Autowired
	private VentaService ventaService;
	
	
	@GetMapping("/venta-boletos")
	public String ventaBoletos(Model model) {
		List<Ciudad> lista_ciudades = ciudadService.obtenerTodasCiudades();
		model.addAttribute("lista_ciudades", lista_ciudades);
		return "index";
	}
	
	@GetMapping("/registrar-boletos")
    public String registrarBoletos(
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fecha_salida") String fechaSalida,
            @RequestParam("fecha_retorno") String fechaRetorno,
            @RequestParam("nombre_comprador") String nombreComprador,
            @RequestParam("cantidad") int cantidad, Model model, HttpSession session) {
        
        List<Ciudad> lista_ciudades = ciudadService.obtenerTodasCiudades();
        @SuppressWarnings("unchecked")
        List<DetalleVenta> lista_detalles = (List<DetalleVenta>) session.getAttribute("lista_detalles");
        
        if (lista_detalles == null) {
            lista_detalles = new ArrayList<>();
        }
        
        Ciudad ciudad_origen = ciudadService.obtenerCiudadPorCodigoPostal(origen);
        Ciudad ciudad_destino = ciudadService.obtenerCiudadPorCodigoPostal(destino);
        
        LocalDate fechaSalidaLocalDate = LocalDate.parse(fechaSalida);
        LocalDate fechaRetornoLocalDate = LocalDate.parse(fechaRetorno);
        
        Date fechaSalidaSql = Date.valueOf(fechaSalidaLocalDate);
        Date fechaRetornoSql = Date.valueOf(fechaRetornoLocalDate);
        
        Double precio_voleto = 50.00;
        
        DetalleVenta detalle_venta = DetalleVenta.builder()
                .venta(null)
                .codigoPostalOrigen(ciudad_origen)
                .codigoPostalDestino(ciudad_destino)
                .cantidad(cantidad)
                .fechaViaje(fechaSalidaSql)
                .fechaRetorno(fechaRetornoSql)
                .subTotal(cantidad * precio_voleto)
                .build();
        
        lista_detalles.add(detalle_venta);
        
        Double monto_total = lista_detalles.stream().mapToDouble(DetalleVenta::getSubTotal).sum();
        
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        
        Venta venta = Venta.builder()
                .nombreComprador(nombreComprador)
                .fechaVenta(date)
                .montoTotal(monto_total)
                .build();
        
        lista_detalles.forEach(detalle -> detalle.setVenta(venta));
        
        session.setAttribute("venta", venta);
        session.setAttribute("lista_detalles", lista_detalles);
        model.addAttribute("lista_detalles", lista_detalles);
        model.addAttribute("lista_ciudades", lista_ciudades);
        
        return "index";
    }
	
	@GetMapping("/boleto-comprar")
	public String comprarBoleto(Model model, HttpSession session) {
	    List<DetalleVenta> lista_detalles = (List<DetalleVenta>) session.getAttribute("lista_detalles");
	    Venta venta = (Venta) session.getAttribute("venta");
	    
	    if(lista_detalles != null && !lista_detalles.isEmpty()) {
	        ventaService.guardarVenta(venta);
	        
	        lista_detalles.forEach(e -> {
	            e.setVenta(venta);
	            detalleVentaService.guardarDetalleVenta(e);
	        });
	        
	        lista_detalles = new ArrayList<>();
	        Venta venta_nueva = new Venta();
	        
	        session.setAttribute("venta", venta_nueva);
	        session.setAttribute("lista_detalles", lista_detalles);
	        return "compra-exitosa";
	    }
	    return "redirect:/venta-boletos";
	}

	

}
