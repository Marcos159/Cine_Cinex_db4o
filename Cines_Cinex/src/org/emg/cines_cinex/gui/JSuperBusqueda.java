package org.emg.cines_cinex.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import org.emg.cines_cinex.base.Cine;
import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.util.Util;




import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Dialog con el que el usuario introduce informaci—n sobre un Arma
 * para insertar o modificar
 * @author Santiago Faci
 * @version 1.0
 */
public class JSuperBusqueda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private String nombre;
	private String apellidos;
	private int ID;
	private int altura;
	private JScrollPane scrollPane;
	private int idActor_representante; 
	private JScrollPane scrollPane_1;
	private JTextField tfBusquedaGlobal;
	private TablaPeliculas tablaPeliculas;
	private TablaCines tablaCines;
	private JLabel lblPeliculas;
	private JLabel lblCines;
	private JLabel lblBusqueda;


	/**
	 * Getters y setters para obtener y fijar información en la ventana
	 * @return
	 */
	
	public String getNombre() {
		return nombre;
	}
	
	
	public String getApellidos() {
		return apellidos;
	}
	
	
	public int getAltura() {
		return altura;
	}
	
	
	public int getIdActor_representante() {
		return idActor_representante;
	}
	
	public Cine getCine() {
		return tablaCines.getCineSeleccionado();
	}

	/**
	 * Se invoca cuando el usuario ha pulsado en Aceptar. Recoge y valida la información de las cajas de texto
	 * y cierra la ventana
	 */
	
	public void buscarGlobal(){
		
	
		tablaPeliculas.listar(tfBusquedaGlobal.getText());
		tablaCines.listar(tfBusquedaGlobal.getText());
		
		
	}
	
	private void aceptar() {
		
			
			setVisible(false);
		
		
	}
	
	/**
	 * Invocado cuando el usuario cancela. Simplemente cierra la ventana
	 */
	private void cancelar() {
		setVisible(false);
	}
	
	private void inicializar() {
		List<Pelicula> peliculas = Util.db.query(Pelicula.class);
		tablaPeliculas.listar(peliculas);
	
		List<Cine> cines = Util.db.query(Cine.class);
		// Muestra la lista en la JTable
		tablaCines.listar(cines);
		
	}
	
	/**
	 * Constructor. Crea la ventana
	 */
	public JSuperBusqueda() {
		setModal(true);
		setTitle("BUSQUEDA GLOBAL");
		setBounds(100, 100, 305, 413);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 213, 261, 101);
		contentPanel.add(scrollPane);
		
		
		
		tablaCines = new TablaCines();
		scrollPane.setViewportView(tablaCines);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 75, 261, 101);
		contentPanel.add(scrollPane_1);
		
		
		
		tablaPeliculas = new TablaPeliculas();
		scrollPane_1.setViewportView(tablaPeliculas);
		
		tfBusquedaGlobal = new JTextField();
		tfBusquedaGlobal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscarGlobal();
			}
		});
		tfBusquedaGlobal.setBounds(97, 8, 174, 20);
		contentPanel.add(tfBusquedaGlobal);
		tfBusquedaGlobal.setColumns(10);
		
		lblPeliculas = new JLabel("Peliculas");
		lblPeliculas.setBounds(10, 50, 77, 14);
		contentPanel.add(lblPeliculas);
		
		lblCines = new JLabel("Cines");
		lblCines.setBounds(10, 188, 66, 14);
		contentPanel.add(lblCines);
		
		lblBusqueda = new JLabel("Busqueda");
		lblBusqueda.setBounds(10, 11, 77, 14);
		contentPanel.add(lblBusqueda);
		inicializar();
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
