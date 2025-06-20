### Not quite right!

If budgets are nonnegative, this will reduce the number of possible cases. It will also reduce the number of missing cases. Missing cases with negative budget can no longer occur and the missing rule with condition `budget is less than 0` should not be generated any more. This results in a decrease of missing rules.

However, if some of the existing rules had a condition `budget is less than 0`, then limiting the budget to nonnegative values would not change the missing rules.

[Review the previous step](../description.md)
