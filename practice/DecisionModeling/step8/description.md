
### One final thing!

The previous steps introduced a dependency diagram that have two
decision nodes `camera` and `lenses` that together describe a camera
system. It will be convenient to introduce a single decision node that
represents this camera system.

This is simple to achieve. A new decision node `camera system` will be
used for this purpose. This node has a composite value consisting of
the camera model and the lens model. A new type `camera system` will
be introduced for this purpose. It has two attributes:

 - A single-valued attribute `camera model` of type `string`.
 - A multi-valued attribute `lens models` of type `lens model`.

The decision node `camera system` depends on the decision nodes
`camera` and `lenses`. Its decision logic is very simple and just
consists of a rule for creating the composite value:

    set decision to a new camera system where 
	    the lens models are lenses , 
	    the camera model is camera ;
        
It is a good practice to introduce new types for decision nodes that
have composite values!

This final step results into the following dependency graph, which has
a unique final node representing the result of the recommendation
service:
        
![Dependency graph](resources/final-dependency-diagram.png)



[Back to ADS concepts step by step](../README.md)
