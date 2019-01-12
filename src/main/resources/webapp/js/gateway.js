var gateway = new function () {
    this.backendUri = "http://localhost:8889/api";
    this.backendSecurePrefix = "Bearer ";

    this.nodesPath = "/nodes";
    this.filesPath = "/files";

    this.unknownImageSrc = "/pic/new-user.jpg";

    this.authentication = (login, password)=> {
        var data = {login: login, password: password};
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
                app.log(req);
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
                app.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                nodes = res;
                app.log("getAllNodes(data):" + textStatus);
                app.log(res);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                app.log("getAllNodes(errorThrown):" + errorThrown);
            }
        });
        return nodes;
    }

    this.addNewNode = (parentId)=> {
        var data = {parentId: parentId};
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
                app.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                node = JSON.parse(res);
                app.log("addNewNodes(data):" + textStatus);
                app.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                app.log("addNewNodes(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.submitNode = (node)=> {
        var data = node;
        app.log(data)
        $.ajax({
            type: "PUT",
            url : this.backendUri + this.nodesPath,
            async: false,
            data: data,
            dataType: 'text',
            cache: false,
            timeout: 1000,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                app.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                app.log("saveNode(data):" + textStatus);
                app.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                app.log("saveNode(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.removeNode = (node)=> {
        var data = {nodeId: node.id};
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
                app.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                app.log("removeNode(data):" + textStatus);
                app.log(node);
            },
            error: function(jqXhr, textStatus, errorThrown) {
                app.log("removeNode(errorThrown):" + errorThrown);
            }
        });
        return node;
    }

    this.sendFile = (file)=> {
        var formData = new FormData();
        formData.append('file', file[0]);

        var fileId;
        $.ajax({
            type: "POST",
            url : this.backendUri + this.filesPath,
            async: false,
            data: formData,
            cache: false,
            timeout: 1000,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            beforeSend: (xhr)=> {
                var token = getCookie("token");
                app.log(token);
                if (typeof token !== 'undefined')
                    xhr.setRequestHeader('Authorization', this.backendSecurePrefix + token);
            },
            success: function( res, textStatus, jQxhr ) {
                app.log(res);
                fileId = JSON.parse(res);
                app.log("sendFile(data):" + textStatus);
                app.log(fileId);
            },
            error: function(req, err) {
                app.log(req);
                throw err;
            }
        });
        return fileId;
    }

    this.getImageSrcById = (fileId)=> {
        var res
        if(fileId) {
            res = this.backendUri + this.filesPath + "/" + fileId
        } else {
            res = this.unknownImageSrc
        }
        return res;
    }

}