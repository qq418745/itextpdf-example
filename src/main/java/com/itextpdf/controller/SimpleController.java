package com.itextpdf.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/simple")
@Slf4j
public class SimpleController {


    /**
     * 同步的 call
     *
     * @param message
     * @return
     */
    @GetMapping(value = "/call")
    public String call(@RequestParam(value = "message", defaultValue = "说个笑话") String message) {
        return  null;
    }





}