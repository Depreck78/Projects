import numpy as np
import matplotlib.pyplot as plt
import math

# Given Values
your_speed = 7.0  # Your speed in m/s
raptor_speeds = [10.0, 15.0, 12.0]  # Speeds of r0, r1, r2 in m/s
initial_positions = [(25.0, 0), (-25.0 / 2, (25.0 * math.sqrt(3)) / 2), (-25.0 / 2, -(25.0 * math.sqrt(3)) / 2)]  # Initial positions of r0, r1, r2
time_step = 0.1  # Time step for simulation
max_simulation_time = 200  # Maximum simulation time in seconds
small_distance = 0.1  # The distance at which you are considered caught

# Calculate step sizes based on speeds and time step
p_s = your_speed * time_step
r0_s = raptor_speeds[0] * time_step
r1_s = raptor_speeds[1] * time_step
r2_s = raptor_speeds[2] * time_step

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

# Function to find the best angle
def find_best_angle(p_init, r0_init, r1_init, r2_init, dt):
    best_time = 0
    best_angle = 0
    best_trajecs = ([], [], [], [])

    for i in range(0, 360):
        t = 0

        angle = math.radians(i)
        p_dir = np.array([np.cos(angle), np.sin(angle)])

        # Initial positions
        p_pos = p_init.copy()
        r0_pos = r0_init.copy()
        r1_pos = r1_init.copy()
        r2_pos = r2_init.copy()

        # Trajectories
        trajecs = ([], [], [], [])

        while True:
            # Record initial state

            trajecs[0].append(p_pos.copy())
            trajecs[1].append(r0_pos.copy())
            trajecs[2].append(r1_pos.copy())
            trajecs[3].append(r2_pos.copy())

            # Update player pos first

            r0_disp = p_pos - r0_pos
            r1_disp = p_pos - r1_pos
            r2_disp = p_pos - r2_pos

            # Point raptors in the right direction
            r0_dist = np.linalg.norm(r0_disp)
            r1_dist = np.linalg.norm(r1_disp)
            r2_dist = np.linalg.norm(r2_disp)

            r0_dir = (p_pos - r0_pos) / r0_dist
            r1_dir = (p_pos - r1_pos) / r1_dist
            r2_dir = (p_pos - r2_pos) / r2_dist

            r0_pos += r0_dir * r0_s
            r1_pos += r1_dir * r1_s
            r2_pos += r2_dir * r2_s

            # Update player pos last
            p_pos += p_dir * p_s

            t += dt

            # Check for dead
            dead = any([r0_dist < r0_s, r1_dist < r1_s, r2_dist < r2_s])
            if dead:
                if t > best_time:
                    trajecs[0].append(p_pos.copy())
                    trajecs[1].append(r0_pos.copy())
                    trajecs[2].append(r1_pos.copy())
                    trajecs[3].append(r2_pos.copy())

                    best_time = t
                    best_angle = i
                    best_trajecs = trajecs
                break

    return best_time, best_angle, best_trajecs

# Main
d = 25.0

p_init = np.array([0.0, 0.0])
r0_init = np.array([d, 0])
r1_init = np.array([-d / 2, d * np.sqrt(3) / 2])
r2_init = np.array([-d / 2, -d * np.sqrt(3) / 2])

best_time, best_angle, best_trajecs = find_best_angle(p_init, r0_init, r1_init, r2_init, time_step)
print("best time:", best_time, "best angle:", best_angle)

# Plot trajectories
plt.figure(figsize=(10, 6))
plt.plot([pos[0] for pos in best_trajecs[0]], [pos[1] for pos in best_trajecs[0]], label='Player')
plt.plot([pos[0] for pos in best_trajecs[1]], [pos[1] for pos in best_trajecs[1]], label='Raptor 0')
plt.plot([pos[0] for pos in best_trajecs[2]], [pos[1] for pos in best_trajecs[2]], label='Raptor 1')
plt.plot([pos[0] for pos in best_trajecs[3]], [pos[1] for pos in best_trajecs[3]], label='Raptor 2')
plt.xlabel('X-position')
plt.ylabel('Y-position')
plt.title('Trajectories of Player and Raptors')
plt.legend()
plt.grid()
plt.show()
