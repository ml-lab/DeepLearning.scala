package com.thoughtworks.deepLearning.boolean.ast

import cats._
import com.thoughtworks.deepLearning.{Batch, BatchId, NeuralNetwork}
import com.thoughtworks.deepLearning.NeuralNetwork.Cached
import com.thoughtworks.deepLearning.boolean.utilities._

/**
  * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
  */
final case class Not[Input0 <: Batch](differentiableBoolean: NeuralNetwork.Aux[Input0, Boolean#Batch])
    extends Cached {

  protected final class SharedBatch private[deepLearning] (override val input: BatchId.Aux[Input0],
                                                           upstream: Boolean#Batch)
      extends MonoidBatch
      with BooleanMonoidBatch {
    type Input >: Input0
    val value = upstream.value.map(!_)

    override protected def rawBackward(delta: Eval[scala.Boolean]): Unit = {
      upstream.backward(delta.map(!_))
    }

    override protected def closeUpstreams(): Unit = {
      upstream.close()
    }
  }

  type Input = Input0

  override protected def rawForward(input: BatchId.Aux[Input0]): SharedBatch = {
    val upstream = differentiableBoolean.forward(input)
    new SharedBatch(input, upstream.open())
  }
}
