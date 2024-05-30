# This program calculates the following values: Agreeability(AG), Happiness(HA), and Skepticism(SK)

def main():
    import math

    # Header of the Program
    print("This program calculates your agreeability, happiness, and skepticism predictive values.")
    print()

    # Promt user values
    name=input("What is your name? ")
    print("Ok", name,", let's start.")
    print()
    print("Parametters for agreeability")
    height=eval(input(" - height (m): "))
    weight=eval(input(" - weight (Kg): "))
    print()
    print("Parameters for happiness")
    age=eval(input(" - Your age: "))
    courses=eval(input(" - Number of courses you are taking: "))
    print()
    print("Parameters for skepticism")
    day=eval(input(" - Day of birth: "))
    month=eval(input(" - Month of birth: "))

    # Calculate AG
    AG=(((height**(3)+weight**(3)-(height*weight))**(1/4))/(2*weight*height))

    # Calculate HA
    HA=((85-age)/((age+courses**2)**(1/2)))

    # Calculate SK
    SK=((day*(day**2+month**2))/(2*(day+month)))**(1/2)

    # Output
    print()
    print(name,"this is the summary of your predictive values:")
    print(" - Agreeability: ",AG)
    print(" - Happiness: ",HA)
    print(" - Skepticism: ",SK)

main()
