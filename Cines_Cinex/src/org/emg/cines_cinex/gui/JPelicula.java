package org.emg.cines_cinex.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;



import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.util.Util;
import org.emg.cines_cinex.util.Util.Accion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JPelicula extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfProductora;
	private JTextField tfDirector;
	private JTextField tfTitulo;
	
	private String titulo;
	private String director;
	private String productora;
	
	private Util.Accion accion;
	
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProductora() {
		return productora;
	}

	public void setProductora(String productora) {
		this.productora = productora;
	}

	public Accion mostrarDialogo() {
		setVisible(true);
		
		return accion;
	}
	
	public Pelicula getPelicula(){
		Pelicula pelicula = new Pelicula();
		pelicula.setTitulo(titulo);
		pelicula.setDirector(director);
		pelicula.setProductora(productora);
		
		return pelicula;
	}
	
	private void aceptar(){
		if (tfTitulo.getText().equals(""))
			return;
		
		titulo = tfTitulo.getText();
		director = tfDirector.getText();
		productora = tfProductora.getText();
		
		accion = Accion.ACEPTAR;
		setVisible(false);
		
	}
	
	private void cancelar() {
		accion = Accion.CANCELAR;
		setVisible(false);
	}
	
	private void inicializar() {
	}
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JPelicula dialog = new JPelicula();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public JPelicula() {
		setModal(true);
		setBounds(100, 100, 194, 174);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTitulo = new JLabel("Titulo");
			lblTitulo.setBounds(10, 11, 46, 14);
			contentPanel.add(lblTitulo);
		}
		{
			JLabel lblDirector = new JLabel("Director");
			lblDirector.setBounds(10, 36, 46, 14);
			contentPanel.add(lblDirector);
		}
		{
			JLabel lblProductora = new JLabel("Productora");
			lblProductora.setBounds(10, 61, 75, 14);
			contentPanel.add(lblProductora);
		}
		{
			tfProductora = new JTextField();
			tfProductora.setBounds(70, 58, 86, 20);
			contentPanel.add(tfProductora);
			tfProductora.setColumns(10);
		}
		{
			tfDirector = new JTextField();
			tfDirector.setBounds(70, 33, 86, 20);
			contentPanel.add(tfDirector);
			tfDirector.setColumns(10);
		}
		{
			tfTitulo = new JTextField();
			tfTitulo.setBounds(70, 8, 86, 20);
			contentPanel.add(tfTitulo);
			tfTitulo.setColumns(10);
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
