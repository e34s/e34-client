# Element34 Selenium Box - JUnit Reporting Client

Use this client for adding reporting capabilities to your JUnit tests executed on the Selenium Box.

### Required steps
### 1. Edit pom.xml 
In your pom.xml add the ``client-testng`` dependency along with the ``e34s`` repository-  
```xml

 <dependencies>
    <dependency>
      <artifactId>client-junit</artifactId>
      <groupId>com.element34</groupId>
      <version>1.2.52-SNAPSHOT</version>
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


### 2. Add listeners to your tests
You need to add the following listeners to your tests: ``SeleniumTestWatcher`` and ``SeleniumSuiteWatcher``. 

A sample test looks like this. Note: the listeners can also be wired via other mechnanisms (i.e. extending from a TestBase class). 

**JUnitSuite.java**
```
@RunWith(Suite.class)
@Suite.SuiteClasses({DemoJunit.class})
public class JunitSuite {

  @ClassRule
  public static TestRule suiteWatcher = new SeleniumSuiteWatcher();
}
```

**DemoJunit.java**
```
public class DemoJunit {

  @Rule
  public TestWatcher testwatcher = new SeleniumTestWatcher();

  @Test
  public void mytest() {
    ...
    ...
    WebDriver driver = new RemoteWebDriver(new URL("https://vm-105.element34.net/wd/hub"), chrome);

    driver = new DriverAutoLogAugmenter().augment(driver);
    ...
    ...
    
    driver.quit();
  }
}
```

### 3. Report
#### Viewing the report
After the test run is finished, the test report is created in ```/e34report``` by default.`Screenshots are put in the ```/screenshots```  directory on the main project level. 
If you require the report to be generated elsewhere, set the reportsDirectory (from the command line, not in the pom) system property to override the default setting. 
```
mvn test  -DreportsDirectory="/some-path/your-report-directory" 
```

To view the report, just open ```e34report.html``` in a standard browser. 

#### Sharing the report
The report is self-contained and can be shared by i.e. zipping the folder containing the e34report.html and sending it to other individuals. 

#### Note: 
In order to view the videos in the report, a connection to the Selenium Box system is required. All other artifacts are stored locally. 

