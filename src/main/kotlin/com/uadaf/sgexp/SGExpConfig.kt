package com.uadaf.sgexp

import net.minecraftforge.common.config.Configuration

class SGExpConfig(cfg: Configuration) {

    var minId: Int
        private set

    var allowNegative: Boolean
        private set

    init {
        minId = cfg.getInt("minId", "dimensions",
                5000, 2, Int.MAX_VALUE,"Minimum ID for dimension. If negative id's are enabled, this also works as 'max negative id', so id's will be in [MIN_VALIE, -this] + [this, MAX_VALUE]")
        allowNegative = cfg.getBoolean("allowNegativeId", "dimensions", true, "Allow negative id's for dimensions")
    }

}