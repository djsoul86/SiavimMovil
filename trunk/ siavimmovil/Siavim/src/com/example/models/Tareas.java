package com.example.models;

public class Tareas {

	int IdTarea;
	String nombreTarea;
	String DescripcionTarea;
	String FechaEntrega;
	String FechaCreacion;
	int IdCurso;
	
	public void setDescripcionTarea(String descripcionTarea) {
		DescripcionTarea = descripcionTarea;
	}
	public void setFechaCreacion(String fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}
	public void setFechaEntrega(String fechaEntrega) {
		FechaEntrega = fechaEntrega;
	}
	public void setIdCurso(int idCurso) {
		IdCurso = idCurso;
	}
	public void setIdTarea(int idTarea) {
		IdTarea = idTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	public String getDescripcionTarea() {
		return DescripcionTarea;
	}
	public String getFechaCreacion() {
		return FechaCreacion;
	}
	public String getFechaEntrega() {
		return FechaEntrega;
	}
	public int getIdCurso() {
		return IdCurso;
	}
	public int getIdTarea() {
		return IdTarea;
	}
	public String getNombreTarea() {
		return nombreTarea;
	}
	
}
