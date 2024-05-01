package com.webshop.shop.controllers.api;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JavaScriptControllerRest {
    @GetMapping("/static/js/bundle.js")
    public ResponseEntity<Resource> getJavaScript() {
        Resource resource = new ClassPathResource("static/js/bundle.js");

        // Check if the resource exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/javascript"))
                .body(resource);
    }

    @GetMapping("/static/css/main.css")
    public ResponseEntity<Resource> getCSS() {
        Resource resource = new ClassPathResource("static/css/main.css");

        // Check if the resource exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/css"))
                .body(resource);
    }
}
