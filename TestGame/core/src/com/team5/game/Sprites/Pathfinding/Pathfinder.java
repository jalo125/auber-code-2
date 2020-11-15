package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;

public class Pathfinder {

    /*
    Pathfinder is obsolete at the moment
     */

    NodeGraph  nodeGraph;
    GraphPath<Node> nodePath;

    public void Pathfinder(){
        nodeGraph = new NodeGraph();

        buildNodeMap();

        Gdx.app.log("debug", nodeGraph.nodes.toString());

    }

    public int noNodes(){
        return nodeGraph.getNodeCount();
    }

    public Node getNode(int index){
        if (nodeGraph.getNodeCount() > index) {
            return nodeGraph.getNode(index);
        } else {
            return null;
        }
    }

    public GraphPath<Node> findPath(Node start, Node goal){
        return nodeGraph.findPath(start, goal);
    }

    void buildNodeMap(){
        Node lowerEngine = new Node(144, 320, "Lower Engine");
        Node westWing = new Node(144, 640, "West Wing");
        Node upperEngine = new Node(144, 800, "Upper Engine");
        Node reactor = new Node(80, 640, "Reactor");
        Node security = new Node(224, 640, "Security");
        Node northWing = new Node(304, 800, "North Wing");
        Node medical = new Node(304, 720, "Lower Engine");
        Node upperCafe = new Node(480, 800, "Upper Cafeteria");
        Node lowerCafe = new Node(480, 704, "Lower Cafeteria");
        Node weapons = new Node(752, 704, "Weapons");
        Node upperEastWing = new Node(752, 624, "Upper East Wing");
        Node oxygen = new Node(656, 624, "Oxygen");
        Node navigation = new Node(848, 528, "Navigation");
        Node lowerEastWing = new Node(752, 528, "Lower East Wing");
        Node shields = new Node(752, 352, "Lower Engine");
        Node southEastWing = new Node(624, 352, "South East Wing");
        Node communication = new Node(624, 272, "Communication");
        Node upperStorage = new Node(480, 352, "Upper Storage");
        Node lowerStorage = new Node(480, 320, "Lower Storage");
        Node southWestWing = new Node(288, 320, "South West Wing");
        Node electrical = new Node(288, 416, "Electrical");
        Node centralWing = new Node(480, 528, "Central Wing");
        Node admin = new Node(640, 528, "Admin");

        nodeGraph.addNode(lowerEngine);
        nodeGraph.addNode(westWing);
        nodeGraph.addNode(upperEngine);
        nodeGraph.addNode(reactor);
        nodeGraph.addNode(security);
        nodeGraph.addNode(northWing);
        nodeGraph.addNode(medical);
        nodeGraph.addNode(upperCafe);
        nodeGraph.addNode(lowerCafe);
        nodeGraph.addNode(weapons);
        nodeGraph.addNode(upperEastWing);
        nodeGraph.addNode(oxygen);
        nodeGraph.addNode(navigation);
        nodeGraph.addNode(lowerEastWing);
        nodeGraph.addNode(shields);
        nodeGraph.addNode(southEastWing);
        nodeGraph.addNode(communication);
        nodeGraph.addNode(upperStorage);
        nodeGraph.addNode(lowerStorage);
        nodeGraph.addNode(southWestWing);
        nodeGraph.addNode(electrical);
        nodeGraph.addNode(centralWing);
        nodeGraph.addNode(admin);

        nodeGraph.linkNodes(lowerEngine, westWing);
        nodeGraph.linkNodes(lowerEngine, southWestWing);
        nodeGraph.linkNodes(westWing, reactor);
        nodeGraph.linkNodes(westWing, security);
        nodeGraph.linkNodes(westWing, lowerEngine);
        nodeGraph.linkNodes(westWing, upperEngine);
        nodeGraph.linkNodes(upperEngine, westWing);
        nodeGraph.linkNodes(upperEngine, northWing);
        nodeGraph.linkNodes(reactor, westWing);
        nodeGraph.linkNodes(security, westWing);
        nodeGraph.linkNodes(northWing, upperEngine);
        nodeGraph.linkNodes(northWing, medical);
        nodeGraph.linkNodes(northWing, upperCafe);
        nodeGraph.linkNodes(medical, northWing);
        nodeGraph.linkNodes(upperCafe, northWing);
        nodeGraph.linkNodes(upperCafe, lowerCafe);
        nodeGraph.linkNodes(lowerCafe, upperCafe);
        nodeGraph.linkNodes(lowerCafe, weapons);
        nodeGraph.linkNodes(lowerCafe, centralWing);
        nodeGraph.linkNodes(weapons, lowerCafe);
        nodeGraph.linkNodes(weapons, upperEastWing);
        nodeGraph.linkNodes(upperEastWing, weapons);
        nodeGraph.linkNodes(upperEastWing, oxygen);
        nodeGraph.linkNodes(upperEastWing, lowerEastWing);
        nodeGraph.linkNodes(oxygen, upperEastWing);
        nodeGraph.linkNodes(navigation, lowerEastWing);
        nodeGraph.linkNodes(lowerEastWing, upperEastWing);
        nodeGraph.linkNodes(lowerEastWing, navigation);
        nodeGraph.linkNodes(lowerEastWing, shields);
        nodeGraph.linkNodes(shields, lowerEastWing);
        nodeGraph.linkNodes(shields, southEastWing);
        nodeGraph.linkNodes(southEastWing, shields);
        nodeGraph.linkNodes(southEastWing, communication);
        nodeGraph.linkNodes(southEastWing, upperStorage);
        nodeGraph.linkNodes(communication, southEastWing);
        nodeGraph.linkNodes(upperStorage, centralWing);
        nodeGraph.linkNodes(upperStorage, southEastWing);
        nodeGraph.linkNodes(upperStorage, lowerStorage);
        nodeGraph.linkNodes(lowerStorage, upperStorage);
        nodeGraph.linkNodes(lowerStorage, southWestWing);
        nodeGraph.linkNodes(southWestWing, lowerStorage);
        nodeGraph.linkNodes(southWestWing, electrical);
        nodeGraph.linkNodes(southWestWing, lowerEngine);
        nodeGraph.linkNodes(electrical, southWestWing);
        nodeGraph.linkNodes(centralWing, upperStorage);
        nodeGraph.linkNodes(centralWing, lowerCafe);
        nodeGraph.linkNodes(admin, centralWing);
    }

}
