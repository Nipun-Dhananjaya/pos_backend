package lk.ijse.pos_backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemQtyDTO {
    private String itemId;
    private double qty;
}
