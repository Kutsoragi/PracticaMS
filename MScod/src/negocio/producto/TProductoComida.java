/**
 * 
 */
package negocio.producto;

public class TProductoComida extends TProducto {

	private Integer peso = null;
	
	public TProductoComida(){
	
	}

	public TProductoComida(int id, int idProveedor, int idMarca, double precio, int stock, int calorias, int peso) {
		super(id, idProveedor, idMarca, precio, stock, calorias);
		this.peso = peso;
	}
	
	public TProductoComida(int idProveedor, int idMarca, double precio, int stock, int calorias, int peso) {
		super(idProveedor, idMarca, precio, stock, calorias);
		this.peso = peso;
	}
	
	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getVolumen() {return null;}

	public void setVolumen(Integer volumen) {}

}