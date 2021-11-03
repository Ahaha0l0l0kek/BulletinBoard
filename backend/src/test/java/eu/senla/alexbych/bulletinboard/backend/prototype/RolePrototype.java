package eu.senla.alexbych.bulletinboard.backend.prototype;

import eu.senla.alexbych.bulletinboard.backend.model.Role;

public class RolePrototype {
    public static Role aRole(){
        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        return role;
    }
}
