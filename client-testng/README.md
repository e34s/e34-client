# Element34 Selenium Box - TestNG Reporting Client

Use this client for adding reporting capabilities to your TestNG tests executed on the Selenium Box

The following setup is required: 

### 1. Edit pom.xml 
In your pom.xml add the ``client-testng`` dependency along with the ``e34s`` repository-  
```xml

 <dependencies>
    <dependency>
      <artifactId>client-testng</artifactId>
      <groupId>com.element34</groupId>
      <version>1.2.51-SNAPSHOT</version>
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
In your test add the following line after you instatiated your driver:
 
``` 
driver = new DriverAutoLogAugmenter().augment(driver);
```

An example test code: 
```java
 @Test
  public void ff() throws MalformedURLException, InterruptedException {
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

