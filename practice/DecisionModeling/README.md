
## Decision modeling step by step

In this tutorial, discover a series of decision-making problems 
that become more complex at each step. You learn concepts 
and methods to formulate these problems in a simple way. In 
the end, you can build models that are intuitive since they 
address the complexity in the right way.

The first two steps
cover general problems and are given as background. The third
step introduces the specific problems that Automation Decision Services can solve. 
It helps you understand where Automation Decision Services fits into the vast landscape of 
decision-making software.

Each step has one or more exercises. When you solve the exercise, you can proceed to the next step. 
Usually, this next step starts from the solution of the previous steps. 
The solutions for the exercises at each step are available under the `solution` 
folder in [/practice/DecisionModeling/step{stepNumber}/solution]().

1. **Making an individual decision:** Find the best decision among a
   list of given alternatives. This problem can be solved by ranking the
   alternatives according to given preferences and choosing the best
   one. [Start the tutorial](step1/description.md)
   
2. **Making decisions for a population of cases:** The list of given
   alternatives might change from one case to another. Hence, the second problem
   consists in finding the best decision for a specific case. This problem
   can be solved by creating a table that lists each case and
   the best decision for the case. [Continue the tutorial](step2/description.md)
    
3. **Making decisions for families of cases:** If the number of cases
   is large, you can make the same decision for similar
   cases that share some characteristics. The third problem thus
   consists in finding a best decision for a family of cases that logical conditions can describe. 
   This problem can be solved by
   introducing a set of rules that associate the logical conditions of
   each family with a decision. [Continue the tutorial](step2/quiz/quiz.md)

4. **Making intermediate decisions:** If many rules with
   complex conditions exist, you can break the whole
   decision logic into smaller pieces. It requires the
   identification of intermediate decisions that do not depend on all
   characteristics of a specific case, but only some of them. The fourth
   problem thus consists of describing the intermediate decisions, the
   dependencies between decisions and providing a decision
   logic, that is, a set of rules for each intermediate decision and the final decision. 
   [Continue    the tutorial](step3/quiz/quiz.md)
   
5. **Composite types:** If there are many 
   intermediate decisions, the dependency diagram might be complex to
   read. Identify nodes that can be
   regrouped together. The fifth problem requires the definition of
   composite types for decisions and input data nodes. The decision
   logic needs to be enhanced by expressions for navigating through
   these composite types. [Continue the tutorial](step4/quiz/quiz.md)
   
6. **Applying a decision logic to many values:** A case might involve
   an arbitrary number of components and require the same
   decision to be made for each of these components. Such a complex
   case can be described in terms of multi-valued variables and
   multi-valued attributes. Quantified rules are used to apply the
   same logic to all the components. [Continue the tutorial](step5/quiz/quiz.md)

7. **Applying a decision model to many values:** Decisions for
   components might require intermediate decisions as explained in
   Step 4. This can be addressed by introducing a dedicated decision
   model for the components and applying this decision model 
   to make a decision for this component. [Continue the tutorial](step6/quiz/quiz.md)


[Back to main page](./..)

