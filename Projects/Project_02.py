# Decision making program

def calcPaymentHistoryPoints(paymentFactor):
    if paymentFactor=="yes" or paymentFactor=="Yes":
        return 100
    else:
        return 0

def calcFundsOwedPoints(fundsFactor):
    if fundsFactor==0:
        return 100
    if fundsFactor>=1 and fundsFactor<=999:
        return 80
    if fundsFactor>=1000 and fundsFactor<=4999:
        return 30
    if fundsFactor>=5000:
        return 0

def calcCreditHistoryPoints(creditFactor):
    if creditFactor>=0 and creditFactor<=11:
        return 0
    if creditFactor>=12 and creditFactor<=47:
        return 80
    if creditFactor>=48:
        return 100

def calcNumRejectedPoints(rejectedFactor):
    if rejectedFactor==0:
        return 100
    if rejectedFactor>=1 and rejectedFactor<=4:
        return 50
    if rejectedFactor>=5:
        return 0

def calcAnnualIncomePoints(incomeFactor):
    if incomeFactor>=0 and incomeFactor<=4999:
        return 0
    if incomeFactor>=5000 and incomeFactor<=9999:
        return 50
    if incomeFactor>=10000:
        return 100

def makeDecision(payment, funds, credit, rejected, income):
    # use the makeDecision parameters to call calculate the points
    # of each factor
    pP = calcPaymentHistoryPoints(payment)
    pF = calcFundsOwedPoints(int(funds))
    pC = calcCreditHistoryPoints(int(credit))
    pR = calcNumRejectedPoints(int(rejected))
    pI = calcAnnualIncomePoints(int(income))

    # Use the calculated points to compute the application score
    T=(pP+pF+pC+pR+pI)/(5)
    
    # Use decision structure to determine the final decision
    if T>=50:
        return T,"ACCEPTED"
    else:
        return T,"REJECTED"

def main():
    # Read the number of applications (n) from the user
    A=input("Enter the number of applications: ")
    # Loop n times
    for loop in range(int(A)):
      # For each iteration
      # Read the factors from the user
      payment=input("Enter payment history: ")
      funds=input("Enter amount of funds owed: ")
      credit=input("Enter the length of credit history: ")
      rejected=input("Enter the number of loan applications rejected: ")
      income=input("Enter the annual Income: ")
      # Call makeDecision to get the loan application score and
      # final decision
      # print the decision
      print("Decision: ",makeDecision(payment, funds, credit, rejected, income))

main()
