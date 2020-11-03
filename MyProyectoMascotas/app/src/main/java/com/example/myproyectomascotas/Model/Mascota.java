package com.example.myproyectomascotas.Model;

public class Mascota {
    String id;
    String nombre;
    String numeroChip;
    String fecha;
    String especie;
    String tipo_Mascota;
    String Sexo;

    public Mascota() {
    }

    public Mascota(String id, String nombre, String numeroChip, String fecha, String especie, String tipo_Mascota, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroChip = numeroChip;
        this.fecha = fecha;
        this.especie = especie;
        this.tipo_Mascota = tipo_Mascota;
        Sexo = sexo;
    }

    public Mascota(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(String numeroChip) {
        this.numeroChip = numeroChip;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getTipo_Mascota() {
        return tipo_Mascota;
    }

    public void setTipo_Mascota(String tipo_Mascota) {
        this.tipo_Mascota = tipo_Mascota;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
}
