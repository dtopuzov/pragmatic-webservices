import { Browser, Builder, logging, ThenableWebDriver } from "selenium-webdriver";
import { Options as ChromeOptions } from "selenium-webdriver/chrome";
import { Options as EdgeOptions } from "selenium-webdriver/edge";
import { Options as FirefoxOptions, ServiceBuilder as FirefoxServiceBuilder } from "selenium-webdriver/firefox";
import { Options as SafariOptions } from "selenium-webdriver/safari";
import { Settings } from "./settings";

export class DriverManager {

    public DEFAULT_CHROMIUM_OPTIONS = [
        `--window-size=${Settings.browserWidth},${Settings.browserHeight}`,
        '--force-device-scale-factor=1',
        '--log-level=1',
        '--disable-extensions',
        '--disable-notifications',
        '--disable-search-engine-choice-screen',
        '--ignore-certificate-errors'
    ];

    public getDriver(): ThenableWebDriver {
        switch (Settings.browserName) {
            case Browser.EDGE: {
                return this.getEdgeDriver();
            }
            case Browser.FIREFOX: {
                return this.getFirefoxDriver();
            }
            case Browser.SAFARI: {
                return this.getSafariDriver();
            }
            default: {
                return this.getChromeDriver();
            }
        }
    }

    public getChromeOptions(args: string[] = this.DEFAULT_CHROMIUM_OPTIONS): ChromeOptions {
        const options = new ChromeOptions();
        args.forEach(argument => {
            options.addArguments(argument);
        });

        if (Settings.headless) {
            options.addArguments('--headless=new');
        }

        // We must disable the Chrome sandbox when running Chrome inside Docker.
        // Chrome's sandbox needs more permissions than Docker allows by default.
        // See:
        // - https://github.com/GoogleChrome/puppeteer/issues/560
        // - https://github.com/microsoft/playwright/issues/1977#issuecomment-619397496
        if (process.env["CONTAINER"]) {
            options.addArguments('--no-sandbox');
            options.addArguments('--disable-setuid-sandbox');
        }

        return options;
    }

    public getChromeDriver(options: ChromeOptions = this.getChromeOptions()): ThenableWebDriver {
        return new Builder().forBrowser(Browser.CHROME).setChromeOptions(options).build();
    }

    public getEdgeOptions(args: string[] = this.DEFAULT_CHROMIUM_OPTIONS): EdgeOptions {
        const options = new EdgeOptions();
        args.forEach(argument => {
            options.addArguments(argument);
        });

        if (Settings.headless) {
            options.addArguments('--headless');
        }

        return options;
    }

    public getEdgeDriver(options: EdgeOptions = this.getEdgeOptions()): ThenableWebDriver {
        return new Builder().forBrowser(Browser.EDGE).setEdgeOptions(options).build();
    }

    public getFirefoxOptions(): FirefoxOptions {
        const options = new FirefoxOptions();
        options.addArguments(`--width=${Settings.browserWidth}`);
        options.addArguments(`--height=${Settings.browserHeight}`);
        options.setLoggingPrefs(logging.Level.SEVERE);

        if (Settings.headless) {
            options.addArguments('--headless');
        }

        return options;
    }

    public getFirefoxDriver(options: FirefoxOptions = this.getFirefoxOptions()): ThenableWebDriver {
        const prefs = new logging.Preferences();
        prefs.setLevel(logging.Type.BROWSER, logging.Level.ALL);

        const service = new FirefoxServiceBuilder().setStdio("inherit");

        return new Builder()
            .forBrowser(Browser.FIREFOX)
            .setLoggingPrefs(prefs)
            .setFirefoxOptions(options)
            .setFirefoxService(service)
            .build();
    }

    public getSafariOptions(): SafariOptions {
        const options = new SafariOptions();
        return options;
    }

    public getSafariDriver(options: SafariOptions = this.getSafariOptions()): ThenableWebDriver {
        const driver = new Builder().forBrowser(Browser.SAFARI).setSafariOptions(options).build();
        const size = { width: Settings.browserWidth, height: Settings.browserHeight, x: 0, y: 0 };
        driver.manage().window().setRect(size);
        return driver;
    }
}
