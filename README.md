# revoluttesttask
There is a lot of room for improvements. Adding additional level of abstraction could've been nice: steps class, or smth like this. Going away from classic assertions to assertj with its ability to adopt to a different components.
I used asserj for couple of tests just to show that it looks better in my opinion compared with classic testNg asserts.
I was't able to find an optimal solution to wait till the page is loaded. I'd prefer to know more about the framework which was used here.
As test report i used classic testNg emailable report.
Just start up the Suite.xml -> it will run tests, and will create tes-output dir with report.html
Hope it is enough.

P.S I was uding mac, so please don't forget to change chromeDriverServerPath in Config.yml to use your chromedriver.
