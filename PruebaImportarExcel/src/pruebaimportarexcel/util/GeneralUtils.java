/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Emilio
 */
public class GeneralUtils{
    
    public static <T>T[][] listOfListsToArray( List<List<T>> lista ){
        Object[][] resultado = new Object[lista.size()][];
        for(int i = 0 ; i < lista.size() ; i++){
            resultado[i] = lista.get(i).toArray();
        }  
        
        return (T[][])resultado;
    }
    
    /**
     * Pasa un Vector a ArrayList.
     * @param vector
     * @return 
     */
    public static List<Object> vectorTolist(Vector<Object> vector){
        return new ArrayList(vector);
    }
    
    /**
     * Pasa un List a Vector.
     * @param list
     * @return 
     */
    public static Vector<Object> listToVector(List<Object> list){
        return new Vector(list);
    } 
    
    /**
     * Pasa un Set a Vector.
     * @param set
     * @return 
     */
    public static Vector<Object> setToVector(Set set){
        return new Vector(set);
    }
    
    /**
     * Comprueba que toda una lista este llena con Objetos de la clase especificada como parametro.
     * @param list
     * @param type
     * @return 
     */
    public static boolean listContentClass(List<Object> list, Class type){
        boolean result = true;
        
        for(Object item: list){
            if( ! (result=type.isInstance(item)) ) break;
        }
        
        return result;
    }
}
