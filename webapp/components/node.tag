<node>
  <div id="node">
      <ul>
        <li each={node in opts.nodes} node={node}>
            <div class="item">
                <div class="polaroid">
                    <img src={gateway.getImageSrcById(node.imageId)} onclick={() => this.showcard(node)} >
                    <div class="caption">
                        {node.name}
                    </div>
                </div>
            </div>

            <node nodes={node.ancestors} if={node.ancestors && node.ancestors.length > 0} showcard={showcard} updatenodes={opts.updatenodes}>
        </li>
      </ul>
  </div>

  <style>

    .polaroid {
        background: #fff;
        padding: 1rem;
        box-shadow: 0 0.25rem 1rem rgba(0,0,0,0.2);

    }

    .polaroid > img {
        max-width: 100px;
        max-height: 100px;
    }

    .caption {
        font-size: 1.125rem;
        text-align: center;
        line-height: 2em;
        max-width: 100px;
        cursor: text;
    }

    .item {
        display: inline-block;
    }

    .item:hover {
        box-shadow: 5px 1px 10px gray;
        -webkit-transform: scale(1.1,1.1);
        position: relative;
        z-index: 2;
        cursor: pointer;
    }

    .arrow:hover {
        -webkit-transform: scale(1.1,1.1);
        position: relative;
        z-index: 2;
        cursor: pointer;
    }
  </style>

  <script>
    this.on('after-mount', function() {
    })

    showcard(node) {
        opts.showcard(node)
    }
  </script>
</node>