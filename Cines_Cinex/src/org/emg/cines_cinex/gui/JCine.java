package org.emg.cines_cinex.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;


import org.emg.cines_cinex.base.Cine;
import org.emg.cines_cinex.base.Sala;
import org.emg.cines_cinex.util.Util;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JCine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNombre;
	private JLabel lblCiudad;
	private JTextField tfCiudad;
	private JTextField tfNombre;
	private JLabel lblSalas;
	private JScrollPane scrollPane;
	
	private Util.Accion accion;
	
	private String Nombre;
	private String Ciudad;
	
	
	
	private DefaultListModel<Sala> modeloListaSalas;
	
	private List<Sala> salasSeleccionadas;
	private JList<Sala> listSalas;
	
	
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public Util.Accion mostrarDialogo() {
		setVisible(true);
		
		return accion;
	}
	
	public Cine getCine(){
		Cine cine = new Cine();
		cine.setNombre(Nombre);
		cine.setCiudad(Ciudad);
		cine.setSalas(new HashSet<Sala>(salasSeleccionadas));
		
		return cine;
	}
	
	private void aceptar() {
			
		if (tfNombre.getText().equals(""))
			return;
		Nombre = tfNombre.getText();
		Ciudad = tfCiudad.getText();
		salasSeleccionadas = listSalas.getSelectedValuesList();
		
		accion = Util.Accion.ACEPTAR;
		setVisible(false);
	}
	
	private void cancelar() {
		accion = Util.Accion.CANCELAR;
		setVisible(false);
	}
	
	private void inicializar() {
		modeloListaSalas = new DefaultListModel<Sala>();
		cargarSalas();
		
		listSalas = new JList <Sala>(); 
		
		listSalas.setModel(modeloListaSalas);
		
	}
	
	private void cargarSalas(){
			List<Sala> salas = Util.db.query(Sala.class);
			
			for (Sala sala : salas) {
				modeloListaSalas.addElement(sala);
			}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JCine dialog = new JCine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JCine() {
		setModal(true);
		setBounds(100, 100, 455, 369);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		inicializar();
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 51, 46, 14);
		contentPanel.add(lblNombre);
		
		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(10, 11, 46, 14);
		contentPanel.add(lblCiudad);
		
		tfCiudad = new JTextField();
		tfCiudad.setBounds(66, 8, 86, 20);
		contentPanel.add(tfCiudad);
		tfCiudad.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(66, 48, 86, 20);
		contentPanel.add(tfNombre);
		tfNombre.setColumns(10);
		
		lblSalas = new JLabel("Salas");
		lblSalas.setBounds(10, 88, 46, 14);
		contentPanel.add(lblSalas);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 414, 181);
		contentPanel.add(scrollPane);
		
		
		scrollPane.setViewportView(listSalas);
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
