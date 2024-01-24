# 9Tanya 🍿
Welcome to 9Tanya Popcorn! Your ultimate destination for exploring the world of movies. 

## Overview

9Tanya Popcorn is an Android app that leverages the api.themoviedb API for an immersive cinematic experience. Discover curated lists, explore detailed information about movies, search for your favorites, and much more.

## Features

### 1. Cinematic Selections
Discover handpicked movies curated just for you from api.themoviedb. Our interface showcases key details, making it easy to explore and choose your next favorite.

<div style="display: flex; justify-content: space-between;">
    <img src="screenshots/onboarding1.png" alt="Onboarding Screen 1" style="width: 20%;"  />
    <img src="screenshots/onboarding2.png" alt="Onboarding Screen 2" style="width: 20%;" />
    <img src="screenshots/onboarding3.png" alt="Onboarding Screen 3"style="width: 20%;"  />
    <img src="screenshots/home.png" alt="Home Screen" style="width: 20%;" />
</div>

### 2.  Cinematic Details
Dive deep into the world of your chosen movie. Our detailed view provides comprehensive information, including plot summaries, release dates, and more.

<div style="display: flex; justify-content: space-between;">
    <img src="screenshots/Detail1.png" alt="Capture d'écran 1"  />
    <img src="screenshots/Detail2.png" alt="Capture d'écran 2" />
</div>

### 3. Explore and Search
Unleash the power of search! 9Tanya allows you to search for movies by name.

<img src="screenshots/search.png" alt="Capture d'écran 1"  />

## Technologies Used
**API Integration** : Seamless integration with api.themoviedb keeps you updated with the latest information on movies and TV shows.

## Built With 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    - [MutableLiveData](https://developer.android.com/topic/libraries/architecture/mutablelivedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Glide](https://bumptech.github.io/glide/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [MaterialColors](https://github.com/theapache64/material_colors) - Android material color palettes

## Architecture 🗼

This project follows the famous MVVM architecture and best practices from Google's [GithubBrowserSample](https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample)

![](screenshots/arch.png)