package com.fyy.ssm.service;

import com.fyy.ssm.bean.Employee;
import com.fyy.ssm.bean.EmployeeExample;
import com.fyy.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fyy on 6/30/17.
 */

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 查询所有员工
     *
     * @return
     */
    public List<Employee> getAll() {

        return employeeMapper.selectByExampleWithDept(null);
    }

    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 检验用户名是否可用（是否重复）
     * @param empName
     * @return
     */
    public boolean checkUser(String empName) {
        EmployeeExample employee = new EmployeeExample();
        EmployeeExample.Criteria criteria = employee.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(employee);
        return count == 0;

    }
}
