<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Bean.BookBean" %>
<%--
  Created by IntelliJ IDEA.
  User: tien.tran2
  Date: 5/8/2023
  Time: 9:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<span class="custom-checkbox">
    <input type="checkbox" id="selectAll">
    <label for="selectAll"></label>
</span>
<a href="#deleteListBookModal" class="btn btn-danger" data-toggle="modal"><i
        class="material-icons">&#xE15C;</i> <span>Delete</span></a>
<form action="Test" method="post">
<%--    Habits :--%>
<%--    <input type="checkbox" name="habits" value="Reading">Reading--%>
<%--    <input type="checkbox" name="habits" value="Movies">Movies--%>
<%--    <input type="checkbox" name="habits" value="Writing">Writing--%>
<%--    <input type="checkbox" name="habits" value="Singing">Singing--%>
<%--    <input type="submit" name="btnDeleteCheck" value="Submit">--%>
    <table>
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
        </tr>
    </c:forEach>
    </table>
    <input type="submit" name="btnDeleteCheck" value="Submit">
    <div id="deleteListBookModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <%--                                        <form action="CrudBookActionServlet" method="post">--%>
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
                    <button type="submit" name="btnDeleteCheck"
                            class="btn btn-danger" value="deleteListBook">Delete</button>
                </div>
                <%--                                        </form>--%>
            </div>
        </div>
    </div>
</form>
</body>
    <script>
    $(document).ready(function () {
        // Activate tooltip
        $('[data-toggle="tooltip"]').tooltip();

        // Select/Deselect checkboxes
        var checkbox = $('input[type="checkbox"]');
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
    });
</script>
</html>
