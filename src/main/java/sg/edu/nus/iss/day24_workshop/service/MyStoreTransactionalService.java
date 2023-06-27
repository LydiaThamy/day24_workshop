package sg.edu.nus.iss.day24_workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.day24_workshop.model.MyOrder;
import sg.edu.nus.iss.day24_workshop.model.MyOrderDetails;
import sg.edu.nus.iss.day24_workshop.repository.MyOrderDetailsRepo;
import sg.edu.nus.iss.day24_workshop.repository.MyOrderRepo;

@Service
public class MyStoreTransactionalService {
    
    @Autowired
    private MyOrderRepo orderRepo;

    @Autowired
    private MyOrderDetailsRepo orderDetailsRepo;

    @Transactional
    public Object[] createOrderWithDetails(MyOrder order, List<MyOrderDetails> orderDetails) {
        MyOrder updatedOrder = orderRepo.createOrder(order);
        List<MyOrderDetails> updatedOrderDetails = orderDetailsRepo.createOrderDetails(orderDetails, updatedOrder.getOrderId());
        return new Object[]{updatedOrder, updatedOrderDetails};
    }
}
