package com.example.models;

public class Alertas {
	
	public String idAlerta;
	public String idCurso;
	public String TipoAlerta;
	public String DetalleAlerta;
	public String ProcesadaOK;

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}
	public String getIdCurso() {
		return idCurso;
	}
	
	public void setDetalleAlerta(String detalleAlerta) {
		DetalleAlerta = detalleAlerta;
	}
	public String getDetalleAlerta() {
		return DetalleAlerta;
	}
	
	public String getIdAlerta() {
		return idAlerta;
	}
	public String getProcesadaOK() {
		return ProcesadaOK;
	}
	public String getTipoAlerta() {
		return TipoAlerta;
	}
	public void setIdAlerta(String idAlerta) {
		this.idAlerta = idAlerta;
	}
	public void setProcesadaOK(String procesadaOK) {
		ProcesadaOK = procesadaOK;
	}
	public void setTipoAlerta(String tipoAlerta) {
		TipoAlerta = tipoAlerta;
	}
	
}
