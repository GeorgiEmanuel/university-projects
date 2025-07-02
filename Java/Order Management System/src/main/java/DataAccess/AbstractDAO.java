package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

/**
 * A generic abstract Data Access Object responsible for the CRUD operations for any object.
 * Uses Java Reflection and JDBC to generate SQL queries.
 *
 * @param <T> the type of the model
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Constructs the SQL SELECT query.
     *
     * @return the SELECT * FROM query string
     */
    private String selectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * Constructs the SQL SELECT query.
     *
     * @param field the name of the field to be used in the WHERE clause
     * @return the SELECT * FROM WHERE field = ? query string
     */
    private String createSelectQuery(String field) {
        return selectQuery() + " WHERE " + field + " =?";
    }

    /**
     * Constructs the SQL SELECT * FROM query.
     *
     * @return the SELECT * FROM query string
     */
    private String createFindAllQuery() {
        return selectQuery();
    }

    /**
     * Returns the name of the table corresponding to the model class.
     *
     * @return table name string
     */
    protected String getTableName() {
        return type.getSimpleName();
    }

    /**
     * Builds a comma separated list of fields.
     *
     * @return a string representing the class fields
     */
    private String getClassFields() {
        List<String> fields = new ArrayList<>();
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                fields.add(f.getName());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
        return "(" + String.join(", ", fields) + ")";

    }

    /**
     * Constructs the SQL INSERT query.
     *
     * @param fields represents the fields in the needed format for SQL INSERT query
     * @return String representing the query
     */
    private String createInsertQuery(String fields) {
        int nrFields = fields.split(",").length;
        return "INSERT INTO " +
                getTableName() +
                " " +
                fields +
                " VALUES (" +
                "?".repeat(nrFields).replaceAll("(?!^)(?=\\?)", ", ") + // ?, ?, ?, ?

                ")";
    }

    /**
     * Constructs the SQL UPDATE query.
     *
     * @return the SQL UPDATE query string
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(getTableName());
        sb.append(" SET ");
        boolean first = true;
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getName().equals("id")) {
                continue;
            }
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(f.getName()).append(" = ?");
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Constructs the SQL DELETE query.
     *
     * @return the DELETE FROM query string
     */
    private String createDeleteQuery() {
        return "DELETE FROM " +
                getTableName() +
                " WHERE id = ?";
    }

    /**
     * Uses the SQL SELECT * FROM query.
     *
     * @return list of objects from the database
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Uses the SQL SELECT * FROM WHERE ID = ? query.
     *
     * @param id The id used in WHERE clause
     * @return object from the database with the matching id
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Constructs a list of objects of type T from a ResultSet.
     *
     * @param resultSet the ResultSet obtained from a SQL query
     * @return a list of objects of type T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Uses the INSERT INTO query.
     *
     * @param t representing the object going to be inserted
     * @return the inserted object
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(getClassFields());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(i++, value);
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Uses the UPDATE query.
     *
     * @param t the object going to be updated
     * @return the edited object
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;

            for (Field field : type.getDeclaredFields()) {
                if (field.getName().equals("id")) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(i++, value);
            }

            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(t);
            statement.setObject(i, idValue);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Uses the DELETE query.
     *
     * @param t the object going to be removed
     * @return the deleted object
     */
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(t);
            statement.setObject(1, idValue);

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
}
