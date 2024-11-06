<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/png" href="/img/favicon.png" />
<link rel="stylesheet"  href="/css/common.css" />
<script src="https://cdn.jsdelivr.net/npm/browser-scss@1.0.3/dist/browser-scss.min.js"></script>

<script src="https://code.jquery.com/jquery.min.js"></script>
<style>
 #table {
  td {
     padding : 10px;
  }
  td:nth-of-type(1) { 
     text-align: center;  
     width : 200px; 
     background : #666;
     color : white;
     font-weight: bold;   
     border : 1px solid white;    
  } 
  td:nth-of-type(2) { width : 600px; }
  
  tr:last-child > td { 
      background: white; 
      border: 1px solid black
  }
  
  input                  { width : 100%; } 
  input[type=submit]     { width : 100px;  } 
  input[name=userid]     { width : 25%;  } 
  #goList                { width : 100px;  } 
  #dupCheck, #dupCheck2  { width : 100px; }
  
  .red                   { color:red; }
  .green                 { color:green; }
  textarea               {
      height : 200px;
      width  : 100%;      
  }
 }

</style>

<script src="https://code.jquery.com/jquery.min.js"></script>
<script>

   $( function() {
      let num = 1;
      $('#btnAddFile').on('click', function() {
          let tag = '<input type="file" name="upfile" '
                  + ' class="upfile" multiple /><br>'
          $('#tdfile').append( tag );
          num++;       
      })
   } );
 
</script>

</head>
<body>
  <main>  
    <%@include file="/WEB-INF/include/pdsmenus.jsp" %>
   
    <h2>자료실 등록</h2>
    <form action  = "/Pds/Write"  method="POST"  
          enctype = "multipart/form-data"   >
    <input type="hidden"  name="menu_id"  value="${ map.menu_id }" /> 
    <input type="hidden"  name="nowpage"  value="${ map.nowpage }" /> 
    <table id="table">
     <tr>
      <td><span class="red">*</span>제목</td>
      <td>
      <input type="text"     name="title"   id="title"  /> 
      </td>
     </tr>
     <tr>
      <td><span class="red">*</span>작성자 이름</td>
      <td><input type="text" name="writer" 
           id="writer" value="${ login.userid }" /></td>
     </tr>
     <tr>
      <td>내용</td>
      <td><textarea name="content" maxlength="1300"></textarea></td>
     </tr>
     <tr>
      <td>파일</td>
      <td  id="tdfile" >
        <input type="button"  id  ="btnAddFile" value="파일추가(최대 100M Byte)" />
        <input type="file"    name="upfile"     class="upfile"  multiple /><br>
      </td>
     </tr>
     <tr>
      <td colspan="2">
       <input type="submit" value="글 저장" />
       <input type="button" value="목록" id="goList" />
      </td>
     </tr>
    
    </table>    
    </form>
  
    <script>
    /*
       const  btnAddFileEl = document.querySelector('#btnAddFile');
       const  tdfileEl     = document.querySelector('#tdfile');
       let    tag          = '<input type="file"    name="upload"     class="upfile"  multiple /><br>' 
       let    html         = tdfileEl.innerHTML 
       btnAddFileEl.onclick = function() {   
          html += tag;      
          alert(html)
          tdfileEl.innerHTML = html;
	   }
       */
          
       // 목록 이동 버튼
       const  goListEl = document.getElementById('goList')
       goListEl.onclick = function() {
          location.href 
            = '/Pds/List?menu_id=${map.menu_id}&nowpage=${map.nowpage}' 
       }    
       
       //  input validation
       const  formEl       = document.querySelector('form');
       const  titleEl      = document.querySelector('#title');
       const  writerEl     = document.querySelector('#writer');
       
       // 회원가입버튼 클릭
       formEl.onsubmit   = function () {           
		   if(  titleEl.value.trim() == ''  ) {
               alert('제목을 입력하세요')
               titleEl.focus()
           	   return  false;
		   } 
		   if( writerEl.value.trim() == '' ) {
			   alert('작성자를 입력하세요')
               writerEl.focus()
	           return  false;
		   }		
		   return  true;
	   }
    </script> 
   
  </main>
</body>
</html>















