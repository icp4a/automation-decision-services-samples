
### Making intermediate decisions

Many factors influence the choice of a camera. In
addition to budget, subject, and user level, you can consider the color,
the presence of a tilt screen and viewfinder, and a limit on the
camera weight. Each additional factor multiplies the number of
possible cases which lead to extra rows in a decision table
that map the possible cases to camera models.

This example illustrates that the number of rows of decision tables
can grow exponentially with the number of condition columns. One way
to remedy this issue consists in breaking the whole decision logic
into smaller pieces. You can achieve it by identifying intermediate
decisions. These intermediate decisions depend on some of the
input data nodes, but not all of them. The final decision (that is, the
decision that you have to make in the end) depends on the
intermediate decisions. As the final decision now depends on the
intermediate decisions, it might no longer depend on all of the input
data nodes. The whole purpose of this transformation is to reduce the
number of dependencies of the final decision while you keep the number
of dependencies of intermediate decision small as well. A smaller
number of dependencies reduces the number of cases that need to be
covered by the decision logic of the final decision. This
simplification is possible as each intermediate decision has its
own decision logic. As an intermediate decision does not depend on
all of the input data nodes, its decision logic needs to cover fewer
cases than the original decision logic. In the end, a huge decision
table is decomposed into several smaller decision tables.

To simplify the logic for suggesting a camera, you can, for example,
introduce an auxiliary decision for choosing the sensor format. You can
introduce a new decision node for this purpose. This decision depends
on the user level, the subject, and the budget (and has the same
decision logic as specified in [Step 3](../step3/description.md)). 

![Sensor format DT](resources/sensor-format-decision-table.png)

The decision about a camera model now depends on the sensor
format, but no longer on the user level, the subject, and the
budget. Hence, the number of dependencies of the camera model is
reduced by two and the number of columns of its decision table is
reduced by two as well. Note that the intermediate
decision depends on some of the input data nodes, but not all of
them.

#### Exercise 1

Create a decision model for suggesting a camera model:

 - Add an enumeration type for the weight limit with the three values 250 g, 500 g, 1000 g.
 - Add input data nodes for budget, subject, level, weight limit, and tilt screen (yes or no).
 - Add a decision node for the camera model with type string. Camera
   models are anonymously named by `m1`, `m2`, `m3`, and so on.
 - Ensure that the decision node depends on the five input data nodes as indicated by the diagram:
 
![Dependency graph](resources/dependency-diagram1.png)

Add a decision table that suggests the best camera model for each
possible case. 

- Ensure that the columns are in the same order as the ones from the decision table introduced in Step 3 (see
  preceding decision table). The column's order can be updated in the creation wizard using drag-and-drop.
- Copy the contents of the rows from the decision table introduced in Step 3 into the adequate columns.
- Duplicate the rows for the different weight limits and the tilt
  screen option. It requires six copies of the eight rows.
  
For the sake of simplicity, the decision column is not filled out
in this first exercise.

For example, the following row shows all cases where the weight limit
is 250 g, no tilt screen is wanted, user level is beginner, and the
budget is 1500 or more:

![DT1 with one row](resources/camera-table-row1.png)

#### Exercise 2

Refactor the decision model by introducing an intermediate decision for the sensor format:

 - Ensure that the sensor format depends on budget, subject, and level.
 - Ensure that the camera model depends on the sensor format.
 - Remove the dependency links between budget, subject, level, and the camera model.
 - Add a decision table in the logic of the sensor format node and copy the contents of the rows from the decision table introduced in Step 3.

![Dependency graph](resources/dependency-diagram2.png)

Add a decision table that suggests the best camera model for each
possible case and add a camera model to the decision column. For some
requirements, no camera model exists and no suggestion can be
made. The corresponding rows can be left out:
  
- No full-frame cameras that are lighter than 250 g exist. No
  camera model must be suggested in that case.
  
- If the weight limit is 1000 g, full-frame cameras are
  suggested.  No other camera model must be suggested for cases
  with weight limit of 1000 g.
  
For example, the following row shows a suggested camera for a weight
limit of 250 g, no tilt screen, and a Micro Four Thirds format:

![DT2 with one row](resources/camera-table-row2.png)

#### Lessons learned

The number of rows of a decision table might grow exponentially with
respect to its number of condition columns. In many decision-making
problems, it is possible to decompose a large decision table into
smaller decision tables with the help of intermediate decisions. However, it must be noted that
this decomposition is
effective only if the intermediate decisions are breaking two or more
dependencies between input data nodes and the final decision.

In the example, it was assumed that the camera model depends only on the
sensor format and not on the subject, level, or budget. If different
APS-C camera models were suggested for beginners and
professionals, this assumption does not hold. Finding out the
right dependencies thus requires a careful analysis of the
decision-making problem.

#### Limitations

Whereas the dependency diagram of the example has only six nodes,
more complex problems might have numerous input data nodes and decision
nodes. If a diagram has more than twenty nodes, it is not
easy to read and understand. The [next
step](quiz/quiz.md)
presents an easy method to reduce a diagram in size.

[Back to ADS concepts step by step](../README.md)
