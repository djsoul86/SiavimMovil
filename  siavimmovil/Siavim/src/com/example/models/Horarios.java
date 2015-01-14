package com.example.models;

public class Horarios {

	public String IDCurso;
	public int Intensidad;
	public String NombreCurso;
	public String idProfesor;
	public String Lunes;
	public String Martes;
	public String Miercoles;
	public String Jueves;
	public String Viernes;
	public String Sabado;
	
	public String getIDCurso() {
		return IDCurso;
	}
	
	public String getIdProfesor() {
		return idProfesor;
	}
	public int getIntensidad() {
		return Intensidad;
	}
	public String getNombreCurso() {
		return NombreCurso;
	}
	public void setIDCurso(String iDCurso) {
		IDCurso = iDCurso;
	}
	public void setIdProfesor(String idProfesor) {
		this.idProfesor = idProfesor;
	}
	public void setIntensidad(int intensidad) {
		Intensidad = intensidad;
	}
	public void setNombreCurso(String nombreCurso) {
		NombreCurso = nombreCurso;
	}
	
	public String getLunes() {
		return Lunes;
	}
	public String getMartes() {
		return Martes;
	}
	public String getMiercoles() {
		return Miercoles;
	}
	public String getJueves() {
		return Jueves;
	}
	public String getViernes() {
		return Viernes;
	}
	public String getSabado() {
		return Sabado;
	}
	public void setLunes(String lunes) {
		Lunes = lunes;
	}
	public void setMartes(String martes) {
		Martes = martes;
	}
	public void setMiercoles(String miercoles) {
		Miercoles = miercoles;
	}
	public void setJueves(String jueves) {
		Jueves = jueves;
	}
	public void setViernes(String viernes) {
		Viernes = viernes;
	}
	public void setSabado(String sabado) {
		Sabado = sabado;
	}
	
}
