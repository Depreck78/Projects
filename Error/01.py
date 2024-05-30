import matplotlib.pyplot as plt
import math

# Function for maximum error E1
def calculate_E1(n):
    return (1 / ((n + 1) * (n))) * (2 * (n + 1) * (n - 1) / (n * n))


def main():

    # Values of n
    n_values = [3, 4, 5, 6, 7]

    # Calculate E1
    errors = [calculate_E1(n) for n in n_values]

    # Plot
    plt.figure(figsize=(8, 6))
    plt.plot(n_values, errors, marker='o', linestyle='-', color='b')
    plt.title('Maximum Error E1 vs Degree of Interpolating Polynomial')
    plt.xlabel('Degree of Interpolating Polynomial (n)')
    plt.ylabel('Maximum Error E1')
    plt.grid(True)
    plt.show()


if __name__ == "__main__":
    main()
