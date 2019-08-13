package com.uadaf.sgexp.coremod.asm

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions("kotlin", "com.uadaf.sgexp.coremod.asm", "net.shadowfacts.forgelin")
class SGExpCoremod : IFMLLoadingPlugin {

    override fun getModContainerClass() = null

    override fun getASMTransformerClass() = arrayOf("com.uadaf.sgexp.coremod.asm.SGExpASM")

    override fun getSetupClass(): String? = null

    override fun injectData(data: MutableMap<String, Any>?) {}

    override fun getAccessTransformerClass() = null


}