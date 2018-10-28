using log4net;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using System;

namespace GitHubTests.Base
{
    /// <summary>
    /// Base WebTest class.
    /// All web tests clases should inherit it.
    /// </summary>
    [TestFixture]
    public abstract class WebTest
    {
        protected static ILog Log { get; set; }
        protected static IWebDriver Driver { get; set; }

        static WebTest()
        {
            // Register a finalizer here:
            AppDomain.CurrentDomain.DomainUnload += BaseTestCleanup;
        }

        /// <summary>
        /// This will be executred once before each test.
        /// </summary>
        [SetUp]
        public void BaseTestInit()
        {
            // Init logger
            Log = LogManager.GetLogger(GetType());

            // Log beggining of test
            var name = TestContext.CurrentContext.Test.Name;
            Log.Info("=======================================================");
            Log.Info("Start Test: " + name);

            // Start new browser (if not already running).
            if (Driver == null)
            {
                Driver = new ChromeDriver();
                Driver.Manage().Timeouts().PageLoad = TimeSpan.FromSeconds(30);
            }
        }

        /// <summary>
        /// This will be executed once after each test.
        /// </summary>
        [TearDown]
        public void BaseTestCleanup()
        {
            // Log test outcome
            var name = TestContext.CurrentContext.Test.Name;
            var outcome = TestContext.CurrentContext.Result.Outcome.Status;
            Log.Info("Test End: " + name);
            Log.Info("Outcome: " + outcome.ToString());
        }

        /// <summary>
        /// This will be executed after all tests in class.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private static void BaseTestCleanup(object sender, EventArgs e)
        {
            if (Driver != null)
            {
                Driver.Quit();
            }
        }
    }
}
