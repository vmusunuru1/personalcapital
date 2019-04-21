# personalcapital

Analysis:

 1.Since the data in the file is lot and file size is 140MB. But elastic search only index the documents with filesize under 10MB.(data issue).So we need to split the csv file into chunks of json files under 10MB.
 
 Design:
 1.Divide the CSV file into small chunks of json files.
 2.Create json file into elastic search format. Like Ex: {Index: {index:name, type:sometype, id:545}} {key1:value1, key2:value2}
 
 Solution:
 1.Create ElasticSearch domain using cluster of smaller config.
 2.Create a apigateway endpoint with getmethod with resource name as /getPlans
 3.Create a lambda function and map the method to the lambda function.
 4.Index the documents using curl command using a for loop and so created a shell script.
 5.Create an index in kibana dashboard and search in the discover tab for the results.
 
 Technical details:
 1.ConvertCSVtoJSON.java is to convert the split the csv file in chunks and place them in a directory.
 2.CurlExecution.sh is used to index the data in json files.
 3.Request is the pojo is get the key/value and parses that json to the lambda function.
 4.Plans is the lambdafunction.(Build the jar file for lambda and deploy to lambda in aws console)
    mvn package shade:shade -DskipTests
    Output jar - personalcapital-0.0.1-SNAPSHOT.jar (Replacing /Users/vamsimusunuru/Documents/GitHub/personalcapital/target/personalcapital-0.0.1-SNAPSHOT.jar with /Users/vamsimusunuru/Documents/GitHub/personalcapital/target/personalcapital-0.0.1-SNAPSHOT-shaded.jar)
    
    
 
 5.Screenshots related to results attached in the package - ProjectResultsWithScreenshot.
 
 
 Finally the Endpoints to call from postman or swagger etc for different query parameters.
 
 
 
Plan Name: If you want to search for planNAME - "MECHANICAL SOLUTIONS INC 401(K) PLAN",
Uri will be - https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prd/getplans?plan-name=KS
 the encoded link will be : https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prod/getPlans?plan-name=MECHANICAL%20SOLUTIONS%20INC%20401(K)%20PLAN

e.g. https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prd/getplans?plan-name= Your Plan Name

Sponsor Name: If you search for MECHANICAL SOLUTIONS, INC, 
uri is -  https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prod/getplans?sponsor-name=MECHANICAL%20SOLUTIONS,%20INC

e.g. https:/t98fe87pal.execute-api.us-west-2.amazonaws.com/prd/getplans?sponsor-name= Your Sponsor Name

Sponsor State:  To search for the The plans available in KS state
Uri - https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prd/getPlans?sponsor-state=KS

e.g. https://t98fe87pal.execute-api.us-west-2.amazonaws.com/prd/getPlans?sponsor-state= Your Sponsor State
 
