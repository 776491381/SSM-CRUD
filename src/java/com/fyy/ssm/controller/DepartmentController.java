package com.fyy.ssm.controller;

import com.fyy.ssm.bean.Department;
import com.fyy.ssm.bean.Msg;
import com.fyy.ssm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Department
 * Created by fyy on 7/3/17.
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        List<Department> list = departmentService.getDepts();

        return Msg.success().add("depts", list);
    }


}
