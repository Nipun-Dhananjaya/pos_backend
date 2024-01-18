package lk.ijse.pos_backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.pos_backend.db.ItemDB;
import lk.ijse.pos_backend.dto.ItemDTO;
import lk.ijse.pos_backend.dto.ItemQtyDTO;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "item", value = "/item/*", loadOnStartup = 3)
public class Item extends HttpServlet {
    Connection connection;
    ItemDB db = new ItemDB();
    public static final Logger logger= LoggerFactory.getLogger(Item.class);

    @Override
    public void init() throws ServletException {
        logger.info("Init the item servlet");
        try {
            InitialContext initialContext = new InitialContext();
            DataSource pool= (DataSource) initialContext.lookup("java:comp/env/jdbc/pos");
            System.out.println(pool);
            this.connection= pool.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkContentType(req,resp)) {
            try {
                Jsonb jsonb = JsonbBuilder.create();
                ItemDTO item = jsonb.fromJson(req.getReader(),ItemDTO.class);
                System.out.println(item.toString());
                db.saveItem(item, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var action = req.getParameter("action");
        if (action.equals("all")) {
            try {
                var items = db.getAllItem(connection);
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(items, resp.getWriter());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("nextVal")) {
            try {
                resp.setContentType("text/html");
                resp.getWriter().print(db.generateNextItemId(connection));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                resp.setContentType("text/html");
                resp.getWriter().print(db.getCurrentQtyById(action, connection));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            db.deleteItem(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var param = req.getParameter("reduce");

        if (checkContentType(req, resp)) {
            Jsonb jsonb = JsonbBuilder.create();
            if (param != null) {
                try {
                    List<ItemQtyDTO> itemQtyObjArray = jsonb.fromJson(req.getReader(),new ArrayList<ItemQtyDTO>(){}.getClass().getGenericSuperclass());
                    db.reduceItemCount(itemQtyObjArray, connection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    ItemDTO item = jsonb.fromJson(req.getReader(), ItemDTO.class);
                    if (db.updateItem(item, connection)) {
                        resp.setContentType("application/json");
                        resp.getWriter().print(jsonb.toJson(item));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean checkContentType(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            try {
                resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        } else {
            return true;
        }
    }
}
