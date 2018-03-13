# Merakly

#### *Merakly is a advertising library which is based on surveys and written in native Android.*

## Getting Started

### Installation with Gradle

In your module level gradle file, in android block add our maven url link to repositories block.

```gradle
android {
  ...
    repositories { maven { url "http://dl.bintray.com/solidict/maven" } }
}
```

Then you can add Merakly SDK to your project by adding gradle depedencies our path in same module level gradle.

```gradle
android {
  ...
        compile 'com.solidict.merakly:meraklysdk:1.0.2'
}
```


## Usage

### Initalization

First you have to configure Merakly with your API Key and your App Secret in your corresponding Activity or Fragment class. 

```java
import com.solidict.meraklysdk.MeraklyAdBanner;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner = (MeraklyAdBanner) findViewById(R.id.bnr_test);
        banner.setCredentials(YOUR_API_KEY, YOUR_SECRET_KEY);
    }

```

### Using `MeraklyAdBanner`

`MeraklyAdBanner` is a subclass of `RelativeLayout` and only component you need to use for showing beatiful survey based ads. There is two way to use `MeraklyAdBanner` in your applications.

#### 1) Using XML file

You can directly add MeraklyAdBanner via xml to layouts.

```xml
 <com.solidict.meraklysdk.MeraklyAdBanner
        android:id="@+id/bnr_test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"/>
```

After adding view to your layout, you can start ad loading by setting credentials.
```java
import com.solidict.meraklysdk.MeraklyAdBanner;

 private MeraklyAdBanner mAdBanner;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdBanner = (MeraklyAdBanner) findViewById(R.id.bnr_test);
        mAdBanner.setCredentials(YOUR_API_KEY, YOUR_SECRET_KEY);
        //there is no need to initialize loading adds banner view automatically starts with ads after getting credentials.
    }

```

After that point, view loads ads survey and other content provided by service.

#### 1) Programmaticaly

If you want create banner programatically inside java code, you can achieve this like the code snippet below.
```java
import com.solidict.meraklysdk.MeraklyAdBanner;

 private MeraklyAdBanner mAdBanner;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdBanner = new MeraklyAdBanner(this);
        /*
          Add banner to the layout.
          */
          
        mAdBanner.setCredentials(YOUR_API_KEY, YOUR_SECRET_KEY);
        //there is no need to initialize loading adds banner view automatically starts with ads after getting credentials.
    }

```



## Advanced Usage

When you add `MeraklyAdBanner` into your `Activity` or `Fragment`, you really don't worry about the rest. However if you want to take an action according to events fired by `MeraklyAdBanner` ,you can implement `MeraklyAdListener` in your code. The method and explanations can be seen below.

```java
void onNoCampaignToShow() //Fires when there is no campaign to show. You can remove banner view when this event fired.
```
```java
void onCampaignLoaded() //Fires when a campaign loaded and showed to user.
```
```java
void onCampaignSkipped() //Fires when user skipped the campaign.
```
```java
void onCloseBannerClicked() // Fires when user wants to close banner view. This action is available if banner view is closable.
```
```java
void onAdLoaded() // Fires when ad loaded and showed to user in after answering the question in banner view.
```
```java
void onSurveyStarted() // Fires when survey started.
```
```java
void onSurveyEnded // Fires when user completed the survey.
```
