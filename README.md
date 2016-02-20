Getir Android application is a sample implementation of Getir app for Getir Hackathon 2016.

It is implemented with a simple MVC architecture. There are controllers providing the data model to
view connect the user interface. Any changes to the controller are transparent to view and
UI changes will not affect the business logic and vice versa.

This project uses:

1) Support Libraries</br>
    AppCompat</br>
    Design</br>
    RecyclerView</br>
    CardView</br>

2) Butterknife as a View injection library </br>
3) Icepick for Instance State Management </br>
4) Retrofit 2 and OkHTTP3 for HttpStack and Network </br>
5) Parceler for Auto Parcelable implementation </br>
6) FragmentArgs for Activity->Fragment communication without reflection </br>
7) AppMsg for toast messages </br>
8) MaterialDialogs for progress dialogs and custom dialogs </br>
9) SlidingUpPanel for sliding up layout
