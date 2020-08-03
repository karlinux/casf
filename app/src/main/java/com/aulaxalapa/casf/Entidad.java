package com.aulaxalapa.casf;

public class Entidad {

    private String id;
    private String titulo;
    private String contenido;
    private String color;
    private String ide;

    public Entidad(String id, String titulo, String contenido, String color, String ide) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.color = color;
        this.ide = ide;
    }

    public String getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getColor() {
        return color;
    }
    public String getIde() {
        return ide;
    }
}
