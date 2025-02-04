package negocio.factura;

import negocio.pase.TPase;

public class TLineaFactura {
	private int idFactura;
	private TPase tPase;
	private int cantidad;
	private double precio;
	
	public TLineaFactura(int idFactura, TPase tPase, int cantidad) {
		if (idFactura < 1) throw new IllegalArgumentException("ID de Factura incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para devolver.");this.idFactura = idFactura;
		
		this.idFactura = idFactura;
		this.tPase = tPase;
		this.cantidad = cantidad;
	}
	public TLineaFactura(TPase tPase, int cantidad) {
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para devolver.");
		
		this.tPase = tPase;
		this.cantidad = cantidad;
	}
	
	public TLineaFactura(int idFactura, TPase tPase, int cantidad, double precio) {
		if (idFactura < 1) throw new IllegalArgumentException("ID de Factura incorrecto.");
		if (cantidad < 1) throw new IllegalArgumentException("Cantidad insuficiente para devolver.");this.idFactura = idFactura;
		
		this.idFactura = idFactura;
		this.tPase = tPase;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	public int getIdFactura() {
		return idFactura;
	}
	
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public TPase getPase() {
		return tPase;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	//--
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
