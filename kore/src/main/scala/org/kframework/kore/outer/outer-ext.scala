// Copyright (c) 2014 K Team. All Rights Reserved.

package org.kframework.kore.outer

import org.kframework.kore._

case class Configuration(
  body: K,
  ensures: K,
  att: Attributes = Attributes()) extends Sentence {

  override def toString = "configuration " + xmlify(body) + " ensures " + ensures

  def xmlify(x: K): String = x match {
    case KApply(label, klist: KList, att) if att.contains("cell") => {
      val atts = att.att.filterNot(_ == Configuration.cellMarker)

      val attsString = if (atts.size > 0)
        " " + atts.map(xmlifyAttributes).mkString(" ")
      else
        ""

      "<" + label.name + attsString + ">" +
        klist.map(xmlify _).mkString(" ") +
        "<" + label.name + ">"
    }
    case e => e.toString
  }

  def xmlifyAttributes(x: K): String = x match {
    case KApply(label, klist: KList, att) => label.name +
      (if (!klist.isEmpty)
        "=" + klist.map({ e: K => "\"" + e + "\"" }).mkString(" ")
      else
        "")
  }
}

object Configuration {
  val cellMarker = KApply(KLabel("cell"), KList());
}

case class Bubble(ty: String, contents: String, att: Attributes = Attributes()) extends Sentence
