package com.uadaf.sgexp

import net.minecraftforge.common.config.Configuration

class SGExpConfig(cfg: Configuration) {

    var testDimId: Int
        private set

    init {
        testDimId = cfg.getInt("testDimId", "dimensions",
                6741, Int.MIN_VALUE, Int.MAX_VALUE,"TestDim ID")
    }

}