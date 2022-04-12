<%--
  Created by IntelliJ IDEA.
  User: MinhTuan
  Date: 12/01/2022
  Time: 8:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="customerListURL" value="/admin/customer-list"></c:url>
<html>
<head>
    <title>Customer List</title>
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
                <li class="active">Danh sách Khách Hàng</li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <div class="row">
                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Tìm khách hàng </h4>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <form:form commandName="modelSearch" action="${customerListURL}" id="listForm" method="get">
                            <div class="row">
                                <div class="form-group">
                                    <div class="col-xs-12 ">
                                        <!-- PAGE CONTENT BEGIN -->
                                        <div class="col-md-4">
                                            <label><b>Tên Khách Hàng</b></label>
                                            <form:input path="fullName" cssClass="form-control"/>
                                        </div>
                                        <div class="col-md-4">
                                            <label><b>Di Động</b></label>
                                            <form:input path="phone" cssClass="form-control"/>
                                        </div>
                                        <div class="col-md-4">
                                            <label><b>Email</b></label>
                                            <form:input path="email" cssClass="form-control"/>
                                        </div>
                                        <!-- PAGE CONTENT ENDS -->
                                    </div><!-- /.col -->
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 ">
                                    <div class="col-md-4">
                                        <label><b>Chọn Nhân Viên Phụ Trách</b></label>
                                        <form:select path="staffId" class="form-control">
                                            <option selected value="">Chọn nhân viên phụ trách</option>
                                            <c:forEach var="item" items="${modelStaff}">
                                                <form:option value="${item.id}">${item.fullName}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <!-- PAGE CONTENT ENDS -->
                                </div><!-- /.col -->
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-md-3">
                                    <button class="btn btn-primary" id="btnSearch">Tìm Kiếm</button>
                                </div>
                            </div>
                        </div>
                        </form:form>
                    </div>
                </div><!-- /.row -->

            </div>


        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="pull-right">
                    <button id="xoaCustomer" class="btn btn-white btn-warning btn-bold" data-toggle="tooltip,modal"
                            title="Xoá Khách Hàng">
                        <i class="fa fa-trash-o" aria-hidden="true"></i>
                    </button>
                </div>

                <div class="pull-right">
                    <button class="btn btn-white btn-info btn-bold " data-toggle="tooltip"
                            title="Thêm Khách Hàng"
                            onclick="window.location.href='<c:url value="/admin/customer-edit"/>'">
                        <i class="fa fa-plus-circle" aria-hidden="true"></i>
                    </button>
                </div>

            </div>
        </div>
        <br/>
        <!-- Modal Xac Nhan xoa-->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Xác nhận xoá</h4>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có muốn xoá khách hàng đã chọn ????</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" id="btnXoa" class="btn btn-default">Xoá</button>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="col-md-12">
                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" id="selectAll" class="ace"/>
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>Tên</th>
                            <th>Nhân Viên Quản Lí</th>
                            <th>Di Động</th>
                            <th>Email</th>
                            <th>Người Nhập</th>
                            <th>Ngày Nhập</th>
                            <th>Thao Tác</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="item" items="${customers}">
                            <tr>
                                <td class="center">
                                    <label class="pos-rel">
                                        <input type="checkbox" class="ace" name="checkCustomers[]" value="${item.id}">
                                        <span class="lbl"></span>
                                    </label>
                                </td>
                                <td>${item.fullName}</td>
                                <td>${item.staffNames}</td>
                                <td>${item.phone}</td>
                                <td>${item.email}</td>
                                <td>${item.createdBy}</td>
                                <td>${item.createDateStr}</td>
                                <td>
                                    <button class="btn btn-xs btn-info" data-toggle="tooltip"
                                            title="Giao Khách Hàng cho nhân viên quản lí" value="${item.id}"
                                            onclick="assignment(value)">
                                        <i class="fa fa-fire" aria-hidden="true"></i>
                                    </button>
                                    <button class="btn btn-xs btn-dark" data-toggle="tooltip"
                                            title="Sửa thông tin Khách Hàng" value="${item.id}"
                                            onclick="editCustomer(value)">
                                        <i class="fa fa-edit" aria-hidden="true"></i>
                                    </button>
                                    <button class="btn btn-xs btn-danger" data-toggle="tooltip"
                                            title="Xoá Khách Hàng" value="${item.id}"
                                            onclick="deleteOneCustomer(value)">
                                        <i class="fa fa-remove" aria-hidden="true"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div><!-- /.span -->


        </div>
    </div><!-- /.page-content -->
</div>
</div><!-- /.main-content -->
<!-- Modal -->
<div id="assignmentCustomerModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Danh Sách Nhân Viên</h4>
            </div>
            <div class="modal-body">
                <form>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" id="selectAll2" class="ace"/>
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>Full Name</th>
                        </tr>
                        </thead>
                        <tbody id="dsnv">

                        </tbody>
                    </table>
                </form>
            </div>
            <div class="modal-footer">'
                <button type="button" id="assignment" class="btn btn-default" data-dismiss="modal">Giao Khách Hàng
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
<script>
    let cusAssId;

    function assignment(value) {
        cusAssId = value;
        $.ajax({
            type: "get",
            url: "<c:url value='/api/customer/'/>" + value + '/staff',
            dataType: "json",
            success: function (response) {
                let arrBuilding = response;
                $("#dsnv").empty()
                arrBuilding.forEach(item => {
                    let ttd = '<label class="pos-rel">'
                        + '<input type="checkbox"' + item.checked + ' class="ace" name="checkStaffs[]" value="' + item.id + '">'
                        + '  <span class="lbl"></span>'
                        + '</label>'
                    let str = "<tr> <td class='center'>" + ttd + "</td> <td>" + item.fullName + "</td> </tr> "
                    $("#dsnv").append(str)
                })
                console.log(response)
            },
            error: function (res) {
                alert("fail")
            }
        });
        openModalAssignment();
    }

    function openModalAssignment() {
        $('#assignmentCustomerModal').modal();
    }

    $("#selectAll").click(function () {
        $("input[name='checkCustomers[]']").prop('checked', $(this).prop('checked'));

    });
    $("#selectAll2").click(function () {
        $("input[name='checkStaffs[]']").prop('checked', $(this).prop('checked'));

    });
    $("#assignment").click(function (e) {
        e.preventDefault();
        let values = [];
        $.each($("input[name='checkStaffs[]']:checked"), function () {
            values.push($(this).val());
        });
        let data = {
            "staffIds": values
        }
        $.ajax({
            type: "post",
            url: '<c:url value="/api/customer/"/>' + cusAssId + '/assignment',
            data: JSON.stringify(values),
            dataType: "json",//kieu du lieu tu server tra ve client
            contentType: "application/json",//kieu du lieu tu client gui ve server
            success: function (response) {
                console.log("sucess");
                window.location.reload();
            },
            error: function (response) {
                alert("fail")
                console.log(response)
            }
        });
    })

    function editCustomer(value) {
        window.location.href = "<c:url value="/admin/customer-edit"/>" + "?id=" + value;
    }

    let idOne;

    function deleteOneCustomer(value) {
        idOne = value;
        $("#myModal").modal();
    }

    $("#xoaCustomer").click(function (e) {
        e.preventDefault();
        $("#myModal").modal();
    })

    $("#btnXoa").click(function (e) {
        e.preventDefault();
        let values = [];
        if (idOne != null)
            values.push(idOne);
        $.each($("input[name='checkCustomers[]']:checked"), function () {
            values.push($(this).val());
        });
        $.ajax({
            type: "DELETE",
            url: '<c:url value="/api/customer"/>',
            data: JSON.stringify(values),
            dataType: "json",//kieu du lieu tu server tra ve client
            contentType: "application/json",//kieu du lieu tu client gui ve server
            success: function (response) {
                window.location.reload();
            },
            error: function (response) {
                alert("fail")
                console.log(response)
            }
        });
    })
</script>

</body>
</html>
