<app>
    <div class="app">
       <main-page if={getCookie("token")} onlogout={logout}/>
       <login-page if={!getCookie("token")} onlogin={login}/>
    </div>

    <style>

    </style>
    <script>
        var token = getCookie("token");
        app.log(token);
        login(token) {
            app.log("app: try to login");

            try{
                this.nodes = gateway.getAllNodes(token);
                app.log("app: get user data successfully, user data is =" + this.nodes);
            }
            catch(e){
                app.log('app: connection error - ' + e);
            }

            this.update();
        }

        logout() {
            app.log("app: clean token");

            setCookie("token", "");

            this.nodes = null;
            this.update();
        }
    </script>
</app>
