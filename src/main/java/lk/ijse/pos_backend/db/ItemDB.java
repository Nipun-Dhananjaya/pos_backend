package lk.ijse.pos_backend.db;

import lk.ijse.pos_backend.dto.ItemDTO;
import lk.ijse.pos_backend.dto.ItemQtyDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDB {
    Connection connection;
    final static String save = "INSERT INTO item (itemId,itemName,price,qty) VALUES (?,?,?,?)";
    final static String getAll = "SELECT * FROM item";
    final static String getLastItemId = "SELECT itemId FROM item ORDER BY itemId DESC LIMIT 1";
    final static String updateItem = "UPDATE item SET itemName=?, price=?, qty=? WHERE itemId=?";
    final static String deleteItem = "DELETE FROM item WHERE itemId=?";
    final static String getQtyById = " SELECT qty FROM item WHERE itemId = ?";
    final static String reduceItemQty = "UPDATE item SET qty = qty - ? WHERE itemId = ?";

    public void saveItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        this.connection = connection;
        var ps = connection.prepareStatement(save);
        ps.setString(1, itemDTO.getItemId());
        ps.setString(2, itemDTO.getItemName());
        ps.setDouble(3, itemDTO.getPrice());
        ps.setDouble(4, itemDTO.getQtv());

        if (ps.executeUpdate() != 0) {
            System.out.println("item saved");
        } else {
            System.out.println("item not saved");
        }
    }

    public List<ItemDTO> getAllItem(Connection connection) throws SQLException {
        var itemList = new ArrayList<ItemDTO>();
        this.connection = connection;
        var ps = connection.prepareStatement(getAll);
        var items = ps.executeQuery();

        while (items.next()) {
            itemList.add(new ItemDTO(
                    items.getString(1),
                    items.getString(2),
                    items.getDouble(3),
                    items.getDouble(4)
            ));
        }
        return itemList;
    }

    public String generateNextItemId(Connection connection) throws SQLException {
        this.connection = connection;
        var ps = connection.prepareStatement(getLastItemId);
        var rs = ps.executeQuery();
        if (rs.next()) {
            String currentItemId = rs.getString(1);
            int numericPart = Integer.parseInt(currentItemId.substring(3));
            int nextNumericPart = numericPart + 1;
            return "I00" + nextNumericPart;
        }
        return "I001";
    }

    public boolean updateItem(ItemDTO item, Connection connection) throws SQLException {
        this.connection = connection;
        var ps = connection.prepareStatement(updateItem);
        ps.setString(1, item.getItemName());
        ps.setDouble(2, item.getPrice());
        ps.setDouble(3, item.getQtv());
        ps.setString(4, item.getItemId());

        if (ps.executeUpdate() != 0) {
            System.out.println("item updated");
            return false;
        } else {
            System.out.println("item not updated");
            return true;
        }
    }

    public void deleteItem(String id, Connection connection) throws SQLException {
        this.connection = connection;
        var ps = connection.prepareStatement(deleteItem);
        ps.setString(1, id);
        if (ps.executeUpdate() != 0) {
            System.out.println("item deleted");
        } else {
            System.out.println("item not deleted");
        }
    }

    public String getCurrentQtyById(String action, Connection connection) throws SQLException {
        this.connection = connection;
        String qty = "";
        var ps = this.connection.prepareStatement(getQtyById);
        ps.setString(1, action);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            qty = rs.getString(1);
        }
        System.out.println(qty);
        return qty;
    }

    public void reduceItemCount(List<ItemQtyDTO> itemQtyObj, Connection connection) throws SQLException {
        this.connection = connection;
        var ps = connection.prepareStatement(reduceItemQty);
        for (ItemQtyDTO itemQty : itemQtyObj) {
            ps.setDouble(1, itemQty.getQty());
            ps.setString(2, itemQty.getItemId());
            System.out.println(itemQty.getItemId());
            System.out.println(itemQty.getQty());
            if (ps.executeUpdate() <= 0) {
                return;
            }
        }
        System.out.println("qty updated");
    }
}
