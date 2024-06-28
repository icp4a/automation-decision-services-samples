
### Composite types

A dependency diagram can have many nodes. If too many nodes exist, the diagram is
difficult to read. Nodes that have the same direct predecessors and
successors can be regrouped into a single node to reduce the
diagram in size.

The diagram for camera recommendation has several nodes that list
different characteristics of the required camera. There might be many
more such as different kind of connections (for example, microphone input,
headphone output, USB, and other types), the speed of the card slots, and so
on. All these nodes have the same connectivity with respect to other
nodes in the diagram:

1. They have no ingoing dependency link as they are input data nodes.
2. They have a single outgoing dependency link to the node for the
   camera decision.

As these nodes have the same direct predecessors and the same direct
successors in the dependency graph, you can replace them by a single
node without changing the dependency relation.

For example, you can replace the input data nodes for the weight
limit and the tilt screen option by a single node called
`requirement`. But what is the value of this new node? It is a
composite value that consists of two components. These components are

 - a weight limit which might be one of the values `250 g`, `500 g`,
   `1000 g`.
 - a Boolean value that indicates whether a tilt screen is wanted or not.
 
To use such a composite value in Automation Decision Services, a composite type needs
to be defined. A composite type can have one or more attributes, which
are used to describe the components of the composite value. Each
attribute has a type. For example, you can introduce a composite type
`requirement` that has the following two attributes:

 - `weight limit` of type `weight limit`.
 - `equipped with tilt screen` of type `Boolean`.

The node `requirement` has a value of type `requirement`
and thus replaces the two nodes `weight limit` and `tilt screen`
without any loss of information. The new diagram is more compact and
easier to read. Furthermore, it is also more robust with respect to
changes. If new requirements about the camera are added, it is not
necessary to add new input data nodes for these
requirements. Instead, it is sufficient to add attributes
to the type `requirement`.

Interestingly, the regrouping of the two nodes `weight limit` and
`tilt screen` does not require any substantial change of the decision
table of the camera model. The content of the table can be
preserved:

![Camera DT](resources/camera-decision-table.png)

However, the weight limit and the tilt screen option are no longer
values of input data nodes, but attributes of a value of an input data
node. As such, they are not accessed in the same way as before in the
decision table. The previous decision table accesses the values through
the following expressions (which are defined in the headers of the
respective condition columns):

    'weight limit' is <a weight limit> 
    'tilt screen' is <a Boolean>
    
The new decision table accesses the attribute values
by using an expression of the form `<attribute> of <variable>`. It is done in the following way: 
  
    the weight limit of 'requirement' is <a weight limit> 
    'requirement' is equipped with tilt screen is <a Boolean>

This expression can also be used in rules. For example, the
following rule suggests a camera model `m1` if the attributes have specific values. The `weight
limit` of the value of node `requirement` has the value `250 g`. The
attribute `equipped with tilt screen` of the value of node
`requirement` has the value `true`, and the value of node `sensor
format` is `Micro Four Thirds`:

    if
        the weight limit of 'requirement' is 250 g
        and 'requirement' is equipped with tilt screen
        and 'sensor format' is Micro Four Thirds
    then
        set decision to "m1" ;

#### Exercise

Refactor the decision model by regrouping the input data nodes `weight
limit` and `tilt screen`:

 - Define a composite type `requirement` with two attributes
   `weight limit` and `equipped with tilt screen`.
 - Add an input data node `requirement` of type  `requirement`.
 - Delete the input data nodes `weight limit` and `tilt screen`.
 - Ensure that the camera decision depends on the requirement as indicated by the diagram:
 
![Dependency graph](resources/dependency-diagram.png)

Adapt the decision table of the camera decision node:

 - Change the definition of the weight limit column into `the weight limit of 'requirement' is <a weight limit>`.
 - Change the definition of the tilt screen column into `'requirement' is equipped with tilt screen is <a Boolean>`.
 
#### Lessons learned
 
It is possible to express sophisticated decision models in Automation Decision Services
with primitive types alone. So far, the main reason for using
composite types is to regroup nodes with the same predecessors and
successors. Hence, those composite types are not defined before you
create the diagram, but during this process!

You saw an example for regrouping input data nodes, but it might
also be useful to regroup several decision nodes. However, it can
be effective only if the decision logics of these decision nodes are
similar, that is, consider the same families of cases. If not, it is
necessary to split those families into smaller subfamilies, thus
increasing their total number. It will make the decision logic more
complex than necessary.

#### Limitations

In the previous example, you regrouped multiple values such as the weight
limit and the tilt screen option into a composite value. This
composite value has two components, each of which an
attribute of the composite type describes. A composite type has a fixed number
of attributes, meaning that the number of components of a
corresponding composite value is fixed as well.

More complex problems might involve composite values that consist of an
arbitrary number of components. A good example is a discounting
problem for a shopping cart that consists of an arbitrary number of
items. To compute the total price of the shopping cart, a
discount needs to be applied to each of its items before. The [next step](quiz/quiz.md)
introduces methods to address problems that involve an unknown number
of components.


[Back to ADS concepts step by step](../README.md)
