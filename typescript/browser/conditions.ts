import { By, WebDriver, WebElement } from "selenium-webdriver";

export type WaitCondition = (driver: WebDriver) => Promise<boolean>;

const VIEWPORT_SCRIPT = function (element) {
    const box = element.getBoundingClientRect();
    const cx = box.left + box.width / 2;
    const cy = box.top + box.height / 2;
    let target = document.elementFromPoint(cx, cy);
    while (target) {
        if (target === element) {
            return true;
        }
        target = target.parentElement;
    }
    return false;
};

export class EC {
    public static hasText(element: WebElement, text: string): (driver: WebDriver) => Promise<boolean> {
        return () => element.getText().then(result => {
            return result === text;
        });
    }

    public static hasValue(element: WebElement, value: string): (driver: WebDriver) => Promise<boolean> {
        return () => element.getAttribute("value").then(result => {
            return result === value;
        });
    }

    public static hasFocus(element: WebElement): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            const focused = await driver.switchTo().activeElement();
            return await element.getId() === await focused.getId();
        };
    }

    public static hasNoFocus(element: WebElement): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            const focused = await driver.switchTo().activeElement();
            return await element.getId() !== await focused.getId();
        };
    }

    public static hasChild(element: WebElement, locator: By | string): () => Promise<boolean> {
        return async () => {
            return (locator instanceof By)
                ? element.findElements(locator).then(result => { return result.length > 0; })
                : element.findElements(By.css(locator)).then(result => { return result.length > 0; });
        };
    }

    public static hasAttribute(element: WebElement, attribute: string, value: string, exactMatch = true): (driver: WebDriver) => Promise<boolean> {
        return () => element.getAttribute(attribute).then(result => {
            if (exactMatch) {
                return result === value;
            } else {
                return result.includes(value);
            }
        });
    }

    public static hasClass(element: WebElement, value: string, exactMatch = false): (driver: WebDriver) => Promise<boolean> {
        return this.hasAttribute(element, "class", value, exactMatch);
    }

    public static isVisible(element: WebElement | By | string): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            try {
                if (!(element instanceof WebElement)) {
                    element = (element instanceof By)
                        ? await driver.findElement(element)
                        : await driver.findElement(By.css(element));
                }

                return await element.isDisplayed();
            } catch {
                return false;
            }
        };
    }

    public static notVisible(element: WebElement | By | string): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            try {
                if (!(element instanceof WebElement)) {
                    element = (element instanceof By)
                        ? await driver.findElement(element)
                        : await driver.findElement(By.css(element));
                }

                return !(await element.isDisplayed());
            } catch {
                return true;
            }
        };
    }

    public static isInViewport(element: WebElement | By | string): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            try {
                if (!(element instanceof WebElement)) {
                    element = (element instanceof By)
                        ? await driver.findElement(element)
                        : await driver.findElement(By.css(element));
                }

                const result = await driver.executeScript(VIEWPORT_SCRIPT, element);
                return (result + '') === 'true';
            } catch {
                return false;
            }
        };
    }

    public static notInViewport(element: WebElement | By | string): (driver: WebDriver) => Promise<boolean> {
        return async (driver: WebDriver) => {
            try {
                if (!(element instanceof WebElement)) {
                    element = (element instanceof By)
                        ? await driver.findElement(element)
                        : await driver.findElement(By.css(element));
                }

                const result = await driver.executeScript(VIEWPORT_SCRIPT, element);
                return (result + '') === 'false';
            } catch {
                return true;
            }
        };
    }
}
