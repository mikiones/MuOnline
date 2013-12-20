/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.gs.muObjects;

/**
 *
 * @author mikiones
 */
class ObjectIDAllreadyInUse extends Throwable {

    private int id;

    public ObjectIDAllreadyInUse(int obiectId) {
        id = obiectId;
    }

    public int getID() {
        return id;
    }
}
