
# To start the project

To run :  ./gradlew bootRun
To build : ./gradlew build
To run test: ./gradlew test
    #http://localhost:8083/api/music/artist/detail/f27ec8db-af05-4f36-916e-3d57f91ecf5e
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
      I did not finished all requirements which is written on the document. I have time constraint.
     
     I did not do enough exception checking and the test cases may not be also sufficient. 
    
     I did not also do any deployment script. I mean like  a docker file or yaml file for deployment. 

     It needs refactroing ,cleaning and optimizing. Even I did not take much time to think what the name of the classes, methods and variables should be. I just use randomly only 
     to solve the assignment.