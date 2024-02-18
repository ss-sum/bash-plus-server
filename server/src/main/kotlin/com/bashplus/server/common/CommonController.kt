package com.bashplus.server.common

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CommonController {
    @GetMapping("/")
    fun healthCheckController(): ResponseEntity<Void> {
        return ResponseEntity.ok().build();
    }
}