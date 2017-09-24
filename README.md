## Element34 Solutions - Selenium Box reporting plugins

The SBOX reporting plugin allows for test repprts to be generated on the client side (where the test was started from). If you run tests locally from your IDE, the reports will be also created locally. 
The plugin also integrates with Jenkins, where the report can be directly viewed in the Jenkins job. 

Specific information for the JUnit and TestNG plugin and how to configure it can be found in the README.md in the respective project (https://github.com/e34s/e34-client/tree/master/client-junit4 and https://github.com/e34s/e34-client/tree/master/client-testng). 

### Jenkins integration for report publishing
If you want to make the test report viewable as part of the Jenkins job you need to do the following steps: 

- install the HTML publisher plugin 
- set ``System.setProperty(“hudson.model.DirectoryBrowserSupport.CSP”, “”)`` in order to allow publishing of the HTML report 
- in the job configuration add a Post-Build-Action to publish the report
- change the paths if required to your actual report location

![alt text](/images/html-publisher.PNG "HTML publisher setup")
  
- the report can be viewed directly in Jenkins by clicking on the "HTML report" link in the respective job. 
![alt text](/images/html-publisher-left.PNG "HTML publisher link")
