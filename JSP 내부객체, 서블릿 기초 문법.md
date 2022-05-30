# JSP 내부객체, 서블릿 기초 문법

<img src ="jsp내장객체.jpg">



#### JSP 내부객체의 이해

* 개발자가 객체를 생성하지 않아도 jsp페이지가 서블릿 컨테이너(Tomcat)로 

   로딩되면 Tomcat등 서버가 자동으로 생성하는 객체이다

* 개발자는 반복적인 작업을 줄이고 필요한 작업만 할수 있다. 

*  jsp페이지는 Web서버 및 Servlet Container라고 하는 복잡한 환경에서 실행이 
 되기 때문에, 실행중에 여러가지 상태정보를 가지고 있어야 하는데, 

 이런 경우에 사용되는 객체들이 내부 객체들이다. 

* 내부 객체로 인해 개발자는 좀더 쉽게 JSP 프로그래밍이 가능함. 



####  request, response, out 

__클라이언트로 부터 jsp 페이지의 호출에 의해서 전달되는 데이터 요청과 응답,
 출력의 역할을 한다.__

<img src="request 내부 객체.jpg">

 -  javax.servlet.http.HttpServletRequest Interface를 Tomcat등 서버가 구현한 객체, 
    자동화된 객체, 개발자는 사용만함. 
 - <FORM>에서 입력되어 브러우저가 전송한 데이터를 Tomcat 서버의 JSP에서 
    처리할수 있도록 객체로 가져오는 역할을 한다 
 - ? : 파라메터를 보낸다는 뜻
 - &: 접속자가 보내는 값이 2개 이상인 경우  

__실행결과__

http://localhost:8000/jsptest/request.jsp?pay=3000000&name=홍길동&java=100&jsp=90&spring=90





#### response내부 객체 

* javax.servlet.http.HttpServletResponse Interface를 Tomcat등 

  서버가 구현한 객체, 자동화된 객체, 개발자는 사용만함. 
* 처리 결과를 클라이언트(웹 브러우저)에게 출력할 목적을 가지고 있다.  
*  out 객체는 response 객체로부터 생성한다.
* sendRedirect(): 데이터 처리를 한후 특정 페이지로 자동으로 주소 이동. 
  요청자체가 새로운 요청으로 바뀐다.

#### out 내부 객체
*  jsp페이지 결과를 클라이언트에 전송해 주는 출력 스트림을 나타낸다.
*   jsp페이지 결과를 클라이언트에 전송해 주는 출력 스트림을 나타낸다.
*   out.print("접속에 성공했습니다.")   
  HTML이 모두 한줄로 생성됨, 소스 분석 어려움 
* out.println("접속에 성공했습니다.") 
  호출될 때마다 HTML코드를 생성하고 새로운 라인으로 이동함. 
* System.out.print(), System.out.println()은 디버깅 정보를 콘솔창에
  출력하는 용도로 사용


#### session 
* 세션은 요청을 시도한 특정클라이언트와 다른 클라이언트와 구별하여
  각각의 클라이언트에 대한 정보를 지속적으로 관리할 수 있다.

>>> session.html
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h1>Session Example1</h1>
 <form method="post" action="session.jsp">
 아이디 : <input name="id"><p>
 비밀번호 : <input type="password" name="pwd"><p/>
<input type="submit" value="로그인">
</form>
</body>
</html>
--------------------------------------
>>> session.jsp

<%@ page contentType="text/html;charset=UTF-8"
         session="true"
%>
<%
  request.setCharacterEncoding("UTF-8");

  String id = request.getParameter("id");
  String pwd = request.getParameter("pwd");

      session.setAttribute("idKey",id);
      session.setMaxInactiveInterval(60*5); //5분후 연결종료
%>
<h1>Session Example1</h1>
<form method="post" action="session_1.jsp">
    1.가장 좋아하는 계절은?<br/>
<input type="radio" name="season" value="봄">봄
<input type="radio" name="season" value="여름">여름
<input type="radio" name="season" value="가을">가을
<input type="radio" name="season" value="겨울">겨울<p/>

2.가장 좋아하는 과일은?<br/>
<input type="radio" name="fruit" value="watermelon">수박
<input type="radio" name="fruit" value="melon">멜론
<input type="radio" name="fruit" value="apple">사과
<input type="radio" name="fruit" value="orange">오렌지<p/>
<input type="submit" value="결과보기">
</form>
----------------------------------------------------------------------

>>> session_1.jsp

<%@ page contentType="text/html;charset=UTF-8"%>
<%
           request.setCharacterEncoding("UTF-8");

                String season = request.getParameter("season");
                String fruit = request.getParameter("fruit");
                String id = (String)session.getAttribute("idKey");    
                String sessionId = session.getId();
                int intervalTime = session.getMaxInactiveInterval();
     
                if(id != null){
%>
<h1>Session Example1</h1>
<b><%=id%></b>님이 좋아하시는 계절과 과일은<p/>  
<b><%=season%></b>과 <b><%=fruit%></b> 입니다.<p/>
세션 ID : <%=sessionId%><p>
세션 유지 시간 : <%=intervalTime%>초<p/>
<%
         session.invalidate();
        }else{
         out.println("세션의 시간이 경과를 하였거나 다른 이유로 연결을 할 수가 없습니다.");
    }
%>


####  application
* 서블릿 또는 어플리케이션 외부 환경 정보(contenxt)를 나타낸다
* 서버의 정보와 서버측 자원에 대한 정보를 얻을 수 있다.
*  javax.servlet.ServletContext 인터페이스의 구현 객체 
* 서블릿이 서블릿 컨테이너(Tomcat)에서 실행 될 때의 환경 정보를 저장 
*  절대 경로 추출시 JSP 형식 
   String upDir = "/pds/storage"; 
   upDir = application.getRealPath(upDir); 

\>>> application.jsp

<%@ page contentType="text/html;charset=UTF-8"%>

<%

String serverInfo = application.getServerInfo();

String mimeType = application.getMimeType("request1.html");

String realPath = application.getRealPath("/");

application.log("application 내부 객체 로그 테스트");

%>

<h1>Application Example1</h1>

서블릿 컨테이너의 이름과 버전 :<%=serverInfo%><p />

request1.html의 MIME Type :<%=mimeType%><p />

로컬 파일 시스템 경로 :<%=realPath%>
