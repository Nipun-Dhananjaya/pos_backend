package lk.ijse.pos_backend.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDTO implements Serializable {
    private String itemId;
    private String itemName;
    private double price;
    private double qtv;
}
