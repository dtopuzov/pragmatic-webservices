import AxeBuilder from "@axe-core/webdriverjs";
import { By, Key, ThenableWebDriver, until, WebElement, WebElementCondition } from "selenium-webdriver";
import { EC, WaitCondition } from "./conditions";
import { Level, Type } from "selenium-webdriver/lib/logging";
import { DriverManager } from "./driver-manager";

export class Browser {
    public driver: ThenableWebDriver;

    constructor(driver: ThenableWebDriver = new DriverManager().getDriver()) {
        this.driver = driver;
    }

    public async close(): Promise<void> {
        await this.driver.quit();
    }

    public async navigateTo(url: string): Promise<void> {
        await this.driver.navigate().to(url);
    }

    public async refresh(): Promise<void> {
        await this.driver.navigate().refresh();
    }

    public async switchToIFrame(elementLocator: By): Promise<void> {
        const iframe = await this.find(elementLocator);
        await this.driver.switchTo().frame(iframe);
    }

    public async getCurrentUrl(): Promise<string> {
        return await this.driver.getCurrentUrl();
    }

    public async getBrowserName(): Promise<string> {
        const capabilities = (await (await this.driver).getCapabilities());
        const browserName = capabilities.getBrowserName().toLowerCase();
        return browserName;
    }

    public async getAccessibilityViolations(cssSelector = "html", disableRules = ["color-contrast"]): Promise<[]> {
        await this.find(By.css(cssSelector));
        const axe = new AxeBuilder(this.driver)
            .include(cssSelector)
            .disableRules(disableRules);
        const result = await axe.analyze();
        return result.violations as [];
    }

    public async clearLogs(): Promise<void> {
        await this.driver.manage().logs().get(Type.BROWSER);
    }

    public async getErrorLogs(excludeList = ["favicon.ico"], logLevel = Level.SEVERE): Promise<string[]> {
        const errors = [];
        const capabilities = (await (await this.driver).getCapabilities());
        const platform = capabilities.getPlatform().toLowerCase();
        const browserName = capabilities.getBrowserName().toLowerCase();

        // Can not get FF logs due to issue.
        // Please see:
        // https://github.com/mozilla/geckodriver/issues/284#issuecomment-477677764
        //
        // Note: Logs are not supported on mobile platforms too!
        if (browserName === "chrome" && platform !== "android" && platform !== "iphone") {
            const logs = await this.driver.manage().logs().get(Type.BROWSER);
            for (const entry of logs) {
                // Check if the entry's level is greater than or equal to the provided logLevel and collect all included logs
                if (entry.level.value >= logLevel.value) {
                    errors.push(entry.message);
                }
            }
        }

        // Filter errors
        let filteredErrors = errors;
        for (const excludeItem of excludeList) {
            filteredErrors = filteredErrors.filter((error: string) => {
                return error.toLowerCase().indexOf(excludeItem.toLowerCase()) < 0;
            });
        }

        return filteredErrors;
    }

    public async find(locator: By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<WebElement> {
        const errorMessage = `Failed to find element located by ${locator}.`;
        if (locator instanceof By) {
            return await this.driver.wait(until.elementLocated(locator), timeout, errorMessage, pollTimeout);
        } else {
            return await this.driver.wait(until.elementLocated(By.css(locator)), timeout, errorMessage, pollTimeout);
        }
    }

    public async findAll(locator: By | string): Promise<WebElement[]> {
        if (locator instanceof By) {
            return await this.driver.findElements(locator);
        } else {
            return await this.driver.findElements(By.css(locator));
        }
    }

    public async click(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<void> {
        try {
            if (!(element instanceof WebElement)) {
                element = await this.find(element, { timeout: timeout, pollTimeout: pollTimeout });
            }
            await element.click();
        } catch {
            if (!(element instanceof WebElement)) {
                element = await this.find(element, { timeout: timeout, pollTimeout: pollTimeout });
            }

            const notVisibleMessage = (element instanceof WebElement)
                ? `Element located is not visible.`
                : `Element located by ${element} is not visible.`;

            const notEnabledMessage = (element instanceof WebElement)
                ? `Element located is not visible.`
                : `Element located by ${element} is not visible.`;

            await this.wait(until.elementIsVisible(element), { timeout: timeout, message: notVisibleMessage });
            await this.wait(until.elementIsEnabled(element), { timeout: timeout, message: notEnabledMessage });

            await element.click();
        }
    }

    public async hover(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<void> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element, { timeout: timeout, pollTimeout: pollTimeout });
        }

        const actions = this.driver.actions({ async: true, bridge: true });
        await actions.move({ origin: element }).perform();
    }

    public async contextClick(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<void> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element, { timeout: timeout, pollTimeout: pollTimeout });
        }

        const actions = this.driver.actions({ async: true, bridge: true });
        await actions.contextClick(element).perform();
    }

    public async waitForAnimationAndClick(element: WebElement | By | string, { timeout = 10000, pollTimeout = 50 } = {}): Promise<void> {
        await this.waitForAnimation(element, { timeout: timeout, pollTimeout: pollTimeout });
        await this.click(element, { timeout: timeout, pollTimeout: pollTimeout });
    }

    public async dragTo(source: WebElement | By, target: WebElement | By): Promise<void> {
        let sourceElement: WebElement;
        let targetElement: WebElement;

        if (source instanceof By) {
            sourceElement = await this.find(source);
        } else {
            sourceElement = source;
        }

        if (target instanceof By) {
            targetElement = await this.find(target);
        } else {
            targetElement = target;
        }

        const actions = this.driver.actions({ async: true, bridge: true });
        await actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    public async dragByOffset(element: WebElement | By | string, offsetX: number, offsetY: number): Promise<void> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element);
        }

        const actions = this.driver.actions({ async: true, bridge: true });
        await actions.dragAndDrop(element, { x: offsetX, y: offsetY }).perform();
    }

    public async type(text: string): Promise<void> {
        await this.driver.actions({ async: true, bridge: true }).sendKeys(text).perform();
    }

    public async typeInElement(element: WebElement | By | string, text: string, { clear = true, sendEnter = false } = {}): Promise<void> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element);
        }

        if (clear) {
            await element.clear();
        }

        await element.sendKeys(text);

        if (sendEnter) {
            await this.type(Key.ENTER);
        }
    }

    public async sendKeysCombination(keys: string[]): Promise<void> {
        const actions = this.driver.actions({ async: false, bridge: true });
        for (const key of keys) {
            actions.keyDown(key).pause(10);
        }

        for (const key of keys.reverse()) {
            actions.keyUp(key).pause(10);
        }

        await actions.perform();
    }

    public async sendControlKeyCombination(key: string): Promise<void> {
        const control = (process.platform === 'darwin' ? Key.COMMAND : Key.CONTROL);
        await this.sendKeysCombination([control, key]);
    }

    public async isVisible(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<boolean> {
        return await this.waitSafely(EC.isVisible(element), { timeout: timeout, pollTimeout: pollTimeout });
    }

    public async isNotVisible(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<boolean> {
        return await this.waitSafely(EC.notVisible(element), { timeout: timeout, pollTimeout: pollTimeout });
    }

    public async isInViewport(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<boolean> {
        return await this.waitSafely(EC.isInViewport(element), { timeout: timeout, pollTimeout: pollTimeout });
    }

    public async isNotInViewport(element: WebElement | By | string, { timeout = 10000, pollTimeout = 25 } = {}): Promise<boolean> {
        return await this.waitSafely(EC.notInViewport(element), { timeout: timeout, pollTimeout: pollTimeout });
    }

    public async hasFocus(element: WebElement | By | string): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasFocus(element))
            : await this.waitSafely(EC.hasFocus(await this.find(element)));
    }

    public async hasNoFocus(element: WebElement | By | string): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasNoFocus(element))
            : await this.waitSafely(EC.hasNoFocus(await this.find(element)));
    }

    public async hasText(element: WebElement | By | string, text: string): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasText(element, text))
            : await this.waitSafely(EC.hasText(await this.find(element), text));
    }

    public async hasValue(element: WebElement | By | string, value: string): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasValue(element, value))
            : await this.waitSafely(EC.hasValue(await this.find(element), value));
    }

    public async hasAttribute(element: WebElement | By | string, attribute: string, value: string, exactMatch = true): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasAttribute(element, attribute, value, exactMatch))
            : await this.waitSafely(EC.hasAttribute(await this.find(element), attribute, value, exactMatch));
    }

    public async hasClass(element: WebElement | By | string, value: string, exactMatch = false): Promise<boolean> {
        return (element instanceof WebElement)
            ? await this.waitSafely(EC.hasClass(element, value, exactMatch))
            : await this.waitSafely(EC.hasClass(await this.find(element), value, exactMatch));
    }

    public async sleep(milliseconds: number): Promise<void> {
        await this.driver.sleep(milliseconds);
    }

    public async wait(condition: WebElementCondition | WaitCondition, { timeout = 10000, message = 'Failed to satisfy condition.', pollTimeout = 25 } = {}): Promise<void> {
        await this.driver.wait(condition, timeout, message, pollTimeout);
    }

    public async waitSafely(condition: WebElementCondition | WaitCondition, { timeout = 10000, pollTimeout = 25 } = {}): Promise<boolean> {
        try {
            await this.driver.wait(condition, timeout, null, pollTimeout);
            return true;
        }
        catch (error) {
            return false;
        }
    }

    public async waitForAnimation(element: WebElement | By | string, { timeout = 10000, pollTimeout = 50 } = {}): Promise<void> {
        const locatorStringValue = (element instanceof WebElement)
            ? 'element'
            : element;

        await this.wait(EC.isVisible(element), { timeout: timeout, message: `Failed to find ${locatorStringValue}` });

        const isElementStable = async (): Promise<boolean> => {
            const rect = (element instanceof WebElement)
                ? await element.getRect()
                : await (await this.find(element)).getRect();

            await this.sleep(pollTimeout);
            const newRect = (element instanceof WebElement)
                ? await element.getRect()
                : await (await this.find(element)).getRect();

            return (
                rect.x === newRect.x &&
                rect.y === newRect.y &&
                rect.width === newRect.width &&
                rect.height === newRect.height
            );
        };

        await this.wait(isElementStable, { timeout: timeout, message: `Element ${locatorStringValue} is still moving or resizing.` });
    }

    public async getScreenshot(): Promise<string> {
        return await this.driver.takeScreenshot();
    }

    // eslint-disable-next-line @typescript-eslint/ban-types
    public async executeScript(script: string | Function): Promise<unknown> {
        return await this.driver.executeScript(script);
    }

    public async getText(element: WebElement | By | string): Promise<string> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element);
        }

        try {
            return await element.getText();
        }
        catch {
            return undefined;
        }
    }

    public async getAttribute(element: WebElement | By | string, attribute: string): Promise<string> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element);
        }

        return await element.getAttribute(attribute);
    }

    public async getProperty(element: WebElement | By | string, property: string): Promise<string> {
        if (!(element instanceof WebElement)) {
            element = await this.find(element);
        }

        const script = function (element, property) { return element[property]; };
        return await this.driver.executeScript(script, element, property);
    }
}
