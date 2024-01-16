package lk.ijse.pos_backend.db;

import lk.ijse.pos_backend.dto.ItemDTO;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBProcess extends HttpServlet {
    Connection CONNECTION;
    public static final Logger logger= LoggerFactory.getLogger(DBProcess.class);

    public void saveItem(List<ItemDTO> itemList, Connection connection) throws SQLException {

    }

    public void getItem(String item_id,Connection connection) throws SQLException {

    }

    public void deleteItem(String item_id,Connection connection) throws SQLException {

    }
    public void updateItem(ItemDTO item,Connection connection) throws SQLException {
        
    }
}
