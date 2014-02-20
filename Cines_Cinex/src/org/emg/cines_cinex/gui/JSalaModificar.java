package org.emg.cines_cinex.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;


import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.base.Sala;
import org.emg.cines_cinex.util.Util;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class JSalaModificar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfGestor;
	private JTextField tfButaca;
	
	
	private Util.Accion accion;
	private JScrollPane scrollPane;
	
	private String gestor;
	private int butacas;
	private Sala sala;
	private int ID;
	
	
	private DefaultListModel<Pelicula> modeloListaPeliculas;
	
	private Pelicula peliculaSeleccionada;
	private JList<Pelicula> listPeliculas;
	
	
	public Util.Accion mostrarDialogo() {
		setVisible(true);
		
		return accion;
	}
	
	public Sala getSala(){
		
		sala.setGestor(tfGestor.getText());
		sala.setButacas(Integer.parseInt(tfButaca.getText()));
		sala.setPelicula(peliculaSeleccionada);
		
		return sala;
	}
	
	public void setSala(Sala sala){
		this.sala = sala;
		
		
		this.gestor = sala.getGestor();
		this.butacas = sala.getButacas();
		
	}
	
	private void aceptar() {
		
		gestor = tfGestor.getText();
		butacas = Integer.parseInt(tfButaca.getText());
		peliculaSeleccionada = listPeliculas.getSelectedValue();
		
		accion = Util.Accion.ACEPTAR;
		setVisible(false);
	}
	
	private void cancelar() {
		accion = Util.Accion.CANCELAR;
		setVisible(false);
	}
	
	private void inicializar() {		
		
		
		modeloListaPeliculas = new DefaultListModel<Pelicula>();
		cargarPeliculas();
		
		listPeliculas = new JList<Pelicula>();
		listPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPeliculas.setModel(modeloListaPeliculas);		
		
	}

	private void cargarPeliculas(){
		List<Pelicula> peliculas = Util.db.query(Pelicula.class);
		
		for (Pelicula pelicula : peliculas) {
			modeloListaPeliculas.addElement(pelicula);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JSalaModificar dialog = new JSalaModificar();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the dialog.
	 */
	public JSalaModificar() {

		setModal(true);
		setBounds(100, 100, 451, 385);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		inicializar();
		{
			JLabel lblGestor = new JLabel("Gestor");
			lblGestor.setBounds(10, 11, 46, 14);
			contentPanel.add(lblGestor);
		}
		{
			JLabel lblButacas = new JLabel("Butacas");
			lblButacas.setBounds(10, 36, 46, 14);
			contentPanel.add(lblButacas);
		}
		{
			JLabel lblPeliculas = new JLabel("Peliculas");
			lblPeliculas.setBounds(10, 61, 46, 14);
			contentPanel.add(lblPeliculas);
		}
		{
			tfGestor = new JTextField();
			tfGestor.setBounds(66, 8, 86, 20);
			contentPanel.add(tfGestor);
			tfGestor.setColumns(10);
		}
		{
			tfButaca = new JTextField();
			tfButaca.setBounds(66, 33, 86, 20);
			contentPanel.add(tfButaca);
			tfButaca.setColumns(10);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 86, 415, 217);
			contentPanel.add(scrollPane);
			
			
			scrollPane.setViewportView(listPeliculas);
			{
				
				
			}
		}
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
