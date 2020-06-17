import {
    Color,
    Geometry,
    Line,
    LineBasicMaterial,
    Mesh,
    MeshStandardMaterial,
    MeshPhongMaterial,
    PerspectiveCamera,
    Scene,
    SphereGeometry,
    SpotLight,
    TextGeometry,
    Vector3,
    WebGLRenderer,
    FontLoader
} from "https://threejs.org/build/three.module.js";

import {OrbitControls} from "https://threejs.org/examples/jsm/controls/OrbitControls.js";
import {TransformControls} from "https://threejs.org/examples/jsm/controls/TransformControls.js";

const sphereRadius = 10;
const sphereSegments = 100;
const sphereColor = "#800000";
const sphereHighlightedColor = "#008000";

const lineWidth = 5;
const lineColor = "#600000";
const lineHighlightedColor = "#006000";

const fontSize = 8;
const fontHeight = 2;
const fontColor = "#d0d0d0";

const fov = 80;

let socket;


window.start = function () {
    const obj = {
        "from": document.getElementById("pointA").selectedIndex,
        "to": document.getElementById("pointB").selectedIndex,
    };
    const msg = JSON.stringify(obj);
    socket.send(msg);
}


function initScene() {
    const scene = new Scene();

    const light = new SpotLight(0xffffff, 1);
    light.position.set(100, 100, 100);
    scene.add(light);

    return scene;
}

function initCamera(scene) {
    const camera = new PerspectiveCamera(fov, window.innerWidth / window.innerHeight, 1, 1000);
    camera.position.set(100, 100, 100);
    scene.add(camera);
    return camera;
}

function initRenderer() {
    const renderer = new WebGLRenderer({antialias: true});
    renderer.setClearColor(new Color('white'));
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(window.innerWidth, window.innerHeight - document.getElementById("controls").offsetHeight);

    const divForImage = document.getElementById("canvas");
    divForImage.appendChild(renderer.domElement);

    return renderer;
}

function initControls(camera, renderer) {
    new OrbitControls(camera, renderer.domElement);
}

function highlightPath(vertices, spheres, edges, edgesFrom, edgesTo) {
    for (let i = 0; i < vertices.length; i++) {
        spheres[vertices[i]].color.set(sphereHighlightedColor);
    }

    for (let i = 0; i < edgesFrom.length; i++) {
        for (let j = 0; j < vertices.length - 1; j++) {
            if (vertices[j] === edgesFrom[i] && vertices[j + 1] === edgesTo[i] ||
                vertices[j] === edgesTo[i] && vertices[j + 1] === edgesFrom[i]) {
                edges[i].color.set(lineHighlightedColor);
            }

        }
    }
}

function resetColors(spheres, edges) {
    for (let i = 0; i < spheres.length; i++) {
        spheres[i].color.set(sphereColor);
    }

    for (let i = 0; i < edges.length; i++) {
        edges[i].color.set(lineColor);
    }
}

function createSphere(x, y, z) {
    const sphereGeometry = new SphereGeometry(sphereRadius, sphereSegments, sphereSegments, 0, Math.PI * 2, 0, Math.PI * 2);
    const material = new MeshStandardMaterial();
    let mesh = new Mesh(sphereGeometry, material);
    mesh.position.set(x, y, z);
    return mesh;
}

function createText(scene, i, x, y, z) {
    var loader = new FontLoader();
    loader.load('open_sans_regular.json', function (font) {
        var textGeom = new TextGeometry(i.toString(), {
            font: font,
            size: fontSize,
            height: fontHeight
        });
        var material = new MeshPhongMaterial({
            color: fontColor
        });
        var textMesh = new Mesh(textGeom, material);
        textMesh.position.set(x - fontSize / 2, y - sphereRadius - fontSize - 1, z - fontHeight / 2);
        scene.add(textMesh);
    });

}

function createEdge(s, e) {
    const geometry = new Geometry();
    geometry.vertices.push(s, e);
    const material = new LineBasicMaterial();
    material.linewidth = lineWidth;
    return new Line(geometry, material);
}

function addOption(elementId, optiontext) {
    const element = document.getElementById(elementId);
    const option = document.createElement("option");
    option.text = optiontext;
    element.add(option);
}

function onMessage(event, scene, vertices, spheres, edges, edgesFrom, edgesTo) {
    const json = JSON.parse(event.data);
    console.log("Received message: ", json);
    if (json.type === "graph") {
        for (let i = 0; i < json.nVertices; i++) {
            let x = json["v" + i + 'x'];
            let y = json["v" + i + 'y'];
            let z = json["v" + i + 'z'];
            addOption("pointA", i.toString());
            addOption("pointB", i.toString());
            vertices.push(new Vector3(x, y, z));

            let sphere = createSphere(x, y, z);
            scene.add(sphere);
            spheres.push(sphere.material);

            createText(scene, i, x, y, z);
        }
        for (let i = 0; i < json.nEdges; i++) {
            let from = json["e" + i + 'from'];
            let to = json["e" + i + 'to'];
            edgesFrom.push(from);
            edgesTo.push(to);
            let edge = createEdge(vertices[from], vertices[to]);
            scene.add(edge);
            edges.push(edge.material);

        }
    }
    resetColors(spheres, edges)
    if (json.type === "path") {
        let vertices = [];
        for (let i = 0; i < json.nVertices; i++) {
            let v = json["v" + i];
            vertices.push(v);
        }
        highlightPath(vertices, spheres, edges, edgesFrom, edgesTo);
    }
}

function connect(scene, vertices, spheres, edges, edgesFrom, edgesTo) {
    socket = new WebSocket("ws://localhost:8082/action");
    socket.onmessage = (event) => onMessage(event, scene, vertices, spheres, edges, edgesFrom, edgesTo);
}

function animate(scene, camera, renderer) {
    requestAnimationFrame(() => animate(scene, camera, renderer));
    renderer.render(scene, camera);
}

function run() {
    let scene = initScene();
    let camera = initCamera(scene);
    let renderer = initRenderer();
    initControls(camera, renderer);

    let vertices = [], spheres = [], edges = [], edgesFrom = [], edgesTo = [];
    connect(scene, vertices, spheres, edges, edgesFrom, edgesTo);

    animate(scene, camera, renderer);
}

run()

