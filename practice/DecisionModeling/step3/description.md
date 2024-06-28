
### Making decisions for families of cases

In practice, it is not be possible to list all possible
cases. Even in the example of [Step 2](../step2/description.md),
it was laborious to list all the cases and to figure out a decision for
them. 

![Dependency graph](resources/sensor-format-decision-table.png)

Now, assume that the budget is modeled in a quantitative way, rather than qualitative levels
of low, medium, and high as in the preceding decision table. If a low budget ranges from 0 to 499,
it means that all rows that concern a low budget need to be duplicated 500
times. Similarly, if a medium budget ranges from 500 to 1499 all rows
that concern a medium budget need to be duplicated 1000 times. Finally, all rows that concern
a high budget need to be duplicated
many times as a high budget covers all integers
starting from 1500. In other words, this change blows up the
number of cases in a way that prevents you from listing all the
cases.

Fortunately, often the same decision is made for similar
cases. It is then possible to regroup those cases into families of
cases. To find such a family, an analyst needs to identify the
decision boundaries between different families of cases. An analyst
can ask questions such as:

> How much budget does a portrait photography beginner need to
> buy an APS-C camera instead of a Micro Four Thirds
> camera?

Analysts need to be able to find out those thresholds. When they
identify them, they can describe the families of similar cases by
using intervals. For example, you can consider all cases where a
beginner wants to do landscape photography and has a budget between 500
and 1500. This family can be described in terms of a logical condition:

    'level' is beginner
    and 'subject' is landscape
    and 'budget' is at least 500 and less than 1500.

The fact that an APS-C camera is chosen involves formulating a
statement that assigns the value APS-C to a decision variable
to represent the choice of the sensor format:

    set decision to APS-C ;

The fact that this decision is made for all cases in the
considered family can be written as an if-then rule:

    if
        'level' is beginner
        and 'subject' is landscape
        and 'budget' is at least 500 and less than 1500
    then
        set decision to APS-C ;

Each row in a decision table corresponds to such an
if-then rule as a decision table is just a tabular
representation of multiple rules of the same form. Rules allow richer forms of conditions and can
enable shorter textual representations of case families. It is even possible to use both decision
tables and rules. It means that some cases use decision
tables to make decisions, while other cases use rules. The
decision logic of a decision consists of all rules and decision tables
that make this decision for a case.

#### Exercise 1

Use a quantitative budget: 

 - Modify the type of the `budget` node to `integer`. 
 - Redefine the definition of the budget column in the decision
   table. Replace `budget is <a budget>` with `budget is at least <min>
   and less than <max>`.
 - Replace the qualitative budget values with intervals (for example, low with
   `[0, 500)`, medium with `[500, 1500)`, high with `>= 1500`).

For example, the following row describes a beginner who has a budget
in `[500, 1500)` and is interested in landscape photography:

![DT with one row](resources/sensor-format-table-with-integer-budget.png)

#### Exercise 2

Try to reduce the number of rows by merging rows that have the same
decision, the same level and photographic subject, and joint
intervals. Some examples:

 - Whenever the budget is in `[500, 1500)`, an APS-C camera is
   suggested. So, all rows that concern such a budget can be merged
   into a single row.
 - Whenever the budget is at least 1500 and the level is beginner, an
   APS-C camera is suggested. Again, all these rows can be merged
   into a single one.
 - Whenever the subject is landscape and the budget is in `[0, 500)`,
   a Micro Four Thirds camera is suggested. Again, these cases can be
   merged.
 - Whenever the subject is portrait and the budget is in `[0, 500)`,
   a Micro Four Thirds camera is suggested. Again, these cases can be
   merged.
   
Can you transform the decision table into an equivalent one
that has fewer than 10 rows?

#### Lessons learned

The same row might participate into several of these merging steps. As a
consequence, the transformed decision table might have rows that
overlap. It means that some cases match multiple
rows in the decision table. It is not an issue if these rows result into the
same decision.


#### Limitations

The decision table that was developed in the last exercise provides a convenient
way to suggest a sensor format to different kinds of
customers. However, the true problem of the customer is to choose a
camera from a catalog. Many camera models are available and new factors
need to be considered to make a suggestion. Each camera
has a multitude of technical characteristics and features. The customer can consider each of them
to make a choice. For example, the customer might require that the color of the
camera is silver, that it has a tilt screen and a viewfinder, and put
a limit on its weight. All these requirements impact which camera models are
feasible and thus influence which decision is made in the
end.

A decision table for choosing a camera has extra condition
columns and a modified action column. Each new condition column might
duplicate the existing rows for each possible value of this column.
It means that the number of rows might grow exponentially with the
number of columns. The [next step](quiz/quiz.md) examines a method to address this issue.


[Back to ADS concepts step by step](../README.md)
