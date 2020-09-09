# GAD-Leaderboard-Mobile-App
An android app that provides an interface to the Google Africa Developer Scholarship leaderboard API.

## Introduction
### Features
The app has 2 screens: a view for displaying the leaderboard and another for submitting the project.
The app caches the data after every network request and restores this data when there's a network failure while also scheduling another request.
* Data persistence with [Room](http://developer.android.com/training/data-storage/room)
* Network requests with [Retrofit](http://square.github.io/retrofit)
* Json parsing with [GSON](http://github.com/google/gson)
* Persistence of data on configuration change with ViewModels
* ViewPagers and TabLayout for multi-fragment view with tabs
* SwipeToRefresLayout for data refreshing
### Images
![Screenshot 1](/screenshots/screenshot_1.png)
![Screenshot 2](/screenshots/screenshot_2.png)
![Screenshot 3](/screenshots/screenshot_3.png)
![Screenshot 4](/screenshots/screenshot_4.png)
![Screenshot 5](/screenshots/screenshot_5.png)
![Screenshot 6](/screenshots/screenshot_6.png)

## Requirements
- Minimum SDK - API 28: Android 9 (Pie)

## Release
Version 1.0