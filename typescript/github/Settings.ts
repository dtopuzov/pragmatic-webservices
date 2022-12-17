export abstract class Settings {
    static readonly WEB_URL = "https://github.com";
    static readonly API_URL = "https://api.github.com";
    static readonly API_TOKEN = process.env["GITHUB_TOKEN"];
    static readonly OWNER =  process.env["GITHUB_USER"];
    static readonly REPO = process.env["GITHUB_REPO"];
}
