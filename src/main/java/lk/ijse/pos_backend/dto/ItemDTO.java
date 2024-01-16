package lk.ijse.pos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO implements Serializable {
    private String itemId;
    private String name;
    private double price;
    private double qtv;
}
