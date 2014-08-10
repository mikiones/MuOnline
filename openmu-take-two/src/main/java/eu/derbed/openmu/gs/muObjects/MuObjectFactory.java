/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.gs.muObjects;

import java.util.Stack;

import eu.derbed.openmu.gs.templates.MuNpc;

/**
 * The MuObjectFactory keep track on all allocations/ deallocation Objects in game
 * speciefed by Object Id
 * @author mikiones
 */
public class MuObjectFactory {

    /**
     * the max amount of object in game
     */
    private static final int MaxObjecs = 0xffff;

    public MuObjectFactory() {
        objectPool = new MuObject[MaxObjecs];
        freeCells = new Stack<>();
        for (int i = 0; i <= MaxObjecs; i++) {
            freeCells.push(i);
        }
    }

    private int cObject = 0;
    private int cPcInstances = 0;
    private int cNPCInstanes = 0;
    private int cItemInstances = 0;
    private int cMobInstances = 0;
    private int cActorInstances=0;

    public void printUsages() {
        System.out.println("The ObjectFactory alloaated [" + cObject + "]Objects:");
        System.out.printf("Players[%d]\n",cPcInstances);
        System.out.printf("Monsters[%d]\n",cMobInstances);
        System.out.printf("NPC's[%d]\n", cNPCInstanes);
        System.out.printf("Items[%d]\n", cItemInstances);
        System.out.printf("Actors[%d]\n", cActorInstances);
    }
    /**
     * the pool of all object null
     */
    private MuObject objectPool[];
    /**
     * Stack of unused ID's
     */
    private Stack<Integer> freeCells = null;

    /**
     * get Object from pool by Id
     * @param obiectId
     * @return null if id not used
     */
    public MuObject getObject(int obiectId) {
        return objectPool[obiectId];
    }

    /**
     *
     * @param objectId
     * @return new created object
     * @throws ObiectIDAllreadyInUse
     */
    public MuObject getNewObiect(int objectId) throws ObjectIDAllreadyInUse {
        if (objectPool[objectId] != null) {
            throw new ObjectIDAllreadyInUse(objectId);
        }
        objectPool[objectId] = new MuObject();
        objectPool[objectId].setObiectId((short) objectId);
        return objectPool[objectId];
    }

    /**
     * 
     * @return the first available id from Stack
     */
    public int getNewId() {
        return freeCells.pop();
    }

    /**
     * return id to pool of unused id's and if object what wos using  this id is still exist free it
     * @param id unused
     */
    public void returnId(int id) {
        if (objectPool[id] != null) {
            objectPool[id] = null;
        }
        freeCells.push(id);
    }

    /**
     * create new PC instance to use
     * @param id the objectId
     * @param _x x position
     * @param _y y position
     * @param _m  map
     * @return new created object
     * @throws ObiectIDAllreadyInUse if provided ID is all ready in use
     */
    public MuPcInstance NewPcInstance(short id, byte _x, byte _y, byte _m) throws ObjectIDAllreadyInUse {
        if (objectPool[id] != null) {
            throw new ObjectIDAllreadyInUse(id);
        }

        objectPool[id] = new MuPcInstance(id, _x, _y, _m);
        cObject++;
        cPcInstances++;

        return (MuPcInstance) objectPool[id];
    }

    /**
     *
     * @return new ItemOn Ground object
     * @throws ObiectIDAllreadyInUse if Id is used
     */
    public MuItemOnGround NewItemOnGround() throws ObjectIDAllreadyInUse {
        int id = getNewId();
        if (objectPool[id] != null) {
            throw new ObjectIDAllreadyInUse(id);
        }

        objectPool[id] = new MuItemOnGround();
        objectPool[id].setObiectId((short) id);

        cObject++;
        cItemInstances++;

        return (MuItemOnGround) objectPool[id];
    }

    /**
     *
     * @param template the monster data
     * @param world
     * @return new Monster object
     * @throws ObiectIDAllreadyInUse if id in use
     */
    public MuMonsterInstance NewMonsterInstance(final MuNpc template, final MuWorld world) throws ObjectIDAllreadyInUse {
        int id = getNewId();
        if (objectPool[id] != null) {
            throw new ObjectIDAllreadyInUse(id);
        }

        objectPool[id] = new MuMonsterInstance(template, world);
        objectPool[id].setObiectId((short) id);

        cObject++;
        cItemInstances++;

        return (MuMonsterInstance) objectPool[id];
    }

    /**
     *
     * @return
     * @throws ObjectIDAllreadyInUse
     */
    public MuPcActorInstance NewActorInstance() throws ObjectIDAllreadyInUse {
        final int id = getNewId();
        if (objectPool[id] != null) {
            throw new ObjectIDAllreadyInUse(id);
        }

        objectPool[id] = new MuPcActorInstance();
        objectPool[id].setObiectId((short) id);

        cObject++;
        cActorInstances++;
        return (MuPcActorInstance) objectPool[id];

    }
}
