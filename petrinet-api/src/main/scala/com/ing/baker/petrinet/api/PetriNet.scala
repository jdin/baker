package com.ing.baker.petrinet.api

import scalax.collection.edge.WLDiEdge

/**
 * Petri net class.
  *
 * Backed by a graph object from scala-graph (https://github.com/scala-graph/scala-graph)
 */
class PetriNet[P, T](val innerGraph: BiPartiteGraph[P, T, WLDiEdge]) {

  /**
    * The set of places of the petri net
    *
    * @return The set of places
    */
  lazy val places: Set[P] = innerGraph.places()

  /**
    * The set of transitions of the petri net
    *
    * @return The set of transitions.
    */
  lazy val transitions: Set[T] = innerGraph.transitions()

  /**
    * The out-adjecent places of a transition.
    *
    * @param t transition
    * @return
    */
  def outgoingPlaces(t: T): Set[P] = innerGraph.outgoingPlaces(t)

  /**
    * The out-adjacent transitions of a place.
    *
    * @param p place
    * @return
    */
  def outgoingTransitions(p: P): Set[T] = innerGraph.outgoingTransitions(p)

  /**
    * The in-adjacent places of a transition.
    *
    * @param t transition
    * @return
    */
  def incomingPlaces(t: T): Set[P] = innerGraph.incomingPlaces(t)

  /**
    * The in-adjacent transitions of a place.
    *
    * @param p place
    * @return
    */
  def incomingTransitions(p: P): Set[T] = innerGraph.incomingTransitions(p)

  /**
    * The set of nodes (places + transitions) in the petri net.
    *
    * @return The set of nodes.
    */
  def nodes: scala.collection.Set[Either[P, T]] = innerGraph.nodes.map(_.value)

  /**
    * Returns the in-marking of a transition. That is; a map of place -> arc weight
    *
    * @param t transition
    * @return
    */
  def inMarking(t: T): MultiSet[P] = innerGraph.inMarking(t)

  /**
    * The out marking of a transition. That is; a map of place -> arc weight
    *
    * @param t transition
    * @return
    */
  def outMarking(t: T): MultiSet[P] = innerGraph.outMarking(t)


  /**
    * We override the hashCode function since the scalax.collections.Graph hashCode is non deterministic
    */
  override lazy val hashCode = {

    innerGraph.edges.map(e => (e.source.value, e.target.value, e.weight, e.label)).toSet.hashCode
  }

  override def equals(obj: Any) = {

    obj match {
      case null => false
      case pn: PetriNet[_, _] => pn.innerGraph.equals(innerGraph)
      case _ => false
    }
  }
}