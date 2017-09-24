# Element34 Selenium Box - TestNG Reporting Client

Use this client for adding reporting capabilities to your TestNG tests executed on the Selenium Box.

The following setup is required: 

### 1. Edit pom.xml 
In your pom.xml add the ``client-testng`` dependency along with the ``e34s`` repository-  
```xml

 <dependencies>
    <dependency>
      <artifactId>client-testng</artifactId>
      <groupId>com.element34</groupId>
      <version>1.2.52.SNAPSHOT</version>
    </dependency>
  </dependencies>
```

```xml
  <repositories>
    <repository>
      <id>e34-client-mvn-repo</id>
      <url>https://raw.github.com/e34s/e34-client/mvn-repo/</url>
      <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
      </snapshots>
    </repository>
  </repositories>
```


### 2. Augment your driver 
Your driver needs to be augmented in order to create the client side report. In your test add the following line after you instatiated your driver (depending on what your driver is called):
 
``` 
driver = new DriverAutoLogAugmenter().augment(driver);
```

An example test code: 
```java
 @Test
  public void mytest() throws MalformedURLException, InterruptedException {
    ...
    ...
    WebDriver driver = new RemoteWebDriver(new URL("https://vm-105.element34.net/wd/hub"), chrome);
    
    //driver augmentation
    driver = new DriverAutoLogAugmenter().augment(driver);
    
    //test code 
    driver.get("https://google.com");
    ...
    ...
    ...
    driver.quit();
```

### 3. Report
#### Viewing the report
After the test run is finished, the test report is created under ```test-output/e34report.html```. The report can be viewed with any browser. At the moment there is an an issue with Internet Explorer. We recommend to use Chrome to take advantage of the full functionality. 

#### Sharing the report
The report is self-contained and can be shared by i.e. zipping the ```test-output``` folder and sending it to other individuals. 

#### Note: 
In order to view the videos in the report, a connection to the Selenium Box system is required. All other artifacts are stored locally. 

