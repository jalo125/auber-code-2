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
        Room lowerEngine = new Room(128, 304, "Lower Engine");
        Node westWing = new Node(128, 624, "West Wing");
        Room upperEngine = new Room(128, 784, "Upper Engine");
        Room reactor = new Room(64, 624, "Reactor");
        Room security = new Room(208, 624, "Security");
        Node northWing = new Node(288, 784, "North Wing");
        Room medical = new Room(288, 704, "Medical");
        Room upperCafe = new Room(464, 784, "Upper Cafeteria");
        Room lowerCafe = new Room(464, 688, "Lower Cafeteria");
        Room weapons = new Room(736, 688, "Weapons");
        Node upperEastWing = new Node(736, 608, "Upper East Wing");
        Room oxygen = new Room(640, 608, "Oxygen");
        Room navigation = new Room(832, 512, "Navigation");
        Node lowerEastWing = new Node(736, 512, "Lower East Wing");
        Room shields = new Room(736, 336, "Shields");
        Node southEastWing = new Node(608, 336, "South East Wing");
        Room communication = new Room(608, 256, "Communication");
        Room upperStorage = new Room(464, 352, "Upper Storage");
        Room lowerStorage = new Room(464, 304, "Lower Storage");
        Node southWestWing = new Node(272, 304, "South West Wing");
        Room electrical = new Room(272, 400, "Electrical");
        Node centralWing = new Node(464, 512, "Central Wing");
        Room admin = new Room(624, 512, "Admin");

        addRoom(lowerEngine);
        addNode(westWing);
        addRoom(upperEngine);
        addRoom(reactor);
        addRoom(security);
        addNode(northWing);
        addRoom(medical);
        addRoom(upperCafe);
        addRoom(lowerCafe);
        addRoom(weapons);
        addNode(upperEastWing);
        addRoom(oxygen);
        addRoom(navigation);
        addNode(lowerEastWing);
        addRoom(shields);
        addNode(southEastWing);
        addRoom(communication);
        addRoom(upperStorage);
        addRoom(lowerStorage);
        addNode(southWestWing);
        addRoom(electrical);
        addNode(centralWing);
        addRoom(admin);

        linkNodes(lowerEngine, westWing);
        linkNodes(lowerEngine, southWestWing);
        linkNodes(westWing, reactor);
        linkNodes(westWing, security);
        linkNodes(westWing, lowerEngine);
        linkNodes(westWing, upperEngine);
        linkNodes(upperEngine, westWing);
        linkNodes(upperEngine, northWing);
        linkNodes(reactor, westWing);
        linkNodes(security, westWing);
        linkNodes(northWing, upperEngine);
        linkNodes(northWing, medical);
        linkNodes(northWing, upperCafe);
        linkNodes(medical, northWing);
        linkNodes(upperCafe, northWing);
        linkNodes(upperCafe, lowerCafe);
        linkNodes(lowerCafe, upperCafe);
        linkNodes(lowerCafe, weapons);
        linkNodes(lowerCafe, centralWing);
        linkNodes(weapons, lowerCafe);
        linkNodes(weapons, upperEastWing);
        linkNodes(upperEastWing, weapons);
        linkNodes(upperEastWing, oxygen);
        linkNodes(upperEastWing, lowerEastWing);
        linkNodes(oxygen, upperEastWing);
        linkNodes(navigation, lowerEastWing);
        linkNodes(lowerEastWing, upperEastWing);
        linkNodes(lowerEastWing, navigation);
        linkNodes(lowerEastWing, shields);
        linkNodes(shields, lowerEastWing);
        linkNodes(shields, southEastWing);
        linkNodes(southEastWing, shields);
        linkNodes(southEastWing, communication);
        linkNodes(southEastWing, upperStorage);
        linkNodes(communication, southEastWing);
        linkNodes(upperStorage, centralWing);
        linkNodes(upperStorage, southEastWing);
        linkNodes(upperStorage, lowerStorage);
        linkNodes(lowerStorage, upperStorage);
        linkNodes(lowerStorage, southWestWing);
        linkNodes(southWestWing, lowerStorage);
        linkNodes(southWestWing, electrical);
        linkNodes(southWestWing, lowerEngine);
        linkNodes(electrical, southWestWing);
        linkNodes(centralWing, upperStorage);
        linkNodes(centralWing, lowerCafe);
        linkNodes(centralWing, admin);
        linkNodes(admin, centralWing);
    }
}
