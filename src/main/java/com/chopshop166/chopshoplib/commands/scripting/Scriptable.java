package com.chopshop166.chopshoplib.commands.scripting;

/**
 * An object that can be tied into a scripting engine.
 */
@FunctionalInterface
public interface Scriptable {

    /**
     * Add this object's script capabilities to the provided {@link Engine}
     * 
     * @param engine The engine to store data in.
     */
    void registerScriptable(Engine engine);

}
