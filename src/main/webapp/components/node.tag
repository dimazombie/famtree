<node>
  <ul>
    <li each={node in opts.children} node={node}>
        <a href="#">{node.name}</a>
        <node children={node.children} if={node.children}>
    </li>
  </ul>

  <style>

  </style>

  <script>

  </script>
</node>