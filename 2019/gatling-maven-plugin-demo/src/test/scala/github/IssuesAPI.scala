package github

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class IssuesAPI extends Simulation {
  def getEnvironmentVariable(name: String): String = System.getenv(name)

  val user: String = getEnvironmentVariable("USER")
  val pass: String = getEnvironmentVariable("PASS")

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://api.github.com")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .connectionHeader("close")
    .contentTypeHeader("application/json")
    .userAgentHeader("PostmanRuntime/7.19.0")


  val scn: ScenarioBuilder = scenario("IssuesAPI")
    .exec(http("create issue")
      .post("/repos/" + user + "/test/issues")
      .body(RawFileBody("com/github/issuesapi/0000_request.json"))
      .basicAuth(user, pass))
    .pause(1)
    .exec(http("update issue")
      .patch("/repos/" + user + "/test/issues/769")
      .body(RawFileBody("com/github/issuesapi/0001_request.json"))
      .basicAuth(user, pass))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}