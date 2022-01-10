package com.example.paymentcore.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @author Md. Shahinur Rahman
 * @since 9/28/21
 */
@RequestMapping(value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class BaseController {
}