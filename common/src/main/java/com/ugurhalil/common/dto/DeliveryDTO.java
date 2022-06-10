package com.ugurhalil.common.dto;

import com.ugurhalil.common.constant.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long id;
    private UserDTO userDTO;
    private DeliveryStatus status;
}
