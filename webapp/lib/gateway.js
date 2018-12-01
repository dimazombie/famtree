var gateway = new function () {
    this.backendUri = "http://localhost:8889/api";
    this.backendSecurePrefix = "Bearer ";
    this.nodesPath = "/nodes"

    this.authentication = (username, password)=> {
        var data = {username: username, password: password};
        var token;
        $.ajax({
            type: "POST",
            url : this.backendUri + "/authentication",
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
    
    this.getAllNodes = ()=> {
        var nodes;
        $.ajax({
            url : this.backendUri + this.nodesPath,
            type: "GET",
            async: false,
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                nodes = res;
                console.log("getAllNodes(data):" + textStatus);
                console.log(res);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("getAllNodes(errorThrown):" + errorThrown);
            }
        });
        return nodes;
    }

    this.addAllNodes = (nodeId)=> {
        var data = {nodeId: nodeId};
        var node;
        $.ajax({
            type: "POST",
            url : this.backendUri + this.nodesPath,
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                node = JSON.parse(res);
                console.log("addNewNodes(data):" + textStatus);
                console.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("addNewNodes(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.removeNode = (nodeId)=> {
        var data = {nodeId: nodeId};
        var node;
        $.ajax({
            type: "DELETE",
            url : this.backendUri + this.nodesPath,
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                console.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                node = JSON.parse(res);
                console.log("addNewNodes(data):" + textStatus);
                console.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                console.log("addNewNodes(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

}