package org.emg.cines_cinex.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;



import org.emg.cines_cinex.base.Cine;
import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.base.Sala;
import org.emg.cines_cinex.base.Usuarios;

import org.emg.cines_cinex.util.Constantes;
import org.emg.cines_cinex.util.Util;
import org.emg.cines_cinex.util.Util.Accion;



import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.db4o.Db4oEmbedded;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Ventana {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JTabbedPane tabbedPane;
	private JPanel Cines;
	private JPanel Salas;
	private JPanel Peliculas;
	private JScrollPane scrollPane;
	private JButton btNuevoCine;
	private JButton btModificarCine;
	private JButton btEliminarCine;
	private JButton btAnadirPelicula;
	private JButton btModificarPelicula;
	private JButton btEliminarPelicula;
	private JScrollPane scrollPane_1;
	private JButton btAnadirSala;
	private JButton btModificarSala;
	private JButton btEliminarSala;
	private JScrollPane scrollPane_2;
	private TablaPeliculas tablaPeliculas;
	private JMenuItem mntmBuscar;
	private JButton btnXml;
	
	private List<Pelicula> listaPeliculas;
	private TablaSalas tablaSalas;
	private TablaCines tablaCines;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
		inicializar();
	}
	
	private void inicializar() {
		conectar();
		
		cargarPeliculas();
		cargarSalas();
		cargarCines();
		
	}
	
	/**
	 * Conecta con la Base de Datos
	 */
	private void conectar() {
		
		// Conecta con la Base de Datos (si el fichero no existe lo creará)
		Util.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), Constantes.DATABASE_FILENAME);
	}
	
	
	private void buscar(){
		JSuperBusqueda jSuperBusqueda = new JSuperBusqueda();
		jSuperBusqueda.setVisible(true);
	}
/*
 * PELICULA
 */
	
	private void altaPelicula(){
		JPelicula jPelicula = new JPelicula();
		if(jPelicula.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		Pelicula pelicula = jPelicula.getPelicula();
		
		Util.db.store(pelicula);
		Util.db.commit();
		
		cargarPeliculas();
		
	}
	
	private void eliminarPelicula(){
		Pelicula pelicula = tablaPeliculas.getPeliculaSeleccionada();
		if (pelicula == null){
			JOptionPane.showMessageDialog(null, "no hay actores");
			return;
		}
		
		Util.db.delete(pelicula);
		Util.db.commit();
		
		cargarPeliculas();
	}
	
	//TODO
	private void modificarPelicula(){
		
		Pelicula pelicula = tablaPeliculas.getPeliculaSeleccionada();
		
		if (pelicula == null)
			return;
		JPeliculaModificar jPeliculaModificar = new JPeliculaModificar();
		jPeliculaModificar.setPelicula(pelicula);
		jPeliculaModificar.inicializar();
		if(jPeliculaModificar.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		
		pelicula = jPeliculaModificar.getPelicula();
		
		Util.db.store(pelicula);
		Util.db.commit();
		
		cargarPeliculas();
		
		
	}
	
	public void escribirXMLPelicula(List<Pelicula> peliculas) {
 		
 		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 		Document xmlpelicula = null;
 		
 		try {
 			DocumentBuilder builder = factory.newDocumentBuilder();
 			DOMImplementation dom = builder.getDOMImplementation();
 			xmlpelicula = dom.createDocument(null,  "xml", null);
 			
 			Element raiz = xmlpelicula.createElement("Peliculas");
 			xmlpelicula.getDocumentElement().appendChild(raiz);
 			
 			Element nodoPelicula = null, nodoDatos = null;
 			Text texto = null;
 			for (Pelicula pelicula : peliculas) {
 				nodoPelicula = xmlpelicula.createElement("Pelicula");
 				raiz.appendChild(nodoPelicula);
 				
 				nodoDatos = xmlpelicula.createElement("Titulo");
 				nodoPelicula.appendChild(nodoDatos);
 				
 				texto = xmlpelicula.createTextNode(pelicula.getTitulo());
 				nodoDatos.appendChild(texto);
 				
 				nodoDatos = xmlpelicula.createElement("Director");
 				nodoPelicula.appendChild(nodoDatos);
 				
 				texto = xmlpelicula.createTextNode(pelicula.getDirector());
 				nodoDatos.appendChild(texto);
 				
 				nodoDatos = xmlpelicula.createElement("Productora");
 				nodoPelicula.appendChild(nodoDatos);
 				
 				texto = xmlpelicula.createTextNode(pelicula.getProductora());
 				nodoDatos.appendChild(texto);
 				
 			}
 			
 			Source source = new DOMSource(xmlpelicula);
 			Result resultado = new StreamResult(new File("xmlpeliculas.xml"));
 			
 			Transformer transformer = TransformerFactory.newInstance().newTransformer();
 			transformer.transform(source, resultado);
 			
 			
 			
 		} catch (ParserConfigurationException pce) {
 			pce.printStackTrace();
 		} catch (TransformerConfigurationException tce) {
 			tce.printStackTrace();
 		} catch (TransformerException te) {
 			te.printStackTrace();
 		}
 	}
	
	
	
	
	/*
	 * SALA
	 */
	
	private void altaSala(){
		JSala jSala = new JSala();
		if(jSala.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		Sala sala = jSala.getSala();


		Util.db.store(sala);
		Util.db.commit();
		
		cargarSalas();
	}
	
	private void eliminarSala(){
		Sala sala = tablaSalas.getSalaSeleccionada();
		if (sala == null){
			JOptionPane.showMessageDialog(null, "no hay salas");
			return;
		}
		// Elimina el personaje de la tabla de datos
		Util.db.delete(sala);
		Util.db.commit();
		
		cargarSalas();
	}
	//TODO
	private void modificarSala(){
		
		Sala sala = tablaSalas.getSalaSeleccionada();
		
		if (sala == null)
			return;
		
		JSalaModificar jSalaModificar = new JSalaModificar();
		jSalaModificar.setSala(sala);
		
		if(jSalaModificar.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		
		sala = jSalaModificar.getSala();
		
		Util.db.store(sala);
		Util.db.commit();
		
		cargarSalas();
		
		
	}	
	
	/*
	 * CINE
	 */
	
	private void altaCine(){
		JCine jCine = new JCine();
		if(jCine.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		Cine cine = jCine.getCine();
		
		Util.db.store(cine);
		Util.db.commit();
		
		cargarCines();
		
	}
	
	private void eliminarCine(){
		Cine cine = tablaCines.getCineSeleccionado();
		if (cine == null){
			JOptionPane.showMessageDialog(null, "no hay cines");
			return;
		}


		Util.db.delete(cine);
		Util.db.commit();
		
		cargarCines();
	}
	
	//TODO
	private void modificarCine(){
		
		Cine cine = tablaCines.getCineSeleccionado();
		
		if (cine == null)
			return;
		
		JCineModificar jCineModificar = new JCineModificar();
		jCineModificar.setCine(cine);
		
		if(jCineModificar.mostrarDialogo() == Util.Accion.CANCELAR)
			return;
		
		cine = jCineModificar.getCine();
		
		Util.db.store(cine);
		Util.db.commit();
		
		cargarCines();
		
		
	}	

	
	private void cargarPeliculas(){
		
		List<Pelicula> peliculas = Util.db.query(Pelicula.class);
		tablaPeliculas.listar(peliculas);
		
		
		listaPeliculas = peliculas;
	}
	
	private void cargarSalas(){
		
		List<Sala> salas = Util.db.query(Sala.class);
		tablaSalas.listar(salas);
	}
	
	private void cargarCines(){
		List<Cine> cines = Util.db.query(Cine.class);
		tablaCines.listar(cines);
	}
	
		
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 525, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		mntmBuscar = new JMenuItem("Buscar");
		mntmBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				buscar();
			}
		});
		mnNewMenu.add(mntmBuscar);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		Cines = new JPanel();
		tabbedPane.addTab("Cines", null, Cines, null);
		Cines.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 11, 385, 270);
		Cines.add(scrollPane);
		
		tablaCines = new TablaCines();
		scrollPane.setViewportView(tablaCines);
		
		btNuevoCine = new JButton("A\u00F1adir");
		btNuevoCine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaCine();
			}
		});
		btNuevoCine.setBounds(10, 11, 89, 23);
		Cines.add(btNuevoCine);
		
		btModificarCine = new JButton("Modificar");
		btModificarCine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarCine();
			}
		});
		btModificarCine.setBounds(10, 45, 89, 23);
		Cines.add(btModificarCine);
		
		btEliminarCine = new JButton("Eliminar");
		btEliminarCine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarCine();
			}
		});
		btEliminarCine.setBounds(10, 79, 89, 23);
		Cines.add(btEliminarCine);
		
		Salas = new JPanel();
		tabbedPane.addTab("Salas", null, Salas, null);
		Salas.setLayout(null);
		
		btAnadirSala = new JButton("A\u00F1adir");
		btAnadirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaSala();
			}
		});
		btAnadirSala.setBounds(10, 11, 89, 23);
		Salas.add(btAnadirSala);
		
		btModificarSala = new JButton("Modificar");
		btModificarSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificarSala();
			}
		});
		btModificarSala.setBounds(10, 45, 89, 23);
		Salas.add(btModificarSala);
		
		btEliminarSala = new JButton("Eliminar");
		btEliminarSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarSala();
			}
		});
		btEliminarSala.setBounds(10, 79, 89, 23);
		Salas.add(btEliminarSala);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(109, 11, 388, 271);
		Salas.add(scrollPane_2);
		
		tablaSalas = new TablaSalas();
		scrollPane_2.setViewportView(tablaSalas);
		
		Peliculas = new JPanel();
		tabbedPane.addTab("Peliculas", null, Peliculas, null);
		Peliculas.setLayout(null);
		
		btAnadirPelicula = new JButton("A\u00F1adir");
		btAnadirPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaPelicula();
			}
		});
		btAnadirPelicula.setBounds(10, 11, 89, 23);
		Peliculas.add(btAnadirPelicula);
		
		btModificarPelicula = new JButton("Modificar");
		btModificarPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificarPelicula();
			}
		});
		btModificarPelicula.setBounds(10, 45, 89, 23);
		Peliculas.add(btModificarPelicula);
		
		btEliminarPelicula = new JButton("Eliminar");
		btEliminarPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarPelicula();
			}
		});
		btEliminarPelicula.setBounds(10, 79, 89, 23);
		Peliculas.add(btEliminarPelicula);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(109, 11, 386, 271);
		Peliculas.add(scrollPane_1);
		
		tablaPeliculas = new TablaPeliculas();
		scrollPane_1.setViewportView(tablaPeliculas);
		
		btnXml = new JButton("XML");
		btnXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escribirXMLPelicula(listaPeliculas);
			}
		});
		btnXml.setBounds(10, 113, 89, 23);
		Peliculas.add(btnXml);
	}
}
