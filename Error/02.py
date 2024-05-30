import matplotlib.pyplot as plt
import math

# Function for maximum error E2
def calculate_E2(n):
    return 1 / (math.factorial(n + 1) * (2 ** n))


def main():

    # Values of n
    n_values = [3, 4, 5, 6, 7]

    # Calculate E2
    errors_chebyshev = [calculate_E2(n) for n in n_values]

    # Plot
    plt.figure(figsize=(8, 6))
    plt.plot(n_values, errors_chebyshev, marker='o', linestyle='-', color='r')
    plt.title('Maximum Error E2 vs Degree of Interpolating Polynomial (Chebyshev Points)')
    plt.xlabel('Degree of Interpolating Polynomial (n)')
    plt.ylabel('Maximum Error E2')
    plt.grid(True)
    plt.show()

if __name__ == "__main__":
    main()
