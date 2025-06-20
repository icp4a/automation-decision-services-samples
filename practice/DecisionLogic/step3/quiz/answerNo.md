### Not quite right!

Can the decision logic be implemented if no rule has the condition `sensor format is undefined`?

There must be a rule that assigns the value “No camera can be proposed”:
- This rule cannot have the condition `true`. Else, it would be always applied if it was the first rule.
- This rule cannot be the default rule since the default rule should assign “A camera can be proposed”.
- The rule cannot compare sensor format with some value as this will assign the value “No camera can be proposed” to `message` for some of the sensor formats.

Therefore, the answer is no.

[Review the previous step](../description.md)
