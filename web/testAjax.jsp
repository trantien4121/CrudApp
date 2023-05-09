<%@ page import="Bean.BookBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Tìm kiếm sản phẩm</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>Tìm kiếm sách</h1>
<form>
    <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm">
    <button type="submit">Tìm kiếm</button>
</form>
<div id="result"></div>
<script>
    $(document).ready(function() {
        $("form").submit(function(event) {
            event.preventDefault();
            var keyword = $("input[name='keyword']").val();
            $.ajax({
                url: "SearchServlet",
                data: { keyword: keyword },
                success: function(result) {
                    $("#result").html(result);
<%--                    <%--%>
<%--                        ArrayList<BookBean> dsBook = (ArrayList<BookBean>) request.getAttribute("dsBook");--%>
<%--                        for(BookBean b: dsBook)--%>
<%--                            {--%>
<%--                    %>--%>
<%--                    $("#result").innerHTML = `--%>
<%--                        <table>--%>
<%--                            <tr>--%>
<%--                                <td><%=b.getBookId()%></td>--%>
<%--                                <td><%=b.getBookName()%></td>--%>
<%--                                <td><%=b.getAuthor()%></td>--%>
<%--                            </tr>--%>
<%--                        </table>--%>
<%--                    `;--%>
<%--                    <%--%>
<%--                            }--%>
<%--                    %>--%>
                }
            });
        });
    });
</script>
</body>
</html>
