/**
 * 
 */
package negocio.producto;

import java.util.List;

public interface SAProducto {

	public int registrarProducto(TProducto tProducto);

	public List<TProducto> mostrarProductoConMasDeXstock(Integer stock);

	public int editarProducto(TProducto tProducto);

	public TProducto buscarProductoPorId(int id);

	public List<TProducto> mostrarProductos();

	public int borrarProducto(int id);
	
	//public TProducto mostrarProductoMasCalorico();
}