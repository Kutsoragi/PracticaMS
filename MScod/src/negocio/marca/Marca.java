package negocio.marca;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import negocio.producto.Producto;

@Entity
@Table(name = "marca")
@NamedQueries({
	@NamedQuery(name = "negocio.marca.Marca.buscarPorNombre", query = "SELECT m FROM Marca m WHERE m.nombre = :nombre"),
	@NamedQuery(name = "negocio.marca.Marca.listarMarcas", query = "SELECT m FROM Marca m")
})
public class Marca {
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_marca")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "activo", columnDefinition = "boolean default true")
	private boolean activo;
	
	@OneToMany(mappedBy = "marca")
	private List<Producto> listaProductos;
	
	@Version
	private int version;
	
	public Marca(){
	}
	
	public Marca(String nombre, boolean activo){
		this.nombre=nombre;
		this.activo = activo;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {
		this.id=id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo=activo;
	}
	
	public List<Producto> getListaProductos(){
		return listaProductos;
	}
	
	public void setListaProductos(List<Producto> listaProductos){
		this.listaProductos = listaProductos;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}