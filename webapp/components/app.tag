<app>
    <div class="app">
       <main-page if={getCookie("token")} onlogout={logout}/>
       <login-page if={!getCookie("token")} onlogin={login}/>
    </div>

    <style>

    </style>
    <script>
        var token = getCookie("token");
        console.log(token);
        login(token) {
            console.log("app: try to login");

            try{
                this.nodes = gateway.getAllNodes(token);
                console.log("app: get user data successfully, user data is =" + this.nodes);
            }
            catch(e){
                console.log('app: connection error - ' + e);
            }

            this.update();
        }

        logout() {
            console.log("app: clean token");

            setCookie("token", "");

            this.nodes = null;
            this.update();
        }
    </script>
</app>
