<app>
    <div class="app">
       <main-page if={session.isAuthenticated} onlogout={logout}/>
       <login-page if={!session.isAuthenticated || !getCookie("token")} onlogin={login}/>
    </div>

    <style>

    </style>
    <script>
        var token = getCookie("token");
        app.log(token);

        this.on('before-mount', function() {
            app.log("app - before-mount");
            if(!session.isAuthenticated && getCookie("token")) {
                app.log("app - before-mount - try login with token = " + token);
                this.login(token);
            }
        });


        login(token) {
            app.log("app: try to login");

            session.user = gateway.getUserData(token);
            app.log("app: get user data successfully, user data is =" + this.userData);

            if(typeof session.user !== "undefined") {
              session.isAuthenticated = true;
            }

            this.update();
        }

        logout() {
            app.log("app: clean token");

            setCookie("token", "");

            this.userData = null;
            session.isAuthenticated = false;
            session.user = {};
            this.update();
        }
    </script>
</app>
