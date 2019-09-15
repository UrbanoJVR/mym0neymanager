package com.urbanojvr.mym0neymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping(value = "/")
    @ResponseBody
    public String sayHello() {
        return "Hello";
    }

}
