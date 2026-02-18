package com.e_commerce.dto;

import com.e_commerce.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
}
