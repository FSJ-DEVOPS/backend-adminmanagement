package com.kce.admin.feign;



import com.kce.admin.dto.SystemLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "systemlog", url = "http://https://backend-systemlogs.onrender.com/api/adminlogs")
public interface SystemLogClient {

    @PostMapping("/logs")
    void createLog(SystemLogDTO systemLogDTO);
}
