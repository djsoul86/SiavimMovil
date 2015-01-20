package com.example.models;

public class Notas {

	int IdNota;
	String FechaNota;
	String NombreNota;
	String Nota;
	int Corte;
	int PorcentajeNota;
	String Cedula;
	int IdCurso;
	
	public void setCedula(String cedula) {
		Cedula = cedula;
	}
	public void setCorte(int corte) {
		Corte = corte;
	}
	public void setFechaNota(String fechaNota) {
		FechaNota = fechaNota;
	}
	public void setIdCurso(int idCurso) {
		IdCurso = idCurso;
	}
	public void setIdNota(int idNota) {
		IdNota = idNota;
	}
	public void setNombreNota(String nombreNota) {
		NombreNota = nombreNota;
	}
	public void setNota(String nota) {
		Nota = nota;
	}
	public void setPorcentajeNota(int porcentajeNota) {
		PorcentajeNota = porcentajeNota;
	}
	
	public String getCedula() {
		return Cedula;
	}
	public int getCorte() {
		return Corte;
	}
	public String getFechaNota() {
		return FechaNota;
	}
	public int getIdCurso() {
		return IdCurso;
	}
	public int getIdNota() {
		return IdNota;
	}
	public String getNombreNota() {
		return NombreNota;
	}
	public String getNota() {
		return Nota;
	}
	public int getPorcentajeNota() {
		return PorcentajeNota;
	}
}
