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
To build and deploy the 'BeachHack' lambda function, do a ```mvn clean package deploy```.