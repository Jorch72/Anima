package net.machinemuse.utils

import net.minecraft.util.{StatCollector, StringTranslate}
import java.io._
import net.machinemuse.general.MuseLogger
import java.util.Properties
import cpw.mods.fml.common.registry.LanguageRegistry
import net.minecraft.client.Minecraft

/**
 * Author: MachineMuse (Claire Semple)
 * Created: 9:34 PM, 6/28/13
 */
object Localization {
  val LANG_PATH = "/mods/Anima/lang/"
  var extractedLanguage = ""

  def getCurrentLanguage = Minecraft.getMinecraft().func_135016_M().func_135041_c().func_135034_a()

  def loadCurrentLanguage() {
    if (getCurrentLanguage != extractedLanguage) {
      extractedLanguage = getCurrentLanguage
      try {
        val inputStream: InputStream = this.getClass.getResourceAsStream(LANG_PATH + extractedLanguage + ".lang")
        val langPack: Properties = new Properties
        langPack.load(inputStream)
        LanguageRegistry.instance.addStringLocalization(langPack, extractedLanguage)
      } catch {
        case e: Exception => {
          e.printStackTrace()
          MuseLogger.logError("Couldn't read Anima localizations for language " + extractedLanguage + " :(")
        }
      }
    }
  }

  def translate(str: String) = {
    loadCurrentLanguage()
    StatCollector.translateToLocal(str)
  }
}
