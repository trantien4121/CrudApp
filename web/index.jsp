<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: tien.tran2
  Date: 4/28/2023
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Book CRUD App</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <style>
    body {
      color: #566787;
      background: #f5f5f5;
      font-family: 'Varela Round', sans-serif;
      font-size: 13px;
    }
    .search {
      width: 100%;
      position: relative;
      display: flex;
    }

    .searchTerm {
      width: 100%;
      border: 2px solid #00B4CC;
      border-right: none;
      padding: 15px;
      height: 20px;
      outline: none;
      color: gray;
    }

    .searchTerm:focus{
      color: gray;
    }

    .searchButton {
      width: 40px;
      height: 34px;
      border: 1px solid #00B4CC;
      background: #00B4CC;
      text-align: center;
      color: #fff;
      cursor: pointer;
      font-size: 20px;
    }

    /*Resize the wrap to see the search bar change!*/
    .wrap{
      margin-left: 14px;
      width: 30%;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
    .filter-box{
      margin-left: 25px;
      width: 15%;
      position: absolute;
      top: 50%;
      left: 23%;
      transform: translate(-50%, -50%);
    }
    .filter-box>select{
      padding: 6px 0 6px 10px;
      border: 2px solid #00B4CC;
      color: gray;
    }
    .filter-box>select:focus{
      outline: none;
    }
    .table-responsive {
      margin: 100px 0;

    }
    .Filter-title{
      margin-left: 20px;
      width: 10%;
      position: absolute;
      top: 50%;
      left: 10%;
      transform: translate(-50%, -50%);
      font-size: 16px;
    }
    .table-wrapper {
      background: #fff;
      padding: 20px 25px;
      border-radius: 3px;
      min-width: 1000px;
      box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    }

    .table-title {
      padding-bottom: 15px;
      background: #435d7d;
      color: #fff;
      padding: 16px 30px;
      min-width: 100%;
      margin: -20px -25px 10px;
      border-radius: 3px 3px 0 0;
    }

    .table-title h2 {
      margin: 5px 0 0;
      font-size: 24px;
    }

    .table-title .btn-group {
      float: right;
    }

    .table-title .btn {
      color: #fff;
      float: right;
      font-size: 13px;
      border: none;
      min-width: 50px;
      border-radius: 2px;
      border: none;
      outline: none !important;
      margin-left: 10px;
    }

    .table-title .btn i {
      float: left;
      font-size: 21px;
      margin-right: 5px;
    }

    .table-title .btn span {
      float: left;
      margin-top: 2px;
    }

    table.table tr th, table.table tr td {
      border-color: #e9e9e9;
      padding: 12px 15px;
      vertical-align: middle;
    }

    table.table tr th:first-child {
      width: 60px;
    }

    table.table tr th:last-child {
      width: 100px;
    }

    table.table-striped tbody tr:nth-of-type(odd) {
      background-color: #fcfcfc;
    }

    table.table-striped.table-hover tbody tr:hover {
      background: #f5f5f5;
    }

    table.table th i {
      font-size: 13px;
      margin: 0 5px;
      cursor: pointer;
    }

    table.table td:last-child i {
      opacity: 0.9;
      font-size: 22px;
      margin: 0 5px;
    }

    table.table td a {
      font-weight: bold;
      color: #566787;
      display: inline-block;
      text-decoration: none;
      outline: none !important;
    }

    table.table td a:hover {
      color: #2196F3;
    }

    table.table td a.edit {
      color: #FFC107;
    }

    table.table td a.delete {
      color: #F44336;
    }

    table.table td i {
      font-size: 19px;
    }

    table.table .avatar {
      border-radius: 50%;
      vertical-align: middle;
      margin-right: 10px;
    }

    .pagination {
      float: right;
      margin: 0 0 5px;
    }

    .pagination li a {
      border: none;
      font-size: 13px;
      min-width: 30px;
      min-height: 30px;
      color: #999;
      margin: 0 2px;
      line-height: 30px;
      border-radius: 2px !important;
      text-align: center;
      padding: 0 6px;
    }

    .pagination li a:hover {
      color: #666;
    }

    .pagination li.active a, .pagination li.active a.page-link {
      background: #03A9F4;
    }

    .pagination li.active a:hover {
      background: #0397d6;
    }

    .pagination li.disabled a:hover {
      text-decoration: none;
    }
    .pagination li.disabled i {
      color: #ccc;
    }

    .pagination li i {
      font-size: 16px;
      padding-top: 6px
    }

    .hint-text {
      float: left;
      margin-top: 10px;
      font-size: 13px;
    }

    /* Custom checkbox */
    .custom-checkbox {
      position: relative;
    }

    .custom-checkbox input[type="checkbox"] {
      opacity: 0;
      position: absolute;
      margin: 5px 0 0 3px;
      z-index: 9;
    }

    .custom-checkbox label:before {
      width: 18px;
      height: 18px;
    }

    .custom-checkbox label:before {
      content: '';
      margin-right: 10px;
      display: inline-block;
      vertical-align: text-top;
      background: white;
      border: 1px solid #bbb;
      border-radius: 2px;
      box-sizing: border-box;
      z-index: 2;
    }

    .custom-checkbox input[type="checkbox"]:checked + label:after {
      content: '';
      position: absolute;
      left: 6px;
      top: 3px;
      width: 6px;
      height: 11px;
      border: solid #000;
      border-width: 0 3px 3px 0;
      transform: inherit;
      z-index: 3;
      transform: rotateZ(45deg);
    }

    .custom-checkbox input[type="checkbox"]:checked + label:before {
      border-color: #03A9F4;
      background: #03A9F4;
    }

    .custom-checkbox input[type="checkbox"]:checked + label:after {
      border-color: #fff;
    }

    .custom-checkbox input[type="checkbox"]:disabled + label:before {
      color: #b8b8b8;
      cursor: auto;
      box-shadow: none;
      background: #ddd;
    }

    /* Modal styles */
    .modal .modal-dialog {
      max-width: 400px;
    }

    .modal .modal-header, .modal .modal-body, .modal .modal-footer {
      padding: 20px 30px;
    }

    .modal .modal-content {
      border-radius: 3px;
      font-size: 14px;
    }

    .modal .modal-footer {
      background: #ecf0f1;
      border-radius: 0 0 3px 3px;
    }

    .modal .modal-title {
      display: inline-block;
    }

    .modal .form-control {
      border-radius: 2px;
      box-shadow: none;
      border-color: #dddddd;
    }

    .modal textarea.form-control {
      resize: vertical;
    }

    .modal .btn {
      border-radius: 2px;
      min-width: 100px;
    }

    .modal form label {
      font-weight: normal;
    }
    /*Search history*/
    .header__search-history{
      position: absolute;
      top: calc(100% + 3px);
      left: 0;
      width: calc(100% - 37px);
      background-color: #fff;
      border-radius: 0px;
      box-shadow: 0 1px 5px rgb(189, 189, 189);
      display: none;
      overflow: hidden;
      z-index: 1;
      font-family: 'Varela Round', sans-serif;
    }
    .header__search-history-heading{
      margin: 6px 12px;
      font-size: 12px;
      color: #999;
      font-weight: 400;
    }
    .header__search-history-list{
      padding-left: 0;
      list-style: none;
      margin: 6px 0 0;
    }
    .header__search-history-item{
      height: 38px;
      padding: 0 12px;
    }
    .header__search-history-item:hover{
      background-color: #fafafa;
    }
    .header__search-history-item a{
      text-decoration: none;
      font-size: 12px;
      line-height: 38px;
      color: black;
      display: block;
    }
  </style>
  <script>
    $(document).ready(function () {
      // Activate tooltip
      $('[data-toggle="tooltip"]').tooltip();

      // Select/Deselect checkboxes
      var checkbox = $('table tbody input[type="checkbox"]');
      $("#selectAll").click(function () {
        if (this.checked) {
          checkbox.each(function () {
            this.checked = true;
          });
        } else {
          checkbox.each(function () {
            this.checked = false;
          });
        }
      });
      checkbox.click(function () {
        if (!this.checked) {
          $("#selectAll").prop("checked", false);
        }
      });

      //show search history
      $('.searchTerm').focus(function () {
        $('.header__search-history').css('display', 'block');
      }).blur(function () {
        $('.header__search-history').css('display', 'none');
      });

      //click history item and load to search Input
      $('.historyItem').each(function() {
        $(this).on('click', function(e) {
          e.preventDefault();
          $('.searchTerm').val($(this).text());
        });
      });

      // const items = document.querySelectorAll('.historyItem')
      // console.log(items);
      // items.forEach(ele => {
      //   ele.addEventListener("click", function (e) {
      //     e.preventDefault();
      //     document.querySelector('.searchTerm').value = ele.innerText;
      //   })
      // })

    });
  </script>
</head>
<body>
<div class="container-xl">
  <div class="table-responsive">
    <div class="table-wrapper">
      <div class="table-title">
        <div class="row">
          <div class="col-sm-3">
            <h2>CRUD <b>BOOKS</b></h2>
          </div>
          <div class="col-sm-9">
            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i
                    class="material-icons">&#xE147;</i> <span>Add New Book</span></a>
            <a href="#deleteListBookModal" class="btn btn-danger" data-toggle="modal"><i
                    class="material-icons">&#xE15C;</i> <span>Delete</span></a>
            <form action="home?action=SearchAndFilter" method="post">
              <div class="wrap">
                <div class="search">
                  <input type="text" class="searchTerm" name="searchValue" value="${value}" placeholder="What are you looking for?">

                  <button type="submit" class="searchButton" value="search">
                    <i class="fa fa-search"></i>
                  </button>
                </div>
                <!-- Search history -->
                <div class="header__search-history">
                  <h3 class="header__search-history-heading">Lịch sử tìm kiếm</h3>
                  <ul class="header__search-history-list">
                    <c:forEach var="sh" items="${searchHistory}">
                      <li class="header__search-history-item">
                        <a href="" class="historyItem">${sh}</a>
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </div>

              <div class="form-group filter-box">
                <select id="bookTypesFilter" name="BookTypesFilter">
                  <option value="">All</option>
                  <c:forEach var="bT" items="${dsBookType}">

                    <c:choose>
                      <c:when test="${filterValue eq bT.getBookType()}">
                        <option value="${filterValue}" selected>${bT.getBookTypeName()}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${bT.getBookType()}">${bT.getBookTypeName()}</option>
                      </c:otherwise>
                    </c:choose>

                  </c:forEach>

                </select>
              </div>

              <div class="Filter-title">
                <span>Loại sách:</span>
              </div>
            </form>
          </div>
        </div>
      </div>
      <table class="table table-striped table-hover">
        <thead>
        <tr>
          <th>
            <span class="custom-checkbox">
              <input type="checkbox" id="selectAll">
              <label for="selectAll"></label>
            </span>
          </th>
          <th>ID</th>
          <th>Name</th>
          <th>Author</th>
          <th>Quantity</th>
          <th>Price</th>
          <th>Images</th>
          <th>Type</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <form action="CrudBook" method="post">
          <c:forEach var="b" items="${dsBook}">
            <tr>
              <td>
                <span class="custom-checkbox">
                  <input type="checkbox" name="bookItems" id="checkbox${b.getBookId()}" value="${b.getBookId()}">
                  <label for="checkbox${b.getBookId()}"></label>
                </span>
              </td>
              <td>${b.getBookId()}</td>
              <td>${b.getBookName()}</td>
              <td>${b.getAuthor()}</td>
              <td>${b.getQuantity()}</td>
              <td>${b.getPrice()}</td>
              <td>
                <img src="${b.getImage()}" alt="Girl in a jacket" width="40" height="40">
              </td>
              <td>${b.getBookType()}</td>

              <td>
                <!-- Call modal update book-->
                <a href="#editEmployeeModal${b.getBookId()}" class="edit" data-toggle="modal"><i
                        class="material-icons"
                        data-toggle="tooltip"
                        title="Edit">&#xE254;</i></a>
                <!-- Call modal delete 1 Book-->
                <a href="#deleteEmployeeModal${b.getBookId()}" class="delete" data-toggle="modal"><i
                        class="material-icons"
                        data-toggle="tooltip"
                        title="Delete">&#xE872;</i></a>
              </td>

              <!--Modal Delete 1 Book-->
              <div id="deleteEmployeeModal${b.getBookId()}" class="modal fade">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h4 class="modal-title">Delete Book</h4>
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                      </button>
                    </div>
                    <div class="modal-body">
                      <p>Are you sure you want to delete these Book with bookId = ${b.getBookId()}?</p>
                      <p class="text-warning"><small>This action cannot be undone.</small></p>
                    </div>
                    <div class="modal-footer">
                      <input type="button" class="btn btn-default" data-dismiss="modal"
                             value="Cancel">
                      <button type="submit" name="btnActionDelete"
                              class="btn btn-danger" value="${b.getBookId()}">Delete</button>
                    </div>
                  </div>
                </div>
              </div>

              <!--Modal Delete list book-->
              <div id="deleteListBookModal" class="modal fade">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h4 class="modal-title">Delete Book</h4>
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                      </button>
                    </div>
                    <div class="modal-body">
                      <p>Are you sure you want to delete these list Books?</p>
                      <p class="text-warning"><small>This action cannot be undone.</small></p>
                    </div>
                    <div class="modal-footer">
                      <input type="button" class="btn btn-default" data-dismiss="modal"
                             value="Cancel">
                      <button type="submit" name="btnActionDelete"
                              class="btn btn-danger" value="deleteListBook">Delete</button>
                    </div>
                  </div>
                </div>
              </div>
            </tr>

          </c:forEach>
        </form>
        <c:forEach var="b" items="${dsBook}">
          <!--Modal Update Book-->
          <div id="editEmployeeModal${b.getBookId()}" class="modal fade">
            <div class="modal-dialog">
              <div class="modal-content">
                <form action="CrudBook" method="post" enctype="multipart/form-data">
                  <div class="modal-header">
                    <h4 class="modal-title">Update Book</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  </div>
                  <div class="modal-body">
                    <div class="form-group">
                      <select id="bookTypesUpdate" class="form-control" name="txtBookType">
                        <c:forEach var="bT" items="${dsBookType}">
                          <c:choose>
                            <c:when test="${b.getBookType() eq bT.getBookType()}">
                              <option value="${bT.getBookType()}" selected>${bT.getBookTypeName()}</option>
                            </c:when>
                            <c:otherwise>
                              <option value="${bT.getBookType()}">${bT.getBookTypeName()}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="form-group">
                      <label for="bookIdUpdate">BookId</label>
                      <input type="text" class="form-control" name="txtBookId" id="bookIdUpdate"
                             value="${b.getBookId()}" readonly>
                    </div>
                    <div class="form-group">
                      <label for="bookNameUpdate">BookName</label>
                      <input type="text" class="form-control" id="bookNameUpdate" name="txtBookName"
                             value="${b.getBookName()}">
                    </div>
                    <div class="form-group">
                      <label for="authorUpdate">Author</label>
                      <input type="text" class="form-control" id="authorUpdate" name="txtAuthor"
                             value="${b.getAuthor()}">
                    </div>
                    <div class="form-group">
                      <label for="quantityUpdate">Quantity</label>
                      <input type="number" class="form-control" id="quantityUpdate" name="txtQuantity"
                             value="${b.getQuantity()}">
                    </div>
                    <div class="form-group">
                      <label for="priceUpdate">Price</label>
                      <input type="number" class="form-control" id="priceUpdate" name="txtPrice"
                             value="${b.getPrice()}">
                    </div>
                    <div class="form-group">
                      <label for="imageUpdate">Image</label>
                      <br>
                      <img src="${b.getImage()}" alt="Girl in a jacket" width="40" height="40">

                      <input type="file" class="form-control" id="imageUpdate" name="txtImage"
                             value="${b.getImage()}">
                    </div>
                  </div>
                  <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <button type="submit" name="btnAction" class="btn btn-success" value="Update">Update</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </c:forEach>
        </tbody>
      </table>
      <div class="clearfix">
        <div class="hint-text">Showing <b>${dsBook.size()}</b> out of <b>${totalItems}</b> entries</div>
        <ul class="pagination">
          <c:if test="${page != 1}">
            <c:choose>
              <c:when test="${not empty value or not empty filterValue}">
                <!--Nếu chuyển trang mà đã có search hoặc filter, lấy các giá trị search(value) và filter(filter value) để lưu vào section-->
                <li class="page-item"><a href="home?action=SearchAndFilter&page=${page-1}" class="page-link">Previous</a></li>
                <c:set var = "searchValueSession" scope = "session" value="${value}" />
                <c:set var = "filterValueSession" scope = "session" value="${filterValue}" />
              </c:when>
              <c:otherwise>
                <!--Nếu chuyển trang mà trước đó chưa search hoặc filter(chưa có value và filterValue) để lưu vào session-->
                <li class="page-item"><a href="home?page=${page-1}" class="page-link">Previous</a></li>
              </c:otherwise>
            </c:choose>

          </c:if>

          <c:forEach begin="1" end="${totalPage}" var="i">
            <c:choose>
              <c:when test="${page eq i}">
                <li class="page-item active"><a href="#" class="page-link">${i}</a></li>
              </c:when>
              <c:otherwise>
                <td>
                  <c:if test="${i lt totalPage}">
                    <c:choose>
                      <c:when test="${not empty value or not empty filterValue}">
                        <!--Nếu chuyển trang mà đã có search hoặc filter, lấy các giá trị search(value) và filter(filter value) để lưu vào section-->
                        <li class="page-item"><a href="home?action=SearchAndFilter&page=${i}" class="page-link">${i}</a></li>
                        <c:set var = "searchValueSession" scope = "session" value="${value}" />
                        <c:set var = "filterValueSession" scope = "session" value="${filterValue}" />
                      </c:when>
                      <c:otherwise>
                        <!--Nếu chuyển trang mà trước đó chưa search hoặc filter(chưa có value và filterValue) để lưu vào session-->
                        <li class="page-item"><a href="home?page=${i}" class="page-link">${i}</a></li>
                      </c:otherwise>
                    </c:choose>

                  </c:if>
                </td>
              </c:otherwise>
            </c:choose>
          </c:forEach>

          <c:if test="${page lt totalPage}">
            <c:choose>
              <c:when test="${not empty value or not empty filterValue}">
                <!--Nếu chuyển trang mà đã có search hoặc filter, lấy các giá trị search(value) và filter(filter value) để lưu vào section-->
                <li class="page-item"><a href="home?action=SearchAndFilter&page=${page + 1}" class="page-link">Next</a></li>
                <c:set var = "searchValueSession" scope = "session" value="${value}" />
                <c:set var = "filterValueSession" scope = "session" value="${filterValue}" />
              </c:when>
              <c:otherwise>
                <!--Nếu chuyển trang mà trước đó chưa search hoặc filter(chưa có value và filterValue) để lưu vào session-->
                <li class="page-item"><a href="home?page=${page + 1}" class="page-link">Next</a></li>
              </c:otherwise>
            </c:choose>

          </c:if>

        </ul>
      </div>
    </div>
  </div>
</div>
<!-- Modal Add Book -->
<div id="addEmployeeModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="CrudBook" method="post" enctype="multipart/form-data">
        <div class="modal-header">
          <h4 class="modal-title">Add New Book</h4>
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body">
          <label for="bookTypes">BookTypes</label>
          <div class="form-group">
            <select id="bookTypes" class="form-control" name="txtBookType">
              <c:forEach var="bT" items="${dsBookType}">
                <option value="${bT.getBookType()}">${bT.getBookTypeName()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label for="bookId">BookId</label>
            <input type="text" class="form-control" name="txtBookId" id="bookId" required>
          </div>
          <div class="form-group">
            <label for="bookName">BookName</label>
            <input type="text" class="form-control" id="bookName" name="txtBookName" required>
          </div>
          <div class="form-group">
            <label for="author">Author</label>
            <input type="text" class="form-control" id="author" name="txtAuthor" required>
          </div>
          <div class="form-group">
            <label for="quantity">Quantity</label>
            <input type="number" class="form-control" id="quantity" name="txtQuantity" required>
          </div>
          <div class="form-group">
            <label for="price">Price</label>
            <input type="number" class="form-control" id="price" name="txtPrice" required>
          </div>
          <div class="form-group">
            <label for="image">Image</label>
            <input type="file" class="form-control" id="image" name="txtImage" required>
          </div>
        </div>
        <div class="modal-footer">
          <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
          <button type="submit" class="btn btn-success" name="btnAction" value="Add">Add</button>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>

