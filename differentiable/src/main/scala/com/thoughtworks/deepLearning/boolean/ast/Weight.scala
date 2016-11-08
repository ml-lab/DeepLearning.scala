package com.thoughtworks.deepLearning
package boolean.ast

import cats._
import com.thoughtworks.deepLearning.boolean.utilities.BooleanMonoidBatch
import com.thoughtworks.deepLearning.core.{Differentiable, DifferentiableFunction}

/**
  * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
  */
final case class Weight[Input0 <: Differentiable](var rawValue: scala.Boolean) extends DifferentiableFunction with BooleanMonoidBatch {
  override type Input = Input0
  override type Output = Weight[Input0]

  override def forward(any: Input) = this

  override def backward(delta: Delta): Unit = {
    rawValue ^= delta.value
  }

  override def value = Eval.now(rawValue)

  override def close(): Unit = {}

}
