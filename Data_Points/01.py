import numpy as np
import matplotlib.pyplot as plt

# Data points
xii = np.array([-5, -4.6552, -4.3103, -3.9655, -3.6207, -3.2759, -2.931, -2.5862, -2.2414, -1.8966, -1.5517, -1.2069, -0.86207, -0.51724, -0.17241, 0.17241, 0.51724, 0.86207, 1.2069, 1.5517, 1.8966, 2.2414, 2.5862, 2.931, 3.2759, 3.6207, 3.9655, 4.3103, 4.6552, 5])
yii = np.array([25.198, 22.354, 11.286, -5.3643, -9.9406, 5.5574, 20.364, 10.826, 12.018, 18.742, -4.5468, -6.2935, 11.038, -6.9372, 22.685, 10.281, -4.0523, -0.26773, 10.089, 13.64, 20.074, 13.906, 2.7136, 16.384, 2.9209, 27.113, 30.377, 29.667, 22.724, 48.731])

# Construct the design matrix A for the quadratic model
A = np.column_stack((np.ones_like(xii), xii, xii ** 2))

# Construct the observation vector b
b = yii

# Calculate At_A matrix
At_A = np.dot(A.T, A)

# Perform LU decomposition
def lu_decomposition(A):
    n = A.shape[0]
    L = np.eye(n)
    U = np.copy(A)
    for j in range(n - 1):
        for i in range(j + 1, n):
            L[i, j] = U[i, j] / U[j, j]
            U[i, j:] -= L[i, j] * U[j, j:]

    return L, U

# Initialize inverse of At_A matrix
n = At_A.shape[0]
inv_At_A = np.zeros_like(At_A)

# Calculate inverse of At_A
for i in range(n):
    e = np.zeros(n)
    e[i] = 1

    # Forward substitution L
    y = np.linalg.solve(lu_decomposition(At_A)[0], e)

    # Backward substitution U
    inv_At_A[:, i] = np.linalg.solve(lu_decomposition(At_A)[1], y)

# Calculate coefficients
Atb = np.dot(A.T, b)
coefficients = np.dot(inv_At_A, Atb)
print('Polynomial coefficients:')
print(coefficients)

# Calculate fitted y values
y_fitted = np.dot(A, coefficients)

# Plot
plt.plot(xii, yii, 'o', label='Original Data Points')
plt.plot(xii, y_fitted, '-', label='Fitted Quadratic Curve')
plt.xlabel('x-axis')
plt.ylabel('y-axis')
plt.legend()
plt.title('Quadratic Least Squares Regression')
plt.show()
