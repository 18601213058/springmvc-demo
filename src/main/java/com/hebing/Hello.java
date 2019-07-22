package com.hebing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hebing on 2019/7/19.
 */
@Controller
public class Hello {
    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
