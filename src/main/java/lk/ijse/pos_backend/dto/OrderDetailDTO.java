package lk.ijse.pos_backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private String date;
    private String customerId;
    private String orderId;
    private String items;
    private double total;
}
