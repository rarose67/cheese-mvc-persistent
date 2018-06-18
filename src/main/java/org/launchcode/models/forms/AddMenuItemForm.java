package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class AddMenuItemForm {

    private Menu menu;

    private Iterable<Cheese> cheeses;

    @NotNull
    private int menuId;

    @NotNull
    private int cheeseId;

    public AddMenuItemForm() {
    }

    public AddMenuItemForm(Menu aMenu, Iterable<Cheese> aCheeses) {
        this.menu = aMenu;
        this.cheeses = aCheeses;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu aMenu) {
        this.menu = aMenu;
    }

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(Iterable<Cheese> aCheeses) {
        this.cheeses = aCheeses;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int aMenuId) {
        this.menuId = aMenuId;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public void setCheeseId(int aCheeseId) {
        this.cheeseId = aCheeseId;
    }
}
