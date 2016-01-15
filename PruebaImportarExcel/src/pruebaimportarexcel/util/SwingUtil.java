/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Emilio
 */
public class SwingUtil {

    /**
     * Author: Emilio Añover García. Vacia el modelo de combobox y lo llena un
     * comboBox con un Array de Objetos.
     *
     * @param model
     * @param lista
     */
    public static void initComboBoxModel(ComboBoxModel model, Object[] lista) {
        DefaultComboBoxModel defCombo = (DefaultComboBoxModel) model;
        defCombo.removeAllElements();
        for (Object o : lista) {
            defCombo.addElement(o);
        }
    }

    /**
     * Author: Emilio Añover García. Llena un comboBox con un ArrayList Vacia el
     * modelo de combobox y lo llena un comboBox con un Array de Objetos.
     *
     * @param model
     * @param lista
     */
    public static void initComboBoxModel(ComboBoxModel model, List lista) {
        DefaultComboBoxModel defCombo = (DefaultComboBoxModel) model;
        defCombo.removeAllElements();
        for (Object o : lista) {
            defCombo.addElement(o);
        }
    }

    /**
     * Devuelve un model para rellenar una jtable a partir de dos vectores.
     *
     * @param model
     * @param header
     * @param datas
     */
    public static AbstractTableModel initTableModel(Vector<Object> header, Vector<Vector<Object>> datas) {
        AbstractTableModel model = null;

        if (header != null) {
            model = new DefaultTableModel(datas, header);
        } else {
            model = new DefaultTableModel();
        }

        return model;
    }

    /**
     * 
     * @param lista
     * @return 
     */
    public static AbstractListModel initListModel(List lista){
        DefaultListModel<String> model = new DefaultListModel<>();
        
        for(Object item: lista){
            model.addElement(item.toString());
        }
        
        return model;
    }
    
    public static List<String> listModelToList(ListModel<String> modelo){
        List<String> result = new ArrayList<>();
        
        for(int i = 0; i < modelo.getSize(); i++){
            result.add(modelo.getElementAt(i));
        }
        
        return result;
    }
    
    /**
     * Activa o desactiva un array de componentes.
     *
     * @param componentes
     * @param opcion
     */
    public static void enabledComponnects(boolean estado, JComponent... componentes) {
        for (JComponent componente : componentes) {
            componente.setEnabled(estado);
        }
    }
    
    /**
     * Devuelve un jfilechooser 
     * @return 
     */
    public static JFileChooser generateExcelFileChooser(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx");
            }

            @Override
            public String getDescription() {
                return "Excel (xls,xlsx)";
            }
            
        });
        return fileChooser;
    }
}
