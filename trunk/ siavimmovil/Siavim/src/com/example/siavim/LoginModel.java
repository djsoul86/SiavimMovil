package com.example.siavim;

public class LoginModel {

	public String usuario;
	public String fecha;
	public int status;
	public String password;
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFecha() {
		return fecha;
	}
	public int getStatus() {
		return status;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
}
