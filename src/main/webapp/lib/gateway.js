var gateway = new function () {
    this.backend_uri = "http://localhost:8888/api";
    this.backend_secure_prefix = "Bearer ";

    this.authentication = (username, password)=> {
        var data = {username: username, password: password};
        var token;
        $.ajax({
            type: "POST",
            url : this.backend_uri + "/authentication",
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            success: function(res) {
                token = res;
            },
            error: function(req, err) {
                console.log(req);
                throw err;

            }
        });
        return token;
    }

}