<main-page>
  <title-bar onlogout={opts.onlogout}/>
  <div class="tree">
    <node nodes={this.nodes} if={this.nodes.length > 0} onshowcard={showcard}/>
    <div if={this.nodes.length === 0}>
      <button type="button" class="btn btn-secondary" onclick={createNode}>Создать</button>
    </div>
  </div>
  <div id="inner_remaining"></div>
  <div class="md-modal md-effect-1 {md-show: dialogShowing}">
      <div class="md-content">
          <h3>{node.name}</h3>
          <div>
              <p>
                some info
              </p>

              <div class="btn-group" role="group" aria-label="Basic example">
                <button type="button" class="btn btn-secondary" onclick={removeNode}>Удалить</button>
                <button type="button" class="btn btn-secondary" onclick={submitNode}>Сохранить</button>
                <button type="button" class="btn btn-secondary" onclick={closeNode}>Закрыть</button>
              </div>
          </div>
      </div>
  </div>

  <style>
    * {
        margin: 0;
        padding: 0;
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
    }

    .tree ul {
        padding-top: 20px;
        position: relative;
    	transition: all 0.5s;
    	-webkit-transition: all 0.5s;
    	-moz-transition: all 0.5s;
    }

    .tree li {
    	float: left; text-align: center;
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
    	position: absolute; top: 0; right: 50%;
    	border-top: 1px solid #be9656;
    	width: 50%; height: 20px;
    }
    .tree li::after{
    	right: auto; left: 50%;
    	border-left: 1px solid #be9656;
    }

    /*We need to remove left-right connectors from elements without
    any siblings*/
    .tree li:only-child::after, .tree li:only-child::before {
    	display: none;
    }

    /*Remove left connector from first child and
    right connector from last child*/
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
    	position: absolute; top: 0; left: 50%;
    	border-left: 1px solid #be9656;
    	width: 0; height: 20px;
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
        width: 50%;
        max-width: 630px;
        min-width: 320px;
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

      .md-overlay {
        position: fixed;
        width: 100%;
        height: 100%;
        visibility: hidden;
        top: 0;
        left: 0;
        z-index: 1000;
        opacity: 0;
        background: rgba(219,192,137,0.8);
        -webkit-transition: all 0.3s;
        -moz-transition: all 0.3s;
        transition: all 0.3s;
      }

      .md-show ~ .md-overlay {
        opacity: 1;
        visibility: visible;
      }

      /* Content styles */
      .md-content {
        color: #000;
        background: #e9d9b8;
        position: relative;
        border-radius: 3px;
        margin: 0 auto;
      }

      .md-content h3 {
        margin: 0;
        padding: 0.4em;
        text-align: center;
        font-size: 2.4em;
        font-weight: 300;
        opacity: 0.8;
        background: rgba(0,0,0,0.1);
        border-radius: 3px 3px 0 0;
      }

      .md-content > div {
        padding: 15px 40px 30px;
        margin: 0;
        font-weight: 300;
        font-size: 1.15em;
      }

      .md-content > div p {
        margin: 0;
        padding: 10px 0;
      }

      .md-content > div ul {
        margin: 0;
        padding: 0 0 30px 20px;
      }

      .md-content > div ul li {
        padding: 5px 0;
      }

      .md-content button {
        display: block;
        margin: 0 auto;
        font-size: 0.8em;
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
    var node

    this.on('before-mount', function() {
        self.nodes = gateway.getAllNodes();
        console.log("mp update");
        console.log(this.nodes);
    });

    this.showcard = (node) => {
        console.log('showcard')
        console.log(node)
        self.dialogShowing = true;
        self.node = node
        self.update()
    }
    this.removeNode = () => {
        console.log('removenode')
        gateway.removeNode(self.node.id);
        self.dialogShowing = false
        self.nodes = gateway.getAllNodes();
        self.update()
    }
    this.submitNode = () => {
        console.log('submitNode')
        self.dialogShowing = false
    }
    this.closeNode = () => {
        self.dialogShowing = false
    }
  </script>
</main-page>