<login-page>
    <div class="form">
        <input class="form-control" type="email" ref="inputEmail" placeholder="username" required autofocus onKeyPress={checkSubmit}>
        <input class="form-control" type="password" ref="inputPassword" placeholder="password" required onKeyPress={checkSubmit}>
        <div class="btn-login" onclick={authenticate}>LOGIN</div>
        <div class="error-block" if={this.error_authentication}>Incorrect Email or Password...</div>
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
      this.error = null;

      authenticate = ()=> {
        var email = this.refs.inputEmail.value;
        var pass = this.refs.inputPassword.value;
        this.refs.inputEmail.value = null;
        this.refs.inputPassword.value = null;

        logger.debug("loginpage: try to authenticate");
        var token = null;

        try{
            token = gateway.authentication(email, pass);
            this.error_connection = false;

            if (token) {
                logger.debug('loginpage: successfully authenticated');
                opts.onlogin(token);
            }
            else {
                logger.debug('loginpage: authentication failed');
                this.error_authentication = true;
            }
        }
        catch(e){
            logger.error('loginpage: connection error - ' + e);
            this.error_authentication = false;
            this.error_connection = true;
        }
      };

      checkSubmit = (e)=> {
          if(e && e.keyCode == 13) {
              authenticate();
          }
      }
  </script>
</login-page>