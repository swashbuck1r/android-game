A Simple Android Example
========================
This android app is ready to be built by the CloudBees Jenkins service (http://www.cloudbees.com)


Create your build job as a free-style project
---------------------------------------------
From the Jenkins UI, choose to create a New Job.  When prompted, choose to use a free-style build project.

Configure Source Repository
---------------------------
In Source Code Management section, add the URL to this repo (or your forked copy)

Configure the Android build steps
---------------------------------
In the Build section, add an 'Execute shell' build step, with the following command:
    #generate the local.properties file
    rm -f local.properties
    /opt/android/android-sdk-linux/tools/android update project --path ./

Add another 'Invoke Ant' build step
- select Ant Version 1.8.1
- set Targets to: debug

Save and Build
--------------
You are now configured to build your Android app.  Click the 'Build Now' button whenever you want to pull your lastest source, and re-build your Android app.

The built Android artiface can be found at: 
https://ACCOUNT.ci.cloudbees.com/job/JOB_NAME/ws/bin/GameActivity-debug.apk
