package com.base.message.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/message")
@RestController
public class MessageController {

    @RequestMapping(value = "notify", produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    public String messageNotify(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        String msg = "data:11111";
        return "data:Testing 1,2,3\n\n";
    }

}
