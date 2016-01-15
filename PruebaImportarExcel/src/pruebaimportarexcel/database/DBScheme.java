package pruebaimportarexcel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import pruebaimportarexcel.util.GeneralUtils;

/**
 * Clase que representa un esquema de una base de datos
 */
public class DBScheme implements AutoCloseable {

    private DBClass driver;
    private String host, port, schemeName, user, password;
    private Connection con;

    /**
     * Crea un objeto de DBScheme y abre la conexion a la base de datos.
     *
     * @param db
     * @param ip
     * @param port
     * @param dbName
     * @param user
     * @param pass
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public DBScheme(DBClass db, String ip, String port, String dbName, String user, String pass)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        this.setDriver(db);
        this.setHost(ip);
        this.setPort(port);
        this.setPassword(pass);
        this.setUser(user);
        this.setSchemeName(dbName);

        this.openConnexion();
    }

//    public DBScheme(DBClass db, String ip, String dbName)
//            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
//        this(db, ip, "3306", dbName, null, null);
//    }
//
//    public DBScheme(String ip, String dbName, String user, String pass)
//            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
//        this(DBClass.MYSQL, ip, "3306", dbName, user, pass);
//    }
//
//    public DBScheme(String ip, String dbName)
//            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
//        this(DBClass.MYSQL, ip, dbName);
//    }
    /**
     * Author: Emilio Añover Garcia. Permite cambiar a otra base de datos.
     *
     * @param name
     * @throws SQLException
     */
    public void useCatalog(String name) throws SQLException {
        try {
            this.con.setCatalog(name);
            this.setSchemeName(name);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    /**
     * Author: Emilio Añover Garcia. Devuelve el nombre del esquema de base de
     * datos que se esta usando ahora.
     *
     * @return
     * @throws SQLException
     */
    public String getCatalogName() throws SQLException {
        return this.con.getCatalog();
    }

    /**
     * Author: Emilio Añover García. Devuelve una lista de los esquemas de datos
     * que hay en una base de datos.
     *
     * @return
     * @throws SQLException
     */
    public List<String> getDataBaseSchemas() throws SQLException {
        ResultSet rs = con.getMetaData().getCatalogs();
        List<String> schemas = new ArrayList();

        String schema;
        while (rs.next()) {
            schema = rs.getString(1);
            schemas.add(schema);
        }

        return schemas;
    }

    /**
     * Devuelve una lista con todos los nombres de las tablas del esquema actual
     *
     * @return
     * @throws SQLException
     */
    public Set<String> getTableNames() throws SQLException {
        ResultSet rs = con.getMetaData().getTables(null, null, null, null);
        Set<String> listaNombres = new TreeSet();
        String name;
        while (rs.next()) {
            name = rs.getString(3);
            listaNombres.add(name);
        }

        return listaNombres;
    }

    /**
     * Devuelve un map con el nombre y tipo de todas las columnas de una tabla
     * dada existente que el actual esquema
     *
     * @param table
     * @return
     * @throws SQLException
     */
    public Map<String, String> getColumnsInfo(String table) throws SQLException {
        ResultSet rs = con.getMetaData().getColumns(null, null, table, null);
        Map mapNombreTipo = new HashMap();
        String name;
        String type;
        while (rs.next()) {
            name = rs.getString(4);
            type = rs.getString(5);
            mapNombreTipo.put(name, type);
        }

        return mapNombreTipo;
    }

    /**
     * Devuelve un resultset con todos los registros de la tabla pasada por
     * parámetro
     *
     * @param table
     * @return
     * @throws SQLException
     */
    public ResultSet selectAllFrom(String table) throws SQLException {
        Statement sta = con.createStatement();

        return sta.executeQuery("SELECT * FROM " + table);
    }

    /**
     * Comprobar si la base de datos esta abierta.
     *
     * @return
     * @throws SQLException
     */
    public boolean isOpen() {
        boolean resultado = false;
        try {
            resultado = this.con.isValid(0);
        } catch (SQLException ex) {
            //Logger.getLogger(DBScheme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
        }
        return resultado;
    }

    /**
     * Abre la connexion a la base de datos.
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public void openConnexion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName(this.driver.getClassName()).newInstance();
        this.con = DriverManager.getConnection(this.getURL(), this.getUser(), this.getPassword());
    }

    @Override
    public void close() throws Exception {
        con.close();
    }

    /**
     * Devuelve una lista de los nombres de las columnas del ResultSet pasado
     * como parametro.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public List<Object> getColumns(ResultSet rs) throws SQLException {
        List<Object> result = new ArrayList<>();;

        try {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                result.add(rs.getMetaData().getColumnName(i));
            }
        } catch (NullPointerException ex) {
            result = null;
        }

        return result;
    }

    public List<Object> getRow(ResultSet rs) throws SQLException {
        List<Object> result = new ArrayList<>();
        List<Object> columns = null;

        try {
            columns = this.getColumns(rs);
            for (Object col : columns) {
                result.add(rs.getObject((String) col));
            }
        } catch (NullPointerException ex) {
            result = null;
        }

        return result;
    }

    public List<List<Object>> getRows(ResultSet rs) throws SQLException {
        List<List<Object>> result = new ArrayList<>();

        try {
            while (rs.next()) {
                result.add(this.getRow(rs));
            }
        } catch (NullPointerException ex) {
            result = null;
        }

        return result;
    }

    /**
     * Devuelve un modelo de JTable con los datos del ResultSet.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public static AbstractTableModel resultSetToJTable(ResultSet rs) throws SQLException {
        AbstractTableModel resultado = null;
        Vector<Object> columns = null;
        Vector<Vector<Object>> allDatas = null;
        Vector<Object> data = null;

        if (rs != null) {
            columns = new Vector<>();
            allDatas = new Vector<>();
            //Llenar Vector de Columns.
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                columns.add(rs.getMetaData().getColumnName(i));
            }

            //Lenar Vector de Datos.
            while (rs.next()) {
                data = new Vector<>();
                for (Object col : columns) {
                    data.add(rs.getObject((String) col));
                }

                allDatas.add(data);
            }
        }

        if (columns == null) { //Crea un modelo de datos vacio.
            resultado = new DefaultTableModel();
        } else {
            resultado = new DefaultTableModel(allDatas, columns);
        }

        return resultado;
    }

    /**
     * Devuelve un modelo de JTable con los datos del ResultSet.
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public AbstractTableModel resultSetToJTable(String tableName) throws SQLException {
        AbstractTableModel resultado = null;
        ResultSet rs = null;
        Vector<Object> columns = null;
        Vector<Vector<Object>> allDatas = null;

        //Inicializacion de las difetenes varables locales.
        rs = this.selectAllFrom(tableName);
        columns = (Vector<Object>) GeneralUtils.arrayListToVector((ArrayList<Object>) this.getColumns(rs));
        allDatas = new Vector<>();

        for (List<Object> item : this.getRows(rs)) {
            allDatas.add((Vector<Object>) GeneralUtils.arrayListToVector((ArrayList<Object>) item));
        }

        if (columns == null) { //Crea un modelo de datos vacio.
            resultado = new DefaultTableModel();
        } else {
            resultado = new DefaultTableModel(allDatas, columns);
        }

        return resultado;
    }

    public static List<String> listToSQLInsert(String table, List<Object> columns, List<Vector<Object>> rows) {
        List<String> result = new ArrayList<>();
        String cabecera = null;
        
        //result.add("// --- INSERTS TO "+table+" ---");
        //Recorrer todas las filas.
        for (List<Object> row : rows) {
            cabecera = DBScheme.getStringInsertHead(table, columns);
            for (int i = 0; i < row.size(); i++) {
                cabecera = cabecera + DBScheme.objectToStringSQL(row.get(i),"'");
                if (i != (row.size() - 1)) {
                    cabecera = cabecera + ",";
                }
            }
            result.add(cabecera = cabecera +");");
        }

        return result;
    }

    private static String getStringInsertHead(String table, List<Object> columns) {
        String result = "INSERT INTO " + table + " (";

        for (int i = 0; i < columns.size(); i++) {
            result = result + DBScheme.objectToStringSQL(columns.get(i),"`");
            if (i != (columns.size() - 1)) {
                result = result + ",";
            }
        }

        result = result + ") VALUES (";

        return result;
    }

    private static String objectToStringSQL(Object obj, String delimitador) {
        SimpleDateFormat formatSQL = new SimpleDateFormat("yyyy-MM-dd");
        String result = "null";

        if (obj != null) {
            if (obj instanceof Double || obj instanceof Integer) {
                result = String.valueOf(obj);
            } else if (obj instanceof String) {
                result = delimitador + (String) obj + delimitador;
            } else if (obj instanceof Date) {
                result = delimitador + formatSQL.format((Date) obj) + delimitador;
            }
        }

        return result;
    }
    
    public void excuteStatement(String sql) throws SQLException{
        Statement statement = this.con.createStatement();
        statement.executeUpdate(sql);
    }

    /**
     * Author: Emilio Añover García. Devuelve la url de conexion a la base de
     * datos.
     *
     * @return String - URL de conexion a la base de datos.
     */
    public String getURL() {
        return "jdbc:" + this.driver + "://" + this.host + ":" + this.port + "/" + this.schemeName;
    }

    //Getter and Setter
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public DBClass getDriver() {
        return driver;
    }

    public void setDriver(DBClass driver) {
        this.driver = driver;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
