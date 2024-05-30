import numpy as np

# Define the xi and yi values
xi = -5 + np.arange(0, 21)/2
yi = 2*np.sin(xi) + np.cos(3*xi)

# Number of points
n = len(xi)

# Construct the matrix for the system of equations
A = np.ones((n, n))

# Fill in the matrix with the appropriate Newton basis polynomials
for j in range(1, n):
    for i in range(n):
        A[i, j] = np.prod(xi[i] - xi[0:j])  # The product of (x - x_m) for m < j

# Solve the system for the coefficients
coefficients = np.linalg.solve(A, yi)

# Display the polynomial coefficients
print('Polynomial coefficients:')
print(coefficients)
