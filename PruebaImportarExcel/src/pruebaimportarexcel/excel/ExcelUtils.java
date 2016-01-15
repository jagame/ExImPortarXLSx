/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaimportarexcel.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import pruebaimportarexcel.database.DBScheme;

/**
 * Clase orientada a la gestión de ficheros excel
 */
public class ExcelUtils {

    Workbook wb;
    String path;

    public ExcelUtils(String path) {
        wb = new HSSFWorkbook();
        this.path = path;
    }

    /**
     * Importa a un excel todas las tablas existentes en un objeto DBScheme
     *
     * @param db
     * @param path
     * @throws IOException
     * @throws SQLException
     */
    public static void saveDBtoExcel(DBScheme db, String path) throws IOException, SQLException {
        ExcelUtils eu = new ExcelUtils(path);
        ResultSet rs;

        for (String s : db.getTableNames()) {
            rs = db.selectAllFrom(s);
            eu.saveResultSetToExcelSheet(rs);
        }

        eu.saveWorkbook();
    }
    
    public static void saveDBtoExcelTemplate(DBScheme db, String path) throws SQLException, IOException{
        ExcelUtils eu = new ExcelUtils(path);
        ResultSet rs;

        for (String s : db.getTableNames()) {
            rs = db.selectAllFrom(s);
            eu.saveMetaDataToExcelSheet(rs);
        }

        eu.saveWorkbook();
    }

    /**
     * Almacena el fichero excel en el path indicado al instanciar esta clase
     *
     * @throws IOException
     */
    public void saveWorkbook() throws IOException {
        wb.write(new FileOutputStream(path));
    }

    /**
     * Devuelve el CellStyle indicado para las celdas de cabecera de la tabla
     *
     * @return
     */
    public CellStyle GetTitleStyle() {
        CellStyle title = wb.createCellStyle();
        Font fuenteTitle = wb.createFont();
        fuenteTitle.setBold(true);
        title.setFont(fuenteTitle);
        title.setAlignment(CellStyle.ALIGN_CENTER);
        title.setBorderBottom(CellStyle.BORDER_MEDIUM);

        return title;
    }

    /**
     * Devuelve el CellStyle adecuado para mostrar una fecha en un formato
     * legible
     *
     * @return
     */
    public CellStyle getDateStyle() {
        CellStyle style = wb.createCellStyle();
        CreationHelper ch = wb.getCreationHelper();
        style.setDataFormat(ch.createDataFormat().getFormat("dd/mm/yyyy"));

        return style;
    }

    /**
     * Almacena todo el contenido del resulset pasado por parámetro en una nueva
     * Sheet dentro de este Workbook
     *
     * @param rs
     * @throws SQLException
     * @throws IOException
     */
    public void saveResultSetToExcelSheet(ResultSet rs) throws SQLException, IOException {
        CellStyle headerStyle = GetTitleStyle();
        Sheet sheet = wb.createSheet(rs.getMetaData().getTableName(1));
        int contaRow = 0;
        Row row = sheet.createRow(contaRow++);
        Cell cell;
        String value;
        int numColumnas = rs.getMetaData().getColumnCount();

        for (int i = 0; i < numColumnas; i++) {
            cell = row.createCell(i);
            value = rs.getMetaData().getColumnName(i + 1);
            cell.setCellValue(value);
            cell.setCellStyle(headerStyle);
        }
        while (rs.next()) {
            row = sheet.createRow(contaRow++);
            setResultSetRowToExcelRow(rs, row);
        }
    }
    
    public void saveMetaDataToExcelSheet(ResultSet rs) throws SQLException, IOException {
        CellStyle headerStyle = GetTitleStyle();
        Sheet sheet = wb.createSheet(rs.getMetaData().getTableName(1));
        int contaRow = 0;
        Row row = sheet.createRow(contaRow++);
        Cell cell;
        String value;
        int numColumnas = rs.getMetaData().getColumnCount();

        //Llenar el nombre de las columnas.
        for (int i = 0; i < numColumnas; i++) {
            cell = row.createCell(i);
            value = rs.getMetaData().getColumnName(i + 1);
            cell.setCellValue(value);
            cell.setCellStyle(headerStyle);
        }
    }

    private void setResultSetRowToExcelRow(ResultSet rs, Row row) throws SQLException {
        int numColumnas = rs.getMetaData().getColumnCount();
        int type;
        int rsCol;
        CellStyle dateStyle = getDateStyle();
        Cell cell;
        for (int i = 0; i < numColumnas; i++) {
            rsCol = i + 1;
            cell = row.createCell(i);
            type = rs.getMetaData().getColumnType(rsCol);
            switch (type) {
                case Types.BIGINT:
                case Types.BINARY:
                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.INTEGER:
                case Types.NUMERIC:
                case Types.REAL:
                case Types.SMALLINT:
                case Types.TINYINT:
                    double number = rs.getDouble(rsCol);
                    cell.setCellValue(number);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                case Types.TIMESTAMP_WITH_TIMEZONE:
                    Date date = rs.getDate(rsCol);
                    cell.setCellValue(date);
                    cell.setCellStyle(dateStyle);
                    break;
                case Types.BIT:
                case Types.BOOLEAN:
                    boolean bool = rs.getBoolean(rsCol);
                    cell.setCellValue(bool);
                    break;
                default:
                    String str = rs.getString(rsCol);
                    cell.setCellValue(str);
            }
        }
    }

////////////////    /**
////////////////     * Author: Emilio Añover García. Devuelve true si el fichero es un fichero
////////////////     * .xls o .xlsx
////////////////     *
////////////////     * @param path
////////////////     * @return boolean
////////////////     * @throws IOException
////////////////     */
////////////////    public static boolean isFileExcel(Path path) throws IOException {
////////////////        boolean result = false;
////////////////        String mime;
////////////////
////////////////        if (Files.isRegularFile(path)) {
////////////////
////////////////            mime = Files.probeContentType(path);
////////////////            for (ExcelMIME excelMime : ExcelMIME.values()) {
////////////////                if (excelMime.getMime().equals(mime)) {
////////////////                    result = true;
////////////////                }
////////////////            }
////////////////        }
////////////////
////////////////        return result;
////////////////    }

////////////////    /**
////////////////     * Author: Emilio Añover Devuelve la extension del archivo, siempre que sea
////////////////     * un excel o null si no es un fichero excel.
////////////////     *
////////////////     * @param path
////////////////     * @return String
////////////////     * @throws IOException
////////////////     */
////////////////    public static String getExtensionExcel(Path path) throws IOException {
////////////////        return (ExcelUtils.getExcelMIME(path) != null) ? ExcelUtils.getExcelMIME(path).name() : null;
////////////////    }

////////////////    /**
////////////////     * Devuelve un ExcelMIME que representa el tipo de fichero excel.
////////////////     *
////////////////     * @param path
////////////////     * @return ExcelMIME
////////////////     * @throws IOException
////////////////     */
////////////////    public static ExcelMIME getExcelMIME(Path path) throws IOException {
////////////////        ExcelMIME resultado = null;
////////////////        String pathMime = Files.probeContentType(path);
////////////////
////////////////        if (isFileExcel(path)) {
////////////////            for (ExcelMIME mime : ExcelMIME.values()) {
////////////////                if (mime.getMime().equals(pathMime)) {
////////////////                    resultado = mime;
////////////////                }
////////////////            }
////////////////        }
////////////////
////////////////        return resultado;
////////////////    }

////////////////    /**
////////////////     * Devuelve la primera fila del excel, siempre que sean todos objetos
////////////////     * Strings.
////////////////     *
////////////////     * @param excel
////////////////     * @param sheetName
////////////////     * @return
////////////////     */
////////////////    public static List<Object> getColumnsName(Excel excel, String sheetName) {
////////////////        List<Object> columnsName = null;
////////////////        Sheet sheet = null;
////////////////        Row row = null;
////////////////        if (excel != null) {
////////////////            sheet = excel.getSheet(sheetName);
////////////////            columnsName = ExcelUtils.getRowToList(sheet, 0, 0);
////////////////        }
////////////////
////////////////        /*
////////////////            Todos los Objetos de la Lista son Strings??
////////////////                - Para que una fila sea considerada como Columns Name esta debe
////////////////                contene solo objetos del tipo String.
////////////////         */
////////////////        columnsName = (GeneralUtils.listContentClass(columnsName, String.class))? columnsName: null;
////////////////
////////////////        return columnsName;
////////////////    }

////////////////    /**
////////////////     * Obtiene todos los datos de una pagina Excel, como un ArrayList de
////////////////     * Vectores.
////////////////     *
////////////////     * @param excel
////////////////     * @param sheetName
////////////////     * @param colunms
////////////////     * @return
////////////////     */
////////////////    public static List<Vector<Object>> getDatasSheet(Excel excel, String sheetName, int colunms) {
////////////////        Sheet sheet = null;
////////////////        int numColumns = 0;
////////////////        List<Vector<Object>> datasheet = new Vector<>();
////////////////
////////////////        Row row = null;
////////////////        if (excel != null) {
////////////////            sheet = excel.getSheet(sheetName);
////////////////            if (sheet != null) {
////////////////                numColumns = Excel.getNumCells(excel.getRow(sheet, 0));
////////////////
////////////////                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
////////////////                    datasheet.add((Vector<Object>) GeneralUtils.arrayListToVector((ArrayList<Object>) ExcelUtils.getRowToList(sheet, i, colunms)));
////////////////                }
////////////////
////////////////            }
////////////////        }
////////////////
////////////////        return datasheet;
////////////////    }

////////    public static List<Object> getRowToVector(Row row) {
////////        List<Object> columnsName = null;
////////
////////        if (row != null) {
////////            columnsName = new Vector<>();
////////            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
////////                columnsName.add(Excel.getCellValue(Excel.getCell(row, i)));
////////            }
////////        }
////////
////////        return columnsName;
////////    }
////////    /**
////////     * Devuelve la fila indicada por rowIndex, de la pagina sheet. Si cellSize
////////     * es 0 o menor optenemos todas las celdas de la fila, si es mayor se
////////     * optiene el numero indicado.
////////     *
////////     * @param sheet
////////     * @param rowIndex
////////     * @param cellSize
////////     * @return
////////     */
////////    public static List<Object> getRowToList(Sheet sheet, int rowIndex, int cellSize) {
////////        List<Object> resultado = null;
////////        Row row = null;
////////        Cell cell = null;
////////        int numColumns = 0;
////////        // 1.- Comprobar si existe la pagina y el numero de fila pedido es >= 0;
////////        if (sheet != null && rowIndex >= 0) {
////////            row = Excel.getRow(sheet, rowIndex); // Obtener la fila indicada por el rowIndex.
////////
////////            // 2.- Existe la fila??
////////            if (row != null) {
////////                resultado = new ArrayList<>();//Ahora podemos crear un ArrayList con el contenido de la fila.
////////
////////                numColumns = (cellSize <= 0) ? row.getPhysicalNumberOfCells() : cellSize; //Establecer el número de columnas que quiero optener de la fila.
////////
////////                for (int i = 0; i < numColumns; i++) {
////////                    cell = Excel.getCell(row, i);
////////                    resultado.add(Excel.getCellValue(cell));
////////                }
////////            }
////////        }
////////
////////        return resultado;
////////    }
}
