Getir Android application is a sample implementation of Getir app for Getir Hackathon 2016.

It is implemented with a simple MVC architecture. There are controllers providing the data model to
view connect the user interface. Any changes to the controller are transparent to view and
UI changes will not affect the business logic and vice versa.

This project uses:

1) Support Libraries
    AppCompat
    Design
    RecyclerView

2) Butterknife as a View injection library
3) Icepick for Instance State Management
4) Retrofit 2 and OkHTTP3 for HttpStack and Network
5) AppMsg for toast messages
6) MaterialDialogs for progress dialogs and custom dialogs
7) Parceler for Auto Parcelable implementation
8) FragmentArgs for Activity->Fragment communication without reflection