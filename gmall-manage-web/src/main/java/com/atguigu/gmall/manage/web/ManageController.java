package com.atguigu.gmall.manage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManageController {

    @RequestMapping("index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("attrListPage")
    public String getGridPage(){
        return "gridpage";
    }
}
