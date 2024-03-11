package com.example.demo.controller;

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
	
	
	@GetMapping("/venta")
	public String ventaBoletos(Model model) {
		List<Ciudad> lista_ciudades = ciudadService.obtenerTodasCiudades();
		model.addAttribute("lista_ciudades", lista_ciudades);
		return "index";
	}
	
	@GetMapping("/registrar")
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

        DetalleVenta detalle_venta= detalleVentaService.crearDetalleVentaConDatos(origen, 
    		   destino, fechaSalida,  fechaRetorno, cantidad, nombreComprador);
        
        lista_detalles.add(detalle_venta);
        
        Venta venta =ventaService.crearVenta(nombreComprador,lista_detalles);
        
        lista_detalles.forEach(detalle -> detalle.setVenta(venta));
        
        session.setAttribute("venta", venta);
        session.setAttribute("lista_detalles", lista_detalles);
        model.addAttribute("lista_detalles", lista_detalles);
        model.addAttribute("lista_ciudades", lista_ciudades);
        
        return "index";
    }
	
	@GetMapping("/comprar")
	public String comprarBoleto(Model model, HttpSession session) {
	    @SuppressWarnings("unchecked")
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
	    return "redirect:/venta";
	}
}
