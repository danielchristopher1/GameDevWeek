package de.hochschuletrier.gdw.ws1314.game;

import com.badlogic.gdx.math.Vector2;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixManager;
import de.hochschuletrier.gdw.commons.tiled.Layer;
import de.hochschuletrier.gdw.commons.tiled.LayerObject;
import de.hochschuletrier.gdw.commons.tiled.SafeProperties;
import de.hochschuletrier.gdw.commons.tiled.TiledMap;
import de.hochschuletrier.gdw.commons.utils.Point;
import de.hochschuletrier.gdw.ws1314.entity.ServerEntity;
import de.hochschuletrier.gdw.ws1314.entity.ServerEntityManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jerry on 18.03.14.
 */
public class LevelLoader {
//    private static ServerEntityManager entityManager;
//    private static PhysixManager physicsManager;
//    private static Vector2 startpos;
//    private static TiledMap map;
//
//    public static void load(TiledMap map, ServerEntityManager entityManager,
//                            PhysixManager physicsManager) {
//        LevelLoader.map = map;
//
//        LevelLoader.entityManager = entityManager;
//        LevelLoader.physicsManager = physicsManager;
//        entityManager.Clear();
//        physicsManager.reset();
//
//        for (Layer layer : map.getLayers()) {
//            if (layer.isObjectLayer()) {
//                loadObjectLayer(layer);
//            }
//        }
//
//        //entityManager.initalUpdate();
//        //conntactInteractions();
//        //setTeleporterTargets();
//    }
//
//    private static void loadObjectLayer(Layer layer) {
//        for (LayerObject object : layer.getObjects()) {
//            String type = object.getType();
//            if (object.getPrimitive() == LayerObject.Primitive.TILE)
//                type = object.getProperty("type", null);
//            if (type == null) {
//                System.err.println("Warning: type missing for object!");
//                continue;
//            }
//            if(object.getProperties() != null) {
//                object.getProperties().setString("renderLayer", layer.getProperty("renderLayer", String.valueOf(layer.getIndex())));
//            }
////            object.getProperties().setProperty("renderLayer", String.valueOf());
//            switch (object.getPrimitive()) {
//                case POINT:
//                    createPoint(type, object.getX(), object.getY(),
//                            object.getProperties());
//                    break;
//                case RECT:
//                    createRect(type, object.getX(), object.getY(),
//                            object.getWidth(), object.getHeight(),
//                            object.getProperties());
//                    break;
//                case TILE:
//
//                        createTile(type, object.getX(), object.getLowestY(),
//                                object.getWidth(), object.getHeight(),
//                                object.getProperties(), object.getName(), object.getGid());
//                    break;
//                case POLYGON:
//                    createPolygon(type, object.getPoints(), object.getProperties());
//                    break;
//                case POLYLINE:
//                    createPolyLine(object.getType(), object.getPoints(),
//                            object.getProperties(), object.getName());
//                    break;
//            }
//        }
//    }
//
//    /**
//     * Create ground, paths, etc here
//     *
//     * @param type
//     *            the type set in the editor
//     * @param points
//     *            the points of the line (absolute points)
//     * @param properties
//     *            the object properties
//     */
//    private static void createPolyLine(String type, ArrayList<Point> points,
//                                       SafeProperties properties, String name) {
//        switch (type) {
//            case "solid":
//                physicsManager.createSolid(0, 0).asPolyline(points);
//                break;
//        }
//    }
//
//    /**
//     * Create deadzones, triggers, etc here
//     *
//     * @param type
//     *            the type set in the editor
//     * @param points
//     *            the points of the line (absolute points)
//     * @param properties
//     *            the object properties
//     */
//    private static void createPolygon(String type, ArrayList<Point> points,
//                                      SafeProperties properties) {
//        switch (type) {
//            case "solid":
//                physicsManager.createSolid(0, 0).asPolygon(points);
//                break;
//            case "activator":
//            case "deadzone":
//            case "winningzone":
//                AbstractZone zone = (AbstractZone)entityManager.createEntity(type, properties);
//                zone.setPoints(points);
//                break;
//        }
//    }
//
//    /**
//     * Create rectangle deadzones, triggers, etc here
//     *
//     * @param type
//     *            the type set in the editor
//     * @param x
//     *            the distance from left in pixels
//     * @param y
//     *            the distance from top in pixels
//     * @param width
//     *            width in pixels
//     * @param height
//     *            height in pixels
//     * @param properties
//     *            the object properties
//     */
//    private static void createRect(String type, int x, int y, int width,
//                                   int height, SafeProperties properties) {
//        x += width/2;
//        y += height/2;
//
//        ServerEntity entity = null;
//        switch (type) {
//            case "solid":
//                physicsManager.createSolid(x, y).asBox(width, height);
//                break;
//            case "activator":
//            case "deadzone":
//            case "winningzone":
//                entity = entityManager.createEntity(type, properties);
//                break;
//            default:
//                System.err.println("Unknown Rect-Object in Map, type: " + type);
//                break;
//        }
//
//        if(entity != null) {
//            entity.setOrigin(x, y);
//            entity.setInitialSize(width, height);
//        }
//    }
//
//    /**
//     * Create items, enemies, etc here
//     *
//     * @param type
//     *            the type set in the editor
//     * @param x
//     *            the distance from left in pixels
//     * @param y
//     *            the distance from top in pixels
//     * @param width
//     *            width in pixels, NOT ACCURATE!!
//     * @param height
//     *            height in pixels, NOT ACCURATE!!
//     * @param properties
//     *            the object properties
//     */
//    private static void createTile(String type, int x, int y, int width, int height, SafeProperties properties, String name, int gid) {
//
//        x += width / 2;
//        y += height / 2;
//
//        ServerEntity entity = null;
//        switch (type) {
//            /*case "decoration":
//                String animation = properties.getProperty("animation");
//                if(animation == null) {
//                    entity = entityManager.createEntity("decorationtile", properties, name);
//                    TileSet tileset = map.findTileSet(gid);
//                    int id = gid - tileset.getFirstGID();
//                    int srcX = tileset.getTileX(id);
//                    int srcY = tileset.getTileY(id);
//                    ((DecorationTile)entity).setTiledImage((TiledImage)tileset.getAttachment());
//                    ((DecorationTile)entity).setSrcPos(srcX, srcY);
//                } else {
//                    entity = entityManager.createEntity("decorationanimation", properties, name);
//                }
//                break;*/
//
//            default:
//                entity = entityManager.createEntity(type, properties, name);
//                break;
//        }
//
//        if(entity != null) {
//            if(entity.isBottomPositioned()) {
//                y += height/2;
//            }
//            entity.setOrigin(x, y);
//            entity.setInitialSize(width, height);
//        }
//    }
//
//    /**
//     * Currently no plan for use
//     *
//     * @param type
//     *            the type set in the editor
//     * @param x
//     *            the distance from left in pixels
//     * @param y
//     *            the distance from top in pixels
//     * @param properties
//     *            the object properties
//     */
//    private static void createPoint(String type, int x, int y,
//                                    SafeProperties properties) {
//    }




}
