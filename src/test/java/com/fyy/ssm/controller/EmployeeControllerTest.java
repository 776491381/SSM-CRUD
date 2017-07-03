package com.fyy.ssm.controller;

import com.fyy.ssm.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Spring4 测试需要servlet3.0以上
 * Created by fyy on 6/30/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //使容器倴城能够autowired
@ContextConfiguration({"classpath:spring-service.xml", "classpath:/spring-mvc.xml"})
public class EmployeeControllerTest {

    //传入springmvc的IOC
    @Autowired
    WebApplicationContext context;
    //虚拟的mvc请求
    MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getEmps() throws Exception {
        //模拟请求并拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("page", "9"))
                .andReturn();

        //请求成功之后，请求域中会有pageInfo

        MockHttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页码 ： " + pageInfo.getPageNum());
        System.out.println("总页码 ： " + pageInfo.getPages());
        System.out.println("总记录数 ： " + pageInfo.getTotal());
        System.out.println("连续显示页码数量 ： " );
        int[] num = pageInfo.getNavigatepageNums();
        for(int i : num){
            System.out.print(i+"  ");
        }
        System.out.println();
        List<Employee> list = pageInfo.getList();
        for (Employee e : list) {
            System.out.println("ID : " + e.getEmpId() + "  姓名 : " + e.getEmpName());
        }

    }

}