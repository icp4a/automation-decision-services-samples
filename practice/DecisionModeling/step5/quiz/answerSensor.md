### Hmm, this is a strange answer!

The decision logic of the sensor format does not use the type
`requirement` in any way. Its decision node has the type `sensor
format`. The logic depends on the nodes `budget`, `subject`, `level`,
which all have primitive types. So none of these nodes involves the
type `requirement`. For this reason, changes of this type
cannot impact the decision logic of the sensor format.

[Review the previous step](../description.md)
