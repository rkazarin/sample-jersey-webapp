sample-jersey-webapp
====================

This is a sample web application that uses Java + Jersey on the backend and Angular JS on the frontend. It demonstrates using the Stormpath SDK to generate API Keys, create new accounts, generate Oauth Tokens, and make HTTP calls to REST endpoints using both Oauth and Basic Authentication.

An in-depth blog post/tutorial for this code is available [here](https://stormpath.com/blog/jersey-app-key-management/).

This project requires maven.

Get started:

```
$ git clone https://github.com/rkazarin/sample-jersey-webapp.git
$ cd sample-jersey-webapp
$ mvn install
```
Deploy the .war file to your web container/application server and launch/access it according to your container's configuration.

Questions or suggestions? Please don't hesitate to email me at email.rkazarin@gmail.com

### Application walkthrough:

**Login Screen** <br>
Type your username/password if you have an account. If not, fill out the 'Create Account' form, submit, and then sign in.<br>
![alt tag](http://i.imgur.com/SACJ88l.png)

**Dashboard** <br>
As soon as you log in, an API Key and Secret will be generated for you. The sidebar titles describe the functionality hidden behind each link.<br>
![alt tag](http://i.imgur.com/UIf6xjS.png)

**Make a REST call using Basic Auth** <br>
Click this sidebar link and the functionality will pop up in the center of the page. Select a city and experience the simplicity of Basic authorization! <br>
![alt tag](http://i.imgur.com/UgQ0jWC.png)

**Generate an Oauth Token** <br>
This functionality lets you exchange your API credentials for an Oauth Token. First, select the cities you'd like to be able to have access to (scope). Clicking GetOauth will generate your token and set you up for the next section.
![alt tag](http://i.imgur.com/RwACzLW.png)

**Make permitted REST call using Oauth** <br>
Since we included San Mateo in our scope when generating an Oauth Token we can view it's weather with no problem.
![alt tag](http://i.imgur.com/7TctAt1.png)

**Make a forbidden REST call using Oauth** <br>
Since we did not include Berlin in our scope when generating an Oauth Token, attempting to view it's weather is prohibited!
![alt tag](http://i.imgur.com/HvhKuml.png)
