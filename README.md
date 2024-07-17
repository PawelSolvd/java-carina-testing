# Setup

## Required :

- JDK 22+
- Selenium Server Grid 4.21+
- Chrome driver
- Firefox driver
- Appium + UiAutomator2 and XCUITest drivers
- Android and iOS simulator

## Run :

1. a) Run Selenium Grid `java -jar selenium-server-<version>.jar standalone --detect-drivers true`
<br/>or <br/>
b) Run Selenium Grid hub `java -jar selenium-server-<version>.jar hub` and
node `java -jar selenium-server-<version>.jar node --detect-drivers true`
<br/>or <br/>
c) Run Appium `appium --allow-insecure chromedriver_autodownload` for mobile tests
2. Run `testng.xml` test suite
<br/><br/>

### Tips
- Adding variables macOS `echo 'export NVM_DIR="$HOME/.nvm" [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"' >> ~/.zshenv`
- AppiumInspector JSON`{"platformName": "android", "appium:automationName": "UiAutomator2"}`