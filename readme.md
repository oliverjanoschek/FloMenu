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
    implementation 'com.github.jitpack:android-example:1.1.0'
}
```

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## Usage

### Adding the base menu

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
### Populating the menu with sub menu FABs

There are two very straightforward, simple ways to add sub menu FABs to FloMenu. You can either choose to add submenu items in your layout XMLs and add onClickListeners afterwards, or simply add them in one step in code. 

This library uses[lambda functions](https://kotlinlang.org/docs/reference/lambdas.html)to make it easy to set click listeners for all FABs in the menu.

#### via XML

##### 1. To add sub menu items to FloMenu via XML add the FloSubMenu custom view like this:

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
        android:focusable="true" />
        
</com.oliverjanoschek.flomenu.components.FloMenu>
```
##### 2. Add OnClickListeners

If you chose to add the sub menu items via layout XMLs, you will need to apply a onClickListener. FloMenu provides an efficient[lambda style](https://kotlinlang.org/docs/reference/lambdas.html)function to pass on the listener

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

### Custom attributes

FloMenu's custom components offer a few attributes to style and customise your menu:

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

see the example here to get a more in-depth overview: [Example XML Layout](https://github.com/oliverjanoschek/FloMenu/blob/master/example/src/main/res/layout/activity_main.xml)

#### Via code

##### 1. Create a List of type \<SubMenuProperties\> with all your needed sub menu FABs
```Kotlin
val fsmList = listOf(
    SubMenuProperties(
        R.drawable.abc_ic_menu_cut_mtrl_alpha,
        R.color.colorAccent,
        R.color.colorButtonRippleLight,
        resources.getString(android.R.string.cut),
        R.color.colorTextLabel,
        R.color.colorTextLabelPressed,
        R.color.colorTextLabelBackground,
        R.color.colorTextLabelBackgroundPressed,
        {
            //In-Line lambda function
            toast("SUB MENU 1 CLICKED!!!")
        }),
    SubMenuProperties(
        R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
        R.color.colorPrimary,
        R.color.colorButtonRippleDark,
        resources.getString(android.R.string.copy),
        R.color.colorTextLabel,
        R.color.colorTextLabelPressed,
        R.color.colorTextLabelBackground,
        R.color.colorTextLabelBackgroundPressed,
        {
            //In-Line lambda function
            toast("SUB MENU 2 CLICKED!!!")
        }),
    SubMenuProperties(
        R.drawable.abc_ic_menu_paste_mtrl_am_alpha,
        R.color.colorAccentPressed,
        R.color.colorAccent,
        resources.getString(android.R.string.paste),
        R.color.colorTextLabelBackground,
        R.color.colorTextLabelBackgroundPressed,
        R.color.colorTextLabel,
        R.color.colorTextLabelPressed,
        //call to external function passing it as a lambda
        OnSubMenu3Clicked()
    )
)
```

#### 2. Pass the list to FloMenu's createSubMenu() method

```Kotlin
    FM.createSubMenu(fsmList)
```

#### 3. Setting values in runtime

To set the individual properties of the Menu Root FAB use these simple methods

* Icons:

```Kotlin
    FM.setRootButtonIcons(R.drawable.ic_action_default, R.drawable.ic_action_add)
```
* FAB Background Color:
```Kotlin
    FM.setRootButtonColors(
        R.color.colorAccent,
        R.color.colorButtonRippleLight)
```
* Label Text:
```Kotlin
    FM.setRootButtonText("FloMenu is awesome")
```
* Label Text and label background color:
```Kotlin
    FM.setRootButtonLabelColors(
        R.color.colorTextLabel,
        R.color.colorTextLabelBackground,
        R.color.colorTextLabelPressed,
        R.color.colorTextLabelBackgroundPressed)
```
* Menu background color:
```Kotlin
    FM.setMenuBGColor(R.color.colorAccent)

```
* Menu background alpha: 
```Kotlin
    FM.setMenuBGAlpha(0.3f)
```

## Built With

* [Android Studio](https://developer.android.com/studio/index.html) - IDE
* [Gradle](https://gradle.org/) - Build tool

## Authors

* **Oliver Janoschek** - *Initial work* - [oliverjanoschek](https://github.com/oliverjanoschek)

See also the list of [contributors](https://github.com/oliverjanoschek/FloMenu/contributors) who participated in this project.

## License

This project is licensed under the GPL v3.0 License - see the [license.md](license.md) file for details
