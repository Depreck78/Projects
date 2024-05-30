import numpy as np

# Define the equi-spaced points
n = 5
xi_equi = -1 + np.arange(n) / 2
yi_equi = np.sin(xi_equi)

# Number of points
n_equi = len(xi_equi)

# Construct the matrix for the system of equations
A_equi = np.ones((n_equi, n_equi))

# Fill in the matrix with the appropriate Newton basis polynomials
for j in range(1, n_equi):
    for i in range(n_equi):
        A_equi[i, j] = np.prod(xi_equi[i] - xi_equi[0:j])

# Solve the system for the coefficients
coefficients_equi = np.linalg.solve(A_equi, yi_equi)

# Display the polynomial coefficients
print('Polynomial coefficients for equi-spaced points:')
print(coefficients_equi)
