package net.machinemuse.anima.entity.render

import org.lwjgl.opengl.GL11._
import net.machinemuse.numina.render.{BillboardHelper, RenderState, MuseTextureUtils}
import net.minecraft.entity.Entity
import net.minecraft.client.renderer.entity.Render
import net.minecraft.util.ResourceLocation
import net.machinemuse.numina.geometry.Colour

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 8:01 PM, 11/12/13
 */
class BillboardRenderer(val resource: String, alpha:Double) extends Render {
  val colour = Colour.WHITE.withAlpha(alpha)
  override def doRender(entity: Entity, x: Double, y: Double, z: Double, yaw: Float, partialticktime: Float) {
    glPushMatrix()
    glPushAttrib(GL_ENABLE_BIT)
    glDisable(GL_CULL_FACE)
    RenderState.glowOn()
    RenderState.blendingOn()
    MuseTextureUtils.pushTexture(resource)
    glTranslated(x, y, z)
    val scale: Double = 0.5
    glScaled(scale, scale, scale)
    BillboardHelper.unRotate
    colour.doGL()
    glBegin(GL_QUADS)
    glTexCoord2d(0, 0)
    glVertex3d(1, 1, 0)
    glTexCoord2d(0, 1)
    glVertex3d(1, -1, 0)
    glTexCoord2d(1, 1)
    glVertex3d(-1, -1, 0)
    glTexCoord2d(1, 0)
    glVertex3d(-1, 1, 0)
    glEnd()
    Colour.WHITE.doGL()
    MuseTextureUtils.popTexture()
    RenderState.blendingOff()
    RenderState.glowOff()
    glPopAttrib()
    glPopMatrix()
  }

  protected def getEntityTexture(entity: Entity): ResourceLocation = new ResourceLocation(resource)
}
