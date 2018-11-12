<node>
  <ul>
    <li each={node in opts.children} node={node}>
        <div class="item">
            <div class="polaroid">
                <img src="./pic/unknown.jpg" height="100px" width="100px">
                <div class="caption">Unknown Person</div>
            </div>
        </div>
        <node children={node.children} if={node.children}>
    </li>
  </ul>

  <style>
    .polaroid {
        background: #fff;
        padding: 1rem;
        box-shadow: 0 0.25rem 1rem rgba(0,0,0,0.2);
    }

    .caption {
        font-size: 1.125rem;
        text-align: center;
        line-height: 2em;
    }

    .item {
        display: inline-block;
    }

    .item:hover {
      box-shadow: 5px 1px 10px gray;
      -webkit-transform: scale(1.1,1.1);
      position: relative;
      z-index: 2;
    }
  </style>

  <script>

  </script>
</node>