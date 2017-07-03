package com.fyy.ssm.service;

import com.fyy.ssm.bean.Employee;
import com.fyy.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by fyy on 6/30/17.
 */

@Service
public class EmployeeService {

    @Autowired
    private  EmployeeMapper employeeMapper;


    /**
     * 查询所有员工
     *
     * @return
     */
    public List<Employee> getAll() {

        return employeeMapper.selectByExampleWithDept(null);
    }

    public void saveEmp(Employee employee){
        employeeMapper.insertSelective(employee);
    }

}
