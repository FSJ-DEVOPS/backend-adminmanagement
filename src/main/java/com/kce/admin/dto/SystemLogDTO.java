package com.kce.admin.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemLogDTO {
    private String type;
    private String message;
    private String role;
    private String roleId;
    private LocalDateTime timestamp = LocalDateTime.now();
}
