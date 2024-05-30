import math
import matplotlib.pyplot as plt

# Constants
your_speed = 7  # Your speed in m/s
raptor_speeds = [10, 15, 12]  # Speeds of r0, r1, r2 in m/s
initial_positions = [(25, 0), (-25/2, (25*math.sqrt(3))/2), (-25/2, -(25*math.sqrt(3))/2)]  # Initial positions of r0, r1, r2
time_step = 0.01  # Time step for simulation
max_simulation_time = 200  # Maximum simulation time in seconds (reduced for efficiency)
small_distance = 0.1  # The distance at which you are considered caught

# Function to calculate the Euclidean distance between two points
def distance(point1, point2):
    return math.sqrt((point1[0] - point2[0])**2 + (point1[1] - point2[1])**2)

# Function to calculate the new position of an entity after a time step
def update_position(position, velocity, time_step):
    new_x = position[0] + velocity[0] * time_step
    new_y = position[1] + velocity[1] * time_step
    return (new_x, new_y)

# Function to calculate the unit vector between two points
def unit_vector(point1, point2):
    delta_x = point2[0] - point1[0]
    delta_y = point2[1] - point1[1]
    magnitude = math.sqrt(delta_x**2 + delta_y**2)
    return (delta_x / magnitude, delta_y / magnitude)

# Initialize variables to store the best angle and corresponding survival time
best_angle = None
max_survival_time = 0
best_position_history = []
best_raptor_position_history = []

# Loop through different escape angles (0 to 360 degrees)
for escape_angle_degrees in range(360):
    escape_angle_rad = math.radians(escape_angle_degrees)
    
    # Calculate the escape velocity based on the escape angle and your speed
    escape_velocity = (your_speed * math.cos(escape_angle_rad), your_speed * math.sin(escape_angle_rad))

    # Initialize positions of raptors and your position
    raptor_positions = initial_positions.copy()
    your_position = (0, 0)

    # Initialize survival time
    survival_time = 0

    # Lists to store positions for plotting
    raptor_positions_history = [[] for _ in range(3)]
    your_position_history = []

    # Simulate the movement for a limited time
    for t in range(int(max_simulation_time / time_step)):
        # Update the positions of raptors and their velocity vectors
        for i in range(3):
            raptor_direction = unit_vector(raptor_positions[i], your_position)
            raptor_velocity = (raptor_speeds[i] * raptor_direction[0], raptor_speeds[i] * raptor_direction[1])
            raptor_positions[i] = update_position(raptor_positions[i], raptor_velocity, time_step)
            raptor_positions_history[i].append(raptor_positions[i])

        your_position = update_position(your_position, escape_velocity, time_step)
        your_position_history.append(your_position)

        # Check if any raptor catches you
        if any(distance(your_position, r) < small_distance for r in raptor_positions):
            break

        # Increment survival time
        survival_time += time_step

    # Check if this angle results in the best survival time
    if survival_time > max_survival_time or best_angle is None:
        max_survival_time = survival_time
        best_angle = escape_angle_degrees

        best_position_history = your_position_history
        best_raptor_position_history = raptor_positions_history

# Print the best angle and corresponding survival time
print(f"Best angle for survival: {best_angle} degrees")
print(f"Max survival time: {max_survival_time:.2f} seconds")

# Plot the positions of entities over time
plt.figure(figsize=(10, 8))
for i in range(3):
    positions_x = [pos[0] for pos in best_raptor_position_history[i]]
    positions_y = [pos[1] for pos in best_raptor_position_history[i]]
    plt.plot(positions_x, positions_y, label=f'Raptor {i}')

your_positions_x = [pos[0] for pos in best_position_history]
your_positions_y = [pos[1] for pos in best_position_history]
plt.plot(your_positions_x, your_positions_y, label='You', linestyle='--')

plt.xlabel('X-position')
plt.ylabel('Y-position')
plt.title('Movement of Raptors and Yourself')
plt.legend()
plt.grid()
plt.show()
