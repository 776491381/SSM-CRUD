<%--
  Created by IntelliJ IDEA.
  User: fyy
  Date: 6/30/17
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("PATH", request.getContextPath());
    %>
    <link href="${PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${PATH}/static/js/jquery-1.12.4.min.js"></script>
    <script src="${PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <title>员工列表</title>
</head>
<body>

<%--员工添加模态框--%>
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">添加员工信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="empName" id="empName_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_input" class="col-sm-2 control-label">email</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="email" id="email_input"
                                   placeholder="email@qq.com">
                            <span class="help-block"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_add_select">

                            </select>
                        </div>
                    </div>


                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>


<%--搭建显示页面--%>
<div class="container">
    <%--标题--%>
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <%--按钮--%>
    <div class="row">
        <div class="col-md-2 col-md-offset-10">
            <button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>

    </div>
    <%--表格--%>
    <div class="row">

        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>


            </table>
        </div>

    </div>
    <%--分页信息--%>
    <div class="row">
        <div class="col-md-6" id="page_info_aera">
        </div>

        <div class="col-md-6" id="page_nav_aera">
        </div>
    </div>
</div>

<script type="text/javascript">
    //页面加载完成后加载ajax请求
    var totalRecord;
    $(function () {
        to_page(1);
    });

    function to_page(page) {
        $.ajax({
            url: "${PATH}/emps",
            data: "page=" + page,
            type: "GET",
            success: function (result) {
                console.log(result);
                //请求成功后解析员工数据
                build_emps_table(result);
                //然后解析显示分页信息
                build_page_info(result);
                build_page_nav(result);
            }

        });
    }

    function build_emps_table(result) {
        //清空表格
        $("#emps_table tbody").empty();
        var emps = result.extend.pageInfo.list;
        $.each(emps, function (index, item) {
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var gender = item.gender === "M" ? "男" : "女";
            var genderTd = $("<td></td>").append(gender);
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
            //将俩个按钮追加到一个单元格
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);


            /**
             *
             <button class="btn btn-primary btn-sm">
             <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
             编辑
             </button>
             */
            $("<tr></tr>").append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emps_table tbody");


        });
    }

    //分页信息
    function build_page_info(result) {
        $("#page_info_aera").empty();
        $("#page_info_aera").append("当前第" + result.extend.pageInfo.pageNum + "页，总共" + result.extend.pageInfo.pages + "页，总共" + result.extend.pageInfo.total + "条记录");
        totalRecord = result.extend.pageInfo.total + 5;
    }

    //分页条
    function build_page_nav(result) {
        $("#page_nav_aera").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));

        if (result.extend.pageInfo.hasPreviousPage === false) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        } else {
            firstPageLi.click(function () {
                to_page(1);
            });

            prePageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum - 1);
            });
        }

        if (result.extend.pageInfo.hasNextPage === false) {
            lastPageLi.addClass("disabled");
            nextPageLi.addClass("disabled");
        } else {

            lastPageLi.click(function () {
                to_page(result.extend.pageInfo.pages);
            });

            nextPageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum + 1);
            });
        }

        ul.append(firstPageLi).append(prePageLi);
        $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {
            var numLi;
            if (result.extend.pageInfo.pageNum === item) {
                numLi = $("<li></li>").append($("<a></a>").append(item)).addClass("active");
            } else {
                numLi = $("<li></li>").append($("<a></a>").append(item));
            }

            numLi.click(function () {
                to_page(item);
            });

            ul.append(numLi);
        });

        ul.append(nextPageLi).append(lastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_aera");
    }


    function reset_form(ele) {
        $(ele)[0].reset();
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");
    }


    //点击新增按钮弹出模态框
    $("#emp_add_modal_btn").click(function () {
        //清楚模态框表单数据和样式
        //$("#empAddModal form")[0].reset();
        reset_form("#empAddModal form");


        //发送ajax请求查出部门信息，显示在下拉列表中
        getDepts();
        $("#empAddModal").modal({
            backdrop: 'static'
        });
    });

    //查出部门信息
    function getDepts() {
        $("#dept_add_select").empty();
        $.ajax({
            url: "${PATH}/depts",
            type: "GET",
            success: function (result) {
//                console.log(result);
                $.each(result.extend.depts, function () {

                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo("#dept_add_select");
                });
            }
        })
    }

    function validata_add_form() {
        //拿到校验数据
        var empName = $("#empName_input").val();
        var regName = /(^[a-zA-Z0-9_-]{6,16})|(^[\u2E80-\u9FFF]{2,5})/;

        if (!regName.test(empName)) {
            show_validata_msg("#empName_input", "error", "用户名为2-5位中文或6-16位英文");
            return false;
        } else {
            show_validata_msg("#empName_input", "success", "");
        }

        //校验邮箱
        var email = $("#email_input").val();
        var regEmail = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;

        if (!regEmail.test(email)) {
            show_validata_msg("#email_input", "error", "邮箱错误");
            return false
        } else {
            show_validata_msg("#email_input", "success", "");
        }

        return true;
    }


    $("#empName_input").change(function () {
        $.ajax({
            url: "${PATH}/checkuser",
            type: "GET",
            data: "empName=" + this.value,
            success: function (result) {
                if (result.code === 100) {
                    show_validata_msg("#empName_input", "success", "用户名可用");
                    $("#emp_save_btn").attr("ajax_va", "success");
                } else {
                    show_validata_msg("#empName_input", "fail", result.extend.va_msg);
                    $("#emp_save_btn").attr("ajax_va", "error");
                }

            }
        });
    });


    function show_validata_msg(ele, status, msg) {

        //清除状态
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");

        if (status === "success") {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        } else {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }

    }

    //点击保存
    $("#emp_save_btn").click(function () {
        //表单数据提交
        //对数据校验

        if ($(this).attr("ajax_va") !== "success") {
            return;
        }
        if (validata_add_form()) {
            $.ajax({
                url: "${PATH}/emp",
                type: "POST",
                data: $("#empAddModal form").serialize(),
                success: function (result) {
                    if (result.code === 100) {
                        $("#empAddModal").modal('hide');
                        to_page(totalRecord);
                    }

                }
            })
        }
    })

</script>

</body>
</html>
