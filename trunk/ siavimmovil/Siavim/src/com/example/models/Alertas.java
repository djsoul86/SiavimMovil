package com.example.models;

public class Alertas {
	
	public int idAlerta;
	public int idCurso;
	public String TipoAlerta;
	public String DetalleAlerta;
	public String ProcesadaOK;

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	public int getIdCurso() {
		return idCurso;
	}
	
	public void setDetalleAlerta(String detalleAlerta) {
		DetalleAlerta = detalleAlerta;
	}
	public String getDetalleAlerta() {
		return DetalleAlerta;
	}
	
	public int getIdAlerta() {
		return idAlerta;
	}
	public String getProcesadaOK() {
		return ProcesadaOK;
	}
	public String getTipoAlerta() {
		return TipoAlerta;
	}
	public void setIdAlerta(int idAlerta) {
		this.idAlerta = idAlerta;
	}
	public void setProcesadaOK(String procesadaOK) {
		ProcesadaOK = procesadaOK;
	}
	public void setTipoAlerta(String tipoAlerta) {
		TipoAlerta = tipoAlerta;
	}
	
}
