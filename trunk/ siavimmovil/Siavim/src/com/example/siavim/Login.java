package com.example.siavim;

public class Login {

	public String nombre;
	public String apellido;
	public String password;
	public String cedula;
	public String email;
	public String carrera;
	public String telefono;
	public String usuario;
	public String fecha;
	public int status;
	
	public String getApellido() {
		return apellido;
	}
	public String getCarrera() {
		return carrera;
	}
	public String getCedula() {
		return cedula;
	}
	public String getEmail() {
		return email;
	}
	public String getNombre() {
		return nombre;
	}
	public String getPassword() {
		return password;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
