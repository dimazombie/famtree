<login-page>
    <div class="form">
        <input class="form-control" type="login" ref="inputLogin" value="test" placeholder="логин" required autofocus onKeyPress={checkSubmit}>
        <input class="form-control" type="password" ref="inputPassword" value="test" placeholder="пароль" required onKeyPress={checkSubmit}>
        <div class="btn-login" onclick={authenticate}>ВОЙТИ</div>
        <div class="error-block" if={this.errorAuthentication}>Неверный логин или пароль...</div>
    </div>

  <style>
    .form {
        display: flex;
        flex-wrap: wrap;
        flex-direction: column;
        width: 350px;
        margin: auto;
        transform: translateY(30%);
    }

    .form-control{
        width: 350px;
        margin-top: 12px;
        height: 35px;
        font-size: 16px;
        padding-left: 20px;
        padding-top: 25px;
        padding-bottom: 25px;
        background-color: #F2F2F2;
        border: 0px;

        color: #000;
    }

    .btn-login {
        margin-top: 20px;
        border: 0px;
        padding-top: 25px;
        padding-bottom: 25px;
        width: 350px;
        height: 35px;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #BE9656;
        cursor: pointer;
        user-select: none;
        color: #FFF;
    }
    .btn-login:hover {
        background-color: #FFD79A;
    }
    .btn-login:active {
        color: #BE9656;
    }
    .error-block {
        margin-top: 10px;
        color: red;
    }
  </style>

  <script>
      this.errorAuthentication = null;

      authenticate = ()=> {
        var login = this.refs.inputLogin.value;
        var password = this.refs.inputPassword.value;
        this.refs.inputLogin.value = null;
        this.refs.inputPassword.value = null;

        console.log("loginpage: try to authenticate");
        var token = null;

        try{
            token = gateway.authentication(login, password);
            console.log(token);
            if (token) {
                console.log('loginpage: successfully authenticated');
                setCookie("token", token);
                opts.onlogin(token);
            }
            else {
                console.log('loginpage: authentication failed');
                this.errorAuthentication = true;
            }
        }
        catch(e){
            console.log('loginpage: connection error');
            console.log(e);
            this.errorAuthentication = true;
        }
      };

      checkSubmit = (e)=> {
          if(e && e.keyCode == 13) {
              authenticate();
          }
      }
  </script>
</login-page>