package com.example.models;

public class Asesorias {
	int Asesoria;
	String Pregunta;
	int ResueltaOk;
	String IdCurso;
	String FechaCreacion;
	String FechaRespuesta;
	String Respuesta;
	
	public void setRespuesta(String respuesta) {
		Respuesta = respuesta;
	}
	public String getRespuesta() {
		return Respuesta;
	}
	public void setAsesoria(int asesoria) {
		Asesoria = asesoria;
	}
	public void setFechaCreacion(String fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}
	public void setFechaRespuesta(String fechaRespuesta) {
		FechaRespuesta = fechaRespuesta;
	}
	public void setIdCurso(String idCurso) {
		IdCurso = idCurso;
	}
	public void setPregunta(String pregunta) {
		Pregunta = pregunta;
	}
	public void setResueltaOk(int resueltaOk) {
		ResueltaOk = resueltaOk;
	}
	public int getAsesoria() {
		return Asesoria;
	}
	public String getFechaCreacion() {
		return FechaCreacion;
	}
	public String getFechaRespuesta() {
		return FechaRespuesta;
	}
	public String getIdCurso() {
		return IdCurso;
	}
	public String getPregunta() {
		return Pregunta;
	}
	public int getResueltaOk() {
		return ResueltaOk;
	}
}
