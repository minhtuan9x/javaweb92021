<%--
  Created by IntelliJ IDEA.
  User: MinhTuan
  Date: 12/01/2022
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Detail Customer</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="<c:url value="/admin/home"/> ">Trang chủ</a>
                </li>
                <li class="active">Chi tiết Khách Hàng</li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form:form commandName="customer" cssClass="form-horizontal" role="form" id="formEdit">
                        <div class="form-group">
                            <label class="col-sm-3">
                                Tên Đầy Đủ</label>
                            <div class="col-sm-9">
                                <form:input path="fullName" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">
                                Số Điện Thoại</label>
                            <div class="col-sm-9">
                                <form:input path="phone" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3">
                                Email</label>
                            <div class="col-sm-9">
                                <form:input path="email" cssClass="form-control"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right">
                            </label>
                            <div class="col-sm-9">
                                <c:if test="${customer.id==null}">
                                    <button type="button" class="btn btn-primary" id="btnAddBuilding">Thêm Khách Hàng
                                    </button>
                                </c:if>
                                <c:if test="${customer.id!=null}">
                                    <button type="button" class="btn btn-primary" id="btnAddBuilding">Update Khách Hàng
                                    </button>
                                </c:if>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12">
                    <c:forEach var="item" items="${customer.transactionResponseList}">
                        <div class="row">
                            <div class="row">
                                <div class="col-md-2">
                                    <h5 style="color: darkred">${item.typeValue}</h5>
                                </div>
                                <div class="col-md-10">
                                    <button class="btn btn-white btn-info btn-bold " data-toggle="tooltip"
                                            title="Thêm Giao Dịch"
                                            value="${item.code}" onclick="saveTran(value)">
                                        <i class="fa fa-plus-circle" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Ngày Tạo</th>
                                            <th>Ghi Chú</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="item2" items="${item.transaction}">
                                            <tr>
                                                <td>${item2.createDateStr}</td>
                                                <td>${item2.note}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td></td>
                                            <td><input type="text" id="node${item.code}" class="form-control"></td>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->
<script>
    $("#btnAddBuilding").click(function (e){
        e.preventDefault()
        save();
    })
    function saveTran(value){
        let data={
            "code":value,
            "note":$("#node"+value).val(),
            "customerId":"${customer.id}"
        }
        $.ajax({
            url:"<c:url value="/api/transaction"/> ",
            type: "post",
            data:JSON.stringify(data),
            datatype:"json",
            contentType: "application/json",
            success:function (){
                window.location.reload()
            },
            error:function (){
                alert("error")
            }
        })
    }
    function save(){
        let data = {}
        let formData = $('#formEdit').serializeArray();
        let id = ${customer.id}+'';
        if (id != '') {
            data["id"] = id;
        }
        formData.forEach(item => {
            data[item.name] = item.value
        })
        $.ajax({
            type: "post",
            url: '<c:url value="/api/customer"/>',
            data: JSON.stringify(data),
            dataType: "json",//kieu du lieu tu server tra ve client
            contentType: "application/json",//kieu du lieu tu client gui ve server
            success: function (response) {
                window.location.href = '<c:url value="/admin/customer-list" />'
            },
            error: function (response) {
                alert("fail")
                console.log(response)
            }
        });
    }
</script>
</body>
</html>
