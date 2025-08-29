package com.kce.admin.feign;



import com.kce.admin.dto.SystemLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "systemlogs")
public interface SystemLogClient {

    @PostMapping("/api/adminlogs/logs")
    void createLog(SystemLogDTO systemLogDTO);
}
