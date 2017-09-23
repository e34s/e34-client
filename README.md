## Element34 Solutions - Selenium Box reporting plugins

Specific information for the JUnit and TestNG plugin can be found in the README.md in the respective project (https://github.com/e34s/e34-client/tree/master/client-junit4 and https://github.com/e34s/e34-client/tree/master/client-testng). 

### Jenkins integrartion for report publishing
The test reports can also be published in Jenkins on a per job basis. Please configure Jenkins as follows: 

- install the HTML publisher plugin 
- set ``System.setProperty(“hudson.model.DirectoryBrowserSupport.CSP”, “”)`` in order to allow publishing of the HTML report 
- in the job configuration add the following  

![alt text](/images/html-publisher.PNG "HTML publisher setup")
  
- the report can be viewed directly in Jenkins by clicking on the "HTML report" link in the respective job. 
![alt text](/images/html-publisher-left.PNG "HTML publisher link")
