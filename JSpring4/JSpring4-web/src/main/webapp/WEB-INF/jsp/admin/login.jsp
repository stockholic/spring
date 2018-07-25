<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>


   <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
             <form name='frm' action='/login' method='POST'>
              <h1>Login</h1>
              <div>
                <input type="text" name="userNm" class="form-control" placeholder="아이디" autofocus />
              </div>
              <div>
                <input type="password" name="password" class="form-control" placeholder="패스워드"  />
              </div>
               <div class="checkbox">
                   <label>
                       <input type='checkbox' name='remember-me'/>로그인 유지
                   </label> 
                    <input name="submit" type="submit" value="Login" class="btn btn-default"/>
               </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">New to site?
                 	stock / 1234
                </p>
              </div>

            </form>
          </section>
       </div>
  </div>
