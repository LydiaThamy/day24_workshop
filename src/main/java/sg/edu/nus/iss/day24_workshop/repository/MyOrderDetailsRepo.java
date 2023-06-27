package sg.edu.nus.iss.day24_workshop.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day24_workshop.exception.CreateOrderDetailsException;
import sg.edu.nus.iss.day24_workshop.model.MyOrderDetails;

@Repository
public class MyOrderDetailsRepo {

    @Autowired
    private JdbcTemplate template;

    private final String createOrderDetailsSql = "insert into my_order_details values (null, ?, ?, ?, ?, ?)";

    public List<MyOrderDetails> createOrderDetails(List<MyOrderDetails> orderDetailsList, Integer orderId) {

        List<MyOrderDetails> updatedList = new ArrayList<>();

        for (MyOrderDetails orderDetails : orderDetailsList) {

            KeyHolder generatedKey = new GeneratedKeyHolder();

            PreparedStatementCreator psc = new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(createOrderDetailsSql, new String[] { "id" });
                    ps.setInt(1, orderId);
                    ps.setString(2, orderDetails.getProduct());
                    ps.setDouble(3, orderDetails.getUnitPrice());

                    if (orderDetails.getDiscount() == null) {
                        ps.setDouble(4, .00);
                    } else {
                        ps.setDouble(4, orderDetails.getDiscount());
                    }

                    ps.setInt(5, orderDetails.getQuantity());
                    return ps;
                }
            };

            int rowsChanged = template.update(psc, generatedKey);
            // int rowsChanged = template.update(createOrderDetailsSql,
            // orderDetails.getProduct(), orderDetails.getUnitPrice(),
            // orderDetails.getDiscount(), orderDetails.getQuantity());

            if (rowsChanged == 0)
                throw new CreateOrderDetailsException("Order details could not be created");

            // set generated values into object
            orderDetails.setId(generatedKey.getKey().intValue());
            orderDetails.setOrderId(orderId);

            updatedList.add(orderDetails);
        }

        return updatedList;
    }
}
