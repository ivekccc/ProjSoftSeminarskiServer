/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domain.Administrator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author x
 */
public class ModelTabeleAdministratori extends AbstractTableModel {

    private List<Administrator> listaAdministratora=new ArrayList<>();
    private final String[] kolone={"ime","prezime","username"};

    public ModelTabeleAdministratori() {
    }
    public ModelTabeleAdministratori(List<Administrator> listaAdministratora){
        this.listaAdministratora=listaAdministratora;
    }
    
    @Override
    public int getRowCount() {
        return listaAdministratora.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Administrator admin=listaAdministratora.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return admin.getIme();
            case 1:
                return admin.getPrezime();
            case 2:
                return admin.getUsername();
            default:
                return "NA";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
}
