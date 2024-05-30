import numpy as np
import matplotlib.pyplot as plt

# Data points
xi = np.array([-5, -4.6552, -4.3103, -3.9655, -3.6207, -3.2759, -2.931, -2.5862, -2.2414, -1.8966, -1.5517, -1.2069, -0.86207, -0.51724, -0.17241, 0.17241, 0.51724, 0.86207, 1.2069, 1.5517, 1.8966, 2.2414, 2.5862, 2.931, 3.2759, 3.6207, 3.9655, 4.3103, 4.6552, 5])
yi = np.array([25.198, 22.354, 11.286, -5.3643, -9.9406, 5.5574, 20.364, 10.826, 12.018, 18.742, -4.5468, -6.2935, 11.038, -6.9372, 22.685, 10.281, -4.0523, -0.26773, 10.089, 13.64, 20.074, 13.906, 2.7136, 16.384, 2.9209, 27.113, 30.377, 29.667, 22.724, 48.731])

# Construct matrix A
A = np.column_stack([np.ones_like(xi), xi, xi**2])

# Construct vector b
b = yi

# Number of basis vectors
n = A.shape[1]

# Initialize Q and R matrices
Q = np.zeros_like(A)
R = np.zeros((n, n))

# Gram-Schmidt
for j in range(n):
    vj = A[:, j]
    for i in range(j):
        R[i, j] = np.dot(Q[:, i], A[:, j])
        vj = vj - R[i, j] * Q[:, i]
    R[j, j] = np.linalg.norm(vj)
    Q[:, j] = vj / R[j, j]

# Solve R 
coefficients = np.linalg.solve(R, np.dot(Q.T, b))

# Calculate fitted y values
y_fitted = np.dot(A, coefficients)

# Plot original data points
plt.plot(xi, yi, 'bo', label='Data Points')

# Plot regression curve
plt.plot(xi, y_fitted, 'r-', label='Quadratic Fit')
plt.xlabel('x')
plt.ylabel('y')
plt.legend()
plt.title('Quadratic Least Squares Regression without using qr function')

print('Polynomial coefficients:')
print(coefficients)
plt.show()
