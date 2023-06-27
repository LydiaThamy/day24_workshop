package sg.edu.nus.iss.day24_workshop.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrderDetails {
    
    private Integer id;
    private Integer orderId;

    @NotBlank(message = "Fill in the product")
    private String product;

    @NotNull(message = "Fill in the unit price")
    @Positive(message = "The unit price must be at least $0.01")
    @Digits(integer = 1, fraction = 2, message = "The unit price must be less than $10")
    private Double unitPrice;

    @Digits(integer = 0, fraction = 2, message = "The discount must be less than 1")
    private Double discount;

    @NotNull(message = "Fill in the quantity")
    @Min(value = 1, message = "The quantity must be at least 1")
    private Integer quantity;
}