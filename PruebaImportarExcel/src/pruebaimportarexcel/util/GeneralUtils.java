/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Emilio
 */
public class GeneralUtils{
    /**
     * Pasa un Vector a ArrayList.
     * @param vector
     * @return 
     */
    public static List<Object> vectorToArrayList(Vector<Object> vector){
        List<Object> result = new ArrayList<>();
        
        for(Object item: vector){
            result.add(item);
        }        
        
        return result;
    } 
    
    /**
     * Pasa un Vector a ArrayList.
     * @param vector
     * @return 
     */
    public static List<Object> arrayListToVector(ArrayList<Object> arrayList){
        List<Object> result = new Vector<>();
        
        for(Object item: arrayList){
            result.add(item);
        }        
        
        return result;
    } 
    
    /**
     * Pasa un Set a Vector.
     * @param set
     * @return 
     */
    public static Vector<Object> setToVector(Set set){
        Vector<Object> result = null;
        Iterator<Object> it = null;
        
        if(!set.isEmpty()){
            result = new Vector<>();
            it = set.iterator();
            while(it.hasNext()){
                result.add(it.next());
            }
        }
        
        return result;
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
            if(!item.getClass().getSimpleName().equals(type.getSimpleName())){
                result = false;
            }
        }
        
        return result;
    }
}
