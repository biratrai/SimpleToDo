# Simple ToDo

Simple Todo is an android app that allows building a todo list and with functionality like adding new ToDo items, editing and deleting an ToDo item.

Time spent: About 12+ hrs in chunks over a week.

## Completed user stories:

The following **required** [functionality](https://gist.github.com/nesquena/843228e83fdc4f5ddc4e) is completed:

* [x] Shows the user a **List of todo** items in list which uses CustomAdapter [RecyclerView.Adapter](https://guides.codepath.com/android/using-the-recyclerview).
* [x] User can **Add** an item by clicking a floating action button which will open a new dialogFragment with input EditText.
* [x] User can **Delete** the item by swiping the item.
* [x] User can **Edit** the item by tapping the item which will open a new dialog activity with EditText.
* [x] User can **persist todo items** into [Realm database](https://realm.io/docs/java/latest/) and retrieve them properly on app restart or orientation changes.

## Best practices and technologies applied.
* [x] Performed [Dagger2](http://google.github.io/dagger/) dependency injection for injecting dependencies and making testing easier and clean.
* [x] Used [Mockito](https://github.com/mockito/mockito) for Mock implementation required during testing.
* [x] Used [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) for UI Testing.
* [x] Implemented [MVP](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/) design pattern following [Clean Coding architecture](http://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html).
* [x] Used [DataBinding library](https://developer.android.com/topic/libraries/data-binding/index.html) for view and data injection.
* [x] Used [recyclerView](https://guides.codepath.com/android/using-the-recyclerview) which uses ViewHolder pattern for efficient use of view objects.
* [x] Followed [material design](https://developer.android.com/design/material/index.html) guidelines to bring material desing to the app.
* [x] Used animation features like [shared Element transition](https://guides.codepath.com/android/Shared-Element-Activity-Transition) and [circular reveal](https://guides.codepath.com/android/Circular-Reveal-Animation).

## Walkthrough of all user stories implemented in the project

![todo](https://cloud.githubusercontent.com/assets/2682565/16129147/a96abb3e-33d1-11e6-92b4-9c2b9c6c763a.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## License

    Copyright 2016 Birat Rai

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
