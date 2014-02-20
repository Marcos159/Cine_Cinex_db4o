package org.emg.cines_cinex.gui;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.emg.cines_cinex.base.Pelicula;
import org.emg.cines_cinex.base.Sala;
import org.emg.cines_cinex.util.Util;

import com.db4o.ObjectSet;



public class TablaSalas extends JTable  {
	private Connection conexion;
	private DefaultTableModel modeloDatos;
	
	private static final boolean DEBUG = false; 
	//private ArrayList<Actores> AActores;
	
	public TablaSalas() {
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
		modeloDatos.addColumn("Gestor");
		modeloDatos.addColumn("Precio");
		modeloDatos.addColumn("Pelicula");
		
		this.setModel(modeloDatos);
	}
	
	public void listar(List<Sala> Salas) {
		
		modeloDatos.setNumRows(0);
		for (Sala sala : Salas) {
			
			Object[] fila = new Object[]{sala.getId(), sala.getGestor(), sala.getButacas(), sala.getPelicula(),};
			
			modeloDatos.addRow(fila);
		}
	}
	
	public Sala getSalaSeleccionada(){
		int filaSeleccionada = 0;
		
		filaSeleccionada = this.getSelectedRow();
		if (filaSeleccionada == -1)
			return null;
		
		String gestor = (String) getValueAt(filaSeleccionada, 1);
		
		Sala sala = new Sala();
		sala.setGestor(gestor);
		
		// Se asume que no existen dos tiendas con el mismo nombre.
		// Así se puede contar con que la consulta sólo devuelve un resultado
		ObjectSet<Sala> resultado = Util.db.queryByExample(sala);
		sala = resultado.next();
		
		return sala;
	}
}
