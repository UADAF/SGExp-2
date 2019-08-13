package com.uadaf.sgexp.coremod;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class Hooks {


    public static boolean matchDims(Entity e, TileEntity te) {
        return e.dimension == te.getWorld().provider.getDimension();
    }

}
