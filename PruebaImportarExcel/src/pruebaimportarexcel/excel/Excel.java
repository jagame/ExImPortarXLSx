/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Emilio
 */
public class Excel {

    private Path file;
    private Workbook book;

    //Constructor  
    private Excel(Workbook book) throws IOException {
        this.setBook(book);
    }

    
    //SHEETS METHODS
    /**
     * Devuelve el objeto Sheet que se identifica por sheetName
     * @param sheetName - Nombre de la pagina a buscar.
     * @return - La Pagina si existe o null en caso de que no exista.
     */
    public Sheet getSheet(String sheetName){
        Sheet resultSheet = null;
        
        if(this.book!=null){
            resultSheet = this.book.getSheet(sheetName);
        }
        
        return resultSheet;
    }
    
    /**
     * Obtiener una lista con los nombre de las paginas.
     *
     * @return List<>
     */
    public List<String> getSheets() {
        ArrayList<String> listSheets = new ArrayList();
        Sheet sheet;
        Iterator<Sheet> sheets = this.book.sheetIterator();

        while (sheets.hasNext()) {
            listSheets.add(sheets.next().getSheetName());
        }

        return listSheets;
    }
    
    
    //ROWS Methods
    /**
     * Devuelve el objeto Row que esta en la posicion indicada en index.
     * @param sheet - Pagina de donde optener la fila.
     * @param index - Indice de la fila a optener.
     * @return - Si la Row indicada por index existe optenemos la Row, sino un null.
     */
    public static Row getRow(Sheet sheet,int index){
        Row resultRow = null;
        
        if(sheet != null){
            resultRow = sheet.getRow(index);
        }
        
        return resultRow;
    }
    
        /**
     * Devuelve el numero de filas que contiene la pagina actual.
     *
     * @return
     */
    public static int getNumRow(Sheet sheet) {
        return (sheet !=null)?sheet.getPhysicalNumberOfRows():0;
    }
    
    
    //CELLS METHODS.
    /**
     * Devuelve el Cell de la posicion 'col' dentro del objeto Row.
     * @param row - Fila donde optener la celda.
     * @param index - Indice de la celda a optener.
     * @return  Si la celda existe la devuelve, sino retorna un null.
     */
    public static Cell getCell(Row row, int index){
        Cell resultCell = null;
        
        if(row != null){
            resultCell = row.getCell(index);
        }
        
        return resultCell;
    }
    
    /**
     * Devuelve un objeto del tipo que contiene la celda.
     *
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object result = null;
        
        if(cell!=null){
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        result = cell.getDateCellValue();
                    } else {
                        if(cell.getNumericCellValue() == (int) cell.getNumericCellValue()){
                            result = new Integer((int)cell.getNumericCellValue());
                        }else{
                            result = new Double(cell.getNumericCellValue());
                        }
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = new Boolean(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                case Cell.CELL_TYPE_BLANK:
                case Cell.CELL_TYPE_ERROR:
                    result = null;
                    break;
            }
        }

        return result;
    }
    
    /**
     * Devuelve el numero de filas que contiene la pagina actual.
     * 
     * @param row
     * @return 
     */
    public static int getNumCells(Row row){
        return (row !=null)?row.getPhysicalNumberOfCells():0;
    }
    
    
    //OBJECT METHODS.
    
    /**
     * Obtiene una instancia de la clase Excel, que representa un fichero del
     * tipo excel.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Excel getInstance(Path file) throws IOException {
        Excel excel = null;
        ExcelMIME excelMime = Excels.getExcelMIME(file);

        switch (excelMime) {
            case XLS:
                excel = new Excel(new HSSFWorkbook(new FileInputStream(file.toFile())));
                break;
            case XLSX:
                excel = new Excel(new XSSFWorkbook(new FileInputStream(file.toFile())));
                break;
        }

        return excel;
    }
    
    
    /**
     * Cerrar el fichero excel.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.file = null;
        this.book.close();
    }
    
    
    //Getter and Setters.
    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public Workbook getBook() {
        return book;
    }

    private void setBook(Workbook book) {
        this.book = book;
    }
}
