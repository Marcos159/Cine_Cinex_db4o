package org.emg.cines_cinex.gui;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.emg.cines_cinex.base.Cine;
import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.util.Util;

import com.db4o.ObjectSet;



public class TablaCines extends JTable{
	private Connection conexion;
	private DefaultTableModel modeloDatos;
	
	private static final boolean DEBUG = false; 

	
	public TablaCines() {
		super();
		
		inicializar();
	}
	
	private void inicializar() {
		
		modeloDatos = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		
		modeloDatos.addColumn("*");
		modeloDatos.addColumn("Nombre");
		modeloDatos.addColumn("Ciudad");
		modeloDatos.addColumn("Salas");
		
		this.setModel(modeloDatos);
	}
	
	public void listar(List<Cine> Cines) {
		
		modeloDatos.setNumRows(0);
		for (Cine cine : Cines) {
			
			Object[] fila = new Object[]{cine.getId(),cine.getNombre(), cine.getCiudad(), cine.getSalas(),};
			
			modeloDatos.addRow(fila);
		}
	}
	
	public Cine getCineSeleccionado(){
		int filaSeleccionada = 0;
		
		filaSeleccionada = this.getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		String nombre = (String) getValueAt(filaSeleccionada, 1);
		
		Cine cine = new Cine();
		cine.setNombre(nombre);
		// Se asume que no existen dos tiendas con el mismo nombre.
		// Así se puede contar con que la consulta sólo devuelve un resultado
		ObjectSet<Cine> resultado = Util.db.queryByExample(cine);
		cine = resultado.next();
		
		return cine;
	}
	
	

}
