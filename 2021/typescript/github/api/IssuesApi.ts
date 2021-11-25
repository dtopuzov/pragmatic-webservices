import request from "supertest";
import { Settings } from "../Settings";

export class IssuesApi {
    user: string;
    repo: string;

    constructor(user: string, repo: string) {
        this.user = user;
        this.repo = repo;
    }

    async getIssueDetails(number: number): Promise<request.Test> {
        return await request(Settings.API_URL)
            .get(`/repos/${this.user}/${this.repo}/issues/${number}`)
            .set("User-Agent", "supertest")
            .set("Authorization", Settings.API_TOKEN);
    }

    async createIssue(payload: object): Promise<request.Test> {
        return await request(Settings.API_URL)
            .post(`/repos/${this.user}/${this.repo}/issues`)
            .set("User-Agent", "supertest")
            .set("Authorization", Settings.API_TOKEN)
            .set("Content-Type", "application/json")
            .send(payload);
    }
}
