# Calculates pi by adding the points inside and outside the circle and dividing those.

from graphics import *
from random import seed
from random import randint
from random import random
import math

# Create window
def createWindow(length, width, color):

    W=GraphWin("Win", length, width)
    W.setBackground(color)
    return W

# Square
def drawSquare(window, squareLength):

    margin = (window.width - squareLength)/2

    UP=Point(margin,(margin+squareLength))
    LP=Point((squareLength+margin),margin)
    
    R=Rectangle(UP,LP)
    R.draw(window)
    R.setWidth(5)

    return R

# Circle
def drawCircle(window, circleRadius, color):

    Center=Point(250,250)

    C=Circle(Center,circleRadius)
    C.draw(window)
    C.setFill(color)

    return C

#Point Gen
def addPoints(window, squareLength, numberOfPoints):

    xl=[]
    for x in range(numberOfPoints):
        xv = randint(50, 450)
        xl.append(xv)

    yl=[]
    for y in range(numberOfPoints):
        yv = randint(50,450)
        yl.append(yv)

    Y=0
    B=0

    for i in range(len(xl)):
        P=Point(xl[i],yl[i])
        P.draw(window)

        if (xl[i] - 250) * (xl[i] - 250) + (yl[i] - 250) * (yl[i] - 250) <= (200 * 200):
            P.setFill("yellow")
            Y+=1
        else:
            P.setFill("black")
            B+=1
    
    return Y, B

# Calculation of pi
def estimatePi(yellowCount, blackCount):

    pi=(4*yellowCount)/(blackCount+yellowCount)

    return pi

# Main func
def main():

    winLength = 500
    winWidth = 500
    squareLength = 400
    
    windowColor = "green"
    circleColor = "blue"
    
    window = createWindow(winLength, winWidth, windowColor)
    drawSquare(window, squareLength)
    drawCircle(window, squareLength/2, circleColor)

    numberOfPoints = eval(input("How many random points: "))
    Yellow, Black = addPoints(window, squareLength, numberOfPoints)

    estimatedPi = estimatePi(Yellow, Black)  
    print("Estimated pi: ",estimatedPi)
    
    window.getMouse()
    window.close()

main()
