package dao;

import entity.InvoiceJson;
import flyway.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceDaoImpl implements DAO<InvoiceJson> {
    private final JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    @Override
    public @NotNull Optional<InvoiceJson> findByName(String name) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Invoice WHERE name = '" + name + "'");
            InvoiceJson invoiceJson = null;
            while (resultSet.next()) {
                invoiceJson = new InvoiceJson(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("firm"),
                        resultSet.getString("amount")
                );
            }
            return Optional.ofNullable(invoiceJson);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public @NotNull Optional<InvoiceJson> findById(int id) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Invoice WHERE id = " + id);
            InvoiceJson invoiceJson = null;
            while (resultSet.next()) {
                invoiceJson = new InvoiceJson(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("firm"),
                        resultSet.getString("amount")
                );
            }
            return Optional.ofNullable(invoiceJson);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public @NotNull List<@NotNull InvoiceJson> getAll() {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Invoice");
            List<InvoiceJson> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new InvoiceJson(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("firm"),
                        resultSet.getString("amount")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(@NotNull InvoiceJson entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                var prepareStatement = connection.prepareStatement(
                        "INSERT INTO Invoice (name, firm, amount) " +
                                "VALUES (?, ?, ?)");
                prepareStatement.setString(1, entity.getName());
                prepareStatement.setString(2, entity.getFirm());
                prepareStatement.setString(3, entity.getAmount());
                statement.executeUpdate(prepareStatement.toString());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull InvoiceJson entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                var prepareStatement = connection.prepareStatement(
                        "UPDATE Invoice SET (id, name, firm, amount) = (?, ?, ?, ?) where name = ?");
                prepareStatement.setInt(1, entity.getId());
                prepareStatement.setString(2, entity.getName());
                prepareStatement.setString(3, entity.getFirm());
                prepareStatement.setString(4, entity.getAmount());
                prepareStatement.setString(5, entity.getName());
                statement.executeUpdate(prepareStatement.toString());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(@NotNull InvoiceJson entity) {
        try (Connection connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (Statement statement = connection.createStatement()) {
                var preparedStatement = connection.prepareStatement(
                        "DELETE FROM Invoice WHERE name = ?");
                preparedStatement.setString(1, entity.getName());
                statement.executeUpdate(preparedStatement.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
