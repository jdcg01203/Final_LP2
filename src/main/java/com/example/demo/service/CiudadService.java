package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Ciudad;

public interface CiudadService {
    Ciudad guardarCiudad(Ciudad ciudad);
    List<Ciudad> obtenerTodasCiudades();
    Ciudad obtenerCiudadPorCodigoPostal(String codigoPostal);
    void eliminarCiudadPorCodigoPostal(String codigoPostal);
}
