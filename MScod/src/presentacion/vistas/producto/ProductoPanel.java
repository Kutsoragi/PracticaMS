package presentacion.vistas.producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import negocio.producto.TProducto;
import negocio.producto.TProductoBebida;
import negocio.producto.TProductoComida;
import presentacion.controller.ApplicationController;
import presentacion.controller.Context;
import presentacion.controller.Evento;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FormPanel;
import presentacion.utility.MostrarPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;
import presentacion.vistas.Gui;

@SuppressWarnings("serial")
public class ProductoPanel extends JPanel implements Gui {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "PRODUCTO";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	public ProductoPanel() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		setOpaque(false);
		setMaximumSize(new Dimension(1024, 460));
		
		inicioPanel = new JPanel(new GridBagLayout());
		inicioPanel.setOpaque(false);
		inicioPanel.setMaximumSize(new Dimension(1024, INICIO_PANEL_HEIGHT));
		
		GridBagConstraints c = new PanelGridBagConstraints();
		
		JButton registrarBtn = new PanelButton("resources/icons/operaciones/registrar.png");
		registrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resTipo = tipoProductoOptionPane(); //Si retorna OK es Comida  
				if (resTipo == JOptionPane.YES_OPTION)
					registrarPanel(true);
				else if (resTipo == JOptionPane.NO_OPTION)
					registrarPanel(false);
				System.out.println("Registrar");
			}
		});
		inicioPanel.add(registrarBtn, c);
		
		c.gridx++;
		JButton modificarBtn = new PanelButton("resources/icons/operaciones/modificar.png");
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resTipo = tipoProductoOptionPane(); //Si retorna OK es Comida
				if (resTipo == JOptionPane.YES_OPTION)
					modificarPanel(true);
				else if (resTipo == JOptionPane.NO_OPTION)
					modificarPanel(false);
				System.out.println("Modificar");
			}
		});
		inicioPanel.add(modificarBtn, c);
		
		c.gridx++;
		JButton borrarBtn = new PanelButton("resources/icons/operaciones/borrar.png");
		borrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarPanel();
				System.out.println("Borrar");
			}
		});
		inicioPanel.add(borrarBtn, c);
		
		c.gridx = 0;
		c.gridy++;
		JButton buscarBtn = new PanelButton("resources/icons/operaciones/buscar.png");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPanel();
				System.out.println("Buscar");
			}
		});
		inicioPanel.add(buscarBtn, c);

		c.gridx++;
		JButton listarBtn = new PanelButton("resources/icons/operaciones/listar.png");
		listarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_PRODUCTOS, null));
				System.out.println("Mostrar Productos");
			}
		});
		inicioPanel.add(listarBtn, c);
		
		c.gridx++;
		JButton mostrarProductosConMasDeXStockBtn = new PanelButton("resources/icons/operaciones/mostrar-productos-con-mas-de-x-stock.png");
		mostrarProductosConMasDeXStockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProductosConMasDeXStockPanel();
				System.out.println("Mostrar Productos con mas de X stock");
			}
		});
		inicioPanel.add(mostrarProductosConMasDeXStockBtn, c);
		
		//--
		
		errorPanel = new ErrorPanel(460 - INICIO_PANEL_HEIGHT);

		RoundButton backBtn = new RoundButton(100);
		backBtn.setMaximumSize(new Dimension(70,70));
		backBtn.setBackground(new Color(51,83,148));
		backBtn.setBorder(null);
		backBtn.setFocusPainted(false);
		backBtn.setIcon("resources/icons/back.png");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Volver");
				
				errorPanel.clear();
				if (panelActual.equals(inicioPanel))
					ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_INICIO, null));
				else {
					remove(panelActual);
					inicioPanel.setVisible(true);
					panelActual = inicioPanel;
				}
			}
		});
		errorPanel.add(backBtn);
		
		inicioPanel.setBorder(BorderFactory.createEmptyBorder((460-INICIO_PANEL_HEIGHT)/2,0,0,0));
		add(inicioPanel, BorderLayout.CENTER);
		add(errorPanel, BorderLayout.PAGE_END);
		panelActual = inicioPanel;
	}

	//-- METODOS AUXILIARES
	
	private void updatePanel(JPanel newPanel, Integer offset) {
		if (panelActual.equals(inicioPanel))
			inicioPanel.setVisible(false);
		else
			remove(panelActual);
		
		newPanel.setBorder(BorderFactory.createEmptyBorder(offset == null ? (460-INICIO_PANEL_HEIGHT)/2 : offset,0,0,0));
		add(newPanel, BorderLayout.CENTER);
		errorPanel.clear();
		revalidate();
		repaint();
		panelActual = newPanel;
	}
	
	private int tipoProductoOptionPane() {
		final JComponent[] input = new JComponent[] { new JLabel("Seleccione el tipo de Producto") };
		Object[] options = {"Comida", "Bebida"};	
		return JOptionPane.showOptionDialog(null, input, " Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	//-- PANELES
	
	private void registrarPanel(final boolean esComida) {
		final FormPanel panel;
		
		if (esComida == true)
			panel = new FormPanel(Arrays.asList("ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "PESO"));
		else
			panel = new FormPanel(Arrays.asList("ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "VOLUMEN"));
				
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idProveedor = Integer.parseInt(panel.getFieldText("ID PROVEEDOR"));
					int idMarca = Integer.parseInt(panel.getFieldText("ID MARCA"));
					double precio = Double.parseDouble(panel.getFieldText("PRECIO"));
					int stock = Integer.parseInt(panel.getFieldText("STOCK"));
					int calorias = Integer.parseInt(panel.getFieldText("CALORIAS"));
					if (esComida == true) {
						int peso = Integer.parseInt(panel.getFieldText("PESO"));
						ApplicationController.getInstance().handleRequest(new Context(Evento.REGISTRAR_PRODUCTO, new TProductoComida(idProveedor, idMarca, precio, stock, calorias, peso)));
					} else {
						int volumen = Integer.parseInt(panel.getFieldText("VOLUMEN"));
						ApplicationController.getInstance().handleRequest(new Context(Evento.REGISTRAR_PRODUCTO, new TProductoBebida(idProveedor, idMarca, precio, stock, calorias, volumen)));
					}
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
			
		updatePanel(panel, null);
	}
	
	private void modificarPanel(final boolean esComida) {
		final FormPanel panel;
		
		if (esComida == true)
			panel = new FormPanel(Arrays.asList("ID", "ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "PESO"));
		else
			panel = new FormPanel(Arrays.asList("ID", "ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "VOLUMEN"));
				
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));
					int idProveedor = Integer.parseInt(panel.getFieldText("ID PROVEEDOR"));
					int idMarca = Integer.parseInt(panel.getFieldText("ID MARCA"));
					double precio = Double.parseDouble(panel.getFieldText("PRECIO"));
					int stock = Integer.parseInt(panel.getFieldText("STOCK"));
					int calorias = Integer.parseInt(panel.getFieldText("CALORIAS"));
					if (esComida == true) {
						int peso = Integer.parseInt(panel.getFieldText("PESO"));
						ApplicationController.getInstance().handleRequest(new Context(Evento.MODIFICAR_PRODUCTO, new TProductoComida(id, idProveedor, idMarca, precio, stock, calorias, peso)));
					} else {
						int volumen = Integer.parseInt(panel.getFieldText("VOLUMEN"));
						ApplicationController.getInstance().handleRequest(new Context(Evento.MODIFICAR_PRODUCTO, new TProductoBebida(id, idProveedor, idMarca, precio, stock, calorias, volumen)));
					}
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
			
		updatePanel(panel, null);
	}
	
	private void borrarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));	
					ApplicationController.getInstance().handleRequest(new Context(Evento.BORRAR_PRODUCTO, id));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void buscarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));	
					ApplicationController.getInstance().handleRequest(new Context(Evento.BUSCAR_PRODUCTO, id));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TProducto p) { // Muestra un transfer dado
		MostrarPanel panel;
		
		if (p instanceof TProductoComida) {
			panel = new MostrarPanel(Arrays.asList("ID", "ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "PESO"));
			panel.setLabelText("PESO", Integer.toString(p.getPeso()));
		} else {	
			panel = new MostrarPanel(Arrays.asList("ID", "ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "VOLUMEN"));
			panel.setLabelText("VOLUMEN", Integer.toString(p.getVolumen()));
		}

		panel.setLabelText("ID", Integer.toString(p.getId()));
		panel.setLabelText("ID PROVEEDOR", Integer.toString(p.getIdProveedor()));
		panel.setLabelText("ID MARCA", Integer.toString(p.getIdMarca()));
		panel.setLabelText("PRECIO", Double.toString(p.getPrecio()));
		panel.setLabelText("STOCK", Integer.toString(p.getStock()));
		panel.setLabelText("CALORIAS", Integer.toString(p.getCalorias()));
		
		updatePanel(panel, 50);
	}	

	private void mostrarProductosPanel(List<TProducto> productos) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		TablePanel tablePanel = new TablePanel(new Dimension(775, 275), Arrays.asList("ID", "ID PROVEEDOR", "ID MARCA", "PRECIO", "STOCK", "CALORIAS", "PESO", "VOLUMEN"));		
		for (TProducto p : productos) {
			Object[] datos = new Object[]{Integer.toString(p.getId()), p.getIdProveedor(), p.getIdMarca(), p.getPrecio(), p.getStock(), p.getCalorias(), p.getPeso(), p.getVolumen()};
			tablePanel.addRow(datos, false);
		}
		
		panel.add(tablePanel);
		
		updatePanel(panel, null);
	}
	
	private void mostrarProductosConMasDeXStockPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("STOCK"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_PRODUCTOS_CON_MAS_DE_X_STOCK, Integer.parseInt(panel.getFieldText("STOCK"))));
			}
		});
		
		updatePanel(panel, null);
	}

	//-- METODOS IMPLEMENTADOS
	
	public String getNombre() {
		return nombre;
	}
	
	public JPanel getPanel() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public void actualizar(Context responseContext) {
		switch (responseContext.getEvent()) {
			
			//SUCCESS
			case REGISTRAR_PRODUCTO_OK:
			case MODIFICAR_PRODUCTO_OK:
			case BORRAR_PRODUCTO_OK:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), true);
				break;
			case BUSCAR_PRODUCTO_OK:
			//case MOSTRAR_PRODUCTO_MAS_CALORICO_OK:
				mostrarPanel((TProducto) responseContext.getDataObject());
				break;
			case MOSTRAR_PRODUCTOS_OK:
			case MOSTRAR_PRODUCTOS_CON_MAS_DE_X_STOCK_OK:
				mostrarProductosPanel((List<TProducto>) responseContext.getDataObject());
				break;
				
			//ERROR
			case REGISTRAR_PRODUCTO_KO:
			case MODIFICAR_PRODUCTO_KO:
			case BORRAR_PRODUCTO_KO:
			case BUSCAR_PRODUCTO_KO:
			case MOSTRAR_PRODUCTOS_KO:
			//case MOSTRAR_PRODUCTO_MAS_CALORICO_KO:
			case MOSTRAR_PRODUCTOS_CON_MAS_DE_X_STOCK_KO:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), false);
				break;
			default:
				errorPanel.showOutputMessage("Evento desconocido: " + responseContext.getEvent().toString(), false);
				break;
		}
	}
}
