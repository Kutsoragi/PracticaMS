package presentacion.vistas.marca;

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
import javax.swing.JPanel;

import negocio.marca.TMarca;
import negocio.producto.TProducto;
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
public class MarcaPanel extends JPanel implements Gui {

	private int INICIO_PANEL_HEIGHT = 300;
	
	private String nombre = "MARCA";
	
	private JPanel inicioPanel, panelActual;
	private ErrorPanel errorPanel;
	
	public MarcaPanel() {
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
				registrarPanel();
				System.out.println("Registrar");
			}
		});
		inicioPanel.add(registrarBtn, c);
		
		c.gridx++;
		JButton modificarBtn = new PanelButton("resources/icons/operaciones/modificar.png");
		modificarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarPanel();
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
		
		c.gridx++;
		JButton buscarBtn = new PanelButton("resources/icons/operaciones/buscar.png");
		buscarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPanel();
				System.out.println("Buscar");
			}
		});
		inicioPanel.add(buscarBtn, c);
		
		c.gridx = 0;
		c.gridy++;

		JButton mostrarCaloriasTotalesDeMarcaBtn = new PanelButton("resources/icons/operaciones/mostrar-calorias-medias-de-marca.png");
		mostrarCaloriasTotalesDeMarcaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerCaloriasMediasDeMarcaPanel();
				System.out.println("Mostrar Calorias Medias de Marca");
			}
		});
		inicioPanel.add(mostrarCaloriasTotalesDeMarcaBtn, c);
		
		c.gridx++;
		JButton listarBtn = new PanelButton("resources/icons/operaciones/listar.png");
		listarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_MARCAS, null));
				System.out.println("Mostrar Marcas");
			}
		});
		inicioPanel.add(listarBtn, c);
		
		c.gridx++;
		JButton mostrarProductosPorMarcaBtn = new PanelButton("resources/icons/operaciones/mostrar-productos-por-marca.png");
		mostrarProductosPorMarcaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarProductosPorMarcaPanel();
				System.out.println("Mostrar Productos Por Marca");
			}
		});
		inicioPanel.add(mostrarProductosPorMarcaBtn, c);
		
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
	
	//-- PANELES
	
	private void registrarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("NOMBRE"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = panel.getFieldText("NOMBRE");	
					ApplicationController.getInstance().handleRequest(new Context(Evento.REGISTRAR_MARCA, new TMarca(nombre)));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipos de datos no válidos o inexistentes.", false);
				} catch (IllegalArgumentException ex) {
					errorPanel.showOutputMessage(ex.getMessage(), false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void modificarPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID", "NOMBRE"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));
					String nombre = panel.getFieldText("NOMBRE");	
					ApplicationController.getInstance().handleRequest(new Context(Evento.MODIFICAR_MARCA, new TMarca(id, nombre)));
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
					ApplicationController.getInstance().handleRequest(new Context(Evento.BORRAR_MARCA, id));
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
					ApplicationController.getInstance().handleRequest(new Context(Evento.BUSCAR_MARCA, id));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarPanel(TMarca m) { // Muestra un transfer dado
		MostrarPanel panel = new MostrarPanel(Arrays.asList("ID", "NOMBRE"));
		panel.setLabelText("ID", Integer.toString(m.getId()));
		panel.setLabelText("NOMBRE", m.getNombre());
		updatePanel(panel, 50);
	}	
	
	private void obtenerCaloriasMediasDeMarcaPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("ID"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(panel.getFieldText("ID"));
					ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_CALORIAS_MEDIAS_DE_MARCA, id));
				} catch (NumberFormatException ex) {
					errorPanel.showOutputMessage("Tipo de dato no válido o inexistente.", false);
				}
			}
		});
		
		updatePanel(panel, null);
	}
	
	private void mostrarCaloriasMediasDeMarcaPanel(int calorias) { // Muestra un transfer dado
		MostrarPanel panel = new MostrarPanel(Arrays.asList("CALORIAS"));
		panel.setLabelText("CALORIAS", Integer.toString(calorias));
		updatePanel(panel, 50);
	}	
	
	private void mostrarMarcasPanel(List<TMarca> marcas) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setMaximumSize(new Dimension(1024, 460));
		
		TablePanel tablePanel = new TablePanel(new Dimension(775, 275), Arrays.asList("ID", "NOMBRE"));		
		for (TMarca m : marcas)
			tablePanel.addRow(new Object[]{Integer.toString(m.getId()), m.getNombre()}, false);
		
		panel.add(tablePanel);
		
		updatePanel(panel, null);
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
	
	private void mostrarProductosPorMarcaPanel() {
		final FormPanel panel = new FormPanel(Arrays.asList("NOMBRE MARCA"));
		
		panel.addEnterActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = panel.getFieldText("NOMBRE MARCA");
				ApplicationController.getInstance().handleRequest(new Context(Evento.MOSTRAR_PRODUCTOS_POR_MARCA, new TMarca(nombre)));
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
	
	public void abrir() {
		setVisible(true);
	}
	
	public void cerrar() {
		setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	public void actualizar(Context responseContext) {
		switch (responseContext.getEvent()) {
		
			//SUCCESS
			case REGISTRAR_MARCA_OK:
			case MODIFICAR_MARCA_OK:
			case BORRAR_MARCA_OK:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), true);
				break;
			case BUSCAR_MARCA_OK:
				mostrarPanel((TMarca) responseContext.getDataObject());
				break;
			case MOSTRAR_MARCAS_OK:
				mostrarMarcasPanel((List<TMarca>) responseContext.getDataObject());
				break;
			case MOSTRAR_CALORIAS_MEDIAS_DE_MARCA_OK:
				mostrarCaloriasMediasDeMarcaPanel((Integer) responseContext.getDataObject());
				break;
			case MOSTRAR_PRODUCTOS_POR_MARCA_OK:
				mostrarProductosPanel((List<TProducto>) responseContext.getDataObject());
				break;
			
			//ERROR
			case REGISTRAR_MARCA_KO:
			case MODIFICAR_MARCA_KO:
			case BORRAR_MARCA_KO:
			case BUSCAR_MARCA_KO:
			case MOSTRAR_CALORIAS_MEDIAS_DE_MARCA_KO:
			case MOSTRAR_PRODUCTOS_POR_MARCA_KO:
				errorPanel.showOutputMessage((String) responseContext.getDataObject(), false);
				break;
			default:
				errorPanel.showOutputMessage("Evento desconocido: " + responseContext.getEvent().toString(), false);
				break;
		}
	}
}
