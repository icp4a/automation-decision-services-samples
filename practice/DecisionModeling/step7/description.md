
### Applying a decision model to many values

The [previous step](../step6/description.md)
has shown how a decision table can be applied to all values of a
multi-valued variable. This has been achieved with the help of a
precondition that iterates over all values of the variable `'lens
requirements'`. In each iteration, the decision table is applied to
the selected value. For example, if the variable `'lens requirements'`
has the values `standard lens` and `super-telephoto lens`, the
decision table is applied to both of them. The decision table
developed in Step 6 has a dedicated column `lens requirements` in
order to make a decision depending on the considered lens type:

![Lens DT](resources/lens-decision-table.png)

In [Step 4](../step4/description.md),
a complex decision logic has been decomposed into several pieces with
the help of intermediate decisions. The same principle can be applied
to a decision logic that is applied to all values of a multi-valued
variable. However, the latter will lead to an additional difficulty,
which requires a new modeling construct.

To illustrate this, we enrich the decision logic for choosing a lens
model. In the following cases, a lens with a fixed focal length should
be chosen instead of a zoom lens:

1. A professional photographer who is taking portraits.
2. A super-telephoto lens that is used for sports photography.

According to these new rules, the decision about lens models depends
on the subject and the photographer level in addition to the sensor
format and the lens type. The dependency graph is therefore enriched
by the corresponding dependencies.

![Dependency graph](resources/dependency-diagram1.png)

Based on this, two new columns for the level and subject are added to
the decision table. For standard lenses, telephoto lenses, wide-angle
lenses, and super wide-angle lenses, a lens with fixed focal length
will be proposed to professional photographers doing portraits. In all
other cases, a zoom lens will be proposed.

For super telephoto lenses, a lens with fixed focal length will be
proposed to professional photographers doing portraits or sports
photography. It will also be proposed to beginners doing sports
photography. In all other cases, a zoom lens will be proposed.

This results into a decision table with 46 rows. The following excerpt
shows the rows for super wide-angle lenses and super telephoto
lenses:

![Enriched lens DT](resources/lens-decision-table1.png)


As this decision table is quite large, it is desirable to decompose it
into a smaller decision tables by following the method explained in
[Step 4](../step4/description.md). This
method replaces a single decision node by multiple decision nodes. It
supposes that the original decision node has a single decision table
without precondition and is not applied to the values of a
multi-valued variable. Hence, it is not possible to directly apply
this decomposition method to the decision table for choosing the lens
models.

It is, however, possible to apply the decomposition method when
refactoring the decision model as follows:

1. A new decision model is introduced for choosing a lens model
   depending on the photographer level, the subject, the sensor
   format, and a selected lens type. This sub-model has a single
   decision node for choosing the lens model. The decision logic of
   this decision node consists of a variant of the decision table
   described above. This variant has no precondition and the column
   for the lens requirement refers to the selected lens type, which is
   the value of an input data node and not of a local variable
   declared in a precondition.
   
2. In the original decision model, the decision table with
   precondition is replaced by a rule that invokes the sub-model for
   each value of the multi-valued variable `lens requirements`.

If the sub-model has the name `lens-model`, the rule for invoking the
sub-model has the following form:

    for each lens type called 'lens type' , in 'lens requirements'
        add the lens-model computed from
            subject being subject ,
            level being level ,
            sensor format being 'sensor format' ,
            lens type being 'lens type' to decision ;

It is now possible to apply the decomposition method explained in Step
4 to the sub-model.


#### Exercise 1:

Add two dependencies to the dependency graph elaborated in Step 6:

 - The decision node `lenses` should depend on the input data node `level`.
 - the decision node `lenses` should depend on the input data node `subject`.
 
Add two condition columns to the decision table of node `lenses` which
refer to the values of the input data nodes `level` and `subject`.

Refine the decision table by duplicating rows and refining them for
different photographer levels and subject:

- For standard lenses, telephoto lenses, and wide-angle lenses, there
  should be four rows for each sensor format. One row should indicate
  that a beginner receives a zoom lens. The three other rows should
  indicate that a professional photographer receives a zoom lens if
  the subject is landscape or sports and a prime lens if the subject
  is portrait.
  
- For super wide-angle lenses, there should be four rows for full
  frame cameras. One row should indicate that a beginner receives a
  zoom lens. The three other rows should indicate that a professional
  photographer receives a zoom lens if the subject is landscape or
  sports and a prime lens if the subject is portrait.
  
- For super telephoto lenses, there should be six rows for full frame
  cameras, namely three rows for beginners and three rows for
  professional photographers. A beginner will receive a prime lens for
  sports photography and zoom lenses for landscape and portraits. A
  professional photographer will receive prime lenses for sports
  and portraits photography and a zoom lens for landscapes.

The resulting decision table should have 46 rows.

#### Exercise 2

Create a new decision model called `lens-model`:

 - It should have four input data nodes `level`, `subject`, `sensor
   format`, and `lens type`. These nodes are all single-valued.
 - It should have a single decision node `lens` of type `lens
   model`. This node is single-valued as well and depends on all the
   four input data nodes.
   
![Dependency graph](resources/dependency-diagram2.png)

Add a decision table to the node `lens`:

 - Create the same columns as for the decision table of Exercise 1,
   but do not introduce any precondition.
 - The column for `lens requirement` should refer to the value of the
   node `lens type` instead of the value of a local variable declared
   by a precondition.
 - The action column should set the decision a new lens model and not
   add a new lens model to the decision.
 - Add the rows and cells from the decision table of Exercise 1.

#### Exercise 3

The choice between zoom and prime lens only depends on subject,
level, and lens type. 

Refactor the decision model by introducing an intermediate decision
node `fixed focal length` of type `boolean`:

 - Ensure that the new node `fixed focal length` depends on the input
   data nodes `subject`, `level`, and `lens type`.
 - Ensure that the decision node `lens model` depends on the new node `fixed focal length`. 
 - Remove the dependency links between `subject` and `level`,
   on the one hand and the `lens model` on the other hand.
   
![Dependency graph](resources/dependency-diagram3.png)
 
The decision logic of node `fixed focal length` is best described in terms of rules:

 - A professional photographer doing portrait photography should
   receive a prime lens.
 - Super wide-angle cameras used for sports photography should
   receive a prime lens.
 - A zoom lens is proposed in all other cases.
   
Modify the decision table of node `lens model` as follows:

 - Replace the columns for photographer level and subject by a single
   column for the indicator whether a fixed focal length should be
   given or not. 
 - Keep only two rows for each combination of lens type and sensor format.
 
For example, the following row recommends a lens of focus length 14:42
and aperture 3.5-5.6 as a standard zoom lens for a micro-four thirds
format:
 
![Lens DT](resources/lens-decision-table-row3.png)

#### Exercise 4

The last exercise consists in adapting the decision logic of node
`lenses` in the main decision model.

Add a function node to the main model:

 - choose the decision model developed in Exercise 3 for this function node.
 - create a knowledge requirement between this function node and the decision node `lenses`.
 
![Dependency graph](resources/dependency-diagram4.png)

Replace the decision logic of the node `lenses` by a single rule:

 - This rule should be applied to all lens types that are values of the node `lens requirements`.
 - The rule should invoke the sub-model developed in Exercise3 for
   each of these values and add the result to the multi-valued
   decision.
 - The arguments of each invocation are the subject, the level, the
   sensor format, and the lens type.
   
#### Lessons learned

Submodels permit the application of complex decision logics to values
of multi-valued nodes and other multi-valued language
expressions. However, they may also be used to decompose a complex
decision model into smaller pieces.

#### Limitations

The dependency graph of the main model has two decision nodes that
represent the results of the recommendation. A [final
step](quiz/quiz.md)
will show how to combine them into a single decision node.

[Back to ADS concepts step by step](../README.md)
