
StrengthPal
===========
*readme file*
1. Team info
    - Noah Cho - nc6923
    - Tyler Corley - trc738
    - Even Hvatum - eah2322

2. Brief Instruction
StrengthPal makes it easier for people to record workouts, discover new workout plans, and find gym facilities in your area. Navigating the app is as simple as invoking the navigation drawer. From there you have access to:
  Workout History
    -View past workouts and and start new ones
  Find a Gym
    -Discover gym locations around you in either an interactive map or a list ranked by distance from the user
    -Click on a gym in the list to see more details about that gym
  Workout Selector
    -Browse through different workouts. When you select one, the exercise fields will automatically be populated within the _New Workout_ page
  Settings
    -Set preferences for a variety of topics.
  About
    -See who made the app and what kinds of technology we used. (You can also shake your device and something might happen!)

3. List of features from prototype completed
  Landing Page / Workout Journal
    -Can add a new workout with multiple exercises and remove exercises
    -“Add Exercise” button that dynamically adds rows consisting of empty text boxes so that a user may enter their own custom exercises and fill in the weights, sets, reps used
    -“X” button to remove particular row if user chooses to do so.
  Gym Search
    -Find all gyms in surrounding radius
    -Display gyms on a map
    -Provide a list of gyms sorted by distance
    -Simple navigation between viewing the gyms on a map or as a list
    -Click on a gym in the list to see more details about that gym
  Workout Creator
    -Contains different workout programs to be chosen
  Navigation Drawer
    -List journal, search, workout, preferences, and about options as the main type of navigation
    -Animated retraction
  Preference Page
    -Allows for changing personal information
    -Changes app preferences
  About Page
    -Name of app
    -Names of creators

4. List of features from prototype not completed
  Landing Page / Workout Journal
    -showing calendar not yet complete
    -Calendar is marked with day where the user worked out
    -The ability to save a workout and mark it complete on the calendar using SQLite
    -auto population for other workout programs (only 1 workout program is being auto populated for one day)
  phone Intent from find gym results
  Gym Search
    -Currently displaying a hardcoded sample picture (of 24 Hour Fitness) for every gym’s detail page
    -Currently not making a HTTP request for a gym’s phone number on the details page, instead just displaying a sample number
    -No intents implemented for gym details page
    -Call button and Get Directions button do nothing upon being clicked
  Preference Page
    -Conversion between imperial and metric
  About Page
    -Version number
    -List of API’s used

5. List of features implemented but not in prototype
  Landing Page / Workout Journal
    -LinearLayout of workouts and ability to remove the LinearLayout.
  Navigation Drawer
  UI Improvements (Colors, custom font, icons)
  Preferences Page
    -Preferences show state with the summary field
  About
    -Photos of teammates included
    -Fade in animation on photos
    -Intents for opening web pages for licensing info and giving credit to the icon makers
    -Easter egg. Shake device for an Arnold Schwarzenegger quote!

6. List of classes and major code from other sources
  Overridden methods contain design patterns necessary for creating the navigation drawer based off the code at: http://www.androidhive.info/2013/11/android-sliding-menu-using-navigation-drawer/
    -also used helper files for consistency and maintainability
    -list_item*.xml files, drawer_list_item.xml, list_selector.xml, color.xml, NavDrawerListAdapter.class, NavDrawerItem.class
  ShakeListener.java from Matthew Wiggins: http://android.hlidskialf.com/blog/code/android-shake-detection-listener
  NewWorkoutActivity.java  add() function inspired by James Meade and answers: http://stackoverflow.com/questions/17883574/add-a-relativelayout-dynamically-to-a-linearlayout-in-android
  GymListAdapter<GymListItem> and GymListItem based on an ArrayAdapter design pattern given as an answer athttp://www.stackoverflow.com
  jsonGetter based on multiple answers at http://www.stackoverflow.com
    -covering topics such as making http requests in Android, downloading JSON data, and parsing JSON data in Java.

7. List of classes and major code Implemented by us:

Java Classes:
  -GymFragment.java - contains logic for switching between the gym search results on a map or in a list.
  -GymListAdapter.java - handles setting up multiple GymListItems using a provided array and populates the ListView of surrounding gyms based on the data in each GymListItem.
  -GymListItem.java - contains the logic for creating and setting up a single GymListItem used by the GymListAdapter. Each GymListItem corresponds to an item in the ListView of gyms in the user’s area.
  -ListPage.java - makes a Google Places request for gyms surrounding the user and lists the results ordered by distance in a ListView. Also handles clicks on individual items in the ListView to launch a popup window with more information about each gym.
  -MapPage.java - contains the code for setting up the map, downloading the necessary Google Places JSON, and displaying the results on the map.
  -NewWorkoutActivity.java - if no workout program chosen then the activity is blank with two buttons to add exercises (linearlayout with edittexts). if workout is chosen then the activity populates the workout journal with appropriate exercises

XML:
  -fragment_gym.xml - has a button to switch between map and list views of the surrounding gyms. The list and map are fragments switched in and out of a container that fragment_gym.xml contains.
  -fragment_list.xml - contains only a single ListView that is populated by the GymListAdapter at runtime.
  -fragment_map.xml - contains a Google Map from the Google GMS Services centered on the user’s current location capable of displaying markers of surrounding gyms.
  -gym_list_header.xml - a single styled list header used in the ListView of surrounding gyms.
  -gym_list_item.xml - contains a single generic horizontal LinearLayout representing each item that may be added to the gym list ListView. Used to facilitate the layout of the ListView by grouping multiple TextViews as a single item.
  -gym_popup.xml - has an ImageView for the picture of the gym, a TextView for the name, address, distance to, and phone number of each gym. All of these will be dynamically populated at runtime for the beta release. In alpha, only the name, address, and distance are dynamically populated. Also contains a button to call the gym and a button to get the directions to the gym. Both of these buttons are currently inactive.
  -activity_new_workout.xml - lays out basic layout of the NewWorkoutActivity activity
  -new_workout_row_detail.xml - a LinearLayout with EditText views in a horizontal fashion. This is added on the NewWorkoutAcitvity page when the “Add Exercise” button is clicked
  -new_workout_row_detail2 - like new_workout_row_detail except this is used for populating the workout journal from a program. This row may not be deleted so the removal button is not in this LinearLayout.

