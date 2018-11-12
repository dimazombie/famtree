<main-page>
  <div class="tree">
    <node children={this.children} if={this.children}>
  </div>

  <style>
    * {
        margin: 0;
        padding: 0;
    }

    .tree {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background-color: #eee;
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
    	border-top: 1px solid #ccc;
    	width: 50%; height: 20px;
    }
    .tree li::after{
    	right: auto; left: 50%;
    	border-left: 1px solid #ccc;
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
    	border-right: 1px solid #ccc;
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
    	border-left: 1px solid #ccc;
    	width: 0; height: 20px;
    }

    .tree li a{
    	border: 1px solid #ccc;
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

  </style>
  <script>
    this.children = [{
                   name: 'My Tree',
                   children: [
                     { name: 'hello' },
                     { name: 'wat' },
                     {
                       name: 'child folder',
                       children: [
                         {
                           name: 'child folder',
                           children: [
                             { name: 'hello' },
                             { name: 'wat' }
                           ]
                         },
                         { name: 'hello' },
                         { name: 'wat' },
                         {
                           name: 'child folder',
                           children: [
                             { name: 'hello' },
                             { name: 'wat',
                               children: [
                                 { name: 'hello' },
                                 { name: 'wat' }
                               ]

                             }
                           ]
                         }
                       ]
                     }
                   ]
                 }];
  </script>
</main-page>