
# To start the project

To run :  ./gradlew bootRun
To build : ./gradlew build

# What I use
         I prefer to use gralde for the build. 
         I created a reactive java project to make the code more resilient and responsive during high request in short period of time.
# #How I structured my project
       I have  two mode classes (Artist and ArtistDto). ArtistDto is mapped when data come from the clien respone. The second Artist model class map from ArtistDto which give us
       the benefit to filter or to make any change according to our end use requirements. 

       Generic client class which enable us to make a request using webclient builder. The webclient builder
       can configurable to meet our need for eg. we can add jwt authorization header and more. 

       I created customized exception handler class which extend from webclient response.
       I have tried to create two Test class.

# What I did not do 
      I did not finished all requirements which is written on the assignment. I have time constraint. So I only make one single call to musicbrainz to get artist information and
      tried to show how can I implement the remaining if I would have more time.
     
     I did not do enough exception checking and the test cases may not be also sufficient. 
    
     I did not also do any deployment script or It would be good if I have created a docker file and yaml file for deployment. 