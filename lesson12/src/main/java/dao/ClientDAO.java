package dao;

import entity.Address;
import entity.Client;
import util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientDAO extends DAO<Client>
{
  public static final String INSERT_ADDRESS_SQL = "INSERT INTO address (street, house, apartmentNr, zip) VALUES (?, ?, ?, ?)";
  public static final String INSERT_CLIENT_SQL = "INSERT INTO client (name, surname, address_id, date_of_birth, phone_nr) VALUES (?, ?, ?, ?, ?)";
  public static final String SELECT_CLIENT_SQL = "SELECT c.id, c.name, c.surname, c.date_of_birth, c.phone_nr," +
      "  a.id, a.street, a.house, a.apartmentNr, a.zip" +
      "  FROM client c" +
      "  JOIN address a on c.address_id = a.id" +
      "  WHERE c.id = ?;";
  public static final String SELECT_CLIENT_SQL_All = "SELECT c.id, c.name, c.surname, c.date_of_birth, c.phone_nr," +
      "  a.id, a.street, a.house, a.apartmentNr, a.zip" +
      "  FROM client c" +
      "  JOIN address a on c.address_id = a.id";
  public static final String UPDATE_CLIENT_SQL = "update client SET name=?,surname=?, address_id=?, date_of_birth=?, phone_nr=? WHERE id=?";
  public static final String DELETE_CLIENT_SQL = "DELETE FROM client WHERE id=?";

  @Override
  public Client create(Client client)
  {
    try (Connection con = DbUtil.getConnectionFromPool())
    {
      long addressId = saveAddress(client.getAddress(), con);

      PreparedStatement pStmt = con.prepareStatement(INSERT_CLIENT_SQL);
      pStmt.setString(1, client.getName());
      pStmt.setString(2, client.getSurname());
      pStmt.setLong(3, addressId);
      pStmt.setLong(4, client.getBirthDate().getTime());
      pStmt.setString(5, client.getPhoneNr());

      pStmt.execute();

      client.setId(getKey(pStmt));
      return client;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  private long saveAddress(Address address, Connection con) throws SQLException
  {
    PreparedStatement pStmt = con.prepareStatement(INSERT_ADDRESS_SQL);

    pStmt.setString(1, address.getStreet());
    pStmt.setString(2, address.getHouse());
    pStmt.setLong(3, address.getApartmentNr());
    pStmt.setLong(4, address.getZip());

    pStmt.execute();
    long key = getKey(pStmt);
    address.setId(key);

    return key;
  }

  @Override
  public Client read(long id)
  {
    try (Connection con = DbUtil.getConnectionFromPool())
    {
      PreparedStatement pStmt = con.prepareStatement(SELECT_CLIENT_SQL);
      pStmt.setLong(1, id);

      ResultSet resultSet = pStmt.executeQuery();
      resultSet.next();

      int pos = 1;
      long cId = resultSet.getLong(pos++);
      String name = resultSet.getString(pos++);
      String surname = resultSet.getString(pos++);
      Date birthDate = new Date(resultSet.getLong(pos++));
      String phoneNr = resultSet.getString(pos++);

      long aId = resultSet.getLong(pos++);
      String street = resultSet.getString(pos++);
      String house = resultSet.getString(pos++);
      long apartmentNr = resultSet.getLong(pos++);
      long zip = resultSet.getLong(pos);

      return new Client(cId, name, surname, new Address(aId, street, house, apartmentNr, zip), birthDate, phoneNr);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Client> readAll()
  {
    ArrayList<Client> clients = new ArrayList<>();


    try (Connection con = DbUtil.getConnectionFromPool())
    {
      PreparedStatement pStmt = con.prepareStatement(SELECT_CLIENT_SQL_All);
      Client client;
      ResultSet resultSet = pStmt.executeQuery();
      while (resultSet.next())
      {
        int pos = 1;
        long cId = resultSet.getLong(pos++);
        String name = resultSet.getString(pos++);
        String surname = resultSet.getString(pos++);
        Date birthDate = new Date(resultSet.getLong(pos++));
        String phoneNr = resultSet.getString(pos++);

        long aId = resultSet.getLong(pos++);
        String street = resultSet.getString(pos++);
        String house = resultSet.getString(pos++);
        long apartmentNr = resultSet.getLong(pos++);
        long zip = resultSet.getLong(pos);

        client = new Client(cId, name, surname, new Address(aId, street, house, apartmentNr, zip), birthDate, phoneNr);
        clients.add(client);
      }
      return clients;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean update(Client entity)
  {
    boolean rez = false;
    try (Connection con = DbUtil.getConnectionFromPool())
    {
      PreparedStatement pStmt = con.prepareStatement(UPDATE_CLIENT_SQL);
      pStmt.setString(1, entity.getName());
      pStmt.setString(2, entity.getSurname());
      pStmt.setLong(3, entity.getAddress().getId());
      pStmt.setLong(4, entity.getBirthDate().getTime());
      pStmt.setString(5, entity.getPhoneNr());
      pStmt.setLong(6, entity.getId());

      if (pStmt.executeUpdate() == 1){
          rez = true;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();

    }
    return rez;

  }

  @Override
  public boolean delete(Client entity)
  {
      boolean rez = false;
      try (Connection con = DbUtil.getConnectionFromPool())
      {
          PreparedStatement pStmt = con.prepareStatement(DELETE_CLIENT_SQL);

          pStmt.setLong(1, entity.getId());
          if (pStmt.executeUpdate() == 1){
              rez = true;
          }

      }
      catch (SQLException e)
      {
          e.printStackTrace();

      }
      return rez;
  }
}