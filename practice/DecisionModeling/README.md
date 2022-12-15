
## Decision modeling step by step

In this tutorial, you will discover a series of decision-making problems 
that will become more and more complex at each step. You will learn concepts 
and methods that allow you to formulate these problems in a simple way. In 
the end, you will be able to build models that are intuitive since they 
address the complexity in the right way.

The first two steps
concern very general problems and are given as background. The third
step introduces the specific problems that are adequately solved by
ADS. You will thus understand where ADS fits into the vast landscape of 
decision-making software.

Each step has one or more exercises. Once you have figured out the
solution of the exercise, you may proceed to the next step. Usually,
this next step starts from the solution of the previous steps, meaning
that it spoils the exercise.

1. **Making an individual decision:** find a best decision among a
   list of given alternatives. This problem may be solved by ranking the
   alternatives according to given preferences and taking the best
   one. [Start the tutorial](step1/description.md)
   
2. **Making decisions for a population of cases:** the list of given
   alternatives may change from case to case. Hence, the second problem
   consists in finding a best decision for a given case. This problem
   may be solved by building a table that lists each case as well as
   the best decision for this case. [Continue the tutorial](step2/description.md)
    
3. **Making decisions for families of cases:** if the number of cases
   is large, it is convenient to make the same decision for similar
   cases, which share some characteristics. The third problem thus
   consists in finding a best decision for a family of cases that can
   be described by logical conditions. This problem may be solved by
   introducing a set of rules that associate the logical conditions of
   each family with a decision. [Continue the tutorial](step2/quiz/quiz.md)

4. **Making intermediate decisions:** if their are many rules with
   complex conditions, it may be convenient to break the whole
   decision logic into smaller pieces. This requires the
   identification of intermediate decisions that do not depend on all
   characteristics of a given case, but only some of them. The fourth
   problem thus consists in describing the intermediate decisions, the
   dependencies between decisions, as well as providing a decision
   logic, i.e. a set of rules for each intermediate decision as well
   as the final decision. [Continue the tutorial](step3/quiz/quiz.md)
   
5. **Composite types:** if there is a huge number of
   intermediate decisions the dependency diagram may be complex to
   read. It is then recommended to identify nodes that can be
   regrouped together. The fifth problem requires the definition of
   composite types for decisions and input data nodes and the decision
   logic need to be enhanced by expressions for navigating through
   these composite types. [Continue the tutorial](step4/quiz/quiz.md)
   
6. **Applying a decision logic to many values:** a case may involve
   an arbitrary number of components and require that the same
   decision is made for each of these components. Such a complex
   case may be described in terms of multi-valued variables and
   multi-valued attributes. Quantified rules will be used to apply the
   same logic to all of the components. [Continue the tutorial](step5/quiz/quiz.md)

7. **Applying a decision model to many values:** decisions for
   components may require intermediate decisions as explained in
   Step 4. This can be addressed by introducing a dedicated decision
   model for the components and applying this decision model in order
   to make a decision for this component. [Continue the tutorial](step6/quiz/quiz.md)


[Back to main page](./..)

