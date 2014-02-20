package org.emg.cines_cinex.gui;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.util.Util;



import com.db4o.ObjectSet;
import com.db4o.query.Predicate;



public class TablaPeliculas extends JTable {
	private Connection conexion;
	private DefaultTableModel modeloDatos;
	
	private static final boolean DEBUG = false; 
	
	public TablaPeliculas() {
		super();
		inicializar();
	}
	
	/**
	 * Inicializa la estructura de la tabla
	 */
	private void inicializar() {
		
		modeloDatos = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		
		modeloDatos.addColumn("*");
		modeloDatos.addColumn("Titulo");
		modeloDatos.addColumn("Director");
		modeloDatos.addColumn("Productora");
		
		this.setModel(modeloDatos);
	}
	
	public void listar(List<Pelicula> Peliculas) {
		
		modeloDatos.setNumRows(0);
		for (Pelicula pelicula : Peliculas) {
			
			Object[] fila = new Object[]{pelicula.getId(),pelicula.getTitulo(), pelicula.getDirector(), pelicula.getProductora(),};
			
			modeloDatos.addRow(fila);
		}
	}
	
	public Pelicula getPeliculaSeleccionada(){
		
		int filaSeleccionada = 0;
		
		filaSeleccionada = this.getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		String titulo = (String) getValueAt(filaSeleccionada, 1);
		
		Pelicula pelicula = new Pelicula();
		pelicula.setTitulo(titulo);
		// Se asume que no existen dos tiendas con el mismo nombre.
		// Así se puede contar con que la consulta sólo devuelve un resultado
		ObjectSet<Pelicula> resultado = Util.db.queryByExample(pelicula);
		pelicula = resultado.next();
		
		return pelicula;
	}
	
	public void listar(String filtro) {
		vaciar();
		filtro = filtro.toLowerCase();
		List<Pelicula> peliculas = Util.db.query(Pelicula.class);
		for (Pelicula pelicula : peliculas) {
			
			if (pelicula.getTitulo().toLowerCase().contains(filtro) || 
				pelicula.getDirector().toLowerCase().contains(filtro)){
				
				anadirFila(pelicula);
			}
			
		}
	}
	private void anadirFila(Pelicula pelicula) {
		Object[] fila = new Object[] {
				pelicula.getId(),
				pelicula.getTitulo(),
				pelicula.getDirector(),
				pelicula.getProductora()				
			};
		
		modeloDatos.addRow(fila);
		
	}
	
	public void vaciar() {
		
		modeloDatos.setNumRows(0);
				
	}
	
}
