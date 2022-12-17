export abstract class Settings {
    static readonly WEB_URL = "https://github.com";
    static readonly API_URL = "https://api.github.com";
    static readonly API_TOKEN = process.env["GITHUB_TOKEN"];
}
