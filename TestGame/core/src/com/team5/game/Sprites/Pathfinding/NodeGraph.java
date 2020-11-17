package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class NodeGraph implements IndexedGraph<Node> {

    /*
    NodeGraph creates a graph of Nodes and Links and allows
    an NPC to find a path using A* search from one point
    in the map to another
     */

    NodeHeuristic nodeHeuristic;
    Array<Node> nodes;
    Array<Room> rooms;
    Array<Link> links;

    ObjectMap<Node, Array<Connection<Node>>> nodeMap = new ObjectMap<>();

    private int nodeIndex = 0;

    public NodeGraph(){
        nodeHeuristic = new NodeHeuristic();
        nodes = new Array<>();
        rooms = new Array<>();
        links = new Array<>();

        buildNodeMap();
    }

    public void addRoom(Room room){
        rooms.add(room);
        this.addNode(room);
    }

    public void addNode(Node node){
        node.index = nodeIndex;
        nodeIndex++;

        nodes.add(node);
    }

    public void linkNodes(Node fromNode, Node toNode){
        Link link = new Link(fromNode, toNode);

        if (!nodeMap.containsKey(fromNode)){
            nodeMap.put(fromNode, new Array<Connection<Node>>());
        }
        nodeMap.get(fromNode).add(link);
        links.add(link);
    }

    public void linkNodes(Node fromNode, Node... args){
        for (Node node : args) {
            this.linkNodes(fromNode, node);
        }
    }

    public GraphPath<Node> findPath(Node startNode, Node goalNode){
        GraphPath<Node> path = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, nodeHeuristic, path);
        return path;
    }

    public Node getNode(int index){
        return nodes.get(index);
    }

    public Node getRandom(Node exception){
        Node node = exception;
        while(node.equals(exception)){
            node = rooms.random();
        }
        Gdx.app.log("Current", exception.getName());
        Gdx.app.log("Goal", node.getName());
        return node;
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if (nodeMap.containsKey(fromNode)){
            return nodeMap.get(fromNode);
        }

        return new Array<>();
    }

    void buildNodeMap(){
        //Declaring the Nodes
        Room bridge = new Room("Bridge", 49.5f, 92.5f, "Bridge", 12, 6);
        Node bridgeExit = new Node(49.5f, 87.5f, "Bridge Exit", 2, 0);
        Node centreNorthWing = new Node(49.5f, 79.5f, "Centre North Wing", 1, 1);

        Room brig = new Room("Brig", 19.5f, 90, "Brig", 12, 2);
        Node brigExit = new Node(19.5f, 87.5f, "Brig Exit", 2, 0);
        Node leftNorthWing = new Node(19.5f, 79.5f, "Left North Wing", 1, 1);

        Room infirmary = new Room("Infirmary", 81.5f, 89.5f, "Infirmary", 12, 3);
        Node infirmaryExit = new Node(81.5f, 84.5f, "Infirmary Exit", 2, 0);
        Node rightNorthWing = new Node(81.5f, 79.5f, "Right North Wing", 1, 1);

        Room topLeftCafeteria = new Room("Cafeteria", 46.5f, 68.5f, "Top Left Cafeteria");
        Room topRightCafeteria = new Room("Cafeteria", 51.5f, 68.5f, "Top Right Cafeteria");
        Room midLeftCafeteria = new Room("Cafeteria", 46.5f, 62.5f, "Mid Left Cafeteria", 1, 1);
        Room midRightCafeteria = new Room("Cafeteria", 51.5f, 62.5f, "Mid Right Cafeteria", 1, 1);
        Room bottomLeftCafeteria = new Room("Cafeteria", 46.5f, 56.5f, "Bottom Left Cafeteria");
        Room bottomRightCafeteria = new Room("Cafeteria", 51.5f, 56.5f, "Bottom Right Cafeteria");
        Node westCafeteriaExit = new Node(40.5f, 62, "West Cafeteria Exit", 0, 1.5f);
        Node eastCafeteriaExit = new Node(58.5f, 62, "East Cafeteria Exit", 0, 1.5f);
        Node northCafeteriaExit = new Node(49.5f, 70.5f, "North Cafeteria Exit", 2, 0);
        Node southCafeteriaExit = new Node(49.5f, 54.5f, "South Cafeteria Exit", 2, 0);

        //PQ stands for personal quarters, that's just a bit long for a name
        Room midRightPQ = new Room("Personal Quarters", 23.5f, 61.5f, "Mid Right PQ");
        Room midCentrePQ = new Room("Personal Quarters", 14.5f, 61.5f, "Mid Centre PQ");
        Room midLeftPQ = new Room("Personal Quarters", 6.5f, 61.5f, "Mid Left PQ");
        Room topRightPQ = new Room("Personal Quarters", 23.5f, 66.5f, "Top Right PQ");
        Room topCentrePQ = new Room("Personal Quarters", 14.5f, 66.5f, "Top Centre PQ");
        Room topLeftPQ = new Room("Personal Quarters", 6.5f, 66.5f, "Top Left PQ");
        Room bottomRightPQ = new Room("Personal Quarters", 23.5f, 56.5f, "Bottom Right PQ");
        Room bottomCentrePQ = new Room("Personal Quarters", 14.5f, 56.5f, "Bottom Centre PQ");
        Room bottomLeftPQ = new Room("Personal Quarters", 6.5f, 56.5f, "Bottom Left PQ");
        Node PQExit = new Node(26.5f, 62, "PQ Exit", 0, 1.5f);

        Room cargo = new Room("Cargo Bay", 81.5f, 62.5f, "Cargo Bay", 2, 3);
        Node northCargoExit = new Node(81.5f, 74.5f, "North Cargo Exit", 2, 0);
        Node westCargoExit = new Node(73.5f, 62f, "West Cargo Exit", 0, 1.5f);
        Node southCargoExit = new Node(81.5f, 53.5f, "South Cargo Exit", 1, 0);

        Room reactor = new Room("Reactor", 49.5f, 37.5f, "Reactor", 9, 11);
        Node northReactorExit = new Node(49.5f, 45.5f, "North Reactor Exit", 2, 0);
        Node eastReactorExit = new Node(55.5f, 37, "East Reactor Exit", 0, 1.5f);

        Room midLeftEngine = new Room("Engine", 78.5f, 36.5f, "Mid Left Engine");
        Room midCentreEngine = new Room("Engine", 83.5f, 36.5f, "Mid Centre Engine");
        Room midRightEngine = new Room("Engine", 91.5f, 36.5f, "Mid Right Engine");
        Room topLeftEngine = new Room("Engine", 78.5f, 45, "Top Left Engine");
        Room topCentreEngine = new Room("Engine", 83.5f, 45, "Top Centre Engine");
        Node northEngineExit = new Node(81.5f, 47.5f, "North Engine Exit", 1, 0);
        Node westEngineExit = new Node(65.5f, 37, "West Engine Exit", 0, 1.5f);

        //Adding the Nodes to the NodeGraph
        addRoom(bridge);
        addNode(bridgeExit);
        addNode(centreNorthWing);

        addRoom(brig);
        addNode(brigExit);
        addNode(leftNorthWing);

        addRoom(infirmary);
        addNode(infirmaryExit);
        addNode(rightNorthWing);

        addRoom(topLeftCafeteria);
        addRoom(topRightCafeteria);
        addRoom(midLeftCafeteria);
        addRoom(midRightCafeteria);
        addRoom(bottomLeftCafeteria);
        addRoom(bottomRightCafeteria);
        addNode(westCafeteriaExit);
        addNode(eastCafeteriaExit);
        addNode(northCafeteriaExit);
        addNode(southCafeteriaExit);

        addRoom(midRightPQ);
        addRoom(midCentrePQ);
        addRoom(midLeftPQ);
        addRoom(topRightPQ);
        addRoom(topCentrePQ);
        addRoom(topLeftPQ);
        addRoom(bottomRightPQ);
        addRoom(bottomCentrePQ);
        addRoom(bottomLeftPQ);
        addNode(PQExit);

        addRoom(cargo);
        addNode(northCargoExit);
        addNode(westCargoExit);
        addNode(southCargoExit);

        addRoom(reactor);
        addNode(northReactorExit);
        addNode(eastReactorExit);

        addRoom(midLeftEngine);
        addRoom(midCentreEngine);
        addRoom(midRightEngine);
        addRoom(topLeftEngine);
        addRoom(topCentreEngine);
        addNode(northEngineExit);
        addNode(westEngineExit);

        //Linking the Nodes on the NodeGraph
        linkNodes(bridge, bridgeExit);
        linkNodes(bridgeExit, bridge, centreNorthWing);
        linkNodes(centreNorthWing, bridgeExit, leftNorthWing, rightNorthWing, northCafeteriaExit);

        linkNodes(brig, brigExit);
        linkNodes(brigExit, brig, leftNorthWing);
        linkNodes(leftNorthWing, centreNorthWing, brigExit);

        linkNodes(infirmary, infirmaryExit);
        linkNodes(infirmaryExit, infirmary, rightNorthWing);
        linkNodes(rightNorthWing, infirmaryExit, centreNorthWing, northCargoExit);

        linkNodes(topLeftCafeteria, northCafeteriaExit, topRightCafeteria, midLeftCafeteria);
        linkNodes(topRightCafeteria, northCafeteriaExit, topLeftCafeteria, midRightCafeteria);
        linkNodes(midLeftCafeteria, westCafeteriaExit, topLeftCafeteria, midRightCafeteria, bottomLeftCafeteria);
        linkNodes(midRightCafeteria, eastCafeteriaExit, topRightCafeteria, midLeftCafeteria, bottomRightCafeteria);
        linkNodes(bottomLeftCafeteria, southCafeteriaExit, midLeftCafeteria, bottomRightCafeteria);
        linkNodes(bottomRightCafeteria, southCafeteriaExit, midRightCafeteria, bottomLeftCafeteria);
        linkNodes(westCafeteriaExit, midLeftCafeteria, PQExit);
        linkNodes(eastCafeteriaExit, midRightCafeteria, westCargoExit);
        linkNodes(northCafeteriaExit, topLeftCafeteria, topRightCafeteria, centreNorthWing);
        linkNodes(southCafeteriaExit, bottomLeftCafeteria, bottomRightCafeteria, northReactorExit);

        linkNodes(midRightPQ, PQExit, midCentrePQ, topRightPQ, bottomRightPQ);
        linkNodes(midCentrePQ, midRightPQ, midLeftPQ);
        linkNodes(midLeftPQ, midCentrePQ, topLeftPQ, bottomLeftPQ);
        linkNodes(topRightPQ, midRightPQ, topCentrePQ);
        linkNodes(topCentrePQ, topRightPQ, topLeftPQ);
        linkNodes(topLeftPQ, midLeftPQ, topCentrePQ);
        linkNodes(bottomRightPQ, midRightPQ, bottomCentrePQ);
        linkNodes(bottomCentrePQ, bottomRightPQ, bottomLeftPQ);
        linkNodes(bottomLeftPQ, midLeftPQ, bottomCentrePQ);
        linkNodes(PQExit, midRightPQ, westCafeteriaExit);

        linkNodes(cargo, northCargoExit, southCargoExit, westCargoExit);
        linkNodes(northCargoExit, cargo, rightNorthWing);
        linkNodes(westCargoExit, cargo, eastCafeteriaExit);
        linkNodes(southCargoExit, cargo, northEngineExit);

        linkNodes(reactor, northReactorExit, eastReactorExit);
        linkNodes(northReactorExit, southCafeteriaExit, reactor);
        linkNodes(eastReactorExit, westEngineExit, reactor);

        linkNodes(midLeftEngine, westEngineExit, topLeftEngine, midCentreEngine);
        linkNodes(midCentreEngine, topCentreEngine, midLeftEngine, midRightEngine);
        linkNodes(midRightEngine, midCentreEngine);
        linkNodes(topLeftEngine, northEngineExit, midLeftEngine, topCentreEngine);
        linkNodes(topCentreEngine, northEngineExit, midCentreEngine, topLeftEngine);
        linkNodes(northEngineExit, southCargoExit, topLeftEngine, topCentreEngine);
        linkNodes(westEngineExit, eastReactorExit, midLeftEngine);
    }
}
