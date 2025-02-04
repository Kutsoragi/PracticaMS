package negocio.producto;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;


public class SAProductoTest {
	
	@Test
	public void registrarProductoComidaExito() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoComida tProducto = new TProductoComida(2, 1, 5.2, 50, 10, 5);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res > 0); //registrado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void registrarProductoComidaFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoComida tProducto = new TProductoComida(5000, 5000, 5.2, 50, 10, 5);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res <= 0); //registro sin exito ya que no esiste un proveedor o marca con id 5000
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void registrarProductoBebidaExito() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoBebida tProducto = new TProductoBebida(2, 2, 3.2, 100, 245, 50);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res > 0); //registrado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void registrarProductoBebidaFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoBebida tProducto = new TProductoBebida(5000, 5000, 3.2, 100, 245, 50);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res <= 0); //registro sin exito ya que no esiste un proveedor o marca con id 5000
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void mostrarProductoConMasDeXstockExito() {
		SAProducto saProducto = new SAProductoImp();
		
		int stock = 1;
		try {
			List<TProducto> res = saProducto.mostrarProductoConMasDeXstock(1);
			assertTrue(res != null); //listado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	
	@Test
	public void editarProductoComidaExito() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoComida tProducto = new TProductoComida(2, 1, 26, 100, 20, 5);
		try {
			int res = saProducto.editarProducto(tProducto);
			assertTrue(res > 0); //modificado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void editarProductoComidaFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoComida tProducto = new TProductoComida(5000, 5000, 26, 100, 20, 5);
		try {
			int res = saProducto.editarProducto(tProducto);
			assertTrue(res > 0); //modificado sin exito ya que no esiste un proveedor o marca con id 5000
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void editarProductoBebidaExito() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoBebida tProducto = new TProductoBebida(2, 2, 32, 26, 160, 60);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res > 0); //rmodificado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void editarProductoBebidaFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		TProductoBebida tProducto = new TProductoBebida(5000, 5000, 32, 26, 160, 60);
		try {
			int res = saProducto.registrarProducto(tProducto);
			assertTrue(res <= 0); //registro sin exito ya que no esiste un proveedor o marca con id 5000
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void buscarProductoPorIdExito() {
		SAProducto saProducto = new SAProductoImp();
		
		int id = 1;
		try{
			TProducto res = saProducto.buscarProductoPorId(id);
			assertTrue(res != null); //encontrado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void buscarProductoPorIdFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		int id = 2000;
		try{
			TProducto res = saProducto.buscarProductoPorId(id);
			assertTrue(res == null); //producto no encontrado
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void mostrarProductosExito() {
		SAProducto saProducto = new SAProductoImp();
		
		try {
			List<TProducto> res = saProducto.mostrarProductos();
			assertTrue(res != null); //listado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void borrarProductoExito() {
		SAProducto saProducto = new SAProductoImp();
		
		int id = 2;
		try{
			int res = saProducto.borrarProducto(id);
			assertTrue(res > 0); //eliminado correctamente
		} catch(Exception e) { fail(e.getMessage());}
	}
	
	@Test
	public void borrarProductoFallo() {
		SAProducto saProducto = new SAProductoImp();
		
		int id = 2000;
		try{
			int res = saProducto.borrarProducto(id);
			assertTrue(res <= 0); //producto no eliminado porque no existe con ese id
		} catch(Exception e) { fail(e.getMessage());}
	}
	

}
