import numpy as np

# Define the Chebyshev points
n = 5
xi_chebyshev = np.cos((np.arange(n) * np.pi) / 4)
yi_chebyshev = np.sin(xi_chebyshev)

# Number of points
n_chebyshev = len(xi_chebyshev)

# Construct the matrix for the system of equations
A_chebyshev = np.ones((n_chebyshev, n_chebyshev))

# Fill in the matrix with the appropriate Newton basis polynomials
for j in range(1, n_chebyshev):
    for i in range(n_chebyshev):
        A_chebyshev[i, j] = np.prod(xi_chebyshev[i] - xi_chebyshev[0:j])

# Solve the system for the coefficients
coefficients_chebyshev = np.linalg.solve(A_chebyshev, yi_chebyshev)

# Display the polynomial coefficients
print('Polynomial coefficients for Chebyshev points:')
print(coefficients_chebyshev)
