# Documentation of toggle-1-item  function (Tweak function)
## Design
### What it does
This function either takes one thing out of or puts one thing into the knapsack. The user of the function
can specify the index of which item they want to toggle. The function takes in an entire answer and also returns
an entire answer that is slightly modified. The new answer that is returned has its weight, value, and score
modified to match its current contents.
### Why it does it that way
We chose to implement our tweak function in this way so that it only makes a small change at a time. This allows
our hill-climbing algorithm to make small changes and climb the "hill" incrementally. Another factor that
helps with this is that our scoring mechanism penalizes over-capacity knapsacks by giving it a score that is the
negative of its weight. This means that the more over-capacity a knapsack is, the lower the score it will have. Therefore,
if a knapsack is over-capacity, then the hill climbing behavior will likely cause it to return to a legal state.
### Future consideration
One example when this tweak function may not perform well would be when the optimal solution would be a single large and
valuable item. Our tweaking function would not be likely to remove all the smaller items necessary to make space for
the large valuable item. Additionally, using random restarts is still unlikely to find this optimal solution.
## Results
Note: Random Search and Hill Climbing were ran with 100,000 iterations, and Random Restart used 1000 restarts with 100 iterations (climbing steps) each.

| Problem | Random Search Trial 1 | RS Trial 2 | RS Trial 3 | Hill Climbing Trial 1 | HC Trial 2 | HC Trial 3 |
| ------- | ---------------------:|-----------:| ----------:| ---------------------:| ----------:| ----------:|
| knapPI_11_20_1000_1 | 1428 | 1326 | 1326 | 790 | 624 | 790 |
| same with random restart | N/A | N/A | N/A | 1428 | 1428 | 1428 |
| knapPI_13_20_1000_1 | 1599 | 1638 | 1560 | 1482 | 1287 | 1365 |
| same with random restart | N/A | N/A | N/A | 1716 | 1716 | 1716 |
| knapPI_16_20_1000_1 | 2239 | 2185 | 2185 | 1152 | 2093 | 1831 |
| same with random restart | N/A | N/A | N/A | 2291 | 2291 | 2291 |

