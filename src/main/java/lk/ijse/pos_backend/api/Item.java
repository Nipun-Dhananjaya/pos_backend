package lk.ijse.pos_backend.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.pos_backend.db.DBProcess;
import lk.ijse.pos_backend.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "item", value = "/item", loadOnStartup = 3)
public class Item extends HttpServlet {
    Connection connection;
    DBProcess db = new DBProcess();
    public static final Logger logger= LoggerFactory.getLogger(Item.class);

    @Override
    public void init() throws ServletException {
        logger.info("Init the item servlet");
        try {
            InitialContext initialContext = new InitialContext();
            DataSource pool=(DataSource) initialContext.lookup("java:comp/env/jdbc/");
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
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        } else {
            try {
                Jsonb jsonb = JsonbBuilder.create();
                List<ItemDTO> items = jsonb.fromJson(req.getReader(),
                        new ArrayList<ItemDTO>() {
                        }.getClass().getGenericSuperclass());
                items.forEach(System.out::println);
                db.saveItem(items, connection);

                jsonb.toJson(items,resp.getWriter());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            if (id == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            db.getItem(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            if (id == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            db.deleteItem(id, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO item = jsonb.fromJson(req.getReader(),ItemDTO.class);
            db.updateItem(item,connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
