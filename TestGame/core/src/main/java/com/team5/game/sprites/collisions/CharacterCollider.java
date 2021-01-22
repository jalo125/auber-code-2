package com.team5.game.sprites.collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team5.game.tools.Constants;

public class CharacterCollider {

    /*
    CharacterCollider just sets ups the collider for NPCs,
    infiltrators, and the player.
     */

    //Creates and returns a collider for the NPC, Infiltrators and the Player.
    public Body defineCollider(World world, Vector2 position, float size) {
        Body b2body;
        BodyDef bodDef = new BodyDef();
        bodDef.position.set(position.x, position.y);
        bodDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f);
        shape.setPosition(new Vector2(size / 2, size / 2));
        fixDef.shape = shape;

        fixDef.filter.groupIndex = Constants.GROUP_PLAYER;

        b2body.createFixture(fixDef);

        return b2body;
    }

}
