package com.fyy.ssm.controller;

import com.fyy.ssm.bean.Employee;
import com.fyy.ssm.bean.Msg;
import com.fyy.ssm.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
     * JSR303校验需要Hibernate-validator
     *
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmps(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorField", map);
        } else {
            service.saveEmp(employee);
            return Msg.success();
        }
    }


    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(@RequestParam(value = "empName") String empName) {

        //判断用户名是否合法表达式，也可以前端校验
        String regName = "(^[a-zA-Z0-9_-]{6,16})|(^[\u2E80-\u9FFF]{2,5})";
        if (!empName.matches(regName)) {
            return Msg.fail().add("va_msg", "用户名为2-5位中文或6-16位英文");
        }
        boolean b = service.checkUser(empName);
        if (b) {
            return Msg.success();
        }
        return Msg.fail().add("va_msg", "用户名不可用");

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = service.getEmp(id);
        return Msg.success().add("emp", employee);

    }

    /**
     * 保存员工,更新
     * 如果直接发送PUT ajax请求,PUT请求请求体中的数据拿不到，因为Tomcat发现时PUT请求就不封装map数据只有POST才会封装请求体
     */

    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg saveEmp(Employee employee) {
        System.out.println(employee);
        boolean update = service.updateEmp(employee);
        if (update)
            return Msg.success();
        return Msg.fail();
    }


    /**
     * 删除单个或者批量id
     * 多个id-隔开
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmpById(@PathVariable("id") String ids) {
        if (ids.contains("-")) {
            List<Integer> del_ids = new ArrayList<Integer>();
            String[] str_ids = ids.split("-");
            for(String sid : str_ids){
                del_ids.add(Integer.parseInt(sid));
            }
           service.deleteBatch(del_ids);
        } else {
            Integer id = Integer.parseInt(ids);
            service.deleteEmp(id);
        }
        return Msg.success();
    }


}
