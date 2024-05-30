from graphics import *

def createCanvas():
	win = GraphWin("MyWindow", 500, 500)
	win.setBackground("yellow")

	return win

def simpleCircle(win):
 
	circle = Circle( Point(150,150), 100 )
	circle.draw( win )
 

def coloredCircle(win):
 
	circle = Circle( Point(250,250), 100 )
	circle.setFill('blue')
	circle.draw( win )
 

def simpleRectangle(win):
 
	rect = Rectangle( Point(50,450), Point(350,350) )
	rect.setFill( 'red' )
	rect.draw( win )
 

def simpleLine(win):
 
	line = Line( Point(50,50), Point(250,250) )
	line.draw( win )


def main():
	win = createCanvas()
	win.getMouse()
	
	simpleCircle(win)
	win.getMouse()

	coloredCircle(win)
	win.getMouse()

	simpleRectangle(win)
	win.getMouse()

	simpleLine(win)
	win.getMouse()

main()