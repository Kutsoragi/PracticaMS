
package negocio.producto;

import javax.persistence.*;

@Entity
public class ProductoBebida extends Producto {

	@Column(name = "volumen")
	private Integer volumen;

	public ProductoBebida(Integer id, double precio, Integer calorias, Integer version, Integer stock, Integer volumen) {
		super(id, precio, calorias, version, stock);
		this.volumen = volumen;
	}

	public ProductoBebida() {
	}

	public Integer getVolumen() {
		return this.volumen;
	}

	public void setVolumen(Integer volumen) {
		this.volumen = volumen;
	}

	public Integer calculateCalorias() {
		return calorias * volumen;
	}

}