package com.fyy.ssm.dao;

import com.fyy.ssm.bean.Employee;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * Created by fyy on 6/30/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-service.xml"})
public class MapperTest {

    /**
     * 测试department
     */


    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSession sqlSession;

    @Test()
    public void testCRUD() {

//        System.out.println(departmentMapper);
        //插入部门
//        departmentMapper.insertSelective(new Department(null,"开发部门"));
//        departmentMapper.insertSelective(new Department(null,"测试部门"));

        //生成员工数据
//        employeeMapper.insertSelective(new Employee(null, "jerry", "M", "776491381@qq.com", 419));

        //批量插入
//        for(){
//            employeeMapper.insertSelective(new Employee(null, "jerry", "M", "776491381@qq.com", 419));
//        }
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().substring(0, 5) + "" + i;
            mapper.insertSelective(new Employee(null, uuid, "M", uuid+"@qq.com", 419));
        }


    }

}