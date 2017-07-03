package com.fyy.ssm.controller;

import com.fyy.ssm.bean.Employee;
import com.fyy.ssm.bean.Msg;
import com.fyy.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * employeeController
 * Created by fyy on 6/30/17.
 */
@Controller
public class EmployeeController {


    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    //    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {

        //分页查询
        //每页5条数据
        //startpage紧跟的查询就会是一个分页查询
        PageHelper.startPage(page, 5);
        List<Employee> emps = service.getAll();
        //封装了详细的分页信息，包括了查询出来的数据
        //连续显示5页
        PageInfo pageInfo = new PageInfo(emps, 5);
        model.addAttribute(pageInfo);
        return "list";
    }

    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "page", defaultValue = "1") Integer page) {

        //分页查询
        //每页5条数据
        //startpage紧跟的查询就会是一个分页查询
        PageHelper.startPage(page, 5);
        List<Employee> emps = service.getAll();
        //封装了详细的分页信息，包括了查询出来的数据
        //连续显示5页
        PageInfo pageInfo = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    /**
     * 员工保存
     *
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmps(Employee employee) {
        service.saveEmp(employee);
        return Msg.success();

    }

}
