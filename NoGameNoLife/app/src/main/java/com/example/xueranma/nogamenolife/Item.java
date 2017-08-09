package com.example.xueranma.nogamenolife;

/**
 * Created by xueranma on 7/23/17.
 */

public class Item {
    private String description = null;
    private int value = 0;
    private boolean check = false;

    public void setDescription(String description){this.description =description;}

    public String getDescription(){return this.description;}

    public void setValue(int value){this.value = value;}

    public int getValue(){return value;}

    public void setCheckTrue(){this.check = true;}

    public boolean getCheck(){return check;}

}
