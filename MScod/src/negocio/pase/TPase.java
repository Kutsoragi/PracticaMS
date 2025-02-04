package negocio.pase;


public class TPase {

	private final double PRECIO_MINIMO = 0.50;
	private final double PRECIO_MAXIMO = 99.99;
	private final int STOCK_MINIMO = 0;
	private final int STOCK_MAXIMO = 200;
	
	private int id;
	private double precio;
	private String fecha;
	private String horaInicio;
	private String horaFin;
	private int idPelicula;
	private int idSala;
	private int stock;
	private boolean activo;
	
	public TPase(int id, double precio) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		if (precio > PRECIO_MAXIMO) throw new IllegalArgumentException("Precio superior al máximo " + PRECIO_MAXIMO + ".");
		
		this.id = id;
		this.precio = precio;
	}
	
	public TPase(String horaInicio, String horaFin, String fecha, double precio, int idPelicula, int idSala, int stock) {
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		if (precio > PRECIO_MAXIMO) throw new IllegalArgumentException("Precio superior al máximo " + PRECIO_MAXIMO + ".");
		if (stock < STOCK_MINIMO) throw new IllegalArgumentException("Stock inferior al minimo " + STOCK_MINIMO + ".");
		if (stock > STOCK_MAXIMO) throw new IllegalArgumentException("Stock superior al máximo " + STOCK_MAXIMO + ".");
		if (idPelicula < 1) throw new IllegalArgumentException("ID Película incorrecto.");
		if (idSala < 1) throw new IllegalArgumentException("ID Sala incorrecto.");
		
		this.id = 0;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.fecha = fecha;
		this.precio = precio;
		this.idPelicula = idPelicula;
		this.idSala = idSala;
		this.stock = stock;
	}
	
	public TPase(int id, String horaInicio, String horaFin, String fecha, double precio, int idPelicula, int idSala, boolean activo, int stock) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		if (precio > PRECIO_MAXIMO) throw new IllegalArgumentException("Precio superior al máximo " + PRECIO_MAXIMO + ".");
		if (stock < STOCK_MINIMO) throw new IllegalArgumentException("Stock inferior al minimo " + STOCK_MINIMO + ".");
		if (stock > STOCK_MAXIMO) throw new IllegalArgumentException("Stock superior al máximo " + STOCK_MAXIMO + ".");
		if (idPelicula < 1) throw new IllegalArgumentException("ID Película incorrecto.");
		if (idSala < 1) throw new IllegalArgumentException("ID Sala incorrecto.");
		
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.fecha = fecha;
		this.precio = precio;				
		this.idPelicula = idPelicula;
		this.idSala = idSala;
		this.activo = activo;
		this.stock = stock;
	}
	
	public TPase(int id, String horaInicio, String horaFin, String fecha, double precio, int idPelicula, int idSala, int stock) {
		if (id < 1) throw new IllegalArgumentException("ID incorrecto.");
		if (precio < PRECIO_MINIMO) throw new IllegalArgumentException("Precio inferior al mínimo " + PRECIO_MINIMO + ".");
		if (precio > PRECIO_MAXIMO) throw new IllegalArgumentException("Precio superior al máximo " + PRECIO_MAXIMO + ".");
		if (stock < STOCK_MINIMO) throw new IllegalArgumentException("Stock inferior al minimo " + STOCK_MINIMO + ".");
		if (stock > STOCK_MAXIMO) throw new IllegalArgumentException("Stock superior al máximo " + STOCK_MAXIMO + ".");
		if (idPelicula < 1) throw new IllegalArgumentException("ID Película incorrecto.");
		if (idSala < 1) throw new IllegalArgumentException("ID Sala incorrecto.");
		
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.fecha = fecha;
		this.precio = precio;				
		this.idPelicula = idPelicula;
		this.idSala = idSala;
		this.stock = stock;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getFecha() {
		return fecha;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setFecha(String fecha) {
		//verificarFecha(fecha);
		this.fecha = fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	public int getPelicula() {
		return idPelicula;
	}
	
	public void setPelicula(int idPelicula) {
		this.idPelicula = idPelicula;
	}
	
	public int getSala() {
		return idSala;
	}
	
	public void setSala(int idSala) {
		this.idSala = idSala;
	}
	
	public boolean isActivo() {
		return activo;
	}
}
