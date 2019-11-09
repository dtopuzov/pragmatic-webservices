package github

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class IssuesAPI extends Simulation {

  val httpProtocol = http
    .baseUrl("https://api.github.com")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .connectionHeader("close")
    .contentTypeHeader("application/json")
    .userAgentHeader("PostmanRuntime/7.19.0")

  val headers_0 = Map("Postman-Token" -> "027a021d-5318-44e5-8b3f-1a54b43f5a3e")

  val headers_1 = Map("Postman-Token" -> "42f72d65-26ad-4a1c-a1ac-450013ddc755")


  val scn = scenario("IssuesAPI")
    .exec(http("request_0")
      .post("/repos/ws-test-user/test/issues")
      .headers(headers_0)
      .body(RawFileBody("com/github/issuesapi/0000_request.json"))
      .basicAuth("ws-test-user", "pass1234"))
    .pause(1)
    .exec(http("request_1")
      .patch("/repos/ws-test-user/test/issues/769")
      .headers(headers_1)
      .body(RawFileBody("com/github/issuesapi/0001_request.json"))
      .basicAuth("ws-test-user", "pass1234"))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}