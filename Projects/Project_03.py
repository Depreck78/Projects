# Graph creation

import matplotlib.pyplot as pyplot

def readStudentsData(filename):
    studentsList=[]

    c=open(filename,"r")
    r=c.read()
    l=r.split("\n")
    
    for i in range(len(l)):
        studentsList.append(l[i].split(","))
    c.close()
    return studentsList
   
def readRegistrationData(filename):
    myFile1 = open(filename, 'r')
    data1 = myFile1.read()
    lines = data1.split(("\n"))
    registrationList = []
   
    for i in lines:
        x = i.split(",")
        registrationList.append(x)
    for i in registrationList:
        value = i.pop(2)
        integer = int(value)
        i.append(integer)
       
    myFile1.close()
    return registrationList
 
def readCoursesData(filename):
    myFile2 = open(filename, "r")
    data2 = myFile2.read()
    lines = data2.split(("\n"))
   
    coursesList = []
    for i in lines:
        x = i.split(",")
        coursesList.append(x)
    myFile2.close()
    return coursesList
 
def countStudents(studentsList, gender, state):
    count = 0
    for student in studentsList:
        if student[2] == gender and student[3] == state:
            count += 1
    return count
   
def studentsPassedCourse(studentsList, coursesList, registrationList, courseName):
    passedList = []
    passednames = []
    for course in coursesList:
        if course[1] == courseName:
            cname = course[0]
    for grade in registrationList:
        if grade[2] >= 80:
            passedList.append(grade[0])
    for ID in passedList:
        for name in studentsList:
            if ID == name[0]:
                passednames.append(name[1])
    return (sorted(passednames))
   
def studentsTakingOneCourse(studentsList, registrationList, courseNum):
    data = []
    studentName = []
    for i in registrationList:
        if courseNum == i[1]:
            data.append(i[0])
    for n in data:
        for x in studentsList:
            if n == x[0]:
                studentName.append(x[1])
    count = len(studentName)
    return (sorted(studentName)), count
   
def studentsTakingTwoCourses(studentsList, registrationList, courseNum1, courseNum2):
    list2 = []
    list3 = []
    studentName = []
    for lst in registrationList:
        if courseNum1 == lst[1]:
            list2.append(lst[0])
    for i in list2:
        for lst in registrationList:
            if i == lst[0] and courseNum2 == lst[1]:
                list3.append(lst[0])
    for n in list3:
        for lst1 in studentsList:
            if n == lst1[0]:
                studentName.append(lst1[1])
    count = len(studentName)
    return (sorted(studentName)), count
def stateCourseStatistics(studentsList, registrationList, courseNum):
    data1 = []
    state1 = []
    state2 = []
    data2 = []
   
    for i in registrationList:
        if courseNum == i[1]:
            data1.append(i[0])
    for n in data1:
        for x in studentsList:
            if n == x[0]:
                state1.append(x[3])
                state2 = list(set(state1))
    for y in state2:
        number = state1.count(y)
        data2.append([y, number])   
    return (sorted(data2, key =lambda x:x[0]))
   
    
def plotPieChart(courseNum1, courseNum1Count, courseNum2, courseNum2Count, bothCoursesCount):
    labels = ["Both " + courseNum1 + " and " + courseNum2, courseNum1, courseNum2]
    sizes = [bothCoursesCount, courseNum1Count, courseNum2Count]
    colors = ["tomato", "lightgreen", "gold"]
    pyplot.pie(sizes, colors=colors, autopct = "%1.1f%%", startangle=90)
    pyplot.legend(labels=labels, loc = "lower left")
    pyplot.show()
    pyplot.savefig("plotPieChart.pdf")
    return
 
def plotBarChart(statesCountList):
    state = []
    studentID = []
    for i in statesCountList:
        state.append(i[0])
        studentID.append(i[1])
    pyplot.bar(range(len(studentID)), studentID, tick_label=state, width=0.5, color="Lightcoral")
    pyplot.xticks(rotation=-15)
    pyplot.title("State Statistics")
    pyplot.ylabel("# of students")
    pyplot.show()
    pyplot.savefig("plotBarChart.pdf")
    return
   
def main():
    studentsList = readStudentsData("studentsdata.txt")
    print(studentsList)
    registrationList = readRegistrationData("registrationdata.txt")
    print(registrationList)
    coursesList = readCoursesData("coursesdata.txt")
    print(coursesList)
    passedList = studentsPassedCourse(studentsList, coursesList, registrationList, "Python Programming Language")
    print(passedList)
    oneList, oneCount = studentsTakingOneCourse(studentsList, registrationList, "CS177")
    print(oneList)
    print(oneCount)
    twoList, twoCount = studentsTakingTwoCourses(studentsList, registrationList, "CS177", "CS180")
    print(twoList)
    print(twoCount)
    statesCount = stateCourseStatistics(studentsList, registrationList, "CS177")
    print(statesCount)
    course1Count = studentsTakingOneCourse(studentsList, registrationList, "CS177")[1]
    course2Count = studentsTakingOneCourse(studentsList, registrationList, "CS180")[1]
    bothCoursesCount = studentsTakingTwoCourses(studentsList, registrationList, "CS177", "CS180")[1]
    plotPieChart("CS177", course1Count, "CS180", course2Count, bothCoursesCount)
    statesCountList = stateCourseStatistics(studentsList, registrationList, "CS177")
    plotBarChart(statesCountList)

if __name__ == "__main__":
    main()
