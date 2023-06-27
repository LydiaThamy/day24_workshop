package sg.edu.nus.iss.day24_workshop.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day24_workshop.exception.CreateOrderException;
import sg.edu.nus.iss.day24_workshop.model.MyOrder;


@Repository
public class MyOrderRepo {
    
    @Autowired
    private JdbcTemplate template;

    private final String createOrderSql = "insert into my_orders values (null, ?, ?, ?, ?, ?)";

    public MyOrder createOrder(MyOrder order) {
        // int rowsChanged = template.update(createOrderSql, new Date(System.currentTimeMillis()), order.getCustomerName(), order.getShipAddress(), order.getNotes(), order.getTax());

        KeyHolder generatedKey = new GeneratedKeyHolder();
        Date date = new Date(System.currentTimeMillis());
        
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(createOrderSql, new String[] {"id"});
                
                ps.setDate(1, date);
                ps.setString(2, order.getCustomerName());
                ps.setString(3, order.getShipAddress());
                
                if (order.getNotes() == null) {
                    ps.setNull(4, Types.OTHER);
                } else {
                    ps.setString(4, order.getNotes());
                }

                if (order.getTax() == null) {
                    ps.setDouble(5, 0.05);
                } else {
                    ps.setDouble(5, order.getTax());
                }
                
                return ps;
            }
        };
        
        int rowsChanged = template.update(psc, generatedKey);

        if (rowsChanged == 0)
            throw new CreateOrderException("Order could not be created");

        order.setOrderId(generatedKey.getKey().intValue());
        order.setOrderDate(date);

        return order;
    }
}
