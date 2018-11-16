var gateway = new function () {
    this.backend_uri = "http://localhost:8889/api";
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
    
    this.getAllNodes = (token)=> {
        var nodes;
        $.ajax({
            url : this.backend_uri + "/node/222",
            type: "GET",
            async: false,
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backend_secure_prefix + token);
            },
            success: function( data, textStatus, jQxhr ) {
                nodes = data.data;
                console.log("getAllNodes(data):" + textStatus);
                console.log(data);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("getAllNodes(errorThrown):" + errorThrown);
            }
        });
        return nodes;
    }

}