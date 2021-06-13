# Affirm - Balance the Loan Books.

# Pre-requistes
	Jdk 1.8 or higher.

# Build/Run
To run the application, execute the command below
	
	mvnw compile exec:java
	
Output files "assignment.csv" and "yeilds.csv" will be generated in the root directory. 

#Questions/Answers

1. How long did you spend working on the problem? What did you find to be the most
difficult part?

3h for the first working solution. 1h for refactoring. Problem statement was easy but there were some scenarios where clarification was needed for input validations.

2. How would you modify your data model or code to account for an eventual introduction
of new, as-of-yet unknown types of covenants, beyond just maximum default likelihood
and state restrictions?

Additional restrictions and criterias can be added to existing Covenant class. All we have to do is to add the related validation in isAllowed() method.

3. How would you architect your solution as a production service wherein new facilities can
be introduced at arbitrary points in time. Assume these facilities become available by the
finance team emailing your team and describing the addition with a new set of CSVs.

In the production system, if the facilities are going to be introduced at any time then I would prefer saving them to database as it will allow us to check for the cheapest facilities realtime instead of the facilities we loaded during application startup. We can load CSVs realtime using REST API endpoints and we will be referring database for latest facilities in production.


4. Your solution most likely simulates the streaming process by directly calling a method in
your code to process the loans inside of a for loop. What would a REST API look like for
this same service? Stakeholders using the API will need, at a minimum, to be able to
request a loan be assigned to a facility, and read the funding status of a loan, as well as
query the capacities remaining in facilities.


You can add a REST webserice endpoint which will call the service layer APIs to perform these operations like 

	POST /processLoan          processLoan() to process loan. Fetch loan details and process it. 
	GET  /getFundingStatus     getFundingStatusByLoanId() to get the status of funding for a given loanId.  
	GET  /getFacilityCapacity  getFacilityCapacity() to get the capacities remaining in facilities.  


5. How might you improve your assignment algorithm if you were permitted to assign loans
in batch rather than streaming? We are not looking for code here, but pseudo code or
description of a revised algorithm appreciated.

If the batches are generated on the basis of some criteria like a batch will contain same interest rate then we can tune our algorithm to process them faster as all of the loans will have same interest rate.
Batch processing might also decrease the performance if it is a randomly created batch. As there might be a loan for which interest rate might be high. In that case, we might have to check all the facilities before we hit the expected facility. Also, if the batch size is big the processing time might go up. 

6. Discuss your solution’s runtime complexity.


