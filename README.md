# Introduction
AB-GEP is a symbolic regression (SR) method based on adversarial bandit technique. AB-GEP segments the mathematical space into many subspaces. It then leverages a new selection method, AvgExp3, to enhance the population jump between segmented subspaces while avoiding falling into a local optimum and mataining population diversity.
# Running 
run AvgEXP3/src/New_gep_greedy1/Fitness_test.java to run 24 SR benchmarks and 8 real-world benchmarks in 'datasets/' 30 times. The optimal result in all independent runs is shown in 'results/rmse/'.
