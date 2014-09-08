package net.machinemuse.anima.entity.render

import net.minecraft.client.renderer.entity.{RenderLiving, RenderOcelot, RenderCow}
import net.minecraft.client.model.{ModelBase, ModelOcelot, ModelCow}
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.Entity
import net.machinemuse.numina.geometry.Colour
import net.machinemuse.numina.render.RenderState

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 3:24 AM, 10/16/13
 */

object ModelGreatCow extends ModelCow with ModelGreatSpirit

object RenderGreatCow extends RenderCow(ModelGreatCow, 2.0f /* Shadow size */) with RenderGreatSpirit {
  override val texture = new ResourceLocation("anima:textures/entities/greatcow.png")
}


object ModelGreatOcelot extends ModelOcelot with ModelGreatSpirit

object RenderGreatOcelot extends RenderOcelot(ModelGreatOcelot, 2.0f) with RenderGreatSpirit {
  override val texture = new ResourceLocation("anima:textures/entities/greatocelot.png")
}


trait RenderGreatSpirit extends RenderLiving {
  val texture:ResourceLocation

  override def getEntityTexture(par1Entity: Entity): ResourceLocation = texture
}

trait ModelGreatSpirit extends ModelBase {
  override def render(entity: Entity, par2: Float, par3: Float, par4: Float, par5: Float, par6: Float, par7: Float) {
    RenderState.blendingOn()
    Colour.WHITE.withAlpha(0.5).doGL()
    super.render(entity, par2, par3, par4, par5, par6, par7 * 5.0f)
    Colour.WHITE.doGL()
    RenderState.blendingOff()
  }
}