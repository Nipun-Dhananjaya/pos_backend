package lk.ijse.pos_backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private String customerId;
    private String customerName;
    private String nic;
    private int contact;
}
