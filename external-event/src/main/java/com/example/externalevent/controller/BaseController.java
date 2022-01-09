package com.example.externalevent.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @author Khan Hafizur Rahman
 * @since 9/1/22
 */
@RequestMapping(value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class BaseController {
}
