<!DOCTYPE html>
<html lang="html">
<link rel="stylesheet" href="style.css">
<body>

<div id="controls">
    <label for="pointA">Point A</label><select id="pointA"> </select>
    <label for="pointB">Point B</label><select id="pointB"></select>
    <button onclick="start()"> Find path</button>
</div>

<div id="canvas"></div>

<script type="module" src="https://threejs.org/build/three.module.js"></script>
<script type="module" src="https://threejs.org/examples/jsm/controls/OrbitControls.js"></script>
<script type="module" src="script.js"></script>

</body>
</html>
