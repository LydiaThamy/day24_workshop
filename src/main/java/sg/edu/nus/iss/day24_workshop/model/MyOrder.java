package sg.edu.nus.iss.day24_workshop.model;

import java.sql.Date;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrder {
    
    private Integer orderId;
    private Date orderDate;
    
    @NotBlank(message = "Fill in the customer name")
    private String customerName;

    @NotBlank(message = "Fill in the shipping address")
    private String shipAddress;
    
    private String notes;

    @PositiveOrZero
    @Digits(integer = 0, fraction = 2, message = "The tax must be less than 1")
    private Double tax;
}
