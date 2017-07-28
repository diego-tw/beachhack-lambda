# beachhack-lambda
BeachHack on AWS Lambda: stock alert for Mads

## AWS Setup

### User setup
* Get access to the AWS Account used for this project
* Go into IAM and create a new user, make sure that you generate an AWS Access Key and Secret for the user, tick "Programmatic access"(keep these values as you won't be able to get the secret again).
* Assign the user to the `beachhackers` group, as this will give the user the correct permissions.

### AWS CLI Setup
* Install `awscli` as per [Installing the AWS Command Line Interface](http://docs.aws.amazon.com/cli/latest/userguide/installing.html) or use `homebrew` if on a Mac.
* On your machine use the AWS CLI and run `aws configure` this will as you for the access key, access secret and the default region; use the values from when you created the IAM user and use 'us-east-2' (Ohio) for the default region

## Build and Deploy
To build and deploy the 'BeachHack' lambda function as well as the front-end in ```beach_hack_ui```, do a ```mvn clean package deploy```.

## Run
There are three ways to invoke the Lambda function:
* Via the AWS Lambda console: pressing TEST with a test event ```{"drinkName": "Campari", "quantity": 1}```
* Via the AWS API Gateway console: pressing the TEST icon with a test event ```{"drinkName": "Campari", "quantity": 1}```
* By logging into the frontend on https://s3.us-east-2.amazonaws.com/com.thoughtworks.beachhack.web/index.html

## Getting Notifications
Add a 'email' subscription to the SNS topic ```low_stock_alert``` topic. You will get a confirmation email from AWS SNS, then follow the link in there to activate your subscription.
                                                                