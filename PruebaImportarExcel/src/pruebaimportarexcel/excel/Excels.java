/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import pruebaimportarexcel.database.DBScheme;
import pruebaimportarexcel.util.GeneralUtils;

/**
 *
 * @author Emilio
 */
public class Excels {

    //METODOS RELATOVOS A OBTENER LA EXTENSION DEL EXCEL.
    /**
     * Author: Emilio Añover García. Devuelve true si el fichero es un fichero
     * .xls o .xlsx
     *
     * @param path
     * @return boolean
     * @throws IOException
     */
    public static boolean isFileExcel(Path path) throws IOException {
        boolean result = false;
        String mime;

        if (Files.isRegularFile(path)) {

            mime = Files.probeContentType(path);
            for (ExcelMIME excelMime : ExcelMIME.values()) {
                if (excelMime.getMime().equals(mime)) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Devuelve un ExcelMIME que representa el tipo de fichero excel.
     *
     * @param path
     * @return ExcelMIME
     * @throws IOException
     */
    public static ExcelMIME getExcelMIME(Path path) throws IOException {
        ExcelMIME resultado = null;
        String pathMime = Files.probeContentType(path);

        if (isFileExcel(path)) {
            for (ExcelMIME mime : ExcelMIME.values()) {
                if (mime.getMime().equals(pathMime)) {
                    resultado = mime;
                }
            }
        }

        return resultado;
    }

    /**
     * Author: Emilio Añover Devuelve la extension del archivo, siempre que sea
     * un excel o null si no es un fichero excel.
     *
     * @param path
     * @return String
     * @throws IOException
     */
    public static String getExtensionExcel(Path path) throws IOException {
        return (Excels.getExcelMIME(path) != null) ? Excels.getExcelMIME(path).name() : null;
    }

    //METODOS OPTENER FILAS DE UN EXCEL.
    /**
     * Devuelve la fila indicada por rowIndex, de la pagina sheet. Si cellSize
     * es 0 o menor optenemos todas las celdas de la fila, si es mayor se
     * optiene el numero indicado.
     *
     * @param sheet
     * @param rowIndex
     * @param cellSize
     * @return
     */
    public static List<Object> getRowToList(Sheet sheet, int rowIndex, int cellSize) {
        List<Object> resultado = null;
        Row row = null;
        Cell cell = null;
        int numColumns = 0;
        // 1.- Comprobar si existe la pagina y el numero de fila pedido es >= 0;
        if (sheet != null && rowIndex >= 0) {
            row = Excel.getRow(sheet, rowIndex); // Obtener la fila indicada por el rowIndex.

            // 2.- Existe la fila??
            if (row != null) {
                resultado = new ArrayList<>();//Ahora podemos crear un ArrayList con el contenido de la fila.

                numColumns = (cellSize <= 0) ? row.getPhysicalNumberOfCells() : cellSize; //Establecer el número de columnas que quiero optener de la fila.

                for (int i = 0; i < numColumns; i++) {
                    cell = Excel.getCell(row, i);
                    resultado.add(Excel.getCellValue(cell));
                }
            }
        }

        return resultado;
    }

    /**
     * Devuelve la primera fila del excel, siempre que sean todos objetos
     * Strings.
     *
     * @param excel
     * @param sheetName
     * @return
     */
    public static List<Object> getColumnsName(Excel excel, String sheetName) {
        List<Object> columnsName = null;
        Sheet sheet = null;
        Row row = null;
        if (excel != null) {
            sheet = excel.getSheet(sheetName);
            columnsName = Excels.getRowToList(sheet, 0, 0);
        }

        /*
            Todos los Objetos de la Lista son Strings??
                - Para que una fila sea considerada como Columns Name esta debe
                contene solo objetos del tipo String.
         */
        columnsName = (GeneralUtils.listContentClass(columnsName, String.class)) ? columnsName : null;

        return columnsName;
    }

    //METODOS PARA PASAR UN EXCEL A UNA JTABLA.
    /**
     * Obtiene todos los datos de una pagina Excel, como un ArrayList de
     * Vectores.
     *
     * @param excel
     * @param sheetName
     * @param colunms
     * @return
     */
    public static List<Vector<Object>> getDatasSheet(Excel excel, String sheetName, int colunms) {
        Sheet sheet = null;
        int numColumns = 0;
        List<Vector<Object>> datasheet = new Vector<>();

        Row row = null;
        if (excel != null) {
            sheet = excel.getSheet(sheetName);
            if (sheet != null) {
                numColumns = Excel.getNumCells(excel.getRow(sheet, 0));

                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    datasheet.add((Vector<Object>) GeneralUtils.arrayListToVector((ArrayList<Object>) Excels.getRowToList(sheet, i, colunms)));
                }

            }
        }

        return datasheet;
    }

    public static void excelToSQL(Path pathFile, Excel excelFile, List<String> listSheets) throws FileNotFoundException, IOException {
        PrintWriter fileOut = null;
        List<String> sheets = listSheets;
        List<Object> columns;
        List<Vector<Object>> rows = null;
        List<String> listInsert = null;

        try {
            //Creamos el flujo de salida.
            fileOut = new PrintWriter(pathFile.toFile());
            //Obtener las paginas.
            sheets = (sheets == null) ? excelFile.getSheets() : sheets;

            //Recorre paginas.
            for (String sheet : sheets) {
                //Obtener los nombre de las columnas.
                columns = getColumnsName(excelFile, sheet);
                if (columns != null) {
                    //Obtener todos los datos de la pagina.
                    rows = getDatasSheet(excelFile, sheet, columns.size());

                    //Convertilos a SQL.
                    listInsert = DBScheme.listToSQLInsert(sheet, columns, rows);

                    //Guardar a fichero.
                    for (String insert : listInsert) {
                        fileOut.println(insert);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ex.getClass().getCanonicalName()).log(Level.SEVERE, ex.getLocalizedMessage());
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(ex.getClass().getCanonicalName()).log(Level.SEVERE, ex.getLocalizedMessage());
            throw ex;
        } finally {
            fileOut.close();
        }

    }

    public static List<String> excelToDB(Excel excel, DBScheme db, List<String> listSheets) {
        List<String> result = new ArrayList<>();        
        List<String> sheets = listSheets;
        List<Object> columns = null;
        List<Vector<Object>> rows = null;
        List<String> listInsert = null;
        
        //Obtener las paginas.
        sheets = (sheets == null) ? excel.getSheets() : sheets;

        //Recorre paginas.
        for (String sheet : sheets) {
            //Obtener los nombre de las columnas.
            columns = getColumnsName(excel, sheet);
            if (columns != null) {
                //Obtener todos los datos de la pagina.
                rows = getDatasSheet(excel, sheet, columns.size());

                //Convertilos a SQL.
                listInsert = DBScheme.listToSQLInsert(sheet, columns, rows);

                //Guardar a fichero.
                for (String insert : listInsert) {
                    try {
                        db.excuteStatement(insert);
                    } catch (SQLException ex) {
                        result.add(insert + "\n" + ex.getMessage());
                        Logger.getLogger(Excel.class.getCanonicalName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return result;
    }
}
