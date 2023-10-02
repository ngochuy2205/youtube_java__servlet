<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
 <table class="table table-bordered mt-5">
            <thead>
              <tr>
                <th>Youtube Id</th>
                <th>Video Title</th>
                <th>View Count</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody id="root-list-video">
              <c:forEach var="video" items="${videos}">
              	<tr id="${video.id}">
                <td class="col">${video.id}</td>
                <td class="col">${video.title}</td>
                <td class="col">${video.views}</td>
                <td class="col">${video.active}</td>
                <td>Edit</td>
              </tr>
              </c:forEach>
            </tbody>
          </table>
</body>
</html>