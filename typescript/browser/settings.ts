export class Settings {
    public static get browserName(): string {
        return process.env["BROWSER_NAME"] || "chrome";
    }

    public static get browserWidth(): number {
        return +process.env["BROWSER_WIDTH"] || 1366;
    }

    public static get browserHeight(): number {
        return +process.env["BROWSER_HEIGHT"] || 768;
    }

    public static get headless(): boolean {
        const value = process.env["HEADLESS"];
        return value !== undefined && value.toLocaleLowerCase() === "true";
    }

    public static get baseUrl(): string {
        return process.env["BASE_URL"] || "";
    }

    public static get timeout(): number {
        return +process.env["TIMEOUT"] || 30000;
    }
}
