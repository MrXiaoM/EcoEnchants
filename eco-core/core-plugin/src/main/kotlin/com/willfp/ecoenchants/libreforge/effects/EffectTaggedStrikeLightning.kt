package com.willfp.ecoenchants.libreforge.effects


import com.willfp.eco.core.config.interfaces.Config
import com.willfp.ecoenchants.plugin
import com.willfp.libreforge.NoCompileData
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.getIntFromExpression
import com.willfp.libreforge.getOrElse
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import org.bukkit.metadata.FixedMetadataValue

object EffectTaggedStrikeLightning : Effect<NoCompileData>("tagged_strike_lightning") {
    override val parameters = setOf(
        TriggerParameter.LOCATION
    )

    override fun onTrigger(config: Config, data: TriggerData, compileData: NoCompileData): Boolean {
        val location = data.location ?: return false
        val world = location.world ?: return false

        val amount = config.getOrElse("amount", 1) { getIntFromExpression(it, data) }

        plugin.scheduler.runLater({
            for (i in 1..amount) {
                world.strikeLightning(location).setMetadata("EcoEnchants", FixedMetadataValue(plugin, true))
            }
        }, 1)

        return true
    }
}
