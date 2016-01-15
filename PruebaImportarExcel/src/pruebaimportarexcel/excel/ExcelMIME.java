/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.excel;

/**
 *
 * @author Emilio
 */
public enum ExcelMIME {
    XLS("application/vnd.ms-excel"), XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    
    private String mime;

    private ExcelMIME(String mime) {
        this.mime = mime;
    }
    
    public String getMime() {
        return mime;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
