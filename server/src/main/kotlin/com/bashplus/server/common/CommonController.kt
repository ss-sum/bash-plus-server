package com.bashplus.server.common

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CommonController {
    @Hidden
    @GetMapping("/")
    fun healthCheckController(): ResponseEntity<Void> {
        return ResponseEntity.ok().build();
    }

}