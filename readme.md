# FloMenu
FloMenu is a compact Kotlin Library that aims to recreate the floating action button menus seen in Google Inbox and Google Mail.

## Getting Started

### Jitpack URL

[![Release](https://jitpack.io/v/oliverjanoschek/FloMenu.svg)](https://jitpack.io/#oliverjanoschek/FloMenu)

### Installing

If you use gradle, just add these lines to your build.gradle:

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

and:

```gradle
dependencies {
    implementation 'com.github.jitpack:android-example:1.0.1'
}
```

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Usage

#### Adding the base menu

To add the base menu containing the main FAB and a background that fades in & out when the menu is toggled, just add these lines to your layout XML

```XML
    <com.oliverjanoschek.flomenu.components.FloMenu
        android:id="@+id/FM"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="false"
        android:clipChildren="false"
        android:clipToPadding="false">
    </com.oliverjanoschek.flomenu.components.FloMenu>
```
#### Populating the menu with sub menu FABs

The simplest way to add sub menu items to FloMenu is to use the FloSubMenu component as such:

```XML
    <com.oliverjanoschek.flomenu.components.FloMenu
        android:id="@+id/FM"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="false"
        android:clipChildren="false"
        android:clipToPadding="false">
        
        <com.oliverjanoschek.flomenu.components.FloSubMenu
            android:id="@+id/FSM_0"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="false"
            android:clipChildren="false"
            android:clipToPadding="false"
            android:focusable="true />
            
    </com.oliverjanoschek.flomenu.components.FloMenu>
```

#### Custom attributes

Both components offer a few custom attributes to style and customise your menu:

```XML
        <com.oliverjanoschek.flomenu.components.FloMenu
        android:id="@+id/FM"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="false"
        android:clipChildren="false"
        android:clipToPadding="false"
        app:M_color_button_background="@color/colorAccent"
        app:M_color_button_background_ripple="@color/colorAccentPressed"
        app:M_color_label_background="@color/colorTextLabelBackground"
        app:M_color_label_background_pressed="@color/colorTextLabelBackgroundPressed"
        app:M_color_text_label="@color/colorTextLabel"
        app:M_color_text_label_pressed="@color/colorTextLabelPressed"
        app:M_drawable="@drawable/ic_action_default"
        app:M_drawable_toggled="@drawable/ic_action_add"
        app:M_text_label="FloMenu is awesome">

        <com.oliverjanoschek.flomenu.components.FloSubMenu
            android:id="@+id/FSM_0"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="false"
            android:clipChildren="false"
            android:clipToPadding="false"
            android:focusable="true"
            app:S_color_button_background="@color/colorPrimaryDark"
            app:S_color_button_background_ripple="@color/colorButtonRippleLight"
            app:S_color_label_background="@color/colorTextLabelBackground"
            app:S_color_label_background_pressed="@color/colorTextLabelBackgroundPressed"
            app:S_color_text_label="@color/colorTextLabel"
            app:S_color_text_label_pressed="@color/colorTextLabelPressed"
            app:S_drawable="?attr/actionModeCutDrawable"
            app:S_text_label="@android:string/cut" />
            
    </com.oliverjanoschek.flomenu.components.FloMenu>
```
see the example here to get a more indepth overview: [Example XML Layout](https://github.com/oliverjanoschek/FloMenu/blob/master/example/src/main/res/layout/activity_main.xml)

#### OnClickListeners

This library uses a lambda function to make it easy to set click listeners for all FABs in the menu. Here's an example how to use them:

```Kotlin
       FM.createClickListener(FM.rootButton, {
            when (FM.toggle) {
                true -> { /* Do stuff */ }
                false -> {/* Do even more stuff! */ }
            }
        })

        FM.createClickListener(FSM_0, {
          /* Do submenu stuff */
        })
        FM.createClickListener(FSM_1, {
          /* Do submenu stuff */
        })
        FM.createClickListener(FSM_2, {
          /* Do submenu stuff */
        })
```
Both rootButton and toggle are public properties and enable easy access to the root FAB button as well as the current state of the menu

## Built With

* [Android Studio](https://developer.android.com/studio/index.html) - IDE
* [Gradle](https://gradle.org/) - Build tool

## Authors

* **Oliver Janoschek** - *Initial work* - [oliverjanoschek](https://github.com/oliverjanoschek)

See also the list of [contributors](https://github.com/oliverjanoschek/FloMenu/contributors) who participated in this project.

## License

This project is licensed under the GPL v3.0 License - see the [LICENSE.md](LICENSE.md) file for details