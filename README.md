# GAD-Leaderboard-Mobile-App
An android app that provides an interface to the Google Africa Developer Scholarship leaderboard.

## Introduction
### Features
The app has 2 screens: a view for displaying the leaderboard and another for submitting the project.
The app caches the data after every network request and restores this data when there's a network failure while also scheduling another request.
* Data persistence with [Room](http://developer.android.com/training/data-storage/room)
* Network requests with [Retrofit](http://square.github.io/retrofit)
* Json parsing with [GSON](http://github.com/google/gson)
* Persistence of data on configuration changes with ViewModels
* ViewPagers and TabLayout for multi-fragment view with tabs
* SwipeToRefresLayout for data refreshing
### Images
![Loge](/app/src/main/res/drawable/program_logo.png)
![Logo](/app/src/main/res/drawable/logo.png)

## Requirements
- Minimum SDK - API 28: Android 9 (Pie)

## Release
Version 1.0