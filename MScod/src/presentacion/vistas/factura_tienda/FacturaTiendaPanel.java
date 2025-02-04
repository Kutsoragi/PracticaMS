package presentacion.vistas.factura_tienda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import negocio.factura.Pair;
import negocio.facturaTienda.TCarritoTienda;
import negocio.facturaTienda.TLineaFacturaTienda;
import presentacion.controller.ApplicationController;
import presentacion.controller.Context;
import presentacion.controller.Evento;
import presentacion.utility.ErrorPanel;
import presentacion.utility.FacturaTable;
import presentacion.utility.FormPanel;
import presentacion.utility.PanelButton;
import presentacion.utility.PanelGridBagConstraints;
import presentacion.utility.RoundButton;
import presentacion.utility.TablePanel;
import presentacion.vistas.Gui;

@SuppressWarnings("serial")
public class FacturaTiendaPanel extends JPanel implements Gui {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "FACTURA TIENDA";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	private TCarritoTienda carritoAux;
	private TablePanel carritoTable;
	private JLabel carritoPrecio;
	
	public FacturaTiendaPanel() {
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
		
		JButton abrirFacturaBtn = new PanelButton("resources/icons/operaciones/abrir-factura.png");
		abrirFacturaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().handleRequest(new Context(Evento.ABRIR_FACTURA_TIENDA, null));
				carritoPanel();
				System.out.println("Abrir Factura");
			}
		});
		inicioPanel.add(abrirFacturaBtn, c);
		
		c.gridx++;
		JButton devolverPaseBtn = new PanelButton("resources/icons/operaciones/devolver-producto.png");
		devolverPaseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				devolverProductoPanel();
				System.out.println("Devolver Producto");
			}
		});
		inicioPanel.add(devolverPaseBtn, c);
		
		c.gridx++;
		JButton buscarBtn = new PanelButton("resources/icons/operaciones/buscar.png");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPanel();
				System.out.println("Buscar Factura");
			}
		});
		inicioPanel.add(buscarBtn, c);
		
		c.gridx++;
		JButton listarBtn = new PanelButton("resources/icons/operaciones/listar.png");
		listarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().handleRequest(new Context(Evento.LISTAR_FACTURAS_TIENDA, null));
				System.out.println("Listar Facturas Tienda");
			}
		});
		inicioPanel.add(listarBtn, c);
		
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
	
	private JButton createToolButton(String iconPath, Color color, String tooltip) {
		RoundButton button = new RoundButton(100);
		button.setMaximumSize(new Dimension(35, 35));
		button.setBackground(color);
		button.setBorder(null);
		button.setFocusPainted(false);
		button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		button.setToolTipText(tooltip);
		button.setIcon(iconPath);
		
		return button;
	}
	
	private Pair<Integer, Integer> productoOptionPane(String operacion) {
		JTextField idProductoField = new JTextField();
		JTextField cantidadPaseField = new JTextField();
		final JComponent[] input = new JComponent[] {
		        new JLabel("ID Producto"),
		        idProductoField,
		        new JLabel("Cantidad"),
		        cantidadPaseField,
		};
		Object[] options = {operacion, "Cancelar"};
		int result = JOptionPane.showOptionDialog(null, input, operacion + " Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (result == JOptionPane.OK_OPTION) {
			int idProducto = Integer.parseInt(idProductoField.getText());
			int cantidadPase = Integer.parseInt(cantidadPaseField.getText());
			return new Pair<Integer, Integer>(idProducto, cantidadPase);
		}
		
		return null;
	}
	
	/*
	private Integer empleadoOptionPane() {
		JTextField idEmpleadoField = new JTextField();
		final JComponent[] input = new JComponent[] {
		        new JLabel("ID Empleado"),
		        idEmpleadoField,
		};
		Object[] options = {"Aceptar", "Cancelar"};
		int result = JOptionPane.showOptionDialog(null, input, "Empleado Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (result == JOptionPane.OK_OPTION)
			return Integer.valueOf(idEmpleadoField.getText());
		
		return null;
	}
	*/
	
	//-- PANELES
	
	private void carritoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		JPanel facturaPanel = new JPanel();
		facturaPanel.setLayout(new BoxLayout(facturaPanel, BoxLayout.X_AXIS));
		facturaPanel.setOpaque(false);
		facturaPanel.setMaximumSize(new Dimension(1024, 320));
		
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBackground(new Color(70, 70, 70));
		toolPanel.setMaximumSize(new Dimension(80, 320));
		
		//--
		
		TablePanel productoTable = new TablePanel(new Dimension(600, 320), Arrays.asList("ID PRODUCTO", "CANTIDAD"));//, "PRECIO"));
		
		//--
		
		JLabel totalTitle = new JLabel("TOTAL: ");
		totalTitle.setForeground(new Color(230,230,230));
		totalTitle.setFont(new Font("Arial", Font.BOLD, 12));
		totalTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		totalTitle.setForeground(new Color(70, 70, 70));

		JLabel totalPrecio = new JLabel("0.0");
		totalPrecio.setForeground(new Color(230,230,230));
		totalPrecio.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		totalPrecio.setForeground(new Color(70, 70, 70));
		
		toolPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JButton añadirProductoBtn = createToolButton("resources/icons/operaciones/añadir-pase.png", new Color(63, 164, 31), "Añade un producto a la factura");
		añadirProductoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Pair<Integer, Integer> productos = productoOptionPane("Añadir");
					if (productos != null) {
						carritoAux.setIdProductoAuxiliar(productos.getFirst());
						carritoAux.setCantidadAuxiliar(productos.getSecond());
						ApplicationController.getInstance().handleRequest(new Context(Evento.AÑADIR_PRODUCTO, carritoAux));
					}
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				}
			}
		});
		toolPanel.add(añadirProductoBtn);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton quitarProductoBtn = createToolButton("resources/icons/operaciones/quitar-pase.png", new Color(220, 34, 34), "Quita un producto de la factura");
		quitarProductoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Pair<Integer, Integer> productos = productoOptionPane("Quitar");
					if (productos != null) {
						carritoAux.setIdProductoAuxiliar(productos.getFirst());
						carritoAux.setCantidadAuxiliar(productos.getSecond());
						ApplicationController.getInstance().handleRequest(new Context(Evento.QUITAR_PRODUCTO, carritoAux));
					}
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				}
			}
		});
		toolPanel.add(quitarProductoBtn);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JButton cerrarBtn = createToolButton("resources/icons/operaciones/cerrar-factura.png", new Color(38, 180, 237), "Confirma y cierra la factura");
		cerrarBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JComponent[] input = new JComponent[] {
				        new JLabel("¿Desea confirmar y cerrar la factura?"),
				};
				Object[] options = {"Si", "No"};
				int result = JOptionPane.showOptionDialog(null, input, "Confirmar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				
				if (result == JOptionPane.OK_OPTION) {
					try {
						//Integer idEmpleado = empleadoOptionPane();
						//if (idEmpleado != null){
							//carritoAux.getFactura().setIdEmpleado(idEmpleado);
							ApplicationController.getInstance().handleRequest(new Context(Evento.CERRAR_FACTURA_TIENDA, carritoAux));
						//}
					} catch (NumberFormatException ex) {
						errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
					}
				}
			}
		});
		toolPanel.add(cerrarBtn);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 80)));
		toolPanel.add(totalTitle);
		toolPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		toolPanel.add(totalPrecio);
		
		//--
		
		facturaPanel.add(Box.createRigidArea(new Dimension(170, 0)));
		facturaPanel.add(productoTable);
		facturaPanel.add(toolPanel);

		panel.add(facturaPanel);
		
		carritoTable = productoTable;
		carritoPrecio = totalPrecio;
		
		updatePanel(panel, 40);
	}
	
	private void devolverProductoPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID FACTURA", "ID PRODUCTO", "CANTIDAD"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idFactura = Integer.valueOf(panel.getFieldText("ID FACTURA"));
					Integer idProducto = Integer.valueOf(panel.getFieldText("ID PRODUCTO"));
					Integer cantidad = Integer.valueOf(panel.getFieldText("CANTIDAD"));
					ApplicationController.getInstance().handleRequest(new Context(Evento.DEVOLVER_PRODUCTO, new TLineaFacturaTienda(idFactura, idProducto, cantidad)));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
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
					ApplicationController.getInstance().handleRequest(new Context(Evento.BUSCAR_FACTURA_TIENDA, id));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TCarritoTienda carrito) {
		FacturaTable fTable = new FacturaTable(new Dimension(600, 175), Arrays.asList("PRODUCTO"), false);
		fTable.MostrarFacturaPorCarritoTienda(carrito);
		updatePanel(fTable, null);
	}
	
	private void listarPanel(LinkedList<TCarritoTienda> carritos) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		JPanel listarPanel = new JPanel();
		listarPanel.setLayout(new BoxLayout(listarPanel, BoxLayout.Y_AXIS));
		listarPanel.setOpaque(false);
		listarPanel.setMaximumSize(new Dimension(1024, 400));
		
		for (TCarritoTienda c : carritos) {
			FacturaTable fTable = new FacturaTable(new Dimension(600, 150), Arrays.asList("PRODUCTO"), false);
			fTable.MostrarFacturaPorCarritoTienda(c);
			listarPanel.add(fTable);
			listarPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		}
		
		JScrollPane scrollPane = new JScrollPane(listarPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setPreferredSize(new Dimension(700, 300));
		scrollPane.setMaximumSize(new Dimension(700, 300));
		scrollPane.setOpaque(false);
		panel.add(scrollPane);
		
		updatePanel(panel, 50);
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
		switch(responseContext.getEvent()) {
		
			//SUCCESS
			case ABRIR_FACTURA_TIENDA:
				carritoAux = (TCarritoTienda) responseContext.getDataObject();
				break;
				
			case AÑADIR_PRODUCTO_OK:
			case QUITAR_PRODUCTO_OK:
				carritoAux = (TCarritoTienda) responseContext.getDataObject();
				if (carritoTable != null && carritoPrecio != null) {
					carritoTable.clearTable();
					for (TLineaFacturaTienda l : carritoAux.getLineasFactura()) {
						carritoTable.addRow(new Object[]{l.getIdProducto(), l.getCantidad()}, false);
					}
					errorPanel.clear();
				}
				break;
				
			case CERRAR_FACTURA_TIENDA_OK:
				//Volver a Inicio
				remove(panelActual);
				carritoAux = null;
				carritoTable = null;
				carritoPrecio = null;
				inicioPanel.setVisible(true);
				panelActual = inicioPanel;
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), true);
				break;
				
			case DEVOLVER_PRODUCTO_OK:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), true);
				break;
				
			case BUSCAR_FACTURA_TIENDA_OK:
				mostrarPanel((TCarritoTienda) responseContext.getDataObject());
				break;
			case LISTAR_FACTURAS_TIENDA_OK:
				listarPanel((LinkedList<TCarritoTienda>) responseContext.getDataObject());
				break;
				
			//ERROR
			case AÑADIR_PRODUCTO_KO:
			case QUITAR_PRODUCTO_KO:
			case CERRAR_FACTURA_TIENDA_KO:
			case DEVOLVER_PRODUCTO_KO:
			case BUSCAR_FACTURA_TIENDA_KO:
			case LISTAR_FACTURAS_TIENDA_KO:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), false);
				break;
			default:
				errorPanel.showOutputMessage("Evento desconocido: " + responseContext.getEvent().toString(), false);
				break;
		}		
	}

}