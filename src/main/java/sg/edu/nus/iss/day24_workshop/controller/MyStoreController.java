package sg.edu.nus.iss.day24_workshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.day24_workshop.model.MyOrder;
import sg.edu.nus.iss.day24_workshop.model.MyOrderDetails;
import sg.edu.nus.iss.day24_workshop.service.MyStoreTransactionalService;

@Controller
@RequestMapping("/api/my-store")
public class MyStoreController {

    @Autowired
    private MyStoreTransactionalService transService;

    @GetMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String inputOrder(Model m, HttpSession session) {
        if (session.getAttribute("timeout") != null)
            m.addAttribute("timeout", session.getAttribute("timeout"));
        m.addAttribute("myOrder", new MyOrder());
        return "order-form";
    }

    @PostMapping(path = "/order-details", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String inputOrderDetails(@ModelAttribute @Valid MyOrder myOrder,
            BindingResult result, Model m, HttpSession session) {

        if (result.hasErrors()) {
            return "order-form";
        }
        
        MyOrder mySessionOrder = (MyOrder) session.getAttribute("myOrder");
        
        if (myOrder != null) {
            session.setAttribute("myOrder", myOrder);

        } else if (mySessionOrder == null) {
            session.setAttribute("timeout", "Session timed out");
            return "redirect:/api/my-store";
        }

        m.addAttribute("myOrderDetails", new MyOrderDetails());
        return "order-details-form";
    }

    @PostMapping(path = "/add-order-details")
    public String addOrderDetails(@ModelAttribute @Valid MyOrderDetails myOrderDetails, BindingResult result, Model m,
            HttpSession session) {

        if (result.hasErrors())
            return "order-details-form";

            MyOrder myOrder = (MyOrder) session.getAttribute("myOrder");
        if (myOrder == null) {
            session.setAttribute("timeout", "Session timed out");
            return "redirect:/api/my-store";
        }

        List<MyOrderDetails> myOrderDetailsList;

        if (session.getAttribute("myOrderDetailsList") != null) {
            myOrderDetailsList = (List<MyOrderDetails>) session.getAttribute("myOrderDetailsList");
        } else {
            myOrderDetailsList = new ArrayList<>();
        }

        myOrderDetailsList.add(myOrderDetails);
        session.setAttribute("myOrderDetailsList", myOrderDetailsList);

        // return "redirect:/api/my-store/order-details";

        m.addAttribute("myOrderDetails", new MyOrderDetails());
        return "order-details-form";
    }

    @PostMapping(path = "/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitOrder(@ModelAttribute @Valid MyOrderDetails myOrderDetails,
            BindingResult result, Model m, HttpSession session) {

        if (result.hasErrors()) {
            return "order-details-form";
        }

        MyOrder myOrder = (MyOrder) session.getAttribute("myOrder");
        if (myOrder == null) {
            session.setAttribute("timeout", "Session timed out");
            return "redirect:/api/my-store";
        }

        List<MyOrderDetails> myOrderDetailsList;

        if (session.getAttribute("myOrderDetailsList") != null) {
            myOrderDetailsList = (List<MyOrderDetails>) session.getAttribute("myOrderDetailsList");
        } else {
            myOrderDetailsList = new ArrayList<>();
        }

        myOrderDetailsList.add(myOrderDetails);

        Object[] updatedObjects = transService.createOrderWithDetails(myOrder,
                myOrderDetailsList);

        MyOrder updatedOrder = (MyOrder) updatedObjects[0];
        List<MyOrderDetails> updatedOrderDetails = (List<MyOrderDetails>) updatedObjects[1];

        m.addAttribute("myOrder", updatedOrder);
        m.addAttribute("myOrderDetailsList", updatedOrderDetails);

        session.invalidate();

        return "submitted-form";
        // return "redirect:/api/my-store/order" -- to ask it to go to submitOrder()
    }

}
