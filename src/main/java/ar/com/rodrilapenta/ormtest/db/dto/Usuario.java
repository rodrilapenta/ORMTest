package ar.com.rodrilapenta.ormtest.db.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios_con_indices")
@SecondaryTable(name="usuarios_sin_indices")
public class Usuario {
	@Id @GeneratedValue
	   @Column(name = "id")
	private Integer id;
	@Column(name = "username")
	private String username;
	@Column(name = "fecha")
	private Timestamp fecha;
	
	public Usuario() {
		super();
	}
	
	public Usuario(Integer id, String username, Timestamp fecha) {
		super();
		this.id = id;
		this.username = username;
		this.fecha = fecha;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Timestamp getFecha() {
		return fecha;
	}
	
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}