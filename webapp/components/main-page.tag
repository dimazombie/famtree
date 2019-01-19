<main-page>
  <title-bar onlogout={opts.onlogout}/>
  <div class="tree">
    <node nodes={this.nodes} if={this.nodes.length > 0} showcard={showcard} updatenodes={updatenodes}/>
    <div if={this.nodes.length === 0}>
      <button type="button" class="btn btn-secondary" onclick={initTree}>Создать</button>
    </div>
  </div>
  <div id="inner_remaining"></div>
  <div class="md-modal md-effect-1 {md-show: dialogShowing}">
      <div class="md-content">
          <div class="md-header">
            <h3 ref="name" contenteditable="true" onblur={setName}/>
          </div>

          <div class="md-main">
                <img ref="image" if={tmpNode && tmpNode.imageId}/>
                <p ref="bio" contenteditable="true" onblur={setBio}/>
          </div>

          <div class="btn-group md-footer" role="group">
            <input id="upload_button" ref="upload_button" type="file" accept="image/*" onchange={handleFile}/>

            <div class="btn-group" role="group">
             <div class="btn-group" role="group">
              <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  Операции
              <span class="caret"></span>
              </button>
              <ul class="dropdown-menu">
                <li><a href="#" onclick={chooseFile}>Загрузить фото</a></li>
                <li><a href="#" onclick={addNode}>Добавить родителя</a></li>
                <li><a href="#" onclick={removeNode}>Удалить</a></li>
              </ul>
            </div>
            <button type="button" class="btn btn-secondary" onclick={submitNode}>Сохранить</button>
            <button type="button" class="btn btn-secondary" onclick={closeCard}>Закрыть</button>
          </div>
      </div>
  </div>

  <style>
    * {
        margin: 0; padding: 0;
    }

    #upload_button {
        display:none;
    }

    #inner_remaining {
        position: absolute;
        top: 100px;
        bottom: 0;
        width: 100%;
        background: #DBC089;
        z-index: 1;
    }

    .tree {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: #DBC089;
        background: linear-gradient(to top, #DBC089 0%, #EAD4A2 36%, #FFFFFF 100%);
        position: relative;
        min-height:300px;
        z-index: 3;
        -moz-user-select: none;
        -khtml-user-select: none;
        user-select: none;
    }

    .tree ul {
        padding-top: 20px;
        position: relative;
    	transition: all 0.5s;
    	-webkit-transition: all 0.5s;
    	-moz-transition: all 0.5s;
    }

    .tree li {
    	float: left;
    	text-align: center;
    	list-style-type: none;
    	position: relative;
    	padding: 20px 5px 0 5px;

    	transition: all 0.5s;
    	-webkit-transition: all 0.5s;
    	-moz-transition: all 0.5s;
    }

    /*We will use ::before and ::after to draw the connectors*/
    .tree li::before, .tree li::after{
    	content: '';
    	position: absolute;
    	top: 0;
    	right: 50%;
    	border-top: 1px solid #be9656;
    	width: 50%;
    	height: 20px;
    }
    .tree li::after{
    	right: auto;
    	left: 50%;
    	border-left: 1px solid #be9656;
    }

    /*We need to remove left-right connectors from elements without any siblings*/
    .tree li:only-child::after, .tree li:only-child::before {
    	display: none;
    }

    /*Remove space from the top of single children*/
    .tree li:only-child{
        padding-top: 0;
    }

    /*Remove left connector from first child and right connector from last child*/
    .tree li:first-child::before, .tree li:last-child::after{
    	border: 0 none;
    }

    /*Adding back the vertical connector to the last nodes*/
    .tree li:last-child::before{
    	border-right: 1px solid #be9656;
    	border-radius: 0 5px 0 0;
        -webkit-border-radius: 0 5px 0 0;
        -moz-border-radius: 0 5px 0 0;
    }

    .tree li:first-child::after{
    	border-radius: 5px 0 0 0;
    	-webkit-border-radius: 5px 0 0 0;
    	-moz-border-radius: 5px 0 0 0;
    }

    /*Time to add downward connectors from parents*/
    .tree ul ul::before{
    	content: '';
    	position: absolute;
    	top: 0;
    	left: 50%;
    	border-left: 1px solid #be9656;
    	width: 0;
    	height: 20px;
    }

    .tree li a{
    	border: 1px solid #be9656;
    	padding: 5px 10px;
    	text-decoration: none;
    	color: #666;
    	font-family: arial, verdana, tahoma;
    	font-size: 11px;
    	display: inline-block;

    	border-radius: 5px;
    	-webkit-border-radius: 5px;
    	-moz-border-radius: 5px;

    	transition: all 0.5s;
    	-webkit-transition: all 0.5s;
    	-moz-transition: all 0.5s;
    }

    .md-modal {
        position: fixed;
        top: 50%;
        left: 50%;
        width: 90%;
        max-width: 2030px;
        min-width: 620px;
        height: auto;
        z-index: 2000;
        visibility: hidden;
        -webkit-backface-visibility: hidden;
        -moz-backface-visibility: hidden;
        backface-visibility: hidden;
        -webkit-transform: translateX(-50%) translateY(-50%);
        -moz-transform: translateX(-50%) translateY(-50%);
        -ms-transform: translateX(-50%) translateY(-50%);
        transform: translateX(-50%) translateY(-50%);
    }

    .md-show {
        visibility: visible;
    }

    .md-show ~ .md-overlay {
        opacity: 1;
        visibility: visible;
    }

    /* Content styles */
    .md-content {
        color: #000;
        background: #e9d9b8;

        display: inline-block;
        width: 100%;

        border-radius: 3px;
        margin: 0 auto;

        -moz-user-select: none;
        -khtml-user-select: none;
        user-select: none;
    }

    .md-header h3 {
        margin: 0;
        padding: 0.4em;
        text-align: center;
        font-size: 2.4em;
        font-weight: 300;
        opacity: 0.8;
        background: rgba(0,0,0,0.1);
        border-radius: 3px 3px 0 0;
    }

    .md-content button {
        display: block;
        margin: 0 auto;
        font-size: 0.8em;
    }

    .md-main img {
        margin-right: 15px;
        margin-bottom: 15px;
        float: left;
        width: 250px;
    }

    .md-main {
        margin: 15px 15px 0 15px;
        text-align: justify;
        max-width: 100%;
        overflow-x: hidden;
        max-height: 55vh;
    }

    /* Individual modal styles with animations/transitions */

    /* Effect 1: Fade in and scale up */
    .md-effect-1 .md-content {
        -webkit-transform: scale(0.7);
        -moz-transform: scale(0.7);
        -ms-transform: scale(0.7);
        transform: scale(0.7);
        opacity: 0;
        -webkit-transition: all 0.3s;
        -moz-transition: all 0.3s;
        transition: all 0.3s;
    }

    .md-main {
        max-height: 55vh;
    }

    .md-footer {
       width: 100%;
       padding: 15px 15px 15px;
    }

    .md-show.md-effect-1 .md-content {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        transform: scale(1);
        opacity: 1;
    }

    button {
        border: none;
        padding: 0.6em 1.2em;
        background: #99865f;
        color: #fff;
        font-family: 'Lato', Calibri, Arial, sans-serif;
        font-size: 1em;
        letter-spacing: 1px;
        text-transform: uppercase;
        cursor: pointer;
        display: inline-block;
        margin: 3px 2px;
        border-radius: 2px;
    }

    button:hover {
        background: #af996d;
    }


  </style>
  <script>
    var self = this
    var tmpNode

    this.on('before-mount', function() {
        self.updatenodes()
    });

    this.updatenodes = () => {
        self.nodes = gateway.getAllNodes()
        self.update()
    }

    this.updateCardData = (node) => {
        self.refs.name.textContent = node.name
        self.refs.bio.innerHTML = node.bio
        if(self.node && self.node.imageId) {
            self.update()
            self.refs.image.src = gateway.getImageSrcById(node.imageId)
        }
    }

    this.clearCardData = () => {
        app.log('clear card data')
        self.tmpNode = {}
        self.refs.name.textContent = '';
        self.refs.bio.innerHTML = '';
        if(self.node && self.node.imageId) {
            self.refs.image.src = '';
        }
    }

    this.showcard = (node) => {
        self.tmpNode = copyObject(node)
        self.node = node
        self.updateCardData(node)
        self.dialogShowing = true
        self.update()
    }

   this.removeNode = () => {
        gateway.removeNode(self.node)
        self.closeCard()

        self.updatenodes() //temp WA - TODO: update structure
    }

    this.submitNode = () => {
        gateway.submitNode(self.tmpNode)
        self.closeCard()

        self.updatenodes() //temp WA - TODO: update structure
    }

    this.closeCard = () => {
        self.dialogShowing = false
        self.clearCardData()
    }

    this.initTree = () => {
        var node = gateway.addNewNode(null)
        self.nodes.push(node)
        self.closeCard()
    }

    this.addNode = () => {
        var node = gateway.addNewNode(self.node.id)
        self.node.ancestors = self.node.ancestors || []
        self.node.ancestors.push(node)
        self.closeCard()
    }

    this.chooseFile = () => {
        self.refs.upload_button.click();
    }

    this.handleFile = (e) => {
        if(e.target.files.length > 0) {
            var imageId = gateway.sendFile(e.target.files)
            self.setImageId(imageId)
        }

    }

    this.setImageId = (imageId) => {
        self.tmpNode.imageId = imageId
        self.update() //WA to show card
        self.refs.image.src = gateway.getImageSrcById(imageId)
    }

    this.setName = (e) => {
        self.tmpNode.name = e.target.textContent
    }

    this.setBio = (e) => {
        self.tmpNode.bio = e.target.innerHTML
    }
  </script>
</main-page>